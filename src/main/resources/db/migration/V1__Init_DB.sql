create sequence hibernate_sequence start 1 increment 1;
create table message
(
    id       int8 generated by default as identity,
    filename varchar(255),
    tag      varchar(255),
    text     varchar(2048),
    user_id  int8,
    primary key (id)
);
create table message_likes
(
    message_id int8 not null,
    user_id    int8 not null,
    primary key (message_id, user_id)
);
create table user_subscriptions
(
    subscriber_id int8 not null,
    channel_id    int8 not null,
    primary key (channel_id, subscriber_id)
);
create table usrs
(
    id              int8    not null,
    activation_code varchar(255),
    email           varchar(255),
    is_active       boolean not null,
    password        varchar(255),
    username        varchar(255),
    primary key (id)
);
create table usrs_roles
(
    user_id int8 not null,
    roles   varchar(255)
);
alter table if exists message
    add constraint FK6d262wmhcnjwb6djo75orp46j foreign key (user_id) references usrs;
alter table if exists message_likes
    add constraint FKln7xpsmeamu796o4t4a0cg8bs foreign key (user_id) references usrs;
alter table if exists message_likes
    add constraint FKbldk7l0d886p3mfscd4ti3ppn foreign key (message_id) references message;
alter table if exists user_subscriptions
    add constraint FKmptsm3lpxgkh9ai5pjq8tty59 foreign key (channel_id) references usrs;
alter table if exists user_subscriptions
    add constraint FKpxnw5lbtt0mkor1v181aj89ds foreign key (subscriber_id) references usrs;
alter table if exists usrs_roles
    add constraint FKthp6sujidd1v38560pk6qt7ij foreign key (user_id) references usrs;