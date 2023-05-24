-- hhs
INSERT INTO hhs (user_id, email, first_name, second_name, authority, address, contacts, rate)
SELECT v.*
FROM (VALUES
          (2, 'hh_1@aaa.bb', 'first_nameHH', 'secondNameHH', 'ГКУ Инфогород', 'address', 'contacts', 0),
          (3, 'hh_2@aaa.bb', 'first_nameHH', 'secondNameHH', 'Агентство креативных индустрий', 'address', 'contacts', 0),
          (4, 'hh_3@aaa.bb', 'first_nameHH', 'secondNameHH', 'Департамент строительства города Москвы', 'address', 'contacts', 0)
     ) AS v (user_id, email, first_name, second_name, authority, address, contacts, rate)
WHERE NOT EXISTS (SELECT 1 FROM hhs WHERE user_id = v.user_id);

-- managements
insert into managements (mn_name)
select 'направление_1_работа с общественностью' where not exists (select 1 from managements where management_id = 0) union all
select 'направление_2_менеджмент' where not exists (select 1 from managements where management_id = 1) union all
select 'направление_3_статистика' where not exists (select 1 from managements where management_id = 2);

-- hhs_managements
INSERT INTO hhs_managements (hh_id, management_id)
SELECT *
FROM (VALUES (0, 0),
             (0, 1),
             (0, 2),
             (1, 2),
             (2, 1)) AS v (hh_id, management_id)
WHERE NOT EXISTS (SELECT 1 FROM hhs_managements WHERE hh_id = v.hh_id AND management_id = v.management_id);

--curators
INSERT INTO curators (user_id, email, first_name, second_name, registered_on, owner)
SELECT *
FROM (VALUES (5, 'curators_1@aaa.bb', 'first_name_curator_1', 'secondName_curator_1', NOW(), 0),
             (6, 'curators_2@aaa.bb', 'first_name_curator_2', 'secondName_curator_2', NOW(), 2),
             (7, 'curators_3@aaa.bb', 'first_name_curator_3', 'secondName_curator_3', now(), 2))
         AS new_curators(user_id, email, first_name, second_name, registered_on, owner)
WHERE NOT EXISTS (SELECT 1 FROM curators WHERE user_id = new_curators.user_id);