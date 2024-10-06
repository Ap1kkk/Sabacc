import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { Chat } from '../types/chat';
import { chatApi } from '../services/chatService';
import { Message } from '../types/message';

interface ChatState {
  chats: Chat[];
  messages: Message[];
  currentChat: Chat | null;
  error: any;
}

const initialState: ChatState = {
  chats: [],
  messages: [],
  currentChat: null,
  error: null,
};

const chatSlice = createSlice({
  name: 'chat',
  initialState,
  reducers: {
    setCurrentChat: (state, action: PayloadAction<Chat>) => {
      state.currentChat = action.payload;
    },
    clearCurrentChat: (state) => {
      state.currentChat = null;
    },
  },
  extraReducers: (builder) => {
    builder
      // Обработка загрузки списка чатов
      .addMatcher(chatApi.endpoints.getAllChats.matchFulfilled, (state, action: PayloadAction<Chat[]>) => {
        state.chats = action.payload;
        state.error = null;
      })
      .addMatcher(chatApi.endpoints.getAllChats.matchRejected, (state, action) => {
        state.error = action.error.message || 'Ошибка загрузки чатов';
      })
      // Обработка создания нового чата
      .addMatcher(chatApi.endpoints.createNewChat.matchFulfilled, (state, action: PayloadAction<Chat>) => {
        state.chats.push(action.payload);
        state.currentChat = action.payload;
        state.error = null;
      })
      .addMatcher(chatApi.endpoints.createNewChat.matchRejected, (state, action) => {
        state.error = action.error.message || 'Ошибка создания нового чата';
      })
      // Обработка загрузки истории сообщений
      .addMatcher(chatApi.endpoints.getChatHistory.matchFulfilled, (state, action: PayloadAction<Message[]>) => {
        state.messages = action.payload;
        state.error = null;
      })
      .addMatcher(chatApi.endpoints.getChatHistory.matchRejected, (state, action) => {
        state.error = action.error.message || 'Ошибка загрузки истории сообщений';
      })
  },
});

export const { setCurrentChat, clearCurrentChat } = chatSlice.actions;
export const chatReducer = chatSlice.reducer;
