alter table users add column if not exists passport_number varchar(10),
    add column if not exists address varchar(50);
drop table if exists personal_data;