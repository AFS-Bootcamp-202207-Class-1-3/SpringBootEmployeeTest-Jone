package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTests {
    @Spy
    EmployeeRepository employeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_allEmployees_when_findAllEmployees_given_none() {
        // given
        Employee employee = new Employee(1, "Lily", 20, "Female", 11000);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        doReturn(employees).when(employeeRepository).findAllEmployees();
        // when
        List<Employee> actualEmployees = employeeService.findAll();

        // then
        assertThat(actualEmployees, hasSize(1));
        assertThat(actualEmployees.get(0), equalTo(employee));
    }

    @Test
    void should_return_updatedEmployee_when_update_given_employee() {
        // given
        Employee oldEmployee = new Employee(1, "Jone", 30, "Male", 11000);
        Employee newEmployee = new Employee(1, "Jone", 30, "Male", 12000);
        doReturn(oldEmployee).when(employeeRepository).findEmployeeById(1);
        doCallRealMethod().when(employeeRepository).update(1, newEmployee);
        // given(employeeRepository.findEmployeeById(1)).willReturn(oldEmployee);
        // given(employeeRepository.update(oldEmployee, newEmployee)).willCallRealMethod();
        // when
        Employee updatedEmployee = employeeService.update(1, newEmployee);
        // then
        verify(employeeRepository).update(1, newEmployee);
        assertThat(updatedEmployee.getSalary(), equalTo(newEmployee.getSalary()));
    }


}
