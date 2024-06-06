create table if not exists users
(
    id       int auto_increment
        primary key,
    name     varchar(255) not null,
    age      smallint     null,
    gender   smallint     null,
    phone    varchar(20)  null,
    password varchar(255) not null,
    email    varchar(255) not null,
    constraint email
        unique (email)
);

