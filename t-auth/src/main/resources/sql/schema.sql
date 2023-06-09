CREATE SEQUENCE IF NOT EXISTS seq_users MINVALUE 0 START WITH 0 INCREMENT 1;
CREATE SEQUENCE IF NOT EXISTS seq_roles MINVALUE 0 START WITH 0 INCREMENT 1;
CREATE SEQUENCE IF NOT EXISTS seq_verify_tokens MINVALUE 0 START WITH 0 INCREMENT 1;

CREATE TABLE if not exists roles
(
--     role_id BIGINT generated by default as identity PRIMARY KEY not null,
    role_id BIGINT DEFAULT nextval('seq_roles')     PRIMARY KEY not null,
    role_name varchar(50) unique                                not null
);

create table if not exists users
(
--     user_id     bigint generated by default as identity not null,
    user_id     bigint DEFAULT nextval('seq_users')     not null,
    email       varchar(255)                            not null,
    username    varchar(255)                            not null,
    password    varchar(255)                            not null,
    enabled     boolean                                 not null,
    first_name  varchar(255)                            not null,
    second_name varchar(255),
    registered_on timestamp without time zone,
    constraint pk_users primary key (user_id),
    constraint uq_users_username unique (username),
    constraint uq_users_email unique (email)
    );

create table if not exists users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    CONSTRAINT fk_users_roules_to_user FOREIGN KEY (user_id) references users (user_id) ON DELETE CASCADE,
    CONSTRAINT fk_users_roules_to_role FOREIGN KEY (role_id) references roles (role_id) ON DELETE CASCADE,
    constraint pk_users_roles primary key (user_id, role_id)
);

CREATE TABLE if not exists verify_tokens
(
    token_id  bigint default nextval('seq_verify_tokens')   not null,
    token      varchar(255)                                 not null,
    expired_on timestamp without time zone                  not null,
    user_id    bigint,
    constraint pk_verification_tokens primary key (token_id),
    constraint fk_verification_tokens_on_user foreign key (user_id) references users (user_id) on delete cascade
);

-- CREATE SEQUENCE if not exists users_sequence MINVALUE 0 START WITH 0 INCREMENT 1;
-- ALTER TABLE users ALTER COLUMN user_id SET DEFAULT nextval('users_sequence');
--
-- CREATE SEQUENCE if not exists roles_sequence MINVALUE 0 START WITH 0 INCREMENT 1;
-- ALTER TABLE roles ALTER COLUMN role_id SET DEFAULT nextval('roles_sequence');