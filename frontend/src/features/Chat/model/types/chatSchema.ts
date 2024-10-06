import { Chat } from "./chat";
import { Message } from "./message";

export interface ChatSchema {
  chats: Chat[];
  messages: Message[];
  currentChat: Chat | null;
  error: any;
}