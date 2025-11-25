# 📩 API de Chat – Backend com Spring Boot
Este repositório contém o frontend e backend da aplicação chat com algumas funções, um sistema de mensagens inspirado em chats modernos, permitindo criação de conversas, envio de mensagens e gerenciamento de usuários.
A proposta deste projeto é desenvolver uma **API RESTful organizada, escalável e fácil de manter**, utilizando Spring Boot, JPA, DTOs e boas práticas de arquitetura.

---

# 🧠 Sobre o projeto

No backend tem como objetivo simular o funcionamento essencial de um sistema de chat, incluindo:

- Cadastro de usuários  
- Criação automática e única de conversas entre dois usuários 
- Envio de mensagens atreladas às conversas 
- Listagem de conversas e mensagens  
- API padronizada com DTOs 
---

# ✅ Funcionalidades da Aplicação

### 👤 Usuários
- CRUD completo de usuários  
- Ao excluir um usuário, suas conversas são apagadas para manter integridade  
- Validações via Jakarta (`@NotNull`)  
- DTOs para request e response  
---

### 💬 Conversas
- Criação de nova conversa entre dois usuários  
- A API garante que **nunca existam conversas duplicadas** entre as mesmas duas pessoas  
- Listagem total de todas as conversas  
- Cada conversa possui:
  - Remetente
  - Destinatário
  - Timestamp automático de criação da conversa
  - Lista de mensagens associadas
---
### ✉️ Mensagens
- Enviar mensagens dentro de uma conversa existente  
- A mensagem reaproveita automaticamente quem são o **sender** e o **receiver** da conversa  
- Timestamp gerado automaticamente com **`@CreationTimestamp`**  
- Listagem:
  - Todas as mensagens da aplicação
  - Todas as mensagens de uma conversa específica (ordenadas pela data)

---

# 🧱 Arquitetura da Aplicação

A API segue o padrão clássico de **Arquitetura em Camadas**, com clara separação de responsabilidades:

```
controller/
service/
repository/
entities/
dtos/
config/
```

###  Controller
Camada responsável pelas rotas REST (**`/user`, `/conversation`, `/mensage`**).

### Service
Contém as regras de negócio:
- Validações
- Conversões para DTOs
- Orquestração dos repositórios

### Repository
Interfaces Spring Data JPA utilizando:
- Queries derivadas
- JPQL customizado (ex.: buscar conversa entre dois usuários)

###  DTOs
Utilizam **Java Records**, garantindo:
- Imutabilidade  
- Simplicidade  
- Segurança entre camadas  

###  Entities
Mapeamento JPA com:
- **`@Entity`**
- Relacionamentos **(`@OneToMany`, `@ManyToOne`)**
- Auditoria temporal **(`@CreationTimestamp`)**

---

# 🗄️ Modelagem do Banco de Dados

| Tabela | Descrição |
|-------|-----------|
| **users** | Armazena usuários; referenciado por conversas e mensagens |
| **conversations** | Guarda o par remetente/destinatário e timestamp |
| **mensages** | Contém conteúdo, remetente, destinatário e timestamp |

### Relacionamentos:

- **User 1..N Conversation** (como sender ou receiver)  
- **Conversation 1..N Mensage**  
- **User 1..N Mensage** (como sender e receiver)

---

# 🛠️ Tecnologias Utilizadas

- **Java 21**  
- **Spring Boot**  
- **Spring Data JPA**  
- **Hibernate**  
- **PostgreSQL**  
- **Docker**  
- **DTOs com Java Records**  

---

# ▶️ Como Executar

### 1. Clonar repositório
```bash
git clone https://github.com/YureNathan/api-chat-simples.git
```

### 2. Instalar dependências
```bash
docker compose up -d
```

### 3. Executar a aplicação
```bash
mvn spring-boot:run
```

### 4. Testar os endpoints
Você pode usar:
- **Postman**
- **Insomnia**

---

# 🔗 Endpoints Principais

###  Criar conversa
```
POST /conversation
{
  "senderId": 1,
  "receiverId": 2
}
```

###  Listar conversas
```
GET /conversation
```

###  Enviar mensagem
```
POST /mensage
{
  "conversationId": 1,
  "content": "Olá!"
}
```

### Listar mensagens de uma conversa
```
GET /mensage/{conversationId}
```

### Listar todos os usuários
```
GET /user
```

---

#  Caracteristicas do Projeto

- Uso correto de DTOs (Records)
- Arquitetura em camadas
- IDs automáticos
- Evita duplicidade de conversas
- Query JPQL personalizada
- Timestamps automatizados
- Docker Compose para ambiente simples 

---

# 🚀 Ideias de Melhorias Futuras

- Implementar WebSocket para mensagens em tempo real  
- Criar sistema de autenticação com JWT  
- Criar testes unitários  

---
