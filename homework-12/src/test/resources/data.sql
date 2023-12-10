insert into genres (`name`)
values ('Техническая литература'),
       ('Сказки про код');


insert into authors (`first_name`, `last_name`)
values ('Роберт', 'Мартин' ),
       ('Сказочник', 'Петров');

insert into books (`title`, `publish_year`, `author_id`, `genre_id`)
values ('Чистый код', 2019, 1, 1)

    insert into users (`username`, `password`, `enabled`)
values ('user', '$2a$10$XsmmE0a/ZFpdRKAICRxwre67OfT5XjUGe7slRCyxWIACnD9d0GIKe', true);