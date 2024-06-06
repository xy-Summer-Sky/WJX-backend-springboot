create table if not exists surveys
(
    id          bigint auto_increment
        primary key,
    title       varchar(255)                        not null,
    description text                                null,
    created_by  int                                 null,
    created_at  timestamp default CURRENT_TIMESTAMP null,
    constraint surveys_ibfk_1
        foreign key (created_by) references users (id)
);

create index created_by
    on surveys (created_by);

