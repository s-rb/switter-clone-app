create table message_likes (
    user_id bigint not null references usrs,
    message_id bigint not null references message,
    primary key (user_id, message_id)
)