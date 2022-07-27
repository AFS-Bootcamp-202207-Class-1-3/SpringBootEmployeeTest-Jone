package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundCompany;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companyList;

    public CompanyRepository(EmployeeRepository employeeRepository) {
        companyList = new ArrayList<Company>() {
            {
                add(new Company(1, "Spring", employeeRepository.findAllEmployees()));
                add(new Company(2, "Summer", employeeRepository.findAllEmployees()));
                add(new Company(3, "Aut", employeeRepository.findAllEmployees()));
                add(new Company(4, "A", employeeRepository.findAllEmployees()));
                add(new Company(5, "BBB", employeeRepository.findAllEmployees()));
            }
        };
    }

    public List<Company> findAllCompanies() {
        return companyList;
    }

    public Company findCompanyById(Integer id) {
        return companyList.stream()
                .filter(company -> company.getId() == id)
                .findFirst()
                .orElseThrow(NotFoundCompany::new);
    }

    public List<Employee> findCompanyAllEmployeesByCompanyId(Integer id) {
        return findCompanyById(id).getEmployees();
    }

    public List<Company> findCompaniesByPageAndPageSize(Integer page, Integer pageSize) {
        return companyList.stream()
                .skip((page - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Integer generateId(){
        return companyList.stream()
                .mapToInt(Company::getId)
                .max()
                .orElse(0) + 1;
    }

    public Boolean save(Company company) {
        company.setId(generateId());
        companyList.add(company);
        return true;
    }

    public Boolean update(Integer id,List<Employee> employees) {
        findCompanyById(id).addEmployees(employees);
        return true;
    }

    public Boolean delete(Integer id) {
        return companyList.remove(findCompanyById(id));
    }
}
