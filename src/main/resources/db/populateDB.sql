DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, timestamp, description)  VALUES
  (100000, '2015-05-30 10:0:0', 'Завтрак'),
  (100000, '2015-05-30 13:0:0', 'Обед'),
  (100000, '2015-05-30 20:0:0', 'Ужин');
