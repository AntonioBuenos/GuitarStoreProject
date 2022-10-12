alter table genres add column if not exists modification_date timestamp(6),
                   add column if not exists is_deleted boolean default false not null,
                   add column if not exists termination_date timestamp(6);