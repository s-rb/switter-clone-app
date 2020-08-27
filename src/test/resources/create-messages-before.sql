delete
from message;

insert into message(id, text, tag, user_id)
values (1, 'first msg', 'tag', 1),
       (2, 'second', 'tag2', 1),
       (3, 'third msg', 'tag', 1),
       (4, 'fourth', 'not tag', 1);

alter sequence message_id_seq restart with 10;