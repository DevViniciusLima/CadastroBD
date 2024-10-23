-- definição da criação em sequência dos IDs 
CREATE SEQUENCE PessoaSequence
    START WITH 1
    INCREMENT BY 1;

-- criação da tabela Pessoa
CREATE TABLE Pessoa (
    id INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    logradouro VARCHAR(255),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    telefone VARCHAR(20),
    email VARCHAR(100)
);

-- criação da tabela PessoaFisica
CREATE TABLE PessoaFisica (
    id INT PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL,
    FOREIGN KEY (id) REFERENCES Pessoa(id)
);

-- criação a tabela PessoaJuridica
CREATE TABLE PessoaJuridica (
    id INT PRIMARY KEY,
    cnpj VARCHAR(18) NOT NULL,
    FOREIGN KEY (id) REFERENCES Pessoa(id)
);


