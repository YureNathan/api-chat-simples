# Chat Simples - Frontend React

Interface React moderna para consumir a API de Chat Simples.

## 🚀 Funcionalidades

- ✅ Listar todas as conversas
- ✅ Criar novas conversas
- ✅ Enviar mensagens
- ✅ Listar mensagens de uma conversa específica
- ✅ Interface moderna e responsiva
- ✅ Design tipo WhatsApp/Messenger

## 📋 Pré-requisitos

- Node.js 18+ instalado
- Backend da API rodando em `http://localhost:8080`

## 🛠️ Instalação

1. Instale as dependências:

```bash
npm install
```

## ▶️ Executar

Para iniciar o servidor de desenvolvimento:

```bash
npm run dev
```

A aplicação estará disponível em `http://localhost:5173`

## 📦 Build para Produção

```bash
npm run build
```

O build será gerado na pasta `dist/`

## 🎨 Estrutura do Projeto

```
frontend/
├── src/
│   ├── components/          # Componentes React
│   │   ├── ChatInterface.tsx
│   │   ├── ConversationList.tsx
│   │   ├── ConversationForm.tsx
│   │   ├── MessageList.tsx
│   │   └── MessageForm.tsx
│   ├── services/           # Serviços de API
│   │   └── api.ts
│   ├── types/              # Tipos TypeScript
│   │   └── index.ts
│   ├── App.tsx
│   ├── main.tsx
│   └── index.css
├── package.json
├── tsconfig.json
└── vite.config.ts
```

## 🔌 Endpoints Utilizados

- `GET /conversation` - Lista todas as conversas
- `POST /conversation` - Cria uma nova conversa
- `GET /mensage/{id}/mensages` - Lista mensagens de uma conversa
- `POST /mensage` - Envia uma mensagem

## 💡 Como Usar

1. **Criar uma Conversa:**
   - Preencha os IDs do remetente e destinatário no formulário
   - Clique em "Criar Conversa"

2. **Visualizar Mensagens:**
   - Clique em uma conversa na lista lateral
   - As mensagens serão carregadas automaticamente

3. **Enviar Mensagem:**
   - Selecione uma conversa
   - Digite sua mensagem no campo de texto
   - Pressione Enter ou clique no botão de enviar

## 🔧 Configuração

Se sua API estiver rodando em uma porta ou URL diferente, edite o arquivo `src/services/api.ts`:

```typescript
const api = axios.create({
  baseURL: 'http://sua-url:porta',
  // ...
});
```

## 📱 Responsividade

A interface é totalmente responsiva e se adapta a diferentes tamanhos de tela. Em dispositivos móveis, a lista de conversas e a área de chat alternam automaticamente.

