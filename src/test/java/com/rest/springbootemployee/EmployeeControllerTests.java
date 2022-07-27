package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundEmployee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc
@SpringBootTest
class EmployeeControllerTests {

    @Autowired
    MockMvc client;
    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void clearDB() {
        employeeRepository.clearAll();
    }


    @Test
    void should_return_allEmployees_when_getAllEmployees_given_none() throws Exception {
        employeeRepository.save(new Employee(1, "Lily", 20, "Female", 11000));

        client.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(11000));

    }

    @Test
    void should_return_employee_when_create_employee_given_employee() throws Exception {
        // given
        String newEmployee = "{\n" +
                "    \"id\": 12,\n" +
                "    \"name\": \"zs\",\n" +
                "    \"age\": 20,\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"salary\": 10000\n" +
                "}";

        // when & then
        client.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployee))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("zs"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(10000));

        // should
        List<Employee> allEmployees = employeeRepository.findAllEmployees();
        assertThat(allEmployees, hasSize(1));
        assertThat(allEmployees.get(0).getAge(), equalTo(20));
        assertThat(allEmployees.get(0).getName(), equalTo("zs"));
        assertThat(allEmployees.get(0).getGender(), equalTo("Male"));
        assertThat(allEmployees.get(0).getSalary(), equalTo(10000));
    }

    @Test
    void should_return_rightEmployee_when_getEmployeeById_given_Id() throws Exception {
        employeeRepository.save(new Employee(1, "Lily", 20, "Female", 11000));
        client.perform(MockMvcRequestBuilders.get("/employees/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(11000));
    }

    @Test
    void should_return_NotFoundEmployee_exception_when_getEmployeeById_given_unValid_Id() throws Exception {
        // todo http 应该如何处理异常？
        Exception exception = assertThrows(NotFoundEmployee.class, () -> employeeRepository.findEmployeeById(1));
        assertEquals("Not found employee.", exception.getMessage());
    }
}
