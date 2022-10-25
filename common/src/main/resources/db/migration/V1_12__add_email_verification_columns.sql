alter table guitarshop.users
    add column if not exists verification_code varchar(200),
    add column if not exists is_enabled boolean;
