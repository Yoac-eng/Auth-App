CREATE TABLE person (
    id TEXT PRIMARY KEY UNIQUE NOT NULL,
    nome VARCHAR(255),
    rg VARCHAR(255),
    cpf VARCHAR(255),
    data_emissao DATE,
    data_nascimento DATE,
    genero INTEGER,
    nome_pai VARCHAR(255),
    nome_mae VARCHAR(255)
);