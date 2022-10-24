alter table guitarshop.users add column if not exists email varchar(30);
alter table guitarshop.users alter column email set not null;
create unique index if not exists users_email_uindex
    on guitarshop.users (email);