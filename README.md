# Library API (InfoD) 📚

A **Library API** é um sistema backend robusto para gerenciamento de bibliotecas. O projeto oferece controle completo sobre acervo de livros, cadastro de autores e gestão de usuários, utilizando autenticação moderna via OAuth2 e controle de acesso hierárquico (RBAC).

## 🚀 Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot 3**
* **Spring Data JPA** (Hibernate)
* **Spring Security** + **OAuth2 Client** (Login Google)
* **PostgreSQL** (Com suporte nativo a Arrays via *Hypersistence Utils*)
* **SpringDoc OpenAPI** (Swagger UI)
* **Lombok**
* **Spring Actuator** (Monitoramento)

## ⚙️ Funcionalidades

### 1. Gestão de Acervo
* **Autores (`/autores`):** CRUD completo com validação de duplicidade.
* **Livros (`/livros`):** Cadastro de obras com ISBN, Gênero, Preço e associação com Autores.
* **Busca Avançada:** Pesquisa paginada por múltiplos parâmetros (ISBN, Título, Autor, Gênero).

### 2. Segurança e Acesso
O sistema utiliza controle de permissões baseado em Roles (`varchar[]` no banco):
* **GERENTE:** Acesso total (Criar, Editar, Deletar).
* **OPERADOR:** Acesso operacional (Visualizar, Pesquisar, Cadastrar Livros).
* **USER:** Acesso básico.
* **Autenticação:** Login via formulário ou Google OAuth2.

## 🗂️ Estrutura de Dados

O sistema organiza os dados nas seguintes entidades principais:

* **Relacionamento:** Um **Autor** possui vários **Livros** (1 para N).
* **USUARIO:**
    * `id` (UUID)
    * `login` (String)
    * `email` (String)
    * `roles` (Array de Strings - Permissões)
* **AUTOR:**
    * `id` (UUID)
    * `nome` (String)
    * `nacionalidade` (String)
    * `nascimento` (Date)
* **LIVRO:**
    * `id` (UUID)
    * `titulo` (String)
    * `isbn` (String)
    * `genero` (Enum)
    * `preco` (Decimal)

## 🔧 Como Rodar

### Pré-requisitos
* Java 17+ e Maven.
* PostgreSQL rodando.

### Variáveis de Ambiente (Obrigatório)
O projeto usa o profile `production`. Você deve configurar as seguintes chaves no seu sistema ou IntelliJ/Eclipse:

* **DATASOURCE_URL**: URL de conexão JDBC (Ex: `jdbc:postgresql://localhost:5432/library_db`)
* **DATASOURCE_USERNAME**: Usuário do banco de dados (Ex: `postgres`)
* **DATASOURCE_PASSWORD**: Senha do banco de dados
* **GOOGLE_CLIENT_ID**: Credencial do Google Cloud
* **GOOGLE_CLIENT_SECRET**: Segredo do cliente Google

### Execução

1. Clone o repositório e entre na pasta:
```bash
git clone [https://github.com/seu-usuario/library-api.git](https://github.com/seu-usuario/library-api.git)

Execute via Maven:
mvn spring-boot:run

Acesse a Documentação (Swagger):

http://localhost:8080/swagger-ui.html

Acesse o Monitoramento (Actuator):

http://localhost:9090/actuator (Nota: Porta 9090)
