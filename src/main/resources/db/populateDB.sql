TRUNCATE user_roles;
TRUNCATE meals;
TRUNCATE users CASCADE;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('User', 'user@yandex.ru', 'password'),
('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_USER', 100000),
('ROLE_ADMIN', 100001);

insert into meals (user_id,date_time,description,calories)
select t.user_id, t.date_time, t.description, t.calories from
    (select 1 ord, 100000 user_id, to_timestamp('30/01/2020 10:00','dd/mm/yyyy hh24:mi') date_time, 'Завтрак' description, 500 calories
     union
     select 2 ord, 100000 user_id, to_timestamp('30/01/2020 13:00','dd/mm/yyyy hh24:mi') date_time, 'Обед' description, 1000 calories
     union
     select 3 ord, 100000 user_id, to_timestamp('30/01/2020 20:00','dd/mm/yyyy hh24:mi') date_time, 'Ужин' description, 500 calories
     union
     select 4 ord, 100000 user_id, to_timestamp('31/01/2020 00:00','dd/mm/yyyy hh24:mi') date_time, 'Еда на ограничение' description, 100 calories
     union
     select 5 ord, 100000 user_id, to_timestamp('31/01/2020 10:00','dd/mm/yyyy hh24:mi') date_time, 'Завтрак' description, 1000 calories
     union
     select 6 ord, 100000 user_id, to_timestamp('31/01/2020 13:00','dd/mm/yyyy hh24:mi') date_time, 'Обед' description, 500 calories
     union
     select 7 ord, 100000 user_id, to_timestamp('31/01/2020 20:00','dd/mm/yyyy hh24:mi') date_time, 'Ужин' description, 410 calories) t
order by t.ord;

SELECT * FROM meals;
SELECT * FROM user_roles;
SELECT * FROM users;
