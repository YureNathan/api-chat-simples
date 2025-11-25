import { useState, useEffect } from 'react';
import ChatInterface from './components/ChatInterface/ChatInterface';
import UserModal from './components/UserModal/UserModal';
import type { User } from './types';
import './App.css';

const CURRENT_USER_KEY = 'chat-simples-current-user';

function App() {
  const [currentUser, setCurrentUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Tentar carregar usuário do localStorage
    const savedUser = localStorage.getItem(CURRENT_USER_KEY);
    if (savedUser) {
      try {
        setCurrentUser(JSON.parse(savedUser));
      } catch (err) {
        console.error('Erro ao carregar usuário do localStorage:', err);
        localStorage.removeItem(CURRENT_USER_KEY);
      }
    }
    setLoading(false);
  }, []);

  const handleUserCreated = (user: User) => {
    setCurrentUser(user);
    // Salvar no localStorage
    localStorage.setItem(CURRENT_USER_KEY, JSON.stringify(user));
  };

  if (loading) {
    return (
      <div className="app">
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', height: '100%' }}>
          <p>Carregando...</p>
        </div>
      </div>
    );
  }

  if (!currentUser) {
    return (
      <div className="app">
        <UserModal onUserCreated={handleUserCreated} />
      </div>
    );
  }

  return (
    <div className="app">
      <ChatInterface currentUser={currentUser} />
    </div>
  );
}

export default App;

