insert into hhs (user_id, email, first_name, second_name, authority, management, address, contacts)
select 2, 'emailHH@aaa.bb', 'first_nameHH', 'secondNameHH', 'authority', 'managementm', 'address', 'contacts'
    where not exists (select 1 from hhs where user_id=2);
