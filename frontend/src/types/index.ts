export interface Conversation {
  id: number;
  senderId: number;
  nameSender: string;
  receiverId: number;
  nameReceiver: string;
  created_at: string;
}

export interface ConversationRequest {
  idSender: number;
  idReceiver: number;
}

export interface Message {
  conversationId: number;
  idMensage: number;
  senderId: number;
  recepientId: number;
  senderName: string;
  recipientName: string;
  content: string;
  sendAt: string;
}

export interface MessageRequest {
  conversationId: number;
  senderId: number;
  recipientId: number;
  content: string;
}

export interface User {
  id: number;
  name: string;
}

export interface UserRequest {
  name: string;
}

