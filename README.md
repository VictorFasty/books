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

```mermaid
erDiagram
    AUTOR ||--|{ LIVRO : escreveu
    USUARIO {
        uuid id
        string login
        string email
        string[] roles
    }
    AUTOR {
        uuid id
        string nome
        string nacionalidade
        date nascimento
    }
    LIVRO {
        uuid id
        string titulo
        string isbn
        enum genero
        decimal preco
    }

🔧 Como Rodar
Pré-requisitos
Java 17+ e Maven.

PostgreSQL rodando.

Variáveis de Ambiente (Obrigatório)
O projeto usa o profile production. Configure estas variáveis no seu sistema/IDE:
Variável,Descrição,Exemplo
DATASOURCE_URL,URL JDBC do Postgres,jdbc:postgresql://localhost:5432/library_db
DATASOURCE_USERNAME,Usuário do Banco,postgres
DATASOURCE_PASSWORD,Senha do Banco,sua_senha
GOOGLE_CLIENT_ID,Client ID (Google Cloud),123...apps.googleusercontent.com
GOOGLE_CLIENT_SECRET,Client Secret (Google),GOCSPX-...

Execução
Clone o repositório e entre na pasta: git clone [https://github.com/seu-usuario/library-api.git](https://github.com/seu-usuario/library-api.git)

Execute via Maven:
mvn spring-boot:run

Acesse a Documentação (Swagger):

http://localhost:8080/swagger-ui.html

Acesse o Monitoramento (Actuator):

http://localhost:9090/actuator (Nota: Porta 9090)

Recurso,Método,Endpoint,Permissão
Autores,POST,/autores/create,GERENTE
,GET,/autores/findAll,OPERADOR+
Livros,POST,/livros/create,OPERADOR+
,GET,/livros,Pesquisa (Público/Auth)
Usuários,POST,/usuarios,Público
Auth,GET,/login,Público

Contribuição
Faça um Fork do projeto

Crie uma Branch para sua Feature (git checkout -b feature/MinhaFeature)

Faça o Commit (git commit -m 'Adicionando funcionalidade X')

Faça o Push (git push origin feature/MinhaFeature)

Abra um Pull Request
