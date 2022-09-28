create sequence if not exists goods_guitars_id_seq;

alter sequence goods_guitars_id_seq owner to postgres;

create sequence if not exists music_genres_id_seq;

alter sequence music_genres_id_seq owner to postgres;

create sequence if not exists customers_id_seq;

alter sequence customers_id_seq owner to postgres;

create sequence if not exists brands_id_seq;

alter sequence brands_id_seq owner to postgres;

create table if not exists genres
(
    id                bigint       default nextval('guitarshop.music_genres_id_seq'::regclass) not null
        constraint music_genres_pk
            primary key,
    music_genre       varchar(15)                                                              not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)                                not null,
    modification_date timestamp(6),
    is_deleted        boolean      default false                                               not null,
    termination_date  timestamp(6)
);

alter table genres
    owner to postgres;

alter sequence music_genres_id_seq owned by genres.id;

create unique index if not exists music_genres_id_uindex
    on genres (id);

create unique index if not exists music_genres_genre_uindex
    on genres (music_genre);

create table if not exists pickups
(
    id                bigserial
        constraint pickups_pk
            primary key,
    model             varchar(50)                               not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6) not null,
    modification_date timestamp(6),
    is_deleted        boolean      default false                not null,
    termination_date  timestamp(6)
);

alter table pickups
    owner to postgres;

create unique index if not exists pickups_id_uindex
    on pickups (id);

create unique index if not exists pickups_model_uindex
    on pickups (model);

create table if not exists guitar_manufacturer
(
    id                bigint       default nextval('guitarshop.brands_id_seq'::regclass) not null
        constraint brands_pk
            primary key,
    brand             varchar(20)                                                        not null,
    company           varchar(20)                                                        not null,
    origin_country    varchar(10)  default 'country'::character varying                  not null,
    is_deleted        boolean      default false                                         not null,
    termination_date  timestamp(6),
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)                          not null,
    modification_date timestamp(6)
);

alter table guitar_manufacturer
    owner to postgres;

alter sequence brands_id_seq owned by guitar_manufacturer.id;

create table if not exists guitars
(
    id                bigint       default nextval('guitarshop.goods_guitars_id_seq'::regclass) not null
        constraint goods_guitars_pk
            primary key,
    typeof            varchar(20)                                                               not null,
    shape             varchar(20)                                                               not null,
    series            varchar(30)                                                               not null,
    model             varchar(30)                                                               not null,
    strings_qnt       integer                                                                   not null,
    neck              varchar(15)                                                               not null,
    bridge            varchar(20)                                                               not null,
    body_material     varchar(20)                                                               not null,
    price             real         default 0.00                                                 not null,
    prod_country      varchar(15),
    brand_id          bigint
        constraint guitars_brands_id_fk
            references guitar_manufacturer
            on update cascade on delete cascade,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)                                 not null,
    modification_date timestamp(6),
    is_deleted        boolean      default false                                                not null,
    termination_date  timestamp(6)
);

alter table guitars
    owner to postgres;

alter sequence goods_guitars_id_seq owned by guitars.id;

create unique index if not exists goods_guitars_id_uindex
    on guitars (id);

create unique index if not exists brands_id_uindex
    on guitar_manufacturer (id);

create unique index if not exists brands_brand_uindex
    on guitar_manufacturer (brand);

create table if not exists users
(
    id                bigint       default nextval('guitarshop.customers_id_seq'::regclass) not null
        constraint customers_pk
            primary key,
    first_name        varchar(20)                                                           not null,
    last_name         varchar(20)                                                           not null,
    role              varchar(15)  default 'customer'::character varying                    not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)                             not null,
    modification_date timestamp(6),
    is_deleted        boolean      default false                                            not null,
    termination_date  timestamp(6),
    login             varchar(30)  default 'User'::character varying                        not null,
    password          varchar      default 'Password'::character varying                    not null
);

alter table users
    owner to postgres;

alter sequence customers_id_seq owned by users.id;

create unique index if not exists customers_id_uindex
    on users (id);

create table if not exists instock
(
    id                bigserial
        constraint instock_pk
            primary key,
    good_id           bigint                                                  not null
        constraint instock_guitars_id_fk
            references guitars
            on update cascade on delete cascade,
    placement         varchar(15),
    good_status       varchar(20)  default 'free for sale'::character varying not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6)               not null,
    modification_date timestamp(6),
    date_sold         timestamp(6)
);

alter table instock
    owner to postgres;

create table if not exists orders
(
    id                bigserial
        constraint orders_pk
            primary key,
    user_id           bigint                                            not null
        constraint orders_customers_id_fk
            references users
            on update cascade on delete cascade,
    instock_id        bigint                                            not null
        constraint orders_instock_id_fk
            references instock
            on update cascade on delete cascade,
    delivery_address  varchar(50)                                       not null,
    creation_date     timestamp(6) default CURRENT_TIMESTAMP(6),
    modification_date timestamp(6),
    termination_date  timestamp(6),
    order_status      varchar(15)  default 'created'::character varying not null
);

alter table orders
    owner to postgres;

create unique index if not exists orders_id_uindex
    on orders (id);

create unique index if not exists instock_id_uindex
    on instock (id);

create table if not exists l_pickups_guitars
(
    id         bigserial
        constraint l_pickups_guitars_pk
            primary key,
    position   varchar(15) not null,
    pickups_id bigint
        constraint l_pickups_guitars_pickups_id_fk
            references pickups
            on update cascade on delete cascade,
    guitars_id bigint
        constraint l_pickups_guitars_guitars_id_fk
            references guitars
            on update cascade on delete cascade
);

alter table l_pickups_guitars
    owner to postgres;

create unique index if not exists l_pickups_guitars_id_uindex
    on l_pickups_guitars (id);

alter table l_genres_guitars
    owner to postgres;

create unique index if not exists l_genres_guitars_id_uindex
    on l_genres_guitars (id);

create table if not exists l_genres_guitars
(
    id        bigserial
        constraint l_genres_guitars_pk
            primary key,
    genre     varchar(15) not null
        constraint l_genres_guitars_genres_genre_fk
            references genres (music_genre)
            on update cascade on delete cascade,
    guitar_id bigint      not null
        constraint l_genres_guitars_guitars_id_fk
            references guitars
            on update cascade on delete cascade
);



