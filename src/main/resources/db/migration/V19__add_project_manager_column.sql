ALTER TABLE project ADD COLUMN manager_id BIGINT;
ALTER TABLE project ADD CONSTRAINT fk_manager FOREIGN KEY (manager_id) REFERENCES employee(id);
