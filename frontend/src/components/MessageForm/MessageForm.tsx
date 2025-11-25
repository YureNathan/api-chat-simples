import { useState } from 'react';
import { IoMdSend, IoMdHourglass } from 'react-icons/io';
import type { Conversation, MessageRequest } from '../../types';
import './MessageForm.css';

interface MessageFormProps {
  conversation: Conversation;
  currentUserId: number;
  onSendMessage: (data: MessageRequest) => Promise<void>;
}

function MessageForm({ conversation, currentUserId, onSendMessage }: MessageFormProps) {
  const [content, setContent] = useState('');
  const [loading, setLoading] = useState(false);

  // Determinar o destinatário baseado no usuário atual
  const getRecipientId = () => {
    // Se o usuário atual é o sender, o destinatário é o receiver, e vice-versa
    return currentUserId === conversation.senderId 
      ? conversation.receiverId 
      : conversation.senderId;
  };

  const sendMessage = async () => {
    if (!content.trim() || loading) {
      return;
    }

    try {
      setLoading(true);
      await onSendMessage({
        conversationId: conversation.id,
        senderId: currentUserId,
        recipientId: getRecipientId(),
        content: content.trim(),
      });
      setContent('');
    } catch (err) {
      console.error('Erro ao enviar mensagem:', err);
      alert('Erro ao enviar mensagem. Tente novamente.');
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await sendMessage();
  };

  const handleKeyPress = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      sendMessage();
    }
  };

  return (
    <form className="message-form" onSubmit={handleSubmit}>
      <div className="message-input-container">
        <textarea
          className="message-input"
          value={content}
          onChange={(e) => setContent(e.target.value)}
          onKeyPress={handleKeyPress}
          placeholder="Digite sua mensagem... (Enter para enviar)"
          rows={3}
          disabled={loading}
        />
        <button 
          type="submit" 
          className="send-button"
          disabled={loading || !content.trim()}
        >
          {loading ? <IoMdHourglass /> : <IoMdSend />}
        </button>
      </div>
    </form>
  );
}

export default MessageForm;

