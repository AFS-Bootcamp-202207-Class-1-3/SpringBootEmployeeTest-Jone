package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Resource
    private JpaEmployeeRepository jpaEmployeeRepository;

    public EmployeeService() {
    }

    public List<Employee> findAll() {
        return jpaEmployeeRepository.findAll();
    }

    public Employee update(int id, Employee newEmployee) {
        Employee oldEmployee = findEmployeeById(id);
        oldEmployee.update(newEmployee);
        return jpaEmployeeRepository.save(oldEmployee);
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
