ALTER TABLE employee ADD COLUMN department_id BIGINT;
ALTER TABLE employee ADD CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES department(id);
