
![newHorizon Logo](./assets/newHorizon.png)

# NewHorizon Service 🚀

API REST para gerenciamento de trilhas de aprendizagem, competências, cursos, posições e usuários.

## 📋 Índice

- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Como Executar](#como-executar)
- [Configuração do Banco de Dados](#configuração-do-banco-de-dados)
- [Dependências Principais](#dependências-principais)
- [Endpoints da API](#endpoints-da-api)
- [Exemplos de Requisições](#exemplos-de-requisições)
- [Como Testar](#como-testar)
- [Dados de Seed](#dados-de-seed)
- [Testes Unitários](#testes-unitários)

---

## 🛠️ Tecnologias

- **Java**: 17
- **Spring Boot**: 3.5.7
- **Maven**: 3.x
- **Banco de Dados**: H2 (em memória)
- **ORM**: Hibernate 6.6.33

---

## ⚙️ Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- [Java JDK 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3.x](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/)
- [Postman](https://www.postman.com/) ou [Insomnia](https://insomnia.rest/) (opcional, para testes)

---

## 🚀 Como Executar

### 1. Clone o repositório
```bash
git clone https://github.com/pedroamcerto/newHorizon-service.git
cd newHorizon-service
```

### 2. Compile o projeto
```bash
mvn clean install
```

### 3. Execute a aplicação
```bash
mvn spring-boot:run
```

Ou execute diretamente o JAR gerado:
```bash
java -jar target/newhorizon-service-0.0.1-SNAPSHOT.jar
```

### 4. Acesse a aplicação
A aplicação estará disponível em:
```
http://localhost:8080
```

### 5. Acesse o console H2 (opcional)
```
http://localhost:8080/h2-console
```

**Credenciais do H2:**
- **JDBC URL**: `jdbc:h2:mem:newhorizon-db`
- **Username**: `sa`
- **Password**: *n/a*

---

## 🗄️ Configuração do Banco de Dados

O projeto utiliza o **H2 Database** (banco de dados em memória) para desenvolvimento e testes.

### Configurações (application.yml)

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:newhorizon-db
    driverClassName: org.h2.Driver
    username: sa
    password: 
  
  h2:
    console:
      enabled: true
      path: /h2-console
  
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true
```

### Características:
- ✅ Banco **em memória** (os dados são perdidos ao reiniciar)
- ✅ **Console H2** habilitado para visualização
- ✅ **DDL auto**: `create-drop` (cria o schema ao iniciar e deleta ao finalizar)
- ✅ **SQL formatado** nos logs para debug
- ✅ **Dados de seed** carregados automaticamente ao iniciar

---

## 📦 Dependências Principais

```xml
<!-- Spring Boot Starter Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Boot Starter Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Spring Boot Starter Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- ModelMapper -->
<dependency>
    <groupId>org.modelmapper</groupId>
    <artifactId>modelmapper</artifactId>
    <version>3.2.2</version>
</dependency>
```

---

## 🌐 Endpoints da API

Todos os endpoints estão sob o prefixo `/api/v1`

### 👤 Users
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/v1/users` | Listar todos os usuários |
| GET | `/api/v1/users/{id}` | Buscar usuário por ID |
| POST | `/api/v1/users` | Criar novo usuário |
| PUT | `/api/v1/users/{id}` | Atualizar usuário |
| DELETE | `/api/v1/users/{id}` | Deletar usuário |

### 💡 Competences
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/v1/competences` | Listar todas as competências |
| GET | `/api/v1/competences/{id}` | Buscar competência por ID |
| POST | `/api/v1/competences` | Criar nova competência |
| PUT | `/api/v1/competences/{id}` | Atualizar competência |
| DELETE | `/api/v1/competences/{id}` | Deletar competência |

### 📚 Courses
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/v1/courses` | Listar todos os cursos |
| GET | `/api/v1/courses/{id}` | Buscar curso por ID |
| POST | `/api/v1/courses` | Criar novo curso |
| PUT | `/api/v1/courses/{id}` | Atualizar curso |
| DELETE | `/api/v1/courses/{id}` | Deletar curso |

### 💼 Positions
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/v1/positions` | Listar todas as posições |
| GET | `/api/v1/positions/{id}` | Buscar posição por ID |
| POST | `/api/v1/positions` | Criar nova posição |
| PUT | `/api/v1/positions/{id}` | Atualizar posição |
| DELETE | `/api/v1/positions/{id}` | Deletar posição |

### 🛤️ Trails
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/v1/trails` | Listar todas as trilhas |
| GET | `/api/v1/trails/{id}` | Buscar trilha por ID |
| POST | `/api/v1/trails` | Criar nova trilha |
| PUT | `/api/v1/trails/{id}` | Atualizar trilha |
| DELETE | `/api/v1/trails/{id}` | Deletar trilha |

---

## 📝 Exemplos de Requisições

### 1️⃣ Criar Usuário
**POST** `http://localhost:8080/api/v1/users`

```json
{
  "name": "João Silva",
  "email": "joao.silva@example.com",
  "positionIds": ["1", "2"],
  "trailIds": ["1"]
}
```

**Resposta:** `200 OK` (corpo vazio)

---

### 2️⃣ Criar Competência
**POST** `http://localhost:8080/api/v1/competences`

```json
{
  "name": "Java Programming",
  "type": "HARD"
}
```

**Tipos válidos:** `HARD` ou `SOFT`

**Resposta:** `200 OK` (corpo vazio)

---

### 3️⃣ Criar Curso
**POST** `http://localhost:8080/api/v1/courses`

```json
{
  "name": "Java Básico",
  "competenceIds": ["1", "2"]
}
```

**Resposta:** `200 OK` (corpo vazio)

---

### 4️⃣ Criar Posição
**POST** `http://localhost:8080/api/v1/positions`

```json
{
  "name": "Desenvolvedor Java Júnior",
  "competenceIds": ["1", "2", "3"]
}
```

**Resposta:** `200 OK` (corpo vazio)

---

### 5️⃣ Criar Trilha
**POST** `http://localhost:8080/api/v1/trails`

```json
{
  "name": "Trilha Backend Java",
  "courseIds": ["1", "2", "3"]
}
```

**Resposta:** `200 OK` (corpo vazio)

---

### 6️⃣ Buscar Todos os Usuários
**GET** `http://localhost:8080/api/v1/users`

**Resposta:** `200 OK`
```json
[
  {
    "id": "1",
    "name": "João Silva",
    "email": "joao.silva@example.com",
    "positions": [
      {
        "id": "1",
        "name": "Desenvolvedor Java Júnior"
      }
    ],
    "trails": [
      {
        "id": "1",
        "name": "Trilha Iniciante Java"
      }
    ]
  }
]
```

---

### 7️⃣ Buscar Usuário por ID
**GET** `http://localhost:8080/api/v1/users/1`

**Resposta:** `200 OK`
```json
{
  "id": "1",
  "name": "João Silva",
  "email": "joao.silva@example.com",
  "positions": [
    {
      "id": "1",
      "name": "Desenvolvedor Java Júnior"
    }
  ],
  "trails": [
    {
      "id": "1",
      "name": "Trilha Iniciante Java"
    }
  ]
}
```

---

### 8️⃣ Atualizar Usuário
**PUT** `http://localhost:8080/api/v1/users/1`

```json
{
  "name": "João Silva Atualizado",
  "email": "joao.atualizado@example.com",
  "positionIds": ["2"],
  "trailIds": ["1", "2"]
}
```

**Resposta:** `200 OK` (corpo vazio)

---

### 9️⃣ Deletar Usuário
**DELETE** `http://localhost:8080/api/v1/users/1`

**Resposta:** `200 OK` (corpo vazio)

---

### 🔴 Tratamento de Erros

#### Recurso não encontrado
**GET** `http://localhost:8080/api/v1/users/999`

**Resposta:** `404 Not Found`
```json
{
  "timestamp": "2025-11-16T10:30:00",
  "status": 404,
  "message": "Usuário não encontrado",
  "path": "/api/v1/users/999"
}
```

#### Validação de dados
**POST** `http://localhost:8080/api/v1/users`

```json
{
  "name": "",
  "email": "email-invalido"
}
```

**Resposta:** `400 Bad Request`
```json
{
  "timestamp": "2025-11-16T10:30:00",
  "status": 400,
  "message": "name: O nome é obrigatório; email: Email inválido",
  "path": "/api/v1/users"
}
```

---

## 🧪 Como Testar

### Opção 1: Postman

1. **Importe a collection** disponível em:
   ```
   NewHorizon-API.postman_collection.json
   ```

2. **Configure a variável de ambiente** (opcional):
   - Variável: `baseUrl`
   - Valor: `http://localhost:8080/api/v1`

3. **Execute as requisições** nas pastas:
   - Users
   - Competences
   - Courses
   - Positions
   - Trails

### Opção 2: Insomnia

1. **Crie uma nova Collection**

2. **Configure a URL base**:
   ```
   http://localhost:8080/api/v1
   ```

3. **Crie as requisições** seguindo os exemplos acima

### Opção 3: cURL

```bash
# Listar todos os usuários
curl -X GET http://localhost:8080/api/v1/users

# Criar um usuário
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Maria Santos",
    "email": "maria.santos@example.com",
    "positionIds": ["1"],
    "trailIds": ["1"]
  }'

# Buscar usuário por ID
curl -X GET http://localhost:8080/api/v1/users/1

# Atualizar usuário
curl -X PUT http://localhost:8080/api/v1/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Maria Santos Atualizada",
    "email": "maria.atualizada@example.com",
    "positionIds": ["2"],
    "trailIds": ["1", "2"]
  }'

# Deletar usuário
curl -X DELETE http://localhost:8080/api/v1/users/1
```

---

## 🌱 Dados de Seed

A aplicação carrega dados automaticamente ao iniciar através da classe `DataSeeder.java`.

### Dados criados:

#### 6 Competências
- Java (HARD)
- Spring Boot (HARD)
- Comunicação (SOFT)
- Trabalho em Equipe (SOFT)
- Python (HARD)
- Liderança (SOFT)

#### 6 Cursos
- Java Básico
- Java Avançado
- Spring Boot Essentials
- Spring Boot Advanced
- Python para Iniciantes
- Soft Skills para Desenvolvedores

#### 4 Posições
- Desenvolvedor Java Júnior
- Desenvolvedor Java Pleno
- Desenvolvedor Full Stack
- Tech Lead

#### 3 Trilhas
- Trilha Iniciante Java
- Trilha Avançada Java
- Trilha Full Stack

#### 4 Usuários
- João Silva (joao.silva@example.com)
- Maria Santos (maria.santos@example.com)
- Pedro Oliveira (pedro.oliveira@example.com)
- Ana Costa (ana.costa@example.com)

**Nota:** Os dados são recriados a cada reinicialização da aplicação.

---

## 🧪 Testes Unitários

O projeto possui testes unitários completos para todas as services.

### Executar testes
```bash
mvn test
```

### Executar testes com relatório de cobertura
```bash
mvn test jacoco:report
```

### Estrutura de testes
```
src/test/java/com/newhorizon_service/service/
├── UserServiceTest.java
├── CompetenceServiceTest.java
├── CourseServiceTest.java
├── PositionServiceTest.java
└── TrailServiceTest.java
```

### Cobertura
- ✅ Testes de criação (create)
- ✅ Testes de atualização (update)
- ✅ Testes de listagem (getAll)
- ✅ Testes de busca por ID (getById)
- ✅ Testes de deleção (delete)
- ✅ Testes de tratamento de exceções

---

## 📚 Documentação Adicional

### Relacionamentos entre Entidades

```
User (N) ←→ (N) Position
User (N) ←→ (N) Trail
Position (N) ←→ (N) Competence
Trail (N) ←→ (N) Course
Course (N) ←→ (N) Competence
```

### Validações Implementadas

- **User**:
  - `name`: obrigatório, 3-100 caracteres
  - `email`: obrigatório, formato válido

- **Competence**:
  - `name`: obrigatório, 3-100 caracteres
  - `type`: obrigatório (HARD ou SOFT)

- **Course**:
  - `name`: obrigatório, 3-100 caracteres

- **Position**:
  - `name`: obrigatório, 3-100 caracteres

- **Trail**:
  - `name`: obrigatório, 3-100 caracteres

---

## 👥 Autores

**NewHorizon Team**

- Fabiano Zague     | 555524
- Lorran dos Santos | 558982
- Pedro Certo       | 556268

---

## 📞 Suporte

Para dúvidas ou problemas, abra uma issue no repositório.
