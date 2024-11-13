import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

export const rtkApi = createApi({
  reducerPath: 'rtkApi',
  baseQuery: fetchBaseQuery({
    baseUrl: `${__API__}/api/v1`,
  }),
  endpoints: (builder) => ({
    // Получение всех комнат
    getAllRooms: builder.query({
      query: () => '/room/all',
    }),

    // Создание новой комнаты
    createRoom: builder.mutation({
      query: (userId) => ({
        url: '/room/create',
        method: 'POST',
        params: { userId },
      }),
    }),

    // Присоединение к комнате
    joinRoom: builder.mutation({
      query: ({ roomId, userId }) => ({
        url: `/room/${roomId}/join`,
        method: 'POST',
        params: { userId },
      }),
    }),

    // Получение состояния игры
    getGameState: builder.query({
      query: (sessionId) => `/room/game/current-state?sessionId=${sessionId}`,
    }),
  }),
});

export const {
  useGetAllRoomsQuery,
  useCreateRoomMutation,
  useJoinRoomMutation,
  useGetGameStateQuery,
} = rtkApi;
