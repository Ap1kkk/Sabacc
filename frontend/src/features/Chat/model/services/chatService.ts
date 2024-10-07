import { rtkApi } from '@/shared/api/rtkApi';
import { Chat } from '../types/chat';
import { Message } from '../types/message'; // Определение типа сообщения

export const chatApi = rtkApi.injectEndpoints({
  endpoints: (builder) => ({
    // 1. Создание нового чата
    createNewChat: builder.mutation<Chat, { memberIds: number[] }>({
      query: (chatData) => ({
        url: '/chat/new',
        method: 'POST',
        body: chatData,
      }),
    }),

    // 2. Получение списка всех чатов
    getAllChats: builder.query<Chat[], void>({
      query: () => ({
        url: '/chat/rooms/all',
        method: 'GET',
      }),
    }),

    // 3. Получение истории сообщений в комнате чата
    getChatHistory: builder.query<Message[], { roomId: string }>({
      query: ({ roomId }) => ({
        url: `/chat/room/${roomId}/history`,
        method: 'GET',
      }),
    }),
  }),
});

// Экспортируем хуки для использования запросов в компонентах
export const { 
  useCreateNewChatMutation, 
  useGetAllChatsQuery, 
  useLazyGetChatHistoryQuery
} = chatApi;
