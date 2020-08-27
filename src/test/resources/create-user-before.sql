delete from usrs_roles;
delete from usrs;

insert into usrs(id, is_active, password, username) values
    (1, true, '$2a$08$lqlcyyyZp2kZRs6Ndhhf1.bbtjK/DT/.8Qcxyg9x7cL6Jfz07oML.', 'admin'),
    (2, true, '$2a$08$lqlcyyyZp2kZRs6Ndhhf1.bbtjK/DT/.8Qcxyg9x7cL6Jfz07oML.', 'user');

insert into  usrs_roles(user_id, roles) values
    (1, 'USER'), (1, 'ADMIN'),
    (2, 'USER');