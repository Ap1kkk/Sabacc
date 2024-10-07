import React, { memo, useEffect, useState, useCallback } from 'react';
import { Client, Message } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import cls from './GamePage.module.scss';
import CreateChat from './CreateChat';
import { useGetAllChatsQuery, useLazyGetChatHistoryQuery } from '@/features/Chat/model/services/chatService';
import { Chat } from '@/features/Chat/model/types/chat';
import { Message as IMessage } from '@/features/Chat/model/types/message';
import { useAppDispatch } from '@/shared/lib/hooks/useAppDispatch';
import { useSelector } from 'react-redux';
import { selectCurrentUser } from '@/features/Auth/model/selectors/selectCurrentUser';

export const GamePage = memo(() => {
  const [client, setClient] = useState<Client | null>(null); // STOMP клиент
  const [messages, setMessages] = useState<IMessage[]>([]); // Хранение сообщений
  const [newMessage, setNewMessage] = useState(''); // Текст нового сообщения
  const { data: chats = [], isLoading: isLoadingChats } = useGetAllChatsQuery(); // Запрос всех чатов
  const [currentChat, setCurrentChat] = useState<Chat | null>(null); // Текущий выбранный чат
  const user = useSelector(selectCurrentUser)

  // Ленивый запрос для получения истории сообщений
  const [triggerGetChatHistory, { data: chatHistory }] = useLazyGetChatHistoryQuery();

  // Обновляем историю сообщений при выборе нового чата
  useEffect(() => {
    if (currentChat?.id) {
      // Выполняем запрос истории для выбранного чата
      triggerGetChatHistory({ roomId: `${currentChat.id}` });
    }
  }, [currentChat, triggerGetChatHistory]);

  // Устанавливаем соединение и подписываемся на WebSocket, если выбран текущий чат
  useEffect(() => {
    if (!currentChat) return;

    const socket = new SockJS('http://localhost:8080/ws');
    const stompClient = new Client({
      webSocketFactory: () => socket,
      onConnect: () => handleConnect(stompClient),
      onStompError: (error) => handleError(error),
    });

    stompClient.activate();
    setClient(stompClient);

    return () => {
      if (stompClient.active) {
        console.log('Отключение WebSocket клиента');
        stompClient.deactivate();
      }
    };
  }, [currentChat]);

  // Обработчик подключения WebSocket
  const handleConnect = useCallback(
    (stompClient: Client) => {
      console.log(`Соединение установлено. Подписка на чат с ID: ${currentChat?.id}`);
      if (!currentChat?.id) return;

      // Подписываемся на топик конкретного чата
      const subscription = stompClient.subscribe(`/topic/messages/${currentChat.id}`, (message: Message) => {
        try {
          const chatMessage: IMessage = JSON.parse(message.body);
          console.log('Новое сообщение из WebSocket:', chatMessage);
          console.log(messages)
          // @ts-ignore
          setMessages((prevMessages) => [...prevMessages, chatMessage]);
        } catch (error) {
          console.error('Ошибка при разборе сообщения:', error);
        }
      });

      // Возврат функции отписки при размонтировании компонента
      return () => {
        console.log('Отписка от WebSocket топика');
        subscription.unsubscribe();
      };
    },
    [currentChat]
  );

  // Обработчик ошибок WebSocket
  const handleError = (error: any) => {
    console.error(`Ошибка STOMP: ${error.headers.message}`);
  };

  // Обработчик выбора текущего чата
  const handleSetChat = (item: Chat) => {
    console.log('Выбранный чат:', item);
    setMessages([]); // Очищаем сообщения при переключении чата
    setCurrentChat(item);
  };

  // Функция отправки нового сообщения
  const handleSendMessage = () => {
    if (!client || !newMessage.trim() || !currentChat) {
      console.warn('Сообщение не может быть отправлено: Проверьте соединение и выбранный чат');
      return;
    }
    const messageToSend = {
      chatRoomId: currentChat.id,
      senderName: user?.username, // Имя отправителя
      content: newMessage,
      sentAt: new Date()
    };

    console.log(`Отправка сообщения в /app/chat/${currentChat.id}:`, messageToSend);

    // Публикуем сообщение в текущий чат
    client.publish({ destination: `/app/chat`, body: JSON.stringify(messageToSend) });

    // Добавляем сообщение локально
    // @ts-ignore
    setMessages((prevMessages) => [...prevMessages, messageToSend]);
    setNewMessage(''); // Очищаем поле ввода
  };

  // Рендер списка чатов
  const renderChatList = () => {
    if (isLoadingChats) return <p>Загрузка чатов...</p>;
    if (chats.length === 0) return <p>Чаты не найдены. Создайте новый чат.</p>;

    return (
      <div className={cls.chatList}>
        {chats.map((chat) => (
          <div
            className={`${cls.chatItem} ${currentChat?.id === chat.id ? cls.activeChat : ''}`}
            key={chat.id}
            onClick={() => handleSetChat(chat)}
          >
            {`Чат #${chat.id}`}
          </div>
        ))}
      </div>
    );
  };

  return (
    <div className={cls.container}>
      <h1>Чат</h1>
      {/* Компонент для создания нового чата */}
      <CreateChat />

      {/* Список чатов */}
      <h1>Мои чаты</h1>
      {renderChatList()}

      {/* Объединенный список сообщений */}
      <div className={cls.messageContainer}>
        {[...(chatHistory || []), ...messages] // Объединяем `chatHistory` и `messages`
          .sort((a, b) => new Date(a.sentAt).getTime() - new Date(b.sentAt).getTime()) // Сортируем по дате
          .map((msg, index) => (
            <div key={index} className={cls.message}>
              <strong>{msg.senderName}:</strong> {msg.content}{' '}
              <span className={cls.timestamp}>{new Date(msg.sentAt).toLocaleTimeString()}</span>
            </div>
          ))}
      </div>

      {/* Поле для ввода нового сообщения и кнопка отправки */}
      {currentChat && (
        <div className={cls.messageInputContainer}>
          <input
            type="text"
            value={newMessage}
            onChange={(e) => setNewMessage(e.target.value)}
            placeholder="Введите сообщение"
            className={cls.messageInput}
          />
          <button onClick={handleSendMessage} className={cls.sendButton}>
            Отправить
          </button>
        </div>
      )}
    </div>
  );
});

export default GamePage;
