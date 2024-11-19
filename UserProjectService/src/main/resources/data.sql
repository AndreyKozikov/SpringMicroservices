INSERT INTO users (user_name, email, role)
VALUES
( 'admin', 'admin@example.com', 'ROLE_ADMIN'),
('user','user@example.com', 'ROLE_USER');


INSERT INTO projects (name, description, created_date)
VALUES
('Проект А', 'Описание проекта А', '2024-01-01'),
('Проект Б', 'Описание проекта Б', '2023-02-15'),
('Проект B', 'Описание проекта B', '2022-02-15'),
('Проект Г', 'Описание проекта Г', '2023-02-15'),
('Проект Д', 'Описание проекта Д', '2024-06-15'),
('Проект Е', 'Описание проекта Е', '2024-03-10');

INSERT INTO users_project (project_id, user_id)
VALUES
('1', '2'),
('3', '2'),
('5', '2');