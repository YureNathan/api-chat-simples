import { useState } from 'react';
import { IoHandLeft } from 'react-icons/io5';
import { userService } from '../../services/api';
import type { User, UserRequest } from '../../types';
import './UserModal.css';

interface UserModalProps {
  onUserCreated: (user: User) => void;
}

function UserModal({ onUserCreated }: UserModalProps) {
  const [name, setName] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!name.trim()) {
      setError('Por favor, digite seu nome');
      return;
    }

    try {
      setLoading(true);
      setError(null);
      const user = await userService.create({ name: name.trim() });
      onUserCreated(user);
    } catch (err) {
      setError('Erro ao criar usuário. Tente novamente.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="user-modal-overlay">
      <div className="user-modal">
        <div className="user-modal-header">
          <h2><IoHandLeft /> Bem-vindo ao Chat Simples</h2>
          <p>Para começar, informe seu nome</p>
        </div>
        
        <form className="user-modal-form" onSubmit={handleSubmit}>
          {error && <div className="user-modal-error">{error}</div>}
          
          <div className="user-modal-input-group">
            <label htmlFor="userName">Seu Nome:</label>
            <input
              id="userName"
              type="text"
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="Digite seu nome..."
              disabled={loading}
              autoFocus
            />
          </div>

          <button 
            type="submit" 
            className="user-modal-button"
            disabled={loading || !name.trim()}
          >
            {loading ? 'Salvando...' : 'Começar a Conversar'}
          </button>
        </form>
      </div>
    </div>
  );
}

export default UserModal;

