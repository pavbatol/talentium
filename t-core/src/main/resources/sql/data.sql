-- hhs
INSERT INTO hhs (user_id, email, first_name, second_name, authority, address, contacts, rate)
SELECT v.*
FROM (VALUES
          (2, 'hh_1@aaa.bb', 'first_nameHH', 'secondNameHH', 'ГКУ Инфогород', 'address', 'contacts', 0),
          (3, 'hh_2@aaa.bb', 'first_nameHH', 'secondNameHH', 'Агентство креативных индустрий', 'address', 'contacts', 0),
          (4, 'hh_3@aaa.bb', 'first_nameHH', 'secondNameHH', 'Департамент строительства города Москвы', 'address', 'contacts', 0)
     ) AS v (user_id, email, first_name, second_name, authority, address, contacts, rate)
WHERE NOT EXISTS (SELECT 1 FROM hhs WHERE user_id = v.user_id);

-- insert into hhs (user_id, email, first_name, second_name, authority, address, contacts, rate)
-- select 2, 'hh_1@aaa.bb', 'first_nameHH', 'secondNameHH', 'ГКУ Инфогород', 'address', 'contacts', 0
--     where not exists (select 1 from hhs where user_id=2);
-- insert into hhs (user_id, email, first_name, second_name, authority, address, contacts, rate)
-- select 3, 'hh_2@aaa.bb', 'first_nameHH', 'secondNameHH', 'Агентство креативных индустрий', 'address', 'contacts', 0
--     where not exists (select 1 from hhs where user_id=3);
-- insert into hhs (user_id, email, first_name, second_name, authority, address, contacts, rate)
-- select 4, 'hh_3@aaa.bb', 'first_nameHH', 'secondNameHH', 'Департамент строительства города Москвы', 'address', 'contacts', 0
--     where not exists (select 1 from hhs where user_id=4);

-- managements
insert into managements (mn_name)
select 'направление_1_работа с общественностью' where not exists (select 1 from managements where management_id = 0) union all
select 'направление_2_менеджмент' where not exists (select 1 from managements where management_id = 1) union all
select 'направление_3_статистика' where not exists (select 1 from managements where management_id = 2);

-- insert into managements (mn_name)
-- select 'направление_1_Работа с общественностью' where not exists (select 1 from managements where management_id = 0);
-- insert into managements (mn_name)
-- select 'направление_2_Менеджмент' where not exists (select 1 from managements where management_id = 1);
-- insert into managements (mn_name)
-- select 'направление_3_Статистика' where not exists (select 1 from managements where management_id = 2);

-- hhs_managements
INSERT INTO hhs_managements (hh_id, management_id)
SELECT *
FROM (VALUES (0, 0),
             (0, 1),
             (0, 2),
             (1, 2),
             (2, 1)) AS v (hh_id, management_id)
WHERE NOT EXISTS (SELECT 1 FROM hhs_managements WHERE hh_id = v.hh_id AND management_id = v.management_id);

-- insert into hhs_managements (hh_id, management_id)
-- select 0, 0 where not exists (select 1 from hhs_managements where hh_id = 0 AND management_id = 0);
-- insert into hhs_managements (hh_id, management_id)
-- select 0, 1 where not exists (select 1 from hhs_managements where hh_id = 0 AND management_id = 1);
-- insert into hhs_managements (hh_id, management_id)
-- select 0, 2 where not exists (select 1 from hhs_managements where hh_id = 0 AND management_id = 2);
-- insert into hhs_managements (hh_id, management_id)
-- select 1, 2 where not exists (select 1 from hhs_managements where hh_id = 1 AND management_id = 2);
-- insert into hhs_managements (hh_id, management_id)
-- select 2, 1 where not exists (select 1 from hhs_managements where hh_id = 2 AND management_id = 1);

--curators
INSERT INTO curators (user_id, email, first_name, second_name, registered_on, owner)
SELECT *
FROM (VALUES (5, 'curators_1@aaa.bb', 'first_name_curator_1', 'secondName_curator_1', NOW(), 0),
             (6, 'curators_2@aaa.bb', 'first_name_curator_2', 'secondName_curator_2', NOW(), 2),
             (7, 'curators_3@aaa.bb', 'first_name_curator_3', 'secondName_curator_3', now(), 2))
         AS new_curators(user_id, email, first_name, second_name, registered_on, owner)
WHERE NOT EXISTS (SELECT 1 FROM curators WHERE user_id = new_curators.user_id);

--mentors
INSERT INTO mentors(user_id, email, first_name, second_name, rate, registered_on, deleted, owner_id, management_id)
SELECT *
FROM (VALUES (8, 'mentor_1@aaa.bb', 'first_name_mentor_1', 'secondName_mentor_1', 0, NOW(), false, 0, 0),
             (9, 'mentor_2@aaa.bb', 'first_name_mentor_2', 'secondName_mentor_2', 0, NOW(), false, 2, 1),
             (10, 'mentor_3@aaa.bb', 'first_name_mentor_3', 'secondName_mentor_3', 0, NOW(), false, 2, 2))
         AS v(user_id, email, first_name, second_name, rate, registered_on, deleted, owner_id, management_id)
WHERE NOT EXISTS (SELECT 1 FROM mentors WHERE user_id = v.user_id);

--students
INSERT INTO students(user_id, email, first_name, second_name, position, level,  mentor_id, management_id, rate, registered_on, deleted)
SELECT *
FROM (VALUES (11, 'student_1@aaa.bb', 'first_name_student_1', 'secondName_student_1', 'CANDIDATE', null,  0, 0, 0, NOW(), false),
             (12, 'student_2@aaa.bb', 'first_name_student_2', 'secondName_student_2', 'CANDIDATE', null,  1, 1, 0, NOW(), false),
             (13, 'student_3@aaa.bb', 'first_name_student_3', 'secondName_student_3', 'CANDIDATE', null,  1, 2, 0, NOW(), false))
         AS v(user_id, email, first_name, second_name, position, level,  mentor_id, management_id, rate, registered_on, deleted)
WHERE NOT EXISTS (SELECT 1 FROM students WHERE user_id = v.user_id);

--internships
insert into internships(title, annotation, description, initiator_id, management_id, latitude, longitude, age_from, age_to, participant_limit, confirmed_requests, start_date, end_date, day_duration, state)
select
          'title_1', 'annotation_1', 'description', 0, 0, 55.7558, 37.6173, 18, 35, 4000, 0,
           CAST('2029-05-27T11:55:48.680' AS timestamp),
           CAST('2029-11-27T11:55:48.680' AS timestamp),
           'HALF', 'PENDING'
where not exists (select 1 from internships  where internship_id  = 0);
insert into internships(title, annotation, description, initiator_id, management_id, latitude, longitude, age_from, age_to, participant_limit, confirmed_requests, start_date, end_date, day_duration, state)
select
          'title_2', 'annotation_2', 'description', 1, 1, 55.7558, 37.6173, 18, 40, 5000, 0,
           CAST('2030-05-27T11:55:48.680' AS timestamp),
           CAST('2030-11-27T11:55:48.680' AS timestamp),
           'HALF', 'PENDING'
where not exists (select 1 from internships where internship_id = 1);
insert into internships(title, annotation, description, initiator_id, management_id, latitude, longitude, age_from, age_to, participant_limit, confirmed_requests, start_date, end_date, day_duration, state)
select
          'title_3', 'annotation_3', 'description', 1, 2, 55.7558, 37.6173, 18, 45, 6000, 0,
           CAST('2031-05-27T11:55:48.680' AS timestamp),
           CAST('2031-11-27T11:55:48.680' AS timestamp),
           'HALF', 'PENDING'
where not exists (select 1 from internships where internship_id = 2);

--countries
-- DO
-- $$
--     BEGIN
--         IF NOT EXISTS(SELECT 1 FROM countries) THEN
--             COPY countries (code, name_en, name_ru) FROM 'src/main/resources/sql/country-codes_short.csv' DELIMITER ',' CSV HEADER;
--         END IF;
--     END
-- $$;
-- insert into countries (code, name_en, name_ru)
-- select *
-- from (values ('AM', 'Armenia', 'Армения'),
--              ('AZ', 'Azerbaijan', 'Азербайджан'),
--              ('BY', 'Belarus', 'Беларусь'),
--              ('CN', 'China', 'Китай'),
--              ('IN', 'India', 'Индия'),
--              ('MD', 'Republic of Moldova', 'Республика Молдова'),
--              ('RO', 'Romania', 'Румыния'),
--              ('RU', 'Russian Federation', 'Российская Федерация'),
--              ('ZA', 'South Africa', 'Южная Африка'),
--              ('TJ', 'Tajikistan', 'Таджикистан'),
--              ('TR', 'Turkey', 'Турция'),
--              ('TM', 'Turkmenistan', 'Туркменистан'),
--              ('UZ', 'Uzbekistan', 'Узбекистан')) as v (code, name_en, name_ru)
-- where not exists(select 1 from countries where code = v.code);

--------------------------------------
-- create or replace function csv_import(filename text)
--     returns table (code text, name_en text, name_ru text) as $$
-- begin
-- return query execute format('
--          select *
--          from pg_catalog.pg_read_csv_file(%l,
--             ''code'' text,
--             ''name_en'' text,
--             ''name_ru'' text
--          ) as csv', filename);
-- end;
-- $$
-- language plpgsql;
--
-- create or replace function csv_import(filename text)
--     returns table (code text, name_en text, name_ru text) as $$
-- begin
-- return query execute format('
--     select *
--     from pg_catalog.pg_read_csv_file(%L,
--     ''code'' text,
--     ''name_en'' text,
--     ''name_ru'' text
--     ) as csv', filename);
-- end;
-- $$
-- language plpgsql;
--
-- create or replace function csv_import(filename text)
--     returns table (code varchar, name_en varchar, name_ru varchar) as $$
-- begin
--     return query execute format('
--     select *
--     from pg_catalog.pg_read_csv_file(%L,
--     ''code'' varchar,
--     ''name_en'' varchar,
--     ''name_ru'' varchar
--     ) as csv', filename);
-- end;
-- $$
-- language plpgsql;
--
-- create or replace function csv_import(filename text)
--     returns table (code varchar, name_en varchar, name_ru varchar) as $$
-- begin
--     return query execute format('
--     select *
--     from pg_catalog.pg_read_csv_file(%L,
--     ''code'' varchar,
--     ''name_en'' varchar,
--     ''name_ru'' varchar
--     ) as csv(code varchar, name_en varchar, name_ru varchar)', filename);
-- end;
-- $$
-- language plpgsql;
--
-- create or replace function csv_import(filename text)
--     returns table (code varchar, name_en varchar, name_ru varchar) as $$
-- begin
--     return query execute format('
--     select *
--     from pg_catalog.pg_read_csv_file(%L,
--     ''code'',
--     ''name_en'',
--     ''name_ru''
--     ) as csv(code , name_en , name_ru )', filename);
-- end;
-- $$
-- language plpgsql;
--
--
-- -- create or replace function csv_import(filename text)
-- --     returns setof record as $$
-- -- begin
-- --     return query execute format('
-- --     select *
-- --     from pg_catalog.pg_read_csv_file(%L,
-- --     ''code'' varchar(2),
-- --     ''name_en'' varchar(70),
-- --     ''name_ru'' varchar(70)
-- --     ) as csv', filename);
-- -- end;
-- -- $$
-- -- language plpgsql;
--
-- CREATE TEMPORARY TABLE temp_countries (
--                                           code varchar(2),
--                                           name_en varchar(70),
--                                           name_ru varchar(70)
-- );
--
-- INSERT INTO temp_countries (code, name_en, name_ru)
-- SELECT code, name_en, name_ru FROM csv_import('country-codes_short.csv');
--
-- INSERT INTO countries (country_id, code, name_en, name_ru)
-- SELECT nextval('seq_countries'), code, name_en, name_ru FROM temp_countries;
--
--
--
--
-- INSERT INTO countries (code, name_en, name_ru)
--     SELECT code, name_en, name_ru
--     FROM csv_import('classpath:sql/country-codes_short.csv');
--
--
-- create or replace function csv_import(filename text)
--     returns table (code text, name_en text, name_ru text) as $$
-- begin
--     return query execute format('
--     select *
--     from pg_catalog.pg_read_csv_file(%L,
--     ''code'' text,
--     ''name_en'' text,
--     ''name_ru'' text
--     ) as csv(code, name_en, name_ru)', filename);
-- end;
-- $$
--     language plpgsql;
-- INSERT INTO countries (code, name_en, name_ru)
-- SELECT *
-- FROM csv_import('/home/sergey/HDD/1_Study_Sergey/Projects_java/talentium/t-core/src/main/resources/sql/country-codes_short.csv') AS csv;
--
-- drop function if exists csv_import;
----------------------------
