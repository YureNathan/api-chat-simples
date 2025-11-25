import axios from 'axios';
import type { Conversation, ConversationRequest, Message, MessageRequest, User, UserRequest } from '../types';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

export const conversationService = {
  listAll: async (): Promise<Conversation[]> => {
    const response = await api.get<Conversation[]>('/conversation');
    return response.data;
  },

  create: async (data: ConversationRequest): Promise<Conversation> => {
    const response = await api.post<Conversation>('/conversation', data);
    return response.data;
  },
};

export const messageService = {
  listByConversation: async (conversationId: number): Promise<Message[]> => {
    const response = await api.get<Message[]>(`/mensage/${conversationId}/mensages`);
    return response.data;
  },

  listAll: async (): Promise<Message[]> => {
    const response = await api.get<Message[]>('/mensage/list-mensages');
    return response.data;
  },

  send: async (data: MessageRequest): Promise<Message> => {
    const response = await api.post<Message>('/mensage', data);
    return response.data;
  },
};

export const userService = {
  listAll: async (): Promise<User[]> => {
    const response = await api.get<User[]>('/users');
    return response.data;
  },

  create: async (data: UserRequest): Promise<User> => {
    const response = await api.post<User>('/users', { name: data.name });
    return response.data;
  },

  getById: async (id: number): Promise<User> => {
    const response = await api.get<User>(`/users/${id}`);
    return response.data;
  },
};

export default api;

