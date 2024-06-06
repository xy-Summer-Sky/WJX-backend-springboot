create table if not exists questions
(
    id        bigint auto_increment
        primary key,
    text      varchar(255) not null,
    type      varchar(255) not null,
    survey_id bigint       null,
    `order`   bigint       null
);

create index survey_id
    on questions (survey_id);

