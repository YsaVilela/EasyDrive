-- Tabela: tb_estado
CREATE TABLE tb_estado (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    sigla VARCHAR(2) NOT NULL
);

-- Tabela: tb_cidade
CREATE TABLE tb_cidade (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    FK_ESTADO INTEGER NOT NULL,
    	foreign key (FK_ESTADO)
    	REFERENCES tb_estado(id)
);

-- Tabela: tb_endereco
CREATE TABLE tb_endereco (
    id SERIAL PRIMARY KEY,
    cep VARCHAR(20) NOT NULL,
    logradouro VARCHAR(255) NOT NULL,
    numero VARCHAR(255) NOT NULL,
    complemento VARCHAR(255),
    bairro VARCHAR(255),
    FK_CIDADE INTEGER NOT NULL,
    	foreign key (FK_CIDADE)
    	REFERENCES tb_cidade(id)
);

-- Tabela: tb_pessoa
CREATE TABLE tb_pessoa (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) not null,
    cpf VARCHAR(20) unique not null,
    data_nascimento DATE not null,
    telefone VARCHAR(20),
    email VARCHAR(255) not null unique,
    data_cadastro DATE not null,
    FK_ENDERECO INTEGER not null,
        foreign key (FK_ENDERECO)
    	REFERENCES tb_endereco(id)
);

-- Tabela: tb_funcionario
CREATE TABLE tb_funcionario (
    id SERIAL PRIMARY KEY,
    cargo VARCHAR(255) not null,
    status VARCHAR(45) not null,
    data_cadastro DATE not null,
    FK_PESSOA INTEGER unique not null,
        foreign key (FK_PESSOA)
    	REFERENCES tb_pessoa(id)
);

-- Tabela: tb_cliente
CREATE TABLE tb_cliente (
    id SERIAL PRIMARY KEY,
    pontuacao INTEGER not null,
    plano_assinatura VARCHAR(255) not null,
    numero_cnh VARCHAR(255) unique,
    validade_cnh DATE,
    status VARCHAR(45) not null,
    data_cadastro DATE not null,
    FK_PESSOA INTEGER unique not null,
        foreign key (FK_PESSOA)
    	REFERENCES tb_pessoa(id)
);

-- Tabela: tb_veiculo
CREATE TABLE tb_veiculo (
    id SERIAL PRIMARY KEY,
    placa VARCHAR(20) unique not null,
    modelo VARCHAR(45) not null,
    marca VARCHAR(45) not null,
    ano INTEGER not null,
    quilometragem INTEGER,
    categoria VARCHAR(45) not null,
    cor VARCHAR(45) not null,
    tipo_combustivel VARCHAR(20) not null,
    valor_diaria FLOAT not null,
    status VARCHAR(45) not null
);

-- Tabela: tb_reserva
CREATE TABLE tb_reserva (
    id SERIAL PRIMARY KEY,
    FK_VEICULO INTEGER not null,
    	foreign key (FK_VEICULO)
    	REFERENCES tb_veiculo(id),
    FK_CLIENTE INTEGER not null,
        foreign key (FK_CLIENTE)
    	REFERENCES tb_cliente(id),
    data_inicio TIMESTAMP not null,
    data_retirada TIMESTAMP,
    data_fim TIMESTAMP not null,
    data_devolucao TIMESTAMP,
    orcamento FLOAT not null,
    orcamento_final FLOAT not null,
    valor_final FLOAT,
    valor_pago FLOAT,
    status VARCHAR(45) not null
);

-- Tabela: tb_pagamento
CREATE TABLE tb_pagamento (
    id SERIAL PRIMARY KEY,
    FK_RESERVA INTEGER not null,
    	foreign key (FK_RESERVA)
    	REFERENCES tb_reserva(id),
    valor FLOAT not null,
    forma_pagamento VARCHAR(45) not null,
    data_pagamento TIMESTAMP
);

-- Tabela: tb_servico_reserva (Tabela de relacionamento)
CREATE TABLE tb_servico_reserva (
	ID SERIAL PRIMARY KEY,
    nome VARCHAR(45) not null,
    valor FLOAT not null,
    FK_reserva INTEGER not null, 
    	foreign key (FK_reserva)
    	REFERENCES tb_reserva(id)
);

DROP TABLE IF EXISTS tb_servico_reserva, tb_pagamento , 
tb_reserva, tb_veiculo, tb_cliente, tb_funcionario, tb_pessoa, tb_endereco, tb_cidade, tb_estado;

