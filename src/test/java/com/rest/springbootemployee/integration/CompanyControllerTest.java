package com.rest.springbootemployee.integration;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureMockMvc
@SpringBootTest
public class CompanyControllerTest {

    @Resource
    MockMvc client;
    @Autowired
    CompanyRepository companyRepository;

    @BeforeEach
    void initData() {
        companyRepository.clearAll();
        companyRepository.save(new Company(1, "oocl", Arrays.asList(new Employee(1, "Jone", 23, "Male", 7000))));
    }

    @Test
    void should_get_all_companies_when_perform_get_given_companies() throws Exception {
        //given

        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("oocl"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].name").value("Jone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].age").value(23))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].salary").value(7000));
    }

    @Test
    void should_get_company_when_perform_given_company_id() throws Exception {
        //given
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("oocl"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].name").value("Jone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].age").value(23))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].salary").value(7000));
    }

    @Test
    void should_get_companies_by_page_and_page_size_when_perform_get_given_companies() throws Exception {
        //given
        //when & then
        companyRepository.save(new Company(2, "hxt", Arrays.asList(new Employee(2, "Jon", 23, "Male", 7000))));
        client.perform(MockMvcRequestBuilders.get("/companies").param("page", "1").param("pageSize", "5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("oocl"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].name").value("Jone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].age").value(23))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employees[0].salary").value(7000))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].companyName").value("hxt"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employees", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employees[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employees[0].name").value("Jon"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employees[0].age").value(23))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employees[0].gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employees[0].salary").value(7000));
    }

    @Test
    void should_get_company_employees_when_perform_given_company_id() throws Exception {
        //given
        //when & then
        client.perform(MockMvcRequestBuilders.get("/companies/{id}/employees", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Jone"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(23))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value("Male"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].salary").value(7000));
    }

    @Test
    public void should_add_an_company_when_perform_post_given_an_company() throws Exception {
        // given
        String newCompany = "{\n" +
                "    \"companyName\": \"Spring\",\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"id\": 5,\n" +
                "            \"name\": \"Lily\",\n" +
                "            \"age\": 20,\n" +
                "            \"gender\": \"Female\",\n" +
                "            \"salary\": 8000\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        // when & then
        client.perform(MockMvcRequestBuilders.post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCompany))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("Spring"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].name").value("Lily"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].age").value(20))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].gender").value("Female"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[0].salary").value(8000));
    }

    @Test
    public void should_update_an_employee_when_perform_put_given_an_employee_and_an_id() throws Exception {
        // given
        String newCompany = "{\n" +
                "    \"companyName\": \"SpringBoot\",\n" +
                "    \"employees\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"Jone\",\n" +
                "            \"age\": 20,\n" +
                "            \"gender\": \"Male\",\n" +
                "            \"salary\": 8000\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        // when & then
        client.perform(MockMvcRequestBuilders.put("/companies/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCompany))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("SpringBoot"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.employees", hasSize(1)));
    }

    @Test
    public void should_delete_a_company_when_perform_delete_given_id() throws Exception {
        client.perform(MockMvcRequestBuilders.delete("/companies/{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }



    @Test
    void should_return_no_found_when_find_company_given_no_exist_companyId() throws Exception {
        // given

        // when
        client.perform(MockMvcRequestBuilders.get("/companies/{id}", 2))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Company Not found!."));

        // then
    }
}

