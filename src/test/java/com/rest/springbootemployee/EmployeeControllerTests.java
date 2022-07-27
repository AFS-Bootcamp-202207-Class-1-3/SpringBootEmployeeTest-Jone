package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@AutoConfigureMockMvc
@SpringBootTest
public class EmployeeControllerTests{

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
        client.perform(MockMvcRequestBuilders.get("/employees/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Employee Not found!."));
    }


    @Test
    void should_return_none_when_deleteEmployeeById_given_a_Id() throws Exception {

        // given & when
        employeeRepository.save(new Employee(1, "Lily", 20, "Female", 11000));
        client.perform(MockMvcRequestBuilders.delete("/employees/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // should
        List<Employee> employees = employeeRepository.findAllEmployees();
        assertThat(employees, hasSize(0));
    }

    @Test
    void should_return_rightEmployee_when_updateEmployee_given_employee() throws Exception {
        // given & when
        employeeRepository.save(new Employee(1, "Lily", 20, "Female", 11000));
        String employee = "{\n" +
                "    \"id\": 12,\n" +
                "    \"name\": \"zs\",\n" +
                "    \"age\": 20,\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"salary\": 10000\n" +
                "}";
        //  then
        client.perform(MockMvcRequestBuilders.put("/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employee))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(10000));
    }

    @Test
    void should_get_employees_by_page_and_page_size_when_perform_get_given_employees() throws Exception {
        employeeRepository.save(new Employee(1, "Lily", 29, "female", 9000));
        employeeRepository.save(new Employee(2, "Jone", 30, "male", 5000));

        client.perform(MockMvcRequestBuilders.get("/employees").param("page", "1").param("pageSize", "5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(29))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(9000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Jone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].age").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].gender").value("male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].salary").value(5000));
    }

}
