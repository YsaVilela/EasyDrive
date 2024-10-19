INSERT INTO TB_ENDERECO (CEP, LOGRADOURO , NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, PAIS) VALUES
('01001-000','Rua 1', 123, 'Apt 101', 'Centro', 'São Paulo', 'SP', 'Brasil'),
('20001-000','Rua 2', 456, 'Casa', 'Copacabana', 'Rio de Janeiro', 'RJ', 'Brasil'),
('70001-000','Rua 3', 789, 'Sala 2', 'Asa Norte', 'Brasília', 'DF', 'Brasil');

INSERT INTO TB_PESSOA (NOME, CPF, DATA_NASCIMENTO, TELEFONE, EMAIL, DATA_CADASTRO, STATUS, FK_ENDERECO) VALUES
('João Silva', '12345678901', '1980-05-15', '(11) 98765-4321', 'joao.silva@email.com', '2024-01-01', 'Ativo', 1),
('Maria Souza', '23456789012', '1990-08-10', '(21) 99876-5432', 'maria.souza@email.com', '2024-01-05', 'Ativo', 2),
('Pedro Oliveira', '34567890123', '1985-02-20', '(61) 98765-1234', 'pedro.oliveira@email.com', '2024-01-10', 'Ativo', 3);

INSERT INTO TB_FUNCIONARIO (CARGO, FK_PESSOA) VALUES
('Gerente', 1), 
('Atendente', 2),
('Supervisor', 3);

INSERT INTO TB_CLIENTE (NUMERO_CNH, VALIDADE_CNH, FK_PESSOA) VALUES
('AB1234567', '2026-05-15', 1),
('BC2345678', '2025-08-10', 2),
('CD3456789', '2027-02-20', 3);

INSERT INTO TB_VEICULO (PLACA, MODELO, MARCA, ANO, CATEGORIA, COR, STATUS) VALUES
('ABC1234', 'Onix', 'Chevrolet', 2020, 'Econômico', 'Preto', 'Disponível'),
('DEF5678', 'Corolla', 'Toyota', 2021, 'Executivo', 'Branco', 'Alugado'),
('GHI9012', 'HR-V', 'Honda', 2019, 'SUV', 'Cinza', 'Manutenção');

INSERT INTO TB_RESERVA (DATA_INICIO, DATA_FIM, VALOR_DIARIA, STATUS, FK_CLIENTE, FK_VEICULO) VALUES
('2024-10-20', '2024-10-25', 120.50, 'Ativa', 1, 1),
('2024-10-18', '2024-10-22', 200.75, 'Concluída', 2, 2),
('2024-10-19', '2024-10-26', 150.00, 'Ativa', 3, 3);

INSERT INTO TB_PAGAMENTO (VALOR_TOTAL, FORMA_PAGAMENTO, DATA_PAGAMENTO, STATUS, FK_RESERVA) VALUES
(602.50, 'Cartão de Crédito', '2024-10-25', 'Pago', 1),
(803.00, 'Boleto', '2024-10-22', 'Pago', 2),
(1050.00, 'Cartão de Débito', '2024-10-26', 'Aguardando Pagamento', 3);
