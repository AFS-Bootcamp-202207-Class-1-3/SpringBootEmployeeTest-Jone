package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private List<Employee> employeeList;

    public EmployeeRepository() {
        employeeList = new ArrayList<Employee>() {
            {
                add(new Employee(11, "Lily", 20, "Female", 11000));
                add(new Employee(2, "Lily", 20, "Female", 11000));
                add(new Employee(3, "Lily", 20, "Female", 11000));
                add(new Employee(4, "Lily", 20, "Female", 11000));
                add(new Employee(5, "Leo", 20, "Male", 11000));
            }
        };
    }

    public List<Employee> findAllEmployees() {
        return employeeList;
    }

    public Employee findEmployeeById(int id) {
        return employeeList.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst().orElseThrow(()-> new NotFoundException(Employee.class.getSimpleName()));
    }

    public List<Employee> findEmployeesByGender(String gender) {
        return employeeList.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee save(Employee employee) {
        this.employeeList.add(employee);
        return employee;
    }

    public Employee update(int id, Employee newEmployee) {
        Employee oldEmployee = findEmployeeById(id);
        oldEmployee.update(newEmployee);
        return oldEmployee;
    }

    public void delete(Integer id) {
        employeeList.remove(this.findEmployeeById(id));
    }

    public List<Employee> findEmployeesByPageAndPageSize(Integer page, Integer pageSize) {
        return employeeList.stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public void clearAll() {
        employeeList.clear();
    }
}
