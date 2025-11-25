## 🚀 Instruções de Teste e Configuração Inicial
Para testar as funcionalidades de login e agendamento da aplicação, siga os passos abaixo para popular seu banco de dados PostgreSQL.
1. Certifique-se de que o banco de dados shalomplus esteja criado e que as configurações no application.properties estejam corretas
2. Coloque os Scripts a seguir:

```sql
-- Inserir SECRETÁRIO
INSERT INTO Secretario (nome, cpf, email, senha) VALUES
('Luan', '111.111.111-11', 'secretario@shalom.com', '123');
SELECT * FROM Secretario;

--Inserir profissional
INSERT INTO Profissional (nome, cr, especialidade, senha) VALUES
('Dr. Pedro', 'CRP-13/12345', 'Psicólogo', '123'),
('Dra. Duda Domingos', 'CRM/PB-9876', 'Pediatra', '123');
select * from profissional;

--Inserir alunos
INSERT INTO Aluno (nome, matricula, idade, turma, ativo, senha) VALUES
('Leticia', '000', 17, 'redes', TRUE, '123');
INSERT INTO Aluno (nome, matricula, idade, turma, ativo, senha) VALUES
('Laila', '0111', 17, 'tsi', TRUE, '123'); 
select * from aluno;

--Inserir atendimentos
INSERT INTO Atendimento (aluno_id, profissional_id, data_hora, tipo, observacoes, realizado) VALUES
(1, 1, '2025-11-25 10:00:00', 'Psicológico', 'Avaliação inicial de humor.', FALSE);
INSERT INTO Atendimento (aluno_id, profissional_id, data_hora, tipo, observacoes, realizado) VALUES
(2, 2, '2025-11-25 10:00:00', 'Médica', 'Avaliação inicial.', FALSE);
select * from atendimento;
