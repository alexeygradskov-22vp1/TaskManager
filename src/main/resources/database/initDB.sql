create table if not exists users
(
    id       bigserial primary key unique,
    login    varchar(200) not null unique,
    password varchar(200) not null,
    email    varchar(200) not null,
    locked bool not null,
    reg_date timestamp,
    last_activity_date timestamp

);

create table if not exists boards
(
    id          bigserial primary key unique,
    id_of_user  bigserial references users (id) on delete cascade not null,
    board_name  varchar(200)                                      not null,
    description varchar(400)
);
create table if not exists cards
(
    id          bigserial primary key unique,
    id_of_board bigserial references boards (id) on delete cascade not null,
    card_name   varchar(200)                                       not null,
    description varchar(400)
);
create table if not exists task
(
    id          bigserial primary key unique,
    id_of_cards bigserial references cards (id) on delete cascade not null,
    task_name   varchar(200)                                      not null,
    description varchar(400),
    end_time    timestamp,
    status      int                                               not null
);
create table if not exists roles
(
    id   bigserial primary key unique,
    name varchar(200) not null
);
CREATE TABLE if not exists users_roles
(
    user_id INTEGER REFERENCES users (id),

    role_id INTEGER REFERENCES roles (id),

    CONSTRAINT user_roles PRIMARY KEY (user_id, role_id)
);



