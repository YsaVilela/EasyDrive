-- Inserções para tb_endereco
INSERT INTO tb_endereco (cep, logradouro, numero, complemento, bairro, FK_CIDADE) VALUES
('01001-000', 'Avenida Paulista', 1000, 'Apto 101', 'Bela Vista', 1),
('13083-970', 'Rua Heitor Penteado', 500, NULL, 'Taquaral', 2),
('20040-020', 'Rua da Assembleia', 50, 'Sala 203', 'Centro', 3),
('30130-110', 'Avenida Afonso Pena', 1500, NULL, 'Centro', 4),
('80010-110', 'Rua XV de Novembro', 200, 'Conj. 5', 'Centro', 5);

-- Inserções para tb_pessoa
INSERT INTO tb_pessoa (nome, cpf, data_nascimento, telefone, email, data_cadastro, numero_cnh, validade_cnh, status, FK_ENDERECO) VALUES
('João Silva', '123.456.789-00', '1985-06-15', '(11) 99999-9999', 'joao.silva@example.com', '2023-01-10', '1234567890', '2025-06-15', 'Ativo', 1),
('Maria Souza', '987.654.321-00', '1990-08-20', '(19) 98888-8888', 'maria.souza@example.com', '2023-02-12', '9876543210', '2026-08-20', 'Ativo', 2),
('Carlos Pereira', '456.789.123-00', '1982-03-10', '(21) 97777-7777', 'carlos.pereira@example.com', '2023-03-15', '4567891230', '2024-03-10', 'Inativo', 3),
('Ana Lima', '321.654.987-00', '1995-12-05', '(31) 96666-6666', 'ana.lima@example.com', '2023-04-18', '3216549870', '2027-12-05', 'Ativo', 4),
('Fernanda Costa', '789.123.456-00', '1988-11-22', '(41) 95555-5555', 'fernanda.costa@example.com', '2023-05-20', '7891234560', '2025-11-22', 'Ativo', 5);

-- Inserções para tb_funcionario
INSERT INTO tb_funcionario (cargo, FK_PESSOA) VALUES
('Gerente', 1),
('Atendente', 2),
('Mecânico', 3),
('Vendedor', 4),
('Recepcionista', 5);

-- Inserções para tb_plano_assinatura
INSERT INTO tb_plano_assinatura (nome, pontos_minimos) VALUES
('Bronze', 100),
('Prata', 200),
('Ouro', 300),
('Platina', 400),
('Diamante', 500);

-- Inserções para tb_cliente
INSERT INTO tb_cliente (pontuacao, FK_PLANO_ASSINATURA, FK_PESSOA) VALUES
(150, 1, 1),
(250, 2, 2),
(350, 3, 3),
(450, 4, 4),
(550, 5, 5);

-- Inserções para tb_veiculo
INSERT INTO tb_veiculo (placa, modelo, marca, ano, quilometragem, categoria, tipo_combustivel, status, FK_ENDERECO) VALUES
('ABC-1234', 'Civic', 'Honda', 2020, 15000, 'Sedan', 'Gasolina', 'Disponível', 1),
('DEF-5678', 'Corolla', 'Toyota', 2019, 20000, 'Sedan', 'Flex', 'Disponível', 2),
('GHI-9012', 'Onix', 'Chevrolet', 2021, 10000, 'Hatch', 'Flex', 'Manutenção', 3),
('JKL-3456', 'HB20', 'Hyundai', 2018, 25000, 'Hatch', 'Gasolina', 'Alugado', 4),
('MNO-7890', 'Compass', 'Jeep', 2022, 5000, 'SUV', 'Diesel', 'Disponível', 5);

-- Inserções para tb_reserva
INSERT INTO tb_reserva (FK_VEICULO, FK_CLIENTE, data_inicio, data_prevista_fim, data_fim, orcamento, valor_final, status) VALUES
(1, 1, '2023-06-01 09:00:00', '2023-06-05 18:00:00', NULL, 500.00, NULL, 'Reservado'),
(2, 2, '2023-07-10 10:00:00', '2023-07-15 18:00:00', '2023-07-15 17:00:00', 600.00, 580.00, 'Concluído'),
(3, 3, '2023-08-20 08:00:00', '2023-08-25 18:00:00', NULL, 450.00, NULL, 'Cancelado'),
(4, 4, '2023-09-05 09:30:00', '2023-09-10 18:00:00', NULL, 700.00, NULL, 'Reservado'),
(5, 5, '2023-10-15 11:00:00', '2023-10-20 18:00:00', NULL, 800.00, NULL, 'Reservado');

-- Inserções para tb_pagamento
INSERT INTO tb_pagamento (FK_RESERVA, valor, forma_pagamento, data_pagamento, status) VALUES
(1, 500.00, 'Cartão de Crédito', '2023-06-01 09:30:00', 'Pago'),
(2, 580.00, 'Boleto', '2023-07-10 11:00:00', 'Pago'),
(3, 450.00, 'Transferência', NULL, 'Cancelado'),
(4, 700.00, 'Cartão de Débito', NULL, 'Pendente'),
(5, 800.00, 'Pix', '2023-10-15 12:00:00', 'Pago');

-- Inserções para tb_servico
INSERT INTO tb_servico (valor_base) VALUES
(100),
(150),
(200),
(250),
(300);

-- Inserções para tb_servico_reserva
INSERT INTO tb_servico_reserva (FK_servico, FK_reserva) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);