create table if not exists responses
(
    id          bigint auto_increment
        primary key,
    answer_text text   not null,
    question_id bigint null,
    constraint responses_ibfk_1
        foreign key (question_id) references questions (id)
            on delete cascade
);

create index question_id
    on responses (question_id);

