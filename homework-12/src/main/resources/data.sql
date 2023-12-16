insert into genres (`name`)
values ('Техническая литература'),
       ('Фантастика'),
       ('Нехудожественная литература');


insert into authors (`first_name`, `last_name`)
values ('Роберт', 'Мартин' ),
       ('Герберт', 'Шилдт'),
       ('Юрий', 'Никитин'),
       ('Ник', 'Перумов'),
       ('Максим', 'Дорофеев');

insert into books (`title`, `publish_year`, `author_id`, `genre_id`)
values ('Чистый код', 2019, 1, 1),
       ('Чистая архитектура', 2021, 1, 1),
       ('Java. Полное руководство. Десятое издание', 2018, 2, 1),
       ('Трое из леса', 2007, 3, 2);

insert into users (`username`, `password`, `enabled`)
values ('user', '$2a$10$XsmmE0a/ZFpdRKAICRxwre67OfT5XjUGe7slRCyxWIACnD9d0GIKe', true);
