insert into hhs (user_id, email, first_name, second_name, authority, address, contacts, rate)
select 2, 'hh_1@aaa.bb', 'first_nameHH', 'secondNameHH', 'ГКУ Инфогород', 'address', 'contacts', 0
    where not exists (select 1 from hhs where user_id=2);
insert into hhs (user_id, email, first_name, second_name, authority, address, contacts, rate)
select 3, 'hh_2@aaa.bb', 'first_nameHH', 'secondNameHH', 'Агентство креативных индустрий', 'address', 'contacts', 0
    where not exists (select 1 from hhs where user_id=3);
insert into hhs (user_id, email, first_name, second_name, authority, address, contacts, rate)
select 4, 'hh_3@aaa.bb', 'first_nameHH', 'secondNameHH', 'Департамент строительства города Москвы', 'address', 'contacts', 0
    where not exists (select 1 from hhs where user_id=4);



insert into managements (mn_name)
select 'management_1' where not exists (select 1 from managements where management_id = 0);
insert into managements (mn_name)
select 'management_2' where not exists (select 1 from managements where management_id = 1);
insert into managements (mn_name)
select 'management_3' where not exists (select 1 from managements where management_id = 2);

insert into hhs_managements (hh_id, management_id)
select 0, 0 where not exists (select 1 from hhs_managements where hh_id = 0 AND management_id = 0);
insert into hhs_managements (hh_id, management_id)
select 0, 1 where not exists (select 1 from hhs_managements where hh_id = 0 AND management_id = 1);
insert into hhs_managements (hh_id, management_id)
select 0, 2 where not exists (select 1 from hhs_managements where hh_id = 0 AND management_id = 2);

insert into hhs_managements (hh_id, management_id)
select 1, 2 where not exists (select 1 from hhs_managements where hh_id = 1 AND management_id = 2);

insert into hhs_managements (hh_id, management_id)
select 2, 1 where not exists (select 1 from hhs_managements where hh_id = 2 AND management_id = 1);
