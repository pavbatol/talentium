CREATE OR REPLACE FUNCTION getUserValuesIfUserNotExists(
    unique_email varchar,
    unique_username varchar,
    pass varchar,
    enabled boolean,
    first_name varchar,
    registered_on timestamp without time zone) returns table(
        email_resp varchar,
        username_resp varchar,
        pass_resp varchar,
        enabled_resp boolean,
        first_name_resp varchar,
        registered_on_resp timestamp without time zone) as
'
begin
    return query select unique_email, unique_username, pass, enabled, first_name, registered_on
    where not exists (select 1 from users u where u.email=unique_email or u.username=unique_username);
end
'
language plpgsql;

-- admin, user
INSERT into users (email, username, password, enabled, first_name, registered_on)
select * from  getUserValuesIfUserNotExists('admin@aaa.ru','admin@aaa.ru','$2a$10$lAibskUsBRZfWF/BUpKQQOQ8JdsCWpaF2lV3dXaLR9UtaL557jM5y',true,'first_name_Admin',CURRENT_DATE);
INSERT into users (email, username, password, enabled, first_name,registered_on)
select * from getUserValuesIfUserNotExists('user@aaa.ru','user@aaa.ru','$2a$10$WHkIVZMfDn4HJxl9Czz.1eGmDEgPZlaFo3epTnyumkpSWtZiqVE/G', true, 'first_name_User',CURRENT_DATE);
-- Hh
INSERT into users (email, username, password, enabled, first_name, registered_on)
select 'hh_1@aaa.ru','hh_1@aaa.ru','$2a$10$qsIFdnBEDapGp3IgPCJ.ROew0mL1n9D7H7N3MbY/I.dls8qQG.7oi', true, 'first_name_Hh_1',now()
    where not exists (select 1 from users where email = 'hh_1@aaa.ru');
INSERT into users (email, username, password, enabled, first_name, registered_on)
select 'hh_2@aaa.ru','hh_2@aaa.ru','$2a$10$rKzNXBRn71vBxBnf9tmACekNmZB/vjqF3h7/FtTzJ0L0NZ1Ai./Py', true, 'first_name_Hh_2',now()
    where not exists (select 1 from users where email = 'hh_2@aaa.ru');
INSERT into users (email, username, password, enabled, first_name, registered_on)
select 'hh_3@aaa.ru','hh_3@aaa.ru','$2a$10$UatTmmnYZLfOHHaRPR5CSuQT6tS2ZyGSGQeb0F/ZxA8EN64dSE8Lm', true, 'first_name_Hh_3',now()
    where not exists (select 1 from users where email = 'hh_3@aaa.ru');
-- Curator
INSERT into users (email, username, password, enabled, first_name, registered_on)
select * from (
    values
        ('Curator_1@aaa.ru','Curator_1@aaa.ru','$2a$10$2zwt1ImDJ6gVdVifroJF9O5h0IZy8LUeq8C8X0tecQj17rFKTiVAK', true, 'first_name_Curator_1',now()),
        ('Curator_2@aaa.ru','Curator_2@aaa.ru','$2a$10$KBoljejlLqE4M2NRsWCxL.zeBD1qYNiK2Qxb2vxx622uGjC.v4mR2', true, 'first_name_Curator_2',now()),
        ('Curator_3@aaa.ru','Curator_3@aaa.ru','$2a$10$toLLOMfbKTeF3rYlzVpxHecVdd9ET.tZUJ4hHoaoAqALq/HbyjPB.', true, 'first_name_Curator_3',now())
    ) as v on conflict DO NOTHING ;

-- fill all roles
insert into roles (role_name)
select 'ADMIN' where not exists (select 1 from roles where role_name='ADMIN');
insert into roles (role_name)
select 'USER' where not exists (select 1 from roles where role_name='USER');
insert into roles (role_name)
select 'CANDIDATE' where not exists (select 1 from roles where role_name='CANDIDATE');
insert into roles (role_name)
select 'INTERN' where not exists (select 1 from roles where role_name='INTERN');
insert into roles (role_name)
select 'CURATOR' where not exists (select 1 from roles where role_name='CURATOR');
insert into roles (role_name)
select 'CURATOR' where not exists (select 1 from roles where role_name='CURATOR');
insert into roles (role_name)
select 'MENTOR' where not exists (select 1 from roles where role_name='MENTOR');
insert into roles (role_name)
select 'HH' where not exists (select 1 from roles where role_name='HH');

-- admin, user
insert into users_roles (user_id, role_id)
select u_id, r_id
from
    (select u.user_id u_id, (case when r.role_id is null then r2.role_id else r.role_id END) r_id
     from users u
              left join roles r on u.email like 'admin@%' and (r.role_name = 'ADMIN' or r.role_name = 'USER')
              left join roles r2 on u.email like 'user@%' and r2.role_name = 'USER'
     where r.role_id  is not null or r2.role_id  is not null)  u_id_r_id
where not exists (select 1 from users_roles ur where ur.user_id = u_id and ur.role_id = r_id);

-- Hh
insert into users_roles (user_id, role_id)
select 2, 6 where not exists (select 1 from users_roles where user_id = 2 and role_id = 6);
insert into users_roles (user_id, role_id)
select 3, 6 where not exists (select 1 from users_roles where user_id = 3 and role_id = 6);
insert into users_roles (user_id, role_id)
select 4, 6 where not exists (select 1 from users_roles where user_id = 4 and role_id = 6);
-- Curator
insert into users_roles (user_id, role_id)
select * from (
    values (5, 4),
           (6, 4),
           (7, 4)) as v on conflict DO NOTHING ;

drop function if exists getUserValuesIfUserNotExists;