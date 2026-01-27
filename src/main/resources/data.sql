-- CATEGORIES
insert into categories (name) values ('sementes');

-- PRODUCTS
insert into products(name, stock, unit_of_measure, created_at, category_id)
    values ('milho', 100, 'UNIT', current_timestamp, 1);