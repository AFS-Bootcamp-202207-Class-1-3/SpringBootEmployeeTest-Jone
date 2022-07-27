package com.rest.springbootemployee;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.EmployeeRepository;
import com.rest.springbootemployee.service.CompanyService;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTests {
    @Spy
    CompanyRepository companyRepository;
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
        doReturn(companies).when(companyRepository).findAllCompanies();
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
        doReturn(oldCompany).when(companyRepository).findCompanyById(1);
        doCallRealMethod().when(companyRepository).update(1, newCompany);

        // when
        Company updatedEmployee = companyService.update(1, newCompany);
        // then
        verify(companyRepository).update(1, newCompany);
        assertThat(updatedEmployee.getCompanyName(), equalTo(newCompany.getCompanyName()));
    }


}