import { useState, useEffect, useCallback } from 'react';
import { IoMdChatbubbles, IoMdPerson, IoMdRefresh, IoMdGlobe } from 'react-icons/io';
import { conversationService, messageService } from '../../services/api';
import type { Conversation, Message, ConversationRequest, MessageRequest, User } from '../../types';
import ConversationList from '../ConversationList/ConversationList';
import ConversationForm from '../ConversationForm/ConversationForm';
import MessageList from '../MessageList/MessageList';
import MessageForm from '../MessageForm/MessageForm';
import './ChatInterface.css';

interface ChatInterfaceProps {
  currentUser: User;
}

function ChatInterface({ currentUser }: ChatInterfaceProps) {
  const [allConversations, setAllConversations] = useState<Conversation[]>([]);
  const [conversations, setConversations] = useState<Conversation[]>([]);
  const [selectedConversation, setSelectedConversation] = useState<Conversation | null>(null);
  const [messages, setMessages] = useState<Message[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [showAllConversations, setShowAllConversations] = useState(false);

  // Carregar conversas ao montar o componente
  useEffect(() => {
    loadConversations();
  }, []);

  // Carregar mensagens quando uma conversa é selecionada
  useEffect(() => {
    if (selectedConversation) {
      loadMessages(selectedConversation.id);
    } else {
      setMessages([]);
    }
  }, [selectedConversation]);

  const loadConversations = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await conversationService.listAll();
      setAllConversations(data);
      
      // Se não estiver mostrando todas, filtrar apenas conversas com mensagens do usuário
      if (!showAllConversations) {
        await filterMyConversations(data);
      } else {
        setConversations(data);
      }
    } catch (err) {
      setError('Erro ao carregar conversas');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const filterMyConversations = useCallback(async (allConvs: Conversation[]) => {
    try {
      // Buscar todas as mensagens do servidor
      const allMessages = await messageService.listAll();
      
      // Filtrar mensagens do usuário atual
      const myMessages = allMessages.filter(msg => msg.senderId === currentUser.id);
      
      // Extrair IDs únicos de conversas onde o usuário enviou mensagens
      const conversationIdsWithMyMessages = new Set(
        myMessages.map(msg => msg.conversationId)
      );
      
      // Filtrar conversas que têm mensagens do usuário
      const myConversations = allConvs.filter(conv => 
        conversationIdsWithMyMessages.has(conv.id)
      );
      
      setConversations(myConversations);
    } catch (err) {
      console.error('Erro ao filtrar minhas conversas:', err);
      // Em caso de erro, mostrar todas as conversas
      setConversations(allConvs);
    }
  }, [currentUser.id]);

  const handleToggleView = async () => {
    const newShowAll = !showAllConversations;
    setShowAllConversations(newShowAll);
    
    if (newShowAll) {
      // Mostrar todas as conversas
      setConversations(allConversations);
    } else {
      // Filtrar apenas conversas com mensagens do usuário
      await filterMyConversations(allConversations);
    }
  };

  const loadMessages = async (conversationId: number) => {
    try {
      setLoading(true);
      setError(null);
      const data = await messageService.listByConversation(conversationId);
      setMessages(data);
    } catch (err) {
      setError('Erro ao carregar mensagens');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateConversation = async (data: ConversationRequest) => {
    try {
      setError(null);
      const newConversation = await conversationService.create(data);
      setAllConversations(prev => [...prev, newConversation]);
      
      // Se estiver mostrando todas as conversas, adicionar à lista
      if (showAllConversations) {
        setConversations(prev => [...prev, newConversation]);
      }
      // Se estiver mostrando apenas minhas conversas, a conversa nova será mostrada após enviar mensagem
      
      setSelectedConversation(newConversation);
    } catch (err) {
      setError('Erro ao criar conversa');
      console.error(err);
      throw err;
    }
  };

  const handleSendMessage = async (data: MessageRequest) => {
    try {
      setError(null);
      const newMessage = await messageService.send(data);
      setMessages(prev => [...prev, newMessage]);
      // Recarregar conversas para atualizar a ordem
      await loadConversations();
      
      // Se estiver mostrando apenas minhas conversas, garantir que a conversa atual está na lista
      if (!showAllConversations && selectedConversation) {
        const convExists = conversations.some(c => c.id === selectedConversation.id);
        if (!convExists) {
          // Adicionar a conversa atual à lista se não estiver presente
          setConversations(prev => [...prev, selectedConversation]);
        }
      }
    } catch (err) {
      setError('Erro ao enviar mensagem');
      console.error(err);
      throw err;
    }
  };

  const handleSelectConversation = (conversation: Conversation) => {
    setSelectedConversation(conversation);
  };

  return (
    <div className="chat-interface">
      <div className="chat-sidebar">
        <div className="sidebar-header">
          <h2><IoMdChatbubbles /> Line Chat</h2>
          <p className="current-user-name"><IoMdPerson /> {currentUser.name}</p>
        </div>
        
        <div className="conversation-form-container">
          <ConversationForm 
            currentUserId={currentUser.id}
            onCreateConversation={handleCreateConversation} 
          />
        </div>

        <div className="conversations-container">
          <div className="conversations-header">
            <h3>{showAllConversations ? 'Todas as Conversas' : 'Minhas Conversas'}</h3>
            <div className="conversations-header-buttons">
              <button 
                className="toggle-view-btn"
                onClick={handleToggleView}
                title={showAllConversations ? 'Mostrar apenas minhas conversas' : 'Mostrar todas as conversas do servidor'}
              >
                {showAllConversations ? <IoMdPerson /> : <IoMdGlobe />}
              </button>
              <button 
                className="refresh-conversations-btn"
                onClick={loadConversations}
                title="Atualizar conversas"
                disabled={loading}
              >
                <IoMdRefresh />
              </button>
            </div>
          </div>
          {loading && conversations.length === 0 && (
            <div className="loading">Carregando...</div>
          )}
          {error && <div className="error">{error}</div>}
          <ConversationList
            conversations={conversations}
            selectedConversation={selectedConversation}
            currentUserId={currentUser.id}
            onSelectConversation={handleSelectConversation}
          />
        </div>
      </div>

      <div className="chat-main">
        {selectedConversation ? (
          <>
            <div className="chat-header">
              <h3>
                {currentUser.id === selectedConversation.senderId 
                  ? selectedConversation.nameReceiver 
                  : selectedConversation.nameSender}
              </h3>
              <button 
                className="refresh-btn"
                onClick={() => loadMessages(selectedConversation.id)}
                title="Atualizar mensagens"
              >
                <IoMdRefresh />
              </button>
            </div>
            
            <MessageList 
              messages={messages} 
              currentSenderId={currentUser.id}
              loading={loading}
            />
            
            <MessageForm
              conversation={selectedConversation}
              currentUserId={currentUser.id}
              onSendMessage={handleSendMessage}
            />
          </>
        ) : (
          <div className="chat-placeholder">
            <div className="placeholder-content">
              <h2><IoMdChatbubbles /></h2>
              <p>Selecione uma conversa ou crie uma nova para começar a conversar</p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default ChatInterface;

