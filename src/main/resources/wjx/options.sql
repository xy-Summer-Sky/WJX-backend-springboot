create table if not exists options
(
    id          bigint auto_increment
        primary key,
    option_text varchar(255) not null,
    question_id bigint       null,
    answer_text varchar(255) null,
    `order`     bigint       null,
    constraint options_ibfk_1
        foreign key (question_id) references questions (id)
            on delete cascade
);

create index question_id
    on options (question_id);

