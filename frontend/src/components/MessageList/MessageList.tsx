import { useEffect, useRef } from 'react';
import type { Message } from '../../types';
import './MessageList.css';

interface MessageListProps {
  messages: Message[];
  currentSenderId: number;
  loading: boolean;
}

function MessageList({ messages, currentSenderId, loading }: MessageListProps) {
  const messagesEndRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    const now = new Date();
    const diffInMinutes = (now.getTime() - date.getTime()) / (1000 * 60);

    if (diffInMinutes < 1) {
      return 'Agora';
    } else if (diffInMinutes < 60) {
      return `${Math.floor(diffInMinutes)}m atrás`;
    } else if (diffInMinutes < 1440) {
      return `${Math.floor(diffInMinutes / 60)}h atrás`;
    } else {
      return date.toLocaleDateString('pt-BR', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
      });
    }
  };

  if (loading && messages.length === 0) {
    return (
      <div className="message-list loading">
        <div className="loading-spinner">Carregando mensagens...</div>
      </div>
    );
  }

  if (messages.length === 0) {
    return (
      <div className="message-list empty">
        <div className="empty-messages">
          <p>Nenhuma mensagem ainda. Envie a primeira mensagem!</p>
        </div>
      </div>
    );
  }

  return (
    <div className="message-list">
      {messages.map((message) => {
        const isSentByCurrentUser = message.senderId === currentSenderId;
        
        return (
          <div
            key={message.idMensage}
            className={`message ${isSentByCurrentUser ? 'sent' : 'received'}`}
          >
            <div className="message-content">
              <div className="message-header">
                <span className="message-sender">
                  {isSentByCurrentUser ? 'Você' : message.senderName}
                </span>
                <span className="message-time">{formatDate(message.sendAt)}</span>
              </div>
              <div className="message-text">{message.content}</div>
            </div>
          </div>
        );
      })}
      <div ref={messagesEndRef} />
    </div>
  );
}

export default MessageList;

