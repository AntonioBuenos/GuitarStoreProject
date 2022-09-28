create table if not exists personal_data
(
    id                bigserial
        primary key,
    passport_number   varchar(10),
    address           varchar(50),
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6),
    termination_date  timestamp(6),
    is_deleted        boolean      default false,
    user_id           bigint      not null
        constraint personal_data_users_id_fk
            references guitarshop.users
            on update cascade on delete cascade
);

create unique index if not exists personal_data_user_id_uindex
    on personal_data (user_id);