import type { Conversation } from '../../types';
import './ConversationList.css';

interface ConversationListProps {
  conversations: Conversation[];
  selectedConversation: Conversation | null;
  currentUserId: number;
  onSelectConversation: (conversation: Conversation) => void;
}

function ConversationList({ conversations, selectedConversation, currentUserId, onSelectConversation }: ConversationListProps) {
  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    const now = new Date();
    const diffInHours = (now.getTime() - date.getTime()) / (1000 * 60 * 60);

    if (diffInHours < 24) {
      return date.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
    } else {
      return date.toLocaleDateString('pt-BR', { day: '2-digit', month: '2-digit' });
    }
  };

  if (conversations.length === 0) {
    return (
      <div className="empty-conversations">
        <p>Nenhuma conversa ainda. Crie uma nova conversa para começar!</p>
      </div>
    );
  }

  const getOtherParticipant = (conversation: Conversation) => {
    // Retorna o nome do outro participante (não o usuário atual)
    if (conversation.senderId === currentUserId) {
      return conversation.nameReceiver;
    } else {
      return conversation.nameSender;
    }
  };

  return (
    <div className="conversation-list">
      {conversations.map((conversation) => {
        const otherParticipant = getOtherParticipant(conversation);
        return (
          <div
            key={conversation.id}
            className={`conversation-item ${
              selectedConversation?.id === conversation.id ? 'selected' : ''
            }`}
            onClick={() => onSelectConversation(conversation)}
          >
            <div className="conversation-avatar">
              {otherParticipant.charAt(0).toUpperCase()}
            </div>
            <div className="conversation-info">
              <div className="conversation-participants">
                {otherParticipant}
              </div>
              <div className="conversation-time">
                {formatDate(conversation.created_at)}
              </div>
            </div>
          </div>
        );
      })}
    </div>
  );
}

export default ConversationList;

