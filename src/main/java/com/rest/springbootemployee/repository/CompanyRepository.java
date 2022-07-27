package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companyList;

    public CompanyRepository() {
        companyList = new ArrayList<Company>() {{
            add(new Company(1, "oocl", Arrays.asList(new Employee(1, "Jone", 23, "Male", 100))));
            add(new Company(2, "IQAX", Arrays.asList(new Employee(2, "Marcus", 23, "Male", 100))));
        }};
    }

    public List<Company> findAllCompanies() {
        return companyList;
    }

    public Company findCompanyById(Integer id) {
        return companyList.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new NotFoundException(Company.class.getSimpleName()));
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

    public Company save(Company company) {
        companyList.add(company);
        return company;
    }

    public Company update(Integer id, Company newCompany) {
        Company oldCompany = findCompanyById(id);
        oldCompany.update(newCompany);
        return oldCompany;
    }

    public Boolean delete(Integer id) {
        return companyList.remove(findCompanyById(id));
    }

    public void clearAll() {
        companyList.clear();
    }
}
