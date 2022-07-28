package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import com.rest.springbootemployee.service.CompanyService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CompanyServiceTests {
    @Spy
    JpaCompanyRepository jpaCompanyRepository;

    @InjectMocks
    CompanyService companyService;

    @Test
    void should_return_allCompanies_when_findAllCompanies_given_none() {
        // given
        Company company = new Company(1, "Spring", new ArrayList<Employee>() {
            {
                add(new Employee(1, "Lily", 20, "Female", 11000));
            }
        });
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        doReturn(companies).when(jpaCompanyRepository).findAll();
        // when
        List<Company> actualCompanies = companyService.findAllCompanies();

        // then
        assertThat(actualCompanies, hasSize(1));
        assertThat(actualCompanies.get(0), equalTo(company));
    }

    @Test
    void should_return_updatedCompany_when_update_given_company() {
        // given
        Company oldCompany = new Company(1, "Spring", new ArrayList<Employee>() {
            {
                add(new Employee(1, "Lily", 20, "Female", 11000));
            }
        });
        Company newCompany = new Company(1, "Django", new ArrayList<Employee>() {
            {
                add(new Employee(2, "Lily", 20, "Female", 11000));
            }
        });
        doReturn(Optional.of(oldCompany)).when(jpaCompanyRepository).findById(1);
        doReturn(oldCompany).when(jpaCompanyRepository).save(oldCompany);

        // when
        Company updatedEmployee = companyService.update(1, newCompany);
        // then
        verify(jpaCompanyRepository).save(oldCompany);
        assertThat(updatedEmployee.getCompanyName(), equalTo(newCompany.getCompanyName()));
    }

    @Test
    void should_return_nothing_when_delete_given_company_id() {
        //given
        Company company = new Company(1, "oocl", Arrays.asList(new Employee(1, "Jone", 23, "Male", 100)));
        doReturn(Optional.of(company)).when(jpaCompanyRepository).findById(company.getId());

        //when
        companyService.delete(company.getId());

        //then
        verify(jpaCompanyRepository).deleteById(company.getId());
    }

    @Test
    void should_return_added_company_when_add_given_a_company() {
        //given
        Company company = new Company(1, "oocl", Arrays.asList(new Employee(1, "jone", 23, "Male", 100)));
        doReturn(company).when(jpaCompanyRepository).save(company);

        //when
        Company addedCompany = companyService.save(company);

        //then
        verify(jpaCompanyRepository).save(company);
        assertEquals("oocl", addedCompany.getCompanyName());
        assertNotNull(addedCompany.getId());

    }

    @Test
    void should_return_companies_when_find_companies_by_page_and_page_size() {
        // given
        List<Company> companies = new ArrayList<Company>() {{
            add(new Company(1, "oocl", Arrays.asList(new Employee(1, "Jone", 23, "Male", 100))));
            add(new Company(2, "hxt", Arrays.asList(new Employee(2, "Marcus", 23, "Male", 100))));
        }};
        doReturn(new PageImpl(companies)).when(jpaCompanyRepository).findAll(PageRequest.of(0, 2));

        // when
        List<Company> actualCompanies = companyService.findCompaniesByPageAndPageSize(0, 2);

        //then
        assertThat(actualCompanies, hasSize(2));
        assertEquals("oocl", actualCompanies.get(0).getCompanyName());
    }

    @Test
    void should_return_company_when_find_company_by_company_id() {
        // given
        Company company = new Company(1, "oocl", Arrays.asList(new Employee(1, "Jone", 23, "Male", 100)));
        doReturn(Optional.of(company)).when(jpaCompanyRepository).findById(company.getId());

        // when
        Company actualCompany = companyService.findCompanyById(company.getId());

        //then
        assertEquals(company, actualCompany);
    }

    @Test
    void should_return_company_employees_when_find_employees_by_company_id() {
        // given
        List<Employee> employees = new ArrayList<Employee>() {{
            add(new Employee(1, "Lily", 18, "female", 3000));
            add(new Employee(2, "Lily1", 18, "female", 3000));
            add(new Employee(3, "Lily2", 18, "female", 3000));
        }};
        Company company = new Company(1, "oocl", employees);
        doReturn(Optional.of(company)).when(jpaCompanyRepository).findById(1);

        // when
        List<Employee> actualEmployees = companyService.findCompanyAllEmployeesByCompanyId(1);

        //then
        assertThat(actualEmployees, hasSize(3));
        assertEquals("Lily2", actualEmployees.get(2).getName());
    }


}
