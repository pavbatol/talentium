INSERT into users (email, username, password, enabled, first_name, registered_on)
select 'admin@aaa.ru','admin@aaa.ru','$2a$10$lAibskUsBRZfWF/BUpKQQOQ8JdsCWpaF2lV3dXaLR9UtaL557jM5y',true,'Админей',current_date
    where not exists (select 1 from users where email = 'admin@aaa.ru');
INSERT into users (email, username, password, enabled, first_name, registered_on)
select 'user@aaa.ru','user@aaa.ru','$2a$10$WHkIVZMfDn4HJxl9Czz.1eGmDEgPZlaFo3epTnyumkpSWtZiqVE/G', true, 'Юзерей',current_date
    where not exists (select 1 from users where email = 'user@aaa.ru');

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

insert into users_roles (user_id, role_id)
select u_id, r_id
from
    (select u.user_id u_id, (case when r.role_id is null then r2.role_id else r.role_id END) r_id
     from users u
              left join roles r on u.email like 'admin@%' and (r.role_name = 'ADMIN' or r.role_name = 'USER')
              left join roles r2 on u.email like 'user@%' and r2.role_name = 'USER'
     where r.role_id  is not null or r2.role_id  is not null)  u_id_r_id
where not exists (select 1 from users_roles ur where ur.user_id = u_id and ur.role_id = r_id);
