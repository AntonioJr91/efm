-- CATEGORIES
insert into categories (name)
values ('sementes');

insert into categories (name)
values ('fertilizantes');

insert into categories (name)
values ('defensivos');

insert into categories (name)
values ('ferramentas');

insert into categories (name)
values ('irrigacao');

-- PRODUCTS
insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('milho', 100, 'UNIT',10,  'PURCHASED',current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('ureia', 7, 'KG', 8, 'PURCHASED', current_timestamp, 2);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('glifosato', 2, 'L', 3, 'PURCHASED', current_timestamp, 3);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('feijao carioca', 180, 'KG', 0, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('soja', 140, 'KG', 0, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('cacau', 75, 'KG', 0, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('leite', 220, 'L', 0, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('cafe', 90, 'KG', 0, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('mandioca', 160, 'KG', 0, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('banana', 130, 'KG', 0, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('tomate', 6, 'KG', 5, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('abobora', 4, 'KG', 6, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('pimenta-do-reino', 22, 'KG', 0, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('mel', 3, 'L', 5, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('goiaba', 68, 'KG', 0, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('laranja', 92, 'KG', 0, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('alface', 9, 'UNIT', 12, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('cenoura', 14, 'KG', 18, 'OWN_PRODUCTION', current_timestamp, 1);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('calcario', 6, 'KG', 10, 'PURCHASED', current_timestamp, 2);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('adubo foliar', 4, 'L', 6, 'PURCHASED', current_timestamp, 2);

insert into products(name, stock, unit_of_measure, minimum_stock, product_origin ,created_at, category_id)
values ('inseticida biologico', 3, 'L', 5, 'PURCHASED', current_timestamp, 3);

-- EMPLOYEES
insert into employees(first_name, last_name, cpf, phone_number, job_role, contract_type, hire_date, status)
values ('Xibatinha', 'Doe', '52998224725', '27999999999', 'WORKER', 'CLT', '2025-01-28', 'ACTIVE');

insert into employees(first_name, last_name, cpf, phone_number, job_role, contract_type, hire_date, status)
values ('Marina', 'Silva', '11144477735', '27998887766', 'MANAGER', 'CLT', '2024-08-10', 'ACTIVE');

insert into employees(first_name, last_name, cpf, phone_number, job_role, contract_type, hire_date, status)
values ('Carlos', 'Souza', '98765432100', '27997776655', 'WORKER', 'SHARECROPPER', '2025-02-03', 'ACTIVE');

-- FARM AREA
insert into farm_area(name)
values ('north side');

insert into farm_area(name)
values ('south field');

insert into farm_area(name)
values ('orchard');

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

insert into service_order (employee_id,
                           farm_area_id,
                           status_order,
                           service_type_name,
                           service_type_description,
                           service_type_category,
                           created_at,
                           finished_at)
values (2,
        2,
        'COMPLETED',
        'Adubacao de cobertura',
        'Aplicar ureia no talhao sul',
        'PLANTING',
        CURRENT_DATE - 5,
        CURRENT_DATE - 4);

insert into service_order (employee_id,
                           farm_area_id,
                           status_order,
                           service_type_name,
                           service_type_description,
                           service_type_category,
                           created_at,
                           finished_at)
values (3,
        3,
        'IN_PROGRESS',
        'Controle de pragas',
        'Pulverizacao preventiva no pomar',
        'MAINTENANCE',
        CURRENT_DATE - 1,
        NULL);

insert into service_order (employee_id,
                           farm_area_id,
                           status_order,
                           service_type_name,
                           service_type_description,
                           service_type_category,
                           created_at,
                           finished_at)
values (2,
        1,
        'COMPLETED',
        'Fechamento semanal',
        'Conferencia de atividades e custos do setor norte',
        'ADMINISTRATIVE',
        CURRENT_DATE - 10,
        CURRENT_DATE - 10);

insert into service_order (employee_id,
                           farm_area_id,
                           status_order,
                           service_type_name,
                           service_type_description,
                           service_type_category,
                           created_at,
                           finished_at)
values (1,
        2,
        'CANCELED',
        'Colheita antecipada',
        'Tentativa de colher antes da janela ideal',
        'HARVEST',
        CURRENT_DATE - 3,
        CURRENT_DATE - 2);

-- SERVICE ORDER ITEMS
insert into service_order_item (id,
                                service_order_id,
                                product_id,
                                quantity)
values ('550e8400-e29b-41d4-a716-446655440000',
        1,
        1,
        25);

insert into service_order_item (id,
                                service_order_id,
                                product_id,
                                quantity)
values ('550e8400-e29b-41d4-a716-446655440001',
        2,
        1,
        60);

insert into service_order_item (id,
                                service_order_id,
                                product_id,
                                quantity)
values ('550e8400-e29b-41d4-a716-446655440002',
        3,
        2,
        8);

insert into service_order_item (id,
                                service_order_id,
                                product_id,
                                quantity)
values ('550e8400-e29b-41d4-a716-446655440003',
        4,
        3,
        2);

insert into service_order_item (id,
                                service_order_id,
                                product_id,
                                quantity)
values ('550e8400-e29b-41d4-a716-446655440004',
        6,
        1,
        15);

-- PRODUCTIONS
insert into productions(area_id, product_id, quantity, created_at, observation)
values (1, 1, 120, CURRENT_DATE - 20, 'colheita inicial de milho no talhao norte');

insert into productions(area_id, product_id, quantity, created_at, observation)
values (2, 4, 95, CURRENT_DATE - 7, 'colheita de feijao com volume menor por causa da chuva');

insert into productions(area_id, product_id, quantity, created_at, observation)
values (3, 5, 60, CURRENT_DATE - 18, 'secagem inicial de amendoas de cacau');

insert into productions(area_id, product_id, quantity, created_at, observation)
values (1, 6, 180, CURRENT_DATE - 16, 'ordenha da semana destinada ao resfriamento');

insert into productions(area_id, product_id, quantity, created_at, observation)
values (2, 7, 48, CURRENT_DATE - 14, 'primeiro lote de cafe beneficiado');

insert into productions(area_id, product_id, quantity, created_at, observation)
values (3, 8, 130, CURRENT_DATE - 12, 'mandioca separada para venda local');

insert into productions(area_id, product_id, quantity, created_at, observation)
values (1, 9, 72, CURRENT_DATE - 9, 'banana colhida no ponto para distribuicao');

insert into productions(area_id, product_id, quantity, created_at, observation)
values (2, 10, 40, CURRENT_DATE - 6, 'tomate selecionado para feira da cidade');

insert into productions(area_id, product_id, quantity, created_at, observation)
values (3, 11, 50, CURRENT_DATE - 4, 'abobora colhida para venda no mercado local');

insert into productions(area_id, product_id, quantity, created_at, observation)
values (1, 12, 18, CURRENT_DATE - 3, 'pimenta-do-reino seca e pronta para armazenamento');

insert into productions(area_id, product_id, quantity, created_at, observation)
values (2, 13, 36, CURRENT_DATE - 2, 'mel extraido e separado em lotes');

insert into productions(area_id, product_id, quantity, created_at, observation)
values (3, 14, 62, CURRENT_DATE - 1, 'goiaba selecionada para distribuicao regional');

-- ROLES ADMIN & USER
insert into roles (name)
values ('ADMIN');

insert into roles (name)
values ('USER');

-- USER ADMIN
insert into users(username, password)
values('admin', '$2y$06$GYPDcLGVfOTmzWh/eOQc0.rw3VvnXtkcXrklRInOS7HHhK98mGU.a');

insert into users(username, password)
values('user', '$2y$06$THc1Esa8y34r0kvJCHsy5eN6O3KtYODgWYdAJz/GVLz/maANIPcty');

-- RELACIONAR ADMIN AO USER ADMIN
insert into users_roles (user_id, role_id)
values (1, 1);

insert into users_roles (user_id, role_id)
values (2, 2);
