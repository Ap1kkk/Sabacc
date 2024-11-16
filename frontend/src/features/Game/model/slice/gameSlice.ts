// import { createSlice, PayloadAction } from '@reduxjs/toolkit';
// import { rtkApi } from '@/shared/api/rtkApi';
// import { GameState, Room } from '../types/game'; // Замените на реальные типы, которые у вас используются
// import { AppError } from '@/shared/types/error';

// interface GameSliceState {
//   sessionId: number | null;
//   gameState: GameState | null;
//   rooms: Room[];
//   isLoading: boolean;
//   error: string | null;
// }

// const initialState: GameSliceState = {
//   sessionId: null,
//   gameState: null,
//   rooms: [],
//   isLoading: false,
//   error: null,
// };

// const gameSlice = createSlice({
//   name: 'game',
//   initialState,
//   reducers: {
//     setSessionId: (state, action: PayloadAction<number>) => {
//       state.sessionId = action.payload;
//     },
//     clearGameState: (state) => {
//       state.gameState = null;
//     },
//   },
//   extraReducers: (builder) => {
//     builder
//       // Обработка загрузки списка комнат
//       .addMatcher(rtkApi.endpoints.getAllRooms.matchFulfilled, (state, action: PayloadAction<Room[]>) => {
//         state.rooms = action.payload;
//         state.isLoading = false;
//         state.error = null;
//       })
//       .addMatcher(rtkApi.endpoints.getAllRooms.matchRejected, (state, action: PayloadAction<AppError>) => {
//         state.error = action.payload.message || 'Ошибка загрузки комнат';
//         state.isLoading = false;
//       })
//       .addMatcher(rtkApi.endpoints.getAllRooms.matchPending, (state) => {
//         state.isLoading = true;
//       })

//       // Обработка создания новой комнаты
//       .addMatcher(rtkApi.endpoints.createRoom.matchFulfilled, (state, action: PayloadAction<Room>) => {
//         state.rooms.push(action.payload);
//         state.sessionId = action.payload.id;
//         state.isLoading = false;
//         state.error = null;
//       })
//       .addMatcher(rtkApi.endpoints.createRoom.matchRejected, (state, action: PayloadAction<AppError>) => {
//         state.error = action.payload.message || 'Ошибка создания новой комнаты';
//         state.isLoading = false;
//       })
//       .addMatcher(rtkApi.endpoints.createRoom.matchPending, (state) => {
//         state.isLoading = true;
//       })

//       // Обработка присоединения к комнате
//       .addMatcher(rtkApi.endpoints.joinRoom.matchFulfilled, (state, action: PayloadAction<Room>) => {
//         state.sessionId = action.payload.id;
//         state.isLoading = false;
//         state.error = null;
//       })
//       .addMatcher(rtkApi.endpoints.joinRoom.matchRejected, (state, action: PayloadAction<AppError>) => {
//         state.error = action.payload.message || 'Ошибка присоединения к комнате';
//         state.isLoading = false;
//       })
//       .addMatcher(rtkApi.endpoints.joinRoom.matchPending, (state) => {
//         state.isLoading = true;
//       })

//       // Обработка получения состояния игры
//       .addMatcher(rtkApi.endpoints.getGameState.matchFulfilled, (state, action: PayloadAction<GameState>) => {
//         state.gameState = action.payload;
//         state.isLoading = false;
//         state.error = null;
//       })
//       .addMatcher(rtkApi.endpoints.getGameState.matchRejected, (state, action: PayloadAction<AppError>) => {
//         state.error = action.payload.message || 'Ошибка получения состояния игры';
//         state.isLoading = false;
//       })
//       .addMatcher(rtkApi.endpoints.getGameState.matchPending, (state) => {
//         state.isLoading = true;
//       });
//   },
// });

// export const { setSessionId, clearGameState } = gameSlice.actions;
// export const gameReducer = gameSlice.reducer;
