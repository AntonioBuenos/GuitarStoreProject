alter table genres drop column if exists modification_date,
                   drop column if exists is_deleted,
                   drop column if exists termination_date;