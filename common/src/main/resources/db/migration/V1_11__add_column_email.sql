alter table guitarshop.users add column email varchar(30);
alter table guitarshop.users alter column email set not null;
create unique index users_email_uindex
    on guitarshop.users (email);