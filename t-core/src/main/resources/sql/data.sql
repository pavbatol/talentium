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

INSERT into users (email, username, password, enabled, first_name, registered_on)
select * from  getUserValuesIfUserNotExists('admin@aaa.ru','admin@aaa.ru','$2a$10$lAibskUsBRZfWF/BUpKQQOQ8JdsCWpaF2lV3dXaLR9UtaL557jM5y',true,'Админей',CURRENT_DATE);

INSERT into users (email, username, password, enabled, first_name,registered_on)
select * from getUserValuesIfUserNotExists('user@aaa.ru','user@aaa.ru','$2a$10$WHkIVZMfDn4HJxl9Czz.1eGmDEgPZlaFo3epTnyumkpSWtZiqVE/G', true, 'Юзерей',CURRENT_DATE);

insert into roles (role_name)
select 'ADMIN' where not exists (select 1 from roles where role_name='ADMIN');

insert into roles (role_name)
select 'USER' where not exists (select 1 from roles where role_name='USER');

insert into users_roles (user_id, role_id)
select u_id, r_id
from
    (select u.user_id u_id, (case when r.role_id is null then r2.role_id else r.role_id END) r_id
     from users u
              left join roles r on u.email like 'admin@%' and (r.role_name = 'ADMIN' or r.role_name = 'USER')
              left join roles r2 on u.email like 'user@%' and r2.role_name = 'USER'
     where r.role_id  is not null or r2.role_id  is not null)  u_id_r_id
where not exists (select 1 from users_roles ur where ur.user_id = u_id and ur.role_id = r_id);

drop function if exists getUserValuesIfUserNotExists;