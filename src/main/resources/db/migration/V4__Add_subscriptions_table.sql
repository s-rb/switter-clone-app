CREATE TABLE user_subscriptions (
    channel_id int8 not null references usrs,
    subscriber_id int8 not null references usrs,
    primary key (channel_id, subscriber_id)
)