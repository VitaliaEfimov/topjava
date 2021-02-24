TRUNCATE user_roles;
TRUNCATE meals;
TRUNCATE users CASCADE;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('User', 'user@yandex.ru', 'password'),
('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
('USER', 100000),
('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
(100000, to_timestamp('30/01/2020 10:00','dd/mm/yyyy hh24:mi'),'Завтрак 0', 500),
(100000, to_timestamp('30/01/2020 13:00','dd/mm/yyyy hh24:mi'), 'Обед 0', 1000),
(100000, to_timestamp('30/01/2020 20:00','dd/mm/yyyy hh24:mi'), 'Ужин 0', 500),
(100000, to_timestamp('31/01/2020 00:00','dd/mm/yyyy hh24:mi'), 'Еда на ограничение 0', 100),
(100000, to_timestamp('31/01/2020 10:00','dd/mm/yyyy hh24:mi'), 'Завтрак 0' , 1000),
(100000, to_timestamp('31/01/2020 13:00','dd/mm/yyyy hh24:mi'), 'Обед 0' , 500),
(100000, to_timestamp('31/01/2020 20:00','dd/mm/yyyy hh24:mi'), 'Ужин 0', 410),
(100001, to_timestamp('30/01/2020 10:00','dd/mm/yyyy hh24:mi'),'Завтрак 1', 500),
(100001, to_timestamp('30/01/2020 13:00','dd/mm/yyyy hh24:mi'), 'Обед 1', 1000),
(100001, to_timestamp('30/01/2020 20:00','dd/mm/yyyy hh24:mi'), 'Ужин 1', 500),
(100001, to_timestamp('31/01/2020 00:00','dd/mm/yyyy hh24:mi'), 'Еда на ограничение 1', 100),
(100001, to_timestamp('31/01/2020 10:00','dd/mm/yyyy hh24:mi'), 'Завтрак 1' , 1000),
(100001, to_timestamp('31/01/2020 13:00','dd/mm/yyyy hh24:mi'), 'Обед 1' , 500),
(100001, to_timestamp('31/01/2020 20:00','dd/mm/yyyy hh24:mi'), 'Ужин 1', 410);
