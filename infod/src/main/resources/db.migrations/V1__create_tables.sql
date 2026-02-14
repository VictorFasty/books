-- 1. Tabela de Usuários (Pai de BooksModel e Livro)
CREATE TABLE public.usuario (
    id UUID PRIMARY KEY,
    login VARCHAR(255),
    email VARCHAR(255),
    senha VARCHAR(255),
    roles VARCHAR[]
);

-- 2. Tabela de Clientes (Independente)
CREATE TABLE public.client (
    id UUID PRIMARY KEY,
    client_id VARCHAR(255),
    client_secret VARCHAR(255),
    redirect_uri VARCHAR(255),
    scope VARCHAR(255)
);

-- 3. Tabela de Autores/BooksModel (Pai de Livro)
CREATE TABLE public.tb_book (
    id UUID PRIMARY KEY,
    autor VARCHAR(100) NOT NULL,
    nascimento DATE,
    nationality VARCHAR(50) NOT NULL,
    data_cadastro TIMESTAMP,
    data_atualizacao TIMESTAMP,
    id_usuario UUID,
    CONSTRAINT fk_book_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id)
);

-- 4. Tabela de Livros
CREATE TABLE public.livro (
    id UUID PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    data_publicacao DATE,
    genero VARCHAR(30) NOT NULL,
    preco DECIMAL(18, 2) NOT NULL,
    tb_books UUID,
    id_usuario UUID,
    data_atualizacao TIMESTAMP,
    CONSTRAINT fk_livro_book FOREIGN KEY (tb_books) REFERENCES public.tb_book(id),
    CONSTRAINT fk_livro_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id)
);