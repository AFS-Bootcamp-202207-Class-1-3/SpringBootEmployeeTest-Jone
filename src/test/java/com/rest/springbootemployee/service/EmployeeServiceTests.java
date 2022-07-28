package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaEmployeeRepository;
import com.rest.springbootemployee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTests {
    @Spy
    EmployeeRepository employeeRepository;
    @Spy
    JpaEmployeeRepository jpaEmployeeRepository;
    @InjectMocks
    EmployeeService employeeService;

    @Test
    void should_return_allEmployees_when_findAllEmployees_given_none() {
        // given
        Employee employee = new Employee(1, "Lily", 20, "Female", 11000);
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        doReturn(employees).when(jpaEmployeeRepository).findAll();
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
//         given(employeeRepository.findEmployeeById(1)).willReturn(oldEmployee);
//         given(employeeRepository.update(1, newEmployee)).willCallRealMethod();
        // when
        Employee updatedEmployee = employeeService.update(1, newEmployee);
        // then
        verify(employeeRepository).update(1, newEmployee);
        assertThat(updatedEmployee.getSalary(), equalTo(newEmployee.getSalary()));
    }

    @Test
    void should_return_employee_when_find_employee_by_employee_id() {
        // given
        Employee employee = new Employee(1, "Cedric", 18, "Male", 6000);

        doReturn(Optional.of(employee)).when(jpaEmployeeRepository).findById(employee.getId());

        // when
        Employee actualEmployee = employeeService.findEmployeeById(employee.getId());

        //then
        assertEquals(employee, actualEmployee);
    }

    @Test
    void should_return_employees_when_find_employees_by_gender() {
        // given
        List<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Lily", 18, "female", 3000));
            add(new Employee(2, "Lily1", 18, "female", 3000));
            add(new Employee(3, "Lily2", 18, "female", 3000));
        }};
        doReturn(employees).when(jpaEmployeeRepository).findByGender("female");

        // when
        List<Employee> actualEmployees = employeeService.findEmployeesByGender("female");

        //then
        assertThat(actualEmployees, hasSize(3));
        assertEquals("Lily2", actualEmployees.get(2).getName());
    }

    @Test
    void should_return_employees_when_find_employees_by_page_and_page_size() {
        // given
        List<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Lily", 18, "female", 3000));
            add(new Employee(2, "Lily1", 18, "female", 3000));
            add(new Employee(3, "Lily2", 18, "female", 3000));
        }};
        doReturn(new PageImpl(employees)).when(jpaEmployeeRepository).findAll(PageRequest.of(1, 3));

        // when
        List<Employee> actualEmployees = employeeService.findEmployeesByPageAndPageSize(1, 3);

        //then
        assertThat(actualEmployees, hasSize(3));
        assertEquals("Lily2", actualEmployees.get(2).getName());
    }

    @Test
    void should_return_added_employee_when_add_given_an_employee() {
        //given
        Employee employee = new Employee(1,"Jone", 20, "male", 9000);
        doCallRealMethod().when(employeeRepository).save(employee);

        //when
        Employee addedEmployee = employeeService.save(employee);

        //then
        verify(employeeRepository).save(employee);
        assertEquals(9000, addedEmployee.getSalary());
        assertNotNull(addedEmployee.getId());

    }

    @Test
    void should_return_nothing_when_delete_given_employee_id() {
        //given
        Employee employee = new Employee(1, "Cedric", 23, "Male", 100);
        doReturn(employee).when(employeeRepository).findEmployeeById(employee.getId());

        //when
        employeeService.delete(employee.getId());

        //then
        verify(employeeRepository).delete(employee.getId());
    }

}
