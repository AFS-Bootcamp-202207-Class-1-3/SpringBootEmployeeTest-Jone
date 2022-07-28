package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JpaEmployeeRepository jpaEmployeeRepository;

    public EmployeeService() {
    }

    public List<Employee> findAllOld() {
        return employeeRepository.findAllEmployees();
    }

    public List<Employee> findAll() {
        return jpaEmployeeRepository.findAll();
    }

    public Employee update(int id, Employee newEmployee) {
        return employeeRepository.update(id, newEmployee);
    }

    public Employee save(Employee employee) {
        employee.setId(generateId());
        return employeeRepository.save(employee);
    }

    private Integer generateId() {
        List<Employee> employeeList = employeeRepository.findAllEmployees();
        Integer maxId = employeeList.stream()
                .mapToInt(Employee::getId)
                .max()
                .orElse(0);
        return maxId + 1;
    }

    public Employee findEmployeeById(Integer id) {
        return employeeRepository.findEmployeeById(id);
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return employeeRepository.findEmployeesByGender(gender);
    }

    public void delete(Integer id) {
        employeeRepository.delete(id);
    }

    public List<Employee> findEmployeesByPageAndPageSize(Integer page, Integer pageSize) {
        return jpaEmployeeRepository.findAll(PageRequest.of(page, pageSize)).toList();
    }
}
