import { createSelector } from '@reduxjs/toolkit';
import { ChatSchema } from '../types/chatSchema';
import { StateSchema } from '@/app/providers/Store/config/StateSchema';

const selectChatState = (state: StateSchema): ChatSchema => state.chat;

export const selectAllChats = createSelector(selectChatState, (state) => state.chats);
export const selectCurrentChat = createSelector(selectChatState, (state) => state.currentChat);
export const selectChatError = createSelector(selectChatState, (state) => state.error);

export const selectAllMessages = createSelector(selectChatState, (state) => state.messages);
export const selectMessagesError = createSelector(selectChatState, (state) => state.error);
