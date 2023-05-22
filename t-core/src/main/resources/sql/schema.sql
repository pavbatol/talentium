create sequence if not exists seq_hhs minvalue 0 start with 0 increment 1;
create table if not exists hhs
(
    hh_id       bigint default nextval('seq_hhs')       not null,
    user_id     bigint                                  not null,
    email       varchar(255)                            not null,
    first_name  varchar(255)                            not null,
    second_name varchar(255),
    authority   varchar(255),
    management  varchar(255),
    address     varchar(255),
    contacts    varchar(255),
    rate        INTEGER DEFAULT 0,
    constraint pk_hhs primary key (hh_id),
    constraint uc_hhs_email unique (email),
    constraint uc_hhs_user_id unique (user_id)
);
