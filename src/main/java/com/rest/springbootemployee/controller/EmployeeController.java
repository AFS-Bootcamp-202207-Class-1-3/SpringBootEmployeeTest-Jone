package com.rest.springbootemployee.controller;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.mapper.EmployeeMapper;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;

    @Resource
    private EmployeeMapper employeeMapper;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.findEmployeeById(id);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getEmployeesByGender(@RequestParam String gender) {
        return employeeService.findEmployeesByGender(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse saveEmployee(@RequestBody EmployeeRequest employeeRequest) {

        Employee employee = employeeMapper.toEntity(employeeRequest);
        employeeService.save(employee);
        EmployeeResponse employeeResponse = employeeMapper.toResponse(employee);
        return employeeResponse;
    }

    @PutMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable Integer id, @RequestBody Employee employee) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeeById(@PathVariable Integer id) {
        employeeService.delete(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getEmployeesByPageAndPageSize(@RequestParam Integer page, @RequestParam Integer pageSize) {
        return employeeService.findEmployeesByPageAndPageSize(page, pageSize);
    }
}
