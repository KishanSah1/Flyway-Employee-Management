package com.kishan.Flyware.controller;

import com.kishan.Flyware.model.Employee;
import com.kishan.Flyware.model.Project;
import com.kishan.Flyware.model.Skill;
import com.kishan.Flyware.service.EmployeeService;

//import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        log.info("REST request to get all employees");
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        log.info("REST request to get employee by id: {}", id);
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        log.info("REST request to create employee: {}", employee.getName());
        return ResponseEntity.ok(employeeService.createEmployee(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        log.info("REST request to update employee: {}", id);
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("REST request to delete employee: {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<Employee> addSkillToEmployee(
            @PathVariable Long employeeId,
            @PathVariable Long skillId) {
        log.info("REST request to add skill {} to employee {}", skillId, employeeId);
        return ResponseEntity.ok(employeeService.addSkillToEmployee(employeeId, skillId));
    }

    @DeleteMapping("/{employeeId}/skills/{skillId}")
    public ResponseEntity<Employee> removeSkillFromEmployee(
            @PathVariable Long employeeId,
            @PathVariable Long skillId) {
        log.info("REST request to remove skill {} from employee {}", skillId, employeeId);
        return ResponseEntity.ok(employeeService.removeSkillFromEmployee(employeeId, skillId));
    }

    @PostMapping("/{employeeId}/projects/{projectId}")
    public ResponseEntity<Employee> assignProjectToEmployee(
            @PathVariable Long employeeId,
            @PathVariable Long projectId) {
        log.info("REST request to assign project {} to employee {}", projectId, employeeId);
        return ResponseEntity.ok(employeeService.assignProjectToEmployee(employeeId, projectId));
    }

    @DeleteMapping("/{employeeId}/projects/{projectId}")
    public ResponseEntity<Employee> removeProjectFromEmployee(
            @PathVariable Long employeeId,
            @PathVariable Long projectId) {
        log.info("REST request to remove project {} from employee {}", projectId, employeeId);
        return ResponseEntity.ok(employeeService.removeProjectFromEmployee(employeeId, projectId));
    }

    @GetMapping("/{employeeId}/skills")
    public ResponseEntity<Set<Skill>> getEmployeeSkills(@PathVariable Long employeeId) {
        log.info("REST request to get skills for employee {}", employeeId);
        return ResponseEntity.ok(employeeService.getEmployeeSkills(employeeId));
    }

    @GetMapping("/{employeeId}/projects")
    public ResponseEntity<Set<Project>> getEmployeeProjects(@PathVariable Long employeeId) {
        log.info("REST request to get projects for employee {}", employeeId);
        return ResponseEntity.ok(employeeService.getEmployeeProjects(employeeId));
    }
}
