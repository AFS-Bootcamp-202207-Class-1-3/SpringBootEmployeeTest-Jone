package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
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

    public List<Employee> findAll() {
        return jpaEmployeeRepository.findAll();
    }

    public Employee update(int id, Employee newEmployee) {
        return employeeRepository.update(id, newEmployee);
    }

    public Employee save(Employee employee) {
        return jpaEmployeeRepository.save(employee);
    }

    public Employee findEmployeeById(Integer id) {
        return jpaEmployeeRepository.findById(id).orElseThrow(() -> new NotFoundException(Employee.class.getSimpleName()));
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return jpaEmployeeRepository.findByGender(gender);
    }

    public void delete(Integer id) {
        jpaEmployeeRepository.deleteById(id);
    }

    public List<Employee> findEmployeesByPageAndPageSize(Integer page, Integer pageSize) {
        return jpaEmployeeRepository.findAll(PageRequest.of(page, pageSize)).toList();
    }
}
