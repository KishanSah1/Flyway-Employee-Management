CREATE TYPE employee_role AS ENUM ('Manager', 'Developer', 'Tester', 'HR');
ALTER TABLE employee ALTER COLUMN role TYPE employee_role USING role::employee_role;
