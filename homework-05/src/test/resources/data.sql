insert into genres (`name`)
values ('Техническая литература'),
       ('Сказки про код');


insert into authors (`first_name`, `last_name`)
values ('Роберт', 'Мартин' ),
       ('Сказочник', 'Петров');

insert into books (`title`, `year`, `author_id`, `genre_id`)
values ('Чистый код', 2019, 1, 1)
