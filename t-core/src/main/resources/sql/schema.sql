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
    curator_id    bigint default nextval('seq_curators')             not null,
    user_id       bigint                                             not null,
    email         varchar(255)                                       not null,
    first_name    varchar(255)                                       not null,
    second_name   varchar(255)                                       not null,
    registered_on timestamp without time zone default now()          not null,
    deleted       boolean default false,
    owner         bigint,
    constraint pk_curators primary key (curator_id),
    constraint uc_curators_email unique (email),
    constraint uc_curators_user_id unique (user_id),
    constraint fk_curators_on_owner foreign key (owner) references hhs (hh_id)
);

create sequence if not exists seq_mentors minvalue 0 start with 0 increment 1;
create table if not exists mentors
(
    mentor_id     bigint default nextval('seq_mentors')         not null,
    user_id       bigint                                        not null,
    email         varchar(255)                                  not null,
    first_name    varchar(255)                                  not null,
    second_name   varchar(255)                                  not null,
    rate          integer default 0,
    registered_on timestamp without time zone  default now()    not null,
    deleted       boolean default false,
    owner_id      bigint                                        not null,
    management_id bigint                                        not null,
    constraint pk_mentors primary key (mentor_id),
    constraint uc_mentors_email unique (email),
    constraint uc_mentors_user_id unique (user_id),
    constraint fk_mentors_on_owner foreign key (owner_id) references hhs (hh_id),
    constraint fk_mentors_on_management foreign key (management_id) references managements (management_id)
);

create sequence if not exists seq_students minvalue 0 start with 0 increment 1;
create table if not exists students
(
    student_id    bigint default nextval('seq_students')   not null,
    user_id       bigint,
    email         varchar(255)                              not null,
    first_name    varchar(255)                              not null,
    second_name   varchar(255)                              not null,
    position      varchar(255)                              not null,
    level         varchar(255),
    intern_on     timestamp without time zone,
    mentor_id     bigint,
    management_id bigint,
    rate          integer default 0,
    registered_on timestamp without time zone  default now() not null,
    deleted       boolean,
    constraint pk_students primary key (student_id),
    constraint uc_students_email unique (email),
    constraint uc_students_user_id unique (user_id),
    constraint fk_students_on_mentor foreign key (mentor_id) references mentors (mentor_id),
    constraint fk_students_on_management foreign key (management_id) references managements (management_id)
);

create sequence if not exists seq_internships minvalue 0 start with 0 increment 1;
create table if not exists internships
(
    internship_id      bigint default nextVal('seq_internships') not null,
    title              varchar(120)                              not null,
    annotation         varchar(2000)                             not null,
    description        varchar(7000)                             not null,
    initiator_id       bigint,
    management_id      bigint,
    latitude           double precision                          not null,
    longitude          double precision                          not null,
    age_from           integer                                   not null,
    age_to             integer                                   not null,
    participant_limit  integer default 0                         not null,
    confirmed_requests integer default 0                         not null,
    created_on         timestamp without time zone default now() not null,
    published_on       timestamp without time zone,
    start_date         timestamp without time zone               not null,
    end_date           timestamp without time zone               not null,
    day_duration       varchar(255)                              not null,
    state              varchar(255)                              not null,
    constraint pk_internships primary key (internship_id),
    constraint fk_internships_on_initiator foreign key (initiator_id) references hhs (hh_id),
    constraint fk_internships_on_management foreign key (management_id) references managements (management_id)
);

create sequence if not exists seq_countries minvalue 0 start with 0 increment 1;
create table if not exists countries (
    country_id  bigint default nextval('seq_countries') primary key,
    code        varchar(2)                                 not null,
    name_en     varchar(70)                                not null,
    name_ru     varchar(70)                                not null,
    constraint uc_countries_code unique (code)
);
create index if not exists idx_countries_code on countries (code);

create sequence if not exists seq_highschools minvalue 0 start with 0 increment 1;
create table if not exists highschools
(
    highschool_id bigint generated by default as identity not null,
    name          varchar(255)                            not null,
    type          varchar(15)                             not null,
    region        varchar(255),
    city          varchar(255),
    phone         varchar(255),
    website       varchar(255),
    constraint pk_highschools primary key (highschool_id),
    constraint uc_highschools_name unique (name)
);
create index if not exists idx_highschools_name on highschools (name);



