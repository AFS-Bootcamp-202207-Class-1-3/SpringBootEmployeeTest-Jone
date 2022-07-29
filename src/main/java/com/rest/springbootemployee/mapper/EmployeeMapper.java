package com.rest.springbootemployee.mapper;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.rest.springbootemployee.controller.EmployeeRequest;
import com.rest.springbootemployee.controller.EmployeeResponse;
import com.rest.springbootemployee.entity.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeRequest, employee);
        return employee;
    }


    public EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse employeeResponse = new EmployeeResponse();
        BeanUtils.copyProperties(employee, employeeResponse);
        return employeeResponse;
    }
}
