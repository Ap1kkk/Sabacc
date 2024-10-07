import { useCreateNewChatMutation } from '@/features/Chat/model/services/chatService';
import React, { useState } from 'react';

const CreateChat = () => {
  const [createNewChat, { isLoading, error }] = useCreateNewChatMutation(); // Хук для создания нового чата
  const [chatName, setChatName] = useState(''); // Имя нового чата
  const [memberIds, setMemberIds] = useState(''); // Идентификаторы участников

  const handleCreateChat = async () => {
    // Преобразование строки участников в массив чисел
    const ids = memberIds.split(',').map((id) => parseInt(id.trim(), 10)).filter((id) => !isNaN(id));

    if (!chatName.trim() || ids.length === 0) {
      alert('Введите корректное имя чата и идентификаторы участников');
      return;
    }

    try {
      // Отправка запроса на создание нового чата
      await createNewChat({ memberIds: ids }).unwrap();
      alert('Чат успешно создан!');
      setChatName(''); // Очистка поля имени чата
      setMemberIds(''); // Очистка поля участников
    } catch (err) {
      console.error('Ошибка создания чата:', err);
    }
  };

  return (
    <div>
      <h2>Создать новый чат</h2>
      {/* Поле для имени нового чата */}
      <input
        type="text"
        value={chatName}
        onChange={(e) => setChatName(e.target.value)}
        placeholder="Введите имя чата"
        style={{ width: '300px', marginBottom: '10px', padding: '10px' }}
      />
      <br />
      {/* Поле для идентификаторов участников */}
      <input
        type="text"
        value={memberIds}
        onChange={(e) => setMemberIds(e.target.value)}
        placeholder="Введите ID участников через запятую"
        style={{ width: '300px', marginBottom: '10px', padding: '10px' }}
      />
      <br />
      {/* Кнопка для создания чата */}
      <button onClick={handleCreateChat} style={{ padding: '10px 20px' }} disabled={isLoading}>
        {isLoading ? 'Создание...' : 'Создать чат'}
      </button>
      {/* Сообщение об ошибке, если создание не удалось */}
      {error && <p style={{ color: 'red' }}>Ошибка: {error.toString()}</p>}
    </div>
  );
};

export default CreateChat;
