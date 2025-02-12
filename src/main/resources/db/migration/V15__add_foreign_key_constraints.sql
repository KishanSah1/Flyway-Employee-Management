ALTER TABLE employee_skills
    ADD CONSTRAINT fk_employee FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE;
ALTER TABLE employee_skills
    ADD CONSTRAINT fk_skill FOREIGN KEY (skill_id) REFERENCES skill(id) ON DELETE CASCADE;
