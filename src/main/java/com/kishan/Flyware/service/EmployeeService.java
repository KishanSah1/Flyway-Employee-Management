package com.kishan.Flyware.service;

import com.kishan.Flyware.exception.ResourceNotFoundException;
import com.kishan.Flyware.model.Employee;
import com.kishan.Flyware.model.Project;
import com.kishan.Flyware.model.Skill;
import com.kishan.Flyware.repository.EmployeeRepository;
import com.kishan.Flyware.repository.ProjectRepository;
import com.kishan.Flyware.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;
    private final ProjectRepository projectRepository;

    @Cacheable(value = "employees")
    public List<Employee> getAllEmployees() {
        log.info("Fetching all employees from database");
        return employeeRepository.findAll();
    }

    @Cacheable(value = "employees", key = "#id")
    public Employee getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @CacheEvict(value = "employees", allEntries = true)
    public Employee createEmployee(Employee employee) {
        log.info("Creating new employee: {}", employee.getName());
        return employeeRepository.save(employee);
    }

    @CacheEvict(value = "employees", key = "#id")
    public Employee updateEmployee(Long id, Employee employeeDetails) {
        log.info("Updating employee with id: {}", id);
        Employee employee = getEmployeeById(id);
        
        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setRole(employeeDetails.getRole());
        employee.setDepartment(employeeDetails.getDepartment());
        
        return employeeRepository.save(employee);
    }

    @CacheEvict(value = "employees", key = "#id")
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);
    }

    @Transactional
    @CacheEvict(value = "employees", key = "#employeeId")
    public Employee addSkillToEmployee(Long employeeId, Long skillId) {
        log.info("Adding skill {} to employee {}", skillId, employeeId);
        Employee employee = getEmployeeById(employeeId);
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + skillId));
        
        employee.getSkills().add(skill);
        return employeeRepository.save(employee);
    }

    @Transactional
    @CacheEvict(value = "employees", key = "#employeeId")
    public Employee removeSkillFromEmployee(Long employeeId, Long skillId) {
        log.info("Removing skill {} from employee {}", skillId, employeeId);
        Employee employee = getEmployeeById(employeeId);
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + skillId));
        
        employee.getSkills().remove(skill);
        return employeeRepository.save(employee);
    }

    @Transactional
    @CacheEvict(value = "employees", key = "#employeeId")
    public Employee assignProjectToEmployee(Long employeeId, Long projectId) {
        log.info("Assigning project {} to employee {}", projectId, employeeId);
        Employee employee = getEmployeeById(employeeId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));
        
        employee.getProjects().add(project);
        return employeeRepository.save(employee);
    }

    @Transactional
    @CacheEvict(value = "employees", key = "#employeeId")
    public Employee removeProjectFromEmployee(Long employeeId, Long projectId) {
        log.info("Removing project {} from employee {}", projectId, employeeId);
        Employee employee = getEmployeeById(employeeId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));
        
        employee.getProjects().remove(project);
        return employeeRepository.save(employee);
    }

    @Cacheable(value = "employeeSkills", key = "#employeeId")
    public Set<Skill> getEmployeeSkills(Long employeeId) {
        log.info("Fetching skills for employee {}", employeeId);
        Employee employee = getEmployeeById(employeeId);
        return employee.getSkills();
    }

    @Cacheable(value = "employeeProjects", key = "#employeeId")
    public Set<Project> getEmployeeProjects(Long employeeId) {
        log.info("Fetching projects for employee {}", employeeId);
        Employee employee = getEmployeeById(employeeId);
        return employee.getProjects();
    }
}
