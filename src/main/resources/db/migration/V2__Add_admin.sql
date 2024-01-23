select nextval('hibernate_sequence');

insert into usrs (email, is_active, password, username, id)
values ('surkov.r.b@gmail.com', true, '$2a$08$Vq8XQkXz1MmI0Ag7vBewC.BbenKQPMARTfjS95sNHvKi5bvqQpIEO', 'admin', 1);
insert into usrs_roles (user_id, roles)
values (1, 'USER');
insert into usrs_roles (user_id, roles)
values (1, 'ADMIN');

select nextval('hibernate_sequence');

insert into usrs (email, is_active, password, username, id)
values ('surkoff.com@gmail.com', true, '$2a$08$DkUuHQrMxPHuOAGTBEALy.2BSanmqQaq3W/BPxf/AhZGF2J8IZT.i', 'user', 2);
insert into usrs_roles (user_id, roles)
values (2, 'USER');