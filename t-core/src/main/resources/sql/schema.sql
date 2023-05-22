create sequence if not exists seq_hhs minvalue 0 start with 0 increment 1;
create table if not exists hhs
(
    hh_id         bigint default nextval('seq_hhs')       not null,
    user_id       bigint                                  not null,
    email         varchar(255)                            not null,
    first_name    varchar(255)                            not null,
    second_name   varchar(255)                            not null,
    authority     varchar(255)                            not null,
    management    varchar(255)                            not null,
    address       varchar(255)                            not null,
    contacts      varchar(255)                            not null,
    registered_on timestamp without time zone  default now(),
    rate        integer default 0,
    deleted     boolean default false,
    constraint pk_hhs primary key (hh_id),
    constraint uc_hhs_email unique (email),
    constraint uc_hhs_user_id unique (user_id)
);
