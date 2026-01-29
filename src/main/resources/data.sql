-- CATEGORIES
insert into categories (name)
values ('sementes');

-- PRODUCTS
insert into products(name, stock, unit_of_measure, created_at, category_id)
values ('milho', 100, 'UNIT', current_timestamp, 1);

-- EMPLOYEES
insert into employees(first_name, last_name, cpf, phone_number, job_role, contract_type, hire_date, status)
values('Xibatinha', 'Doe', '52998224725', '27999999999', 'WORKER', 'CLT', '2025-01-28', 'ACTIVE')