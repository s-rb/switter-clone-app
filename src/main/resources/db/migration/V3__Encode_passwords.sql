create extension if not exists pgcrypto;

-- Используется соль для зашифрованного пароля. Сила/сложность - 8. bf - алгоритм
update usrs set password = crypt(password, gen_salt('bf', 8));