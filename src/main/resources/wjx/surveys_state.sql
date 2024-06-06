create table if not exists surveys_state
(
    survey_id     bigint       not null,
    id            int auto_increment
        primary key,
    receiveNumber int          not null,
    state         varchar(255) null,
    constraint surveys_state_pk
        unique (survey_id),
    constraint survey_id
        foreign key (survey_id) references surveys (id)
);

