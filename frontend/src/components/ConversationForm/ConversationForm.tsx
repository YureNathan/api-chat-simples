import { useState, useEffect } from 'react';
import { IoMdRefresh } from 'react-icons/io';
import { userService } from '../../services/api';
import type { ConversationRequest, User } from '../../types';
import './ConversationForm.css';

interface ConversationFormProps {
  currentUserId: number;
  onCreateConversation: (data: ConversationRequest) => Promise<void>;
  onUsersUpdated?: () => void;
}

function ConversationForm({ currentUserId, onCreateConversation, onUsersUpdated }: ConversationFormProps) {
  const [idReceiver, setIdReceiver] = useState('');
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(false);
  const [loadingUsers, setLoadingUsers] = useState(true);

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    try {
      setLoadingUsers(true);
      const allUsers = await userService.listAll();
      // Filtrar o usuário atual da lista
      const otherUsers = allUsers.filter(user => user.id !== currentUserId);
      setUsers(otherUsers);
      if (onUsersUpdated) {
        onUsersUpdated();
      }
    } catch (err) {
      console.error('Erro ao carregar usuários:', err);
    } finally {
      setLoadingUsers(false);
    }
  };

  const handleRefreshUsers = () => {
    loadUsers();
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!idReceiver) {
      alert('Por favor, selecione um destinatário');
      return;
    }

    if (parseInt(idReceiver) === currentUserId) {
      alert('Você não pode criar uma conversa consigo mesmo');
      return;
    }

    try {
      setLoading(true);
      await onCreateConversation({
        idSender: currentUserId,
        idReceiver: parseInt(idReceiver),
      });
      // Limpar formulário após criação bem-sucedida
      setIdReceiver('');
    } catch (err) {
      console.error('Erro ao criar conversa:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className="conversation-form" onSubmit={handleSubmit}>
      <div className="conversation-form-header">
        <h3>Nova Conversa</h3>
        <button 
          type="button"
          className="refresh-users-btn"
          onClick={handleRefreshUsers}
          title="Atualizar lista de destinatários"
          disabled={loadingUsers}
        >
          <IoMdRefresh />
        </button>
      </div>
      <div className="form-group">
        <label htmlFor="idReceiver">Selecione o Destinatário:</label>
        <div className="select-with-refresh">
          <select
            id="idReceiver"
            value={idReceiver}
            onChange={(e) => setIdReceiver(e.target.value)}
            disabled={loading || loadingUsers}
            required
          >
            <option value="">Selecione um usuário...</option>
            {users.map((user) => (
              <option key={user.id} value={user.id}>
                {user.name}
              </option>
            ))}
          </select>
        </div>
        {loadingUsers && <small className="loading-text">Carregando usuários...</small>}
      </div>
      <button type="submit" disabled={loading || !idReceiver || loadingUsers}>
        {loading ? 'Criando...' : 'Criar Conversa'}
      </button>
    </form>
  );
}

export default ConversationForm;

