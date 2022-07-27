package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author HCD
 * @Date 2022年07月27日 21:35
 * @Version 1.0
 * @Description
 */

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAllCompanies() {
        return companyRepository.findAllCompanies();
    }

    public Company findCompanyById(Integer id) {
        return companyRepository.findCompanyById(id);
    }


    public List<Employee> findCompanyAllEmployeesByCompanyId(Integer id) {
        return companyRepository.findCompanyAllEmployeesByCompanyId(id);
    }

    public List<Company> findCompaniesByPageAndPageSize(Integer page, Integer pageSize) {
        return companyRepository.findCompaniesByPageAndPageSize(page, pageSize);
    }


    public Company save(Company company) {
        company.setId(_generateId());
        return companyRepository.save(company);
    }

    private Integer _generateId() {
        List<Company> companyList = companyRepository.findAllCompanies();
        return companyList.stream()
                .mapToInt(Company::getId)
                .max()
                .orElse(0) + 1;
    }

    public Company update(Integer id, Company company) {
        return companyRepository.update(id, company);
    }

    public void delete(Integer id) {
        companyRepository.delete(id);
    }
}
