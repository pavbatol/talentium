create sequence if not exists seq_hhs minvalue 0 start with 0 increment 1;
create table if not exists hhs
(
    hh_id         bigint default nextval('seq_hhs')       not null,
    user_id       bigint                                  not null,
    email         varchar(255)                            not null,
    first_name    varchar(255)                            not null,
    second_name   varchar(255)                            not null,
    authority     varchar(255)                            not null,
    address       varchar(255)                            not null,
    contacts      varchar(255)                            not null,
    registered_on timestamp without time zone  default now(),
    rate        integer default 0,
    deleted     boolean default false,
    constraint pk_hhs primary key (hh_id),
    constraint uc_hhs_email unique (email),
    constraint uc_hhs_user_id unique (user_id)
);

create sequence if not exists seq_managements minvalue 0 start with 0 increment 1;
create table if not exists managements
(
    management_id bigint default nextval('seq_managements') not null,
    mn_name    varchar(255)                              not null,
    constraint pk_managements primary key (management_id)
);

create table if not exists hhs_managements
(
    hh_id                 bigint not null,
    management_id         bigint not null,
    constraint pk_hhs_management primary key (hh_id, management_id),
    constraint fk_hhs_managements_on_hh foreign key (hh_id) references hhs (hh_id) on delete cascade,
    constraint fk_hhs_managements_on_management foreign key (management_id) references managements (management_id) on delete cascade
);

create sequence if not exists seq_curators minvalue 0 start with 0 increment 1;
create table if not exists curators
(
    curator_id    bigint default nextval('curators')  not null,
    user_id       bigint,
    email         varchar(255)                            not null,
    first_name    varchar(255)                            not null,
    second_name   varchar(255),
    registered_on timestamp without time zone             not null,
    deleted       boolean default false,
    owner         bigint,
    constraint pk_curators primary key (curator_id),
    constraint uc_curators_email unique (email),
    constraint uc_curators_user unique (user_id),
    constraint fk_curators_on_owner foreign key (owner) references hhs (hh_id)
);

