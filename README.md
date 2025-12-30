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

🔧 Como RodarPré-requisitosJava 17+ e Maven.PostgreSQL rodando.Variáveis de Ambiente (Obrigatório)O projeto usa o profile production. Configure estas variáveis no seu sistema/IDE:VariávelDescriçãoExemploDATASOURCE_URLURL JDBC do Postgresjdbc:postgresql://localhost:5432/library_dbDATASOURCE_USERNAMEUsuário do BancopostgresDATASOURCE_PASSWORDSenha do Bancosua_senhaGOOGLE_CLIENT_IDClient ID (Google Cloud)123...apps.googleusercontent.comGOOGLE_CLIENT_SECRETClient Secret (Google)GOCSPX-...

ExecuçãoClone o repositório e entre na pasta:Bashgit clone [https://github.com/seu-usuario/library-api.git](https://github.com/seu-usuario/library-api.git)
Execute via Maven:Bashmvn spring-boot:run
Acesse a Documentação (Swagger):http://localhost:8080/swagger-ui.htmlAcesse o Monitoramento (Actuator):http://localhost:9090/actuator (Nota: Porta 9090)📍 Principais EndpointsRecursoMétodoEndpointPermissãoAutoresPOST/autores/createGERENTEGET/autores/findAllOPERADOR+LivrosPOST/livros/createOPERADOR+GET/livrosPesquisa (Público/Auth)UsuáriosPOST/usuariosPúblicoAuthGET/loginPúblico🤝 ContribuiçãoFaça um Fork do projetoCrie uma Branch para sua Feature (git checkout -b feature/MinhaFeature)Faça o Commit (git commit -m 'Adicionando funcionalidade X')Faça o Push (git push origin feature/MinhaFeature)Abra um Pull Request
