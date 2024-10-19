create table TB_ENDERECO(
	ID serial primary key,
	CEP varchar (255),
	LOGRADOURO varchar(255) not null,
	NUMERO integer,
	COMPLEMENTO varchar(255),
	BAIRRO varchar(255),
	CIDADE varchar(255) not null,
	ESTADO varchar(255) not null,
	PAIS varchar(255) not null
);

create table TB_PESSOA(
	ID serial primary key,
	NOME varchar (255) not null,	
	CPF varchar(20) unique,
	DATA_NASCIMENTO varchar(10) not null,
	TELEFONE varchar(50) not null,
	EMAIL varchar(255) not null unique,
	DATA_CADASTRO varchar(10) not null,
	STATUS varchar(100) not null,
	FK_ENDERECO integer not null,
		foreign key (FK_ENDERECO)
		references  TB_ENDERECO(ID)
);

create table TB_FUNCIONARIO(
	ID serial primary key,
	CARGO varchar(255) not null,
	FK_PESSOA integer not null,
		foreign key (FK_PESSOA)
		references  TB_PESSOA(ID)
);

create table TB_CLIENTE(
	ID serial primary key,
	NUMERO_CNH varchar(100) not null,
	VALIDADE_CNH varchar(10) not null,
	FK_PESSOA integer not null,
		foreign key (FK_PESSOA)
		references  TB_PESSOA(ID)
);


create table TB_VEICULO(
	ID serial primary key,
	PLACA varchar(10) not null unique,
	MODELO varchar(255) not null,
	MARCA varchar(255) not null,
	ANO integer not null,
	CATEGORIA varchar(255) not null,
	COR varchar(255) not null,
	STATUS varchar(255) not null
);

create table TB_RESERVA (
	ID serial primary key,
	DATA_INICIO varchar(10) not null,
	DATA_FIM varchar(10),
	VALOR_DIARIA double precision not null,
	STATUS varchar(255) not null,
	FK_CLIENTE integer not null,
		foreign key (FK_CLIENTE)
		references  TB_CLIENTE(ID),
	FK_VEICULO integer not null,
		foreign key (FK_VEICULO)
		references  TB_VEICULO(ID)
);

create table TB_PAGAMENTO (
	ID serial primary key,
	VALOR_TOTAL double precision not null,
	FORMA_PAGAMENTO varchar(255) not null,
	DATA_PAGAMENTO varchar(10),
	STATUS varchar(255) not null,
	FK_RESERVA integer not null,
		foreign key (FK_RESERVA)
		references  TB_RESERVA(ID)
);


DROP TABLE IF EXISTS TB_PAGAMENTO;
DROP TABLE IF EXISTS TB_RESERVA;
DROP TABLE IF EXISTS TB_VEICULO;
DROP TABLE IF EXISTS TB_CLIENTE;
DROP TABLE IF EXISTS TB_FUNCIONARIO;
DROP TABLE IF EXISTS TB_PESSOA;
DROP TABLE IF EXISTS TB_ENDERECO;
