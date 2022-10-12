alter table genres alter column creation_date drop default;
alter table guitar_manufacturer
    alter column origin_country drop default,
    alter column creation_date drop default;
alter table guitars alter column creation_date drop default;
alter table instock
    alter column good_status drop default,
    alter column creation_date drop default;
alter table orders
    alter column order_status drop default,
    alter column creation_date drop default;
alter table pickups alter column creation_date drop default;
alter table users
    alter column role drop default,
    alter column login drop default,
    alter column password drop default,
    alter column creation_date drop default;