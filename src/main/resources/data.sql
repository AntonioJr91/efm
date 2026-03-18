-- CATEGORIES
insert into categories (name)
values ('sementes');

-- PRODUCTS
insert into products(name, stock, unit_of_measure, product_origin ,created_at, category_id)
values ('milho', 100, 'UNIT',  'PURCHASED',current_timestamp, 1);

-- EMPLOYEES
insert into employees(first_name, last_name, cpf, phone_number, job_role, contract_type, hire_date, status)
values ('Xibatinha', 'Doe', '52998224725', '27999999999', 'WORKER', 'CLT', '2025-01-28', 'ACTIVE');

-- FARM AREA
insert into farm_area(name)
values ('north side');

-- SERVICE ORDER
insert into service_order (employee_id,
                           farm_area_id,
                           status_order,
                           service_type_name,
                           service_type_description,
                           service_type_category,
                           created_at,
                           finished_at)
values (1,
        1,
        'IN_PROGRESS',
        'Limpeza',
        'Roçar a sede',
        'PLANTING',
        CURRENT_DATE,
        null);


-- SERVICE ORDER WITH PRODUCT
insert into service_order (employee_id,
                           farm_area_id,
                           status_order,
                           service_type_name,
                           service_type_description,
                           service_type_category,
                           created_at,
                           finished_at)
values (1,
        1,
        'IN_PROGRESS',
        'Plantio de Milho - Lote B',
        'Plantio mecanizado de milho',
        'PLANTING',
        CURRENT_DATE,
        NULL);

-- SERVICE ORDER ITEMS
insert into service_order_item (id,
                                service_order_id,
                                product_id,
                                quantity)
values ('550e8400-e29b-41d4-a716-446655440000',
        1,
        1,
        25);

-- ROLES ADMIN & USER
insert into roles (name)
values ('ADMIN');

insert into roles (name)
values ('USER');

-- USER ADMIN
insert into users(username, password)
values('admin', '$2y$10$9/aZt3ZnLfr398rJ9obD4O9uBUkH4ZpDI.87JmR4BddhZmxOjjLJq');

-- RELACIONAR ADMIN AO USER ADMIN
insert into users_roles (user_id, role_id)
values (1, 1);