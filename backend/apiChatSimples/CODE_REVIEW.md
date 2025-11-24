# Documentação Code Review - API Chat Simples

## 📋 Visão Geral do Projeto

Este projeto é uma API REST desenvolvida em **Spring Boot** para gerenciar um sistema de chat simples. A aplicação permite criar e gerenciar usuários e conversas entre eles.

### Stack Tecnológica

- **Java 21** - Linguagem de programação
- **Spring Boot 4.0.0** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados relacional
- **Docker Compose** - Orquestração do banco de dados
- **Maven** - Gerenciamento de dependências

### Arquitetura

A aplicação segue o padrão de arquitetura em camadas (Layered Architecture):

```
Controller (REST) → Service (Lógica de Negócio) → Repository (Acesso a Dados) → Database
```

---

## ⚙️ Configuração do Projeto

### 1. pom.xml

O arquivo `pom.xml` define as dependências do projeto usando Maven:

```1:88:pom.xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>4.0.0</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.lifty</groupId>
	<artifactId>apiChatSimples</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>apiChatSimples</name>
	<description>Api feita em java!</description>
	<url/>
	<licenses>
		<license/>
	</licenses>
	<developers>
		<developer/>
	</developers>
	<scm>
		<connection/>
		<developerConnection/>
		<tag/>
		<url/>
	</scm>
	<properties>
		<java.version>21</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-docker-compose</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-webmvc-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
```

**Principais Dependências:**
- `spring-boot-starter-data-jpa`: Fornece suporte ao JPA/Hibernate para persistência
- `spring-boot-starter-validation`: Validação de dados
- `spring-boot-starter-webmvc`: Suporte para criar APIs REST
- `postgresql`: Driver JDBC para PostgreSQL
- `spring-boot-docker-compose`: Integração com Docker Compose

### 2. application.properties

```1:8:src/main/resources/application.properties
spring.application.name=apiChatSimples
spring.datasource.url=jdbc:postgresql://localhost:${POSTGRES_PORT}/${POSTGRES_DB}
spring.datasource.username=${POSTGRES_USER}
spring.datasource.password=${POSTGRES_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**Explicação:**
- `spring.datasource.*`: Configuração de conexão com PostgreSQL usando variáveis de ambiente
- `spring.jpa.hibernate.ddl-auto=update`: Atualiza automaticamente o schema do banco de dados
- `spring.jpa.show-sql=true`: Exibe as queries SQL no console (útil para debug)

### 3. compose.yaml

```1:9:compose.yaml
services:
  postgres:
    image: 'postgres:latest'
    container_name: 'container-banco-local'
    env_file:
      - .env
    ports:
      - "${POSTGRES_PORT}:5432"
```

**Explicação:**
- Define um serviço PostgreSQL usando Docker Compose
- Usa variáveis de ambiente do arquivo `.env`
- Expõe a porta do PostgreSQL configurável via variável de ambiente

### 4. ApiChatSimplesApplication.java

```1:13:src/main/java/com/lifty/apiChatSimples/ApiChatSimplesApplication.java
package com.lifty.apiChatSimples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiChatSimplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiChatSimplesApplication.class, args);
	}

}
```

**Explicação:**
- Classe principal que inicia a aplicação Spring Boot
- A anotação `@SpringBootApplication` combina:
  - `@Configuration`: Define beans
  - `@EnableAutoConfiguration`: Habilita auto-configuração
  - `@ComponentScan`: Escaneia componentes no pacote

---

## 🗄️ Camada de Entidades (Entity)

As entidades representam as tabelas do banco de dados usando JPA.

### 1. User.java

```1:36:src/main/java/com/lifty/apiChatSimples/entity/User.java
package com.lifty.apiChatSimples.entity;

import com.lifty.apiChatSimples.dtos.user.UserRequestDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public User() {}

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public User(UserRequestDTO userRequestDTO){
        this.name = userRequestDTO.name();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
```

**Explicação:**
- `@Entity`: Marca a classe como entidade JPA
- `@Table(name = "users")`: Define o nome da tabela no banco
- `@Id`: Identifica a chave primária
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Gera o ID automaticamente (auto-incremento)
- Construtores:
  - Construtor vazio (obrigatório para JPA)
  - Construtor com parâmetros
  - Construtor que recebe `UserRequestDTO` para facilitar conversão

### 2. Conversation.java

```1:79:src/main/java/com/lifty/apiChatSimples/entity/Conversation.java
package com.lifty.apiChatSimples.entity;


import com.lifty.apiChatSimples.dtos.conversation.ConversationRequestDTO;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table (name = "conversations")
@Entity
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @CreationTimestamp
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "id_usuario_remetente")
    private User remetente;
    @ManyToOne
    @JoinColumn(name = "id_usuario_destinatario")
    private User destinatario;


    public Conversation(){
        this.created_at = LocalDateTime.now();
    }
    public Conversation(User remetente, User destinatario, String title){
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.title = title;
    }
    public Conversation(String title){
        this.title = title;
        this.created_at = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public User getRemetente() {
        return remetente;
    }

    public void setRemetente(User remetente) {
        this.remetente = remetente;
    }

    public User getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(User destinatario) {
        this.destinatario = destinatario;
    }
}
```

**Explicação:**
- `@CreationTimestamp`: Anotação do Hibernate que preenche automaticamente o campo com a data/hora de criação
- `@ManyToOne`: Define relacionamento muitos-para-um (muitas conversas podem ter o mesmo usuário como remetente/destinatário)
- `@JoinColumn`: Especifica a coluna de chave estrangeira no banco de dados
- **Relacionamentos:**
  - `remetente`: Usuário que inicia a conversa
  - `destinatario`: Usuário que recebe a conversa

---

## 📦 Camada de DTOs (Data Transfer Objects)

DTOs são objetos usados para transferir dados entre camadas, evitando expor a estrutura interna das entidades.

### 1. UserRequestDTO.java

```1:5:src/main/java/com/lifty/apiChatSimples/dtos/user/UserRequestDTO.java
package com.lifty.apiChatSimples.dtos.user;

public record UserRequestDTO(Long id, String name) {

}
```

**Explicação:**
- Usa `record` (Java 14+), que cria automaticamente:
  - Campos `final` e imutáveis
  - Construtor
  - Getters
  - `equals()`, `hashCode()`, `toString()`
- Usado para receber dados do cliente na criação/atualização de usuários

### 2. UserResponseDTO.java

```1:9:src/main/java/com/lifty/apiChatSimples/dtos/user/UserResponseDTO.java
package com.lifty.apiChatSimples.dtos.user;
import com.lifty.apiChatSimples.entity.User;

public record UserResponseDTO(Long id, String name) {
    public UserResponseDTO(User user){
        this(user.getId(), user.getName());

    }
}
```

**Explicação:**
- Construtor adicional que recebe uma entidade `User` e converte para DTO
- Usado para retornar dados ao cliente sem expor a estrutura interna da entidade

### 3. ConversationRequestDTO.java

```1:5:src/main/java/com/lifty/apiChatSimples/dtos/conversation/ConversationRequestDTO.java
package com.lifty.apiChatSimples.dtos.conversation;

public record ConversationRequestDTO(Long remetente, Long destinatario, String title) {

}
```

**Explicação:**
- Recebe apenas os IDs dos usuários (remetente e destinatario) e o título da conversa
- Evita enviar objetos completos na requisição

### 4. ConversationResponseDTO.java

```1:11:src/main/java/com/lifty/apiChatSimples/dtos/conversation/ConversationResponseDTO.java
package com.lifty.apiChatSimples.dtos.conversation;

import com.lifty.apiChatSimples.entity.Conversation;

import java.time.LocalDateTime;

public record ConversationResponseDTO(Long id, String title, LocalDateTime created_at) {
    public ConversationResponseDTO(Conversation conversation) {
        this(conversation.getId(), conversation.getTitle(), conversation.getCreated_at());
    }
}
```

**Explicação:**
- Construtor que converte `Conversation` para DTO
- Retorna apenas informações essenciais (id, título e data de criação)

---

## 💾 Camada de Repositório (Repository)

Os repositórios fornecem métodos para acesso aos dados usando Spring Data JPA.

### 1. UserRepository.java

```1:10:src/main/java/com/lifty/apiChatSimples/repository/UserRepository.java
package com.lifty.apiChatSimples.repository;

import com.lifty.apiChatSimples.entity.User;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
```

**Explicação:**
- `@Repository`: Marca como componente Spring (gerenciamento de transações)
- `JpaRepository<User, Long>`: Fornece métodos CRUD automáticos:
  - `save()`, `findById()`, `findAll()`, `deleteById()`, etc.
- O segundo parâmetro (`Long`) é o tipo da chave primária

### 2. ConversationRepository.java

```1:7:src/main/java/com/lifty/apiChatSimples/repository/ConversationRepository.java
package com.lifty.apiChatSimples.repository;

import com.lifty.apiChatSimples.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
```

**Explicação:**
- Similar ao `UserRepository`, fornece operações CRUD para `Conversation`
- Spring Data JPA implementa automaticamente os métodos da interface

---

## 🔧 Camada de Serviço (Service)

Os serviços contêm a lógica de negócio da aplicação.

### 1. UserService.java

```1:46:src/main/java/com/lifty/apiChatSimples/service/UserService.java
package com.lifty.apiChatSimples.service;

import com.lifty.apiChatSimples.dtos.user.UserRequestDTO;
import com.lifty.apiChatSimples.dtos.user.UserResponseDTO;
import com.lifty.apiChatSimples.entity.User;
import com.lifty.apiChatSimples.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

   private UserRepository userRepository;

   public UserService(UserRepository userRepository){
       this.userRepository = userRepository;
   }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        User user = new User(userRequestDTO);
        user = userRepository.save(user);
        return new UserResponseDTO(user);
    }

    public List<UserResponseDTO> listAllUser(){
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::new)
                .collect(Collectors.toList());
    }

    public UserResponseDTO listUserId(Long id){
        UserResponseDTO userResponseDTO = userRepository.findById(id)
                .stream()
                .map(UserResponseDTO::new)
                .findFirst()
                .orElseThrow(null);
        return userResponseDTO;
    }
    public void deleteUser(Long id){
       userRepository.deleteById(id);
    }
}
```

**Explicação:**
- `@Service`: Marca como serviço Spring (gerenciamento de transações)
- **Injeção de Dependência:** Construtor recebe `UserRepository` (padrão recomendado)
- **Métodos:**
  - `createUser()`: Cria um novo usuário a partir do DTO
  - `listAllUser()`: Retorna todos os usuários convertidos para DTO usando Stream API
  - `listUserId()`: Busca um usuário por ID (⚠️ **Nota:** `orElseThrow(null)` pode lançar `NullPointerException` - deveria receber um `Supplier<Exception>`)
  - `deleteUser()`: Remove um usuário por ID

### 2. ConversationService.java

```1:44:src/main/java/com/lifty/apiChatSimples/service/ConversationService.java
package com.lifty.apiChatSimples.service;

import com.lifty.apiChatSimples.dtos.conversation.ConversationRequestDTO;
import com.lifty.apiChatSimples.dtos.conversation.ConversationResponseDTO;
import com.lifty.apiChatSimples.entity.Conversation;
import com.lifty.apiChatSimples.entity.User;
import com.lifty.apiChatSimples.repository.ConversationRepository;
import com.lifty.apiChatSimples.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    private ConversationRepository conversationRepository;
    private UserRepository userRepository;

    public ConversationService(ConversationRepository conversationRepository, UserRepository userRepository ){
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    public ConversationResponseDTO createConversation(ConversationRequestDTO conversationRequestDTO){
       User remetente = buscaUsuario(conversationRequestDTO.remetente());
       User destinatario = buscaUsuario(conversationRequestDTO.destinatario());
       Conversation conversation = new Conversation(remetente,destinatario,conversationRequestDTO.title());
        conversation = conversationRepository.save(conversation);
       return new ConversationResponseDTO(conversation);
    }

    private User buscaUsuario(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new RuntimeException(String
                .format("Usuário com id %d não foi encontrado", userId)));
    }

    public List<ConversationResponseDTO> listAllConversations() {
        return conversationRepository.findAll()
                .stream()
                .map(ConversationResponseDTO :: new)
                .collect(Collectors.toList());
    }
}
```

**Explicação:**
- **Injeção de Dependência:** Recebe ambos os repositórios no construtor
- **Métodos:**
  - `createConversation()`: 
    1. Busca os usuários remetente e destinatário pelos IDs
    2. Cria a conversa com os usuários e título
    3. Salva e retorna o DTO
  - `buscaUsuario()`: Método privado que busca um usuário ou lança exceção se não encontrado
  - `listAllConversations()`: Lista todas as conversas convertidas para DTO

---

## 🌐 Camada de Controle (Controller)

Os controllers expõem os endpoints REST da API.

### 1. UserController.java

```1:45:src/main/java/com/lifty/apiChatSimples/controller/UserController.java
package com.lifty.apiChatSimples.controller;

import com.lifty.apiChatSimples.dtos.user.UserRequestDTO;
import com.lifty.apiChatSimples.dtos.user.UserResponseDTO;
import com.lifty.apiChatSimples.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {

    public final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

   @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
       UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
       return ResponseEntity.ok(userResponseDTO);
   }

   @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAllUser(){
      List<UserResponseDTO> userResponseDTOS = userService.listAllUser();
      return ResponseEntity.ok(userResponseDTOS);
   }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> listUserId(@PathVariable Long id){
        UserResponseDTO userResponseDTO = userService.listUserId(id);
        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
```

**Explicação:**
- `@RestController`: Combina `@Controller` + `@ResponseBody` (retorna JSON)
- `@RequestMapping("/users")`: Define o prefixo base para todos os endpoints
- **Injeção de Dependência:** Construtor recebe `UserService`
- **Endpoints:**
  - `POST /users`: Cria um novo usuário
  - `GET /users`: Lista todos os usuários
  - `GET /users/{id}`: Busca usuário por ID
  - `DELETE /users/{id}`: Remove usuário por ID
- `ResponseEntity`: Permite controlar status HTTP e headers

### 2. ConversationController.java

```1:30:src/main/java/com/lifty/apiChatSimples/controller/ConversationController.java
package com.lifty.apiChatSimples.controller;

import com.lifty.apiChatSimples.dtos.conversation.ConversationRequestDTO;
import com.lifty.apiChatSimples.dtos.conversation.ConversationResponseDTO;
import com.lifty.apiChatSimples.service.ConversationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/conversation")
public class ConversationController {

    private ConversationService conversationService;
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }
    @GetMapping
    public ResponseEntity<List<ConversationResponseDTO>> listAllConversation(){
       List<ConversationResponseDTO> conversationResponseDTOS = conversationService.listAllConversations();
       return ResponseEntity.ok(conversationResponseDTOS);
    }
    @PostMapping
    public ResponseEntity<ConversationResponseDTO> createConversation(@RequestBody ConversationRequestDTO conversationRequestDTO) {
        ConversationResponseDTO conversationResponseDTO = conversationService.createConversation(conversationRequestDTO);
        return ResponseEntity.ok(conversationResponseDTO);
    }
}
```

**Explicação:**
- `@RequestMapping("/conversation")`: Prefixo base para endpoints de conversas
- **Endpoints:**
  - `GET /conversation`: Lista todas as conversas
  - `POST /conversation`: Cria uma nova conversa

---

## 🔄 Fluxo de Dados e Relacionamentos

### Fluxo de Requisição

```
Cliente HTTP Request
    ↓
Controller (recebe DTO)
    ↓
Service (lógica de negócio)
    ↓
Repository (acesso ao banco)
    ↓
Database (PostgreSQL)
    ↓
Repository (retorna Entity)
    ↓
Service (converte para DTO)
    ↓
Controller (retorna DTO)
    ↓
Cliente HTTP Response
```

### Relacionamento entre Entidades

```
User (1) ──< (N) Conversation (remetente)
User (1) ──< (N) Conversation (destinatario)
```

- Um usuário pode ser remetente de várias conversas
- Um usuário pode ser destinatário de várias conversas
- Uma conversa tem exatamente um remetente e um destinatário

---

## 📡 Endpoints da API

### Usuários

| Método | Endpoint | Descrição | Request Body | Response |
|--------|----------|-----------|--------------|----------|
| `POST` | `/users` | Cria um novo usuário | `{"id": 1, "name": "João"}` | `{"id": 1, "name": "João"}` |
| `GET` | `/users` | Lista todos os usuários | - | `[{"id": 1, "name": "João"}, ...]` |
| `GET` | `/users/{id}` | Busca usuário por ID | - | `{"id": 1, "name": "João"}` |
| `DELETE` | `/users/{id}` | Remove usuário por ID | - | `204 No Content` |

### Conversas

| Método | Endpoint | Descrição | Request Body | Response |
|--------|----------|-----------|--------------|----------|
| `POST` | `/conversation` | Cria uma nova conversa | `{"remetente": 1, "destinatario": 2, "title": "Chat"}` | `{"id": 1, "title": "Chat", "created_at": "2024-01-01T10:00:00"}` |
| `GET` | `/conversation` | Lista todas as conversas | - | `[{"id": 1, "title": "Chat", "created_at": "2024-01-01T10:00:00"}, ...]` |

### Exemplos de Uso

#### Criar Usuário
```bash
POST http://localhost:8080/users
Content-Type: application/json

{
  "name": "João Silva"
}
```

#### Listar Usuários
```bash
GET http://localhost:8080/users
```

#### Criar Conversa
```bash
POST http://localhost:8080/conversation
Content-Type: application/json

{
  "remetente": 1,
  "destinatario": 2,
  "title": "Conversa entre João e Maria"
}
```

---

## 🔍 Pontos de Atenção para Code Review

### 1. Tratamento de Erros
- `UserService.listUserId()` usa `orElseThrow(null)` que pode causar `NullPointerException`
- **Sugestão:** Usar `orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"))`

### 2. Validação de Dados
- Não há validação de campos obrigatórios nos DTOs
- **Sugestão:** Adicionar `@NotNull`, `@NotBlank` nos DTOs e `@Valid` nos controllers

### 3. Tratamento de Exceções Global
- Não há `@ControllerAdvice` para tratamento centralizado de exceções
- **Sugestão:** Criar um handler global para retornar respostas HTTP apropriadas

### 4. Consistência de Nomenclatura
- `ConversationController` usa `/conversation` (singular), enquanto `UserController` usa `/users` (plural)
- **Sugestão:** Padronizar para plural (`/conversations`)

### 5. Segurança
- Não há autenticação/autorização implementada
- **Sugestão:** Adicionar Spring Security para proteger os endpoints

### 6. Documentação da API
- Não há documentação Swagger/OpenAPI
- **Sugestão:** Adicionar SpringDoc OpenAPI para documentação automática

---

## 📝 Conclusão

Este projeto demonstra uma arquitetura bem organizada seguindo o padrão MVC em camadas, com separação clara de responsabilidades. A estrutura facilita manutenção e evolução do código. As principais melhorias sugeridas são relacionadas a tratamento de erros, validação e segurança.

