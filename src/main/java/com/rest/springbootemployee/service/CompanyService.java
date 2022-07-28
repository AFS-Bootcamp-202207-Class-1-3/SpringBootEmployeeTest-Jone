package com.rest.springbootemployee.service;

import com.rest.springbootemployee.entity.Company;
import com.rest.springbootemployee.entity.Employee;
import com.rest.springbootemployee.exception.NotFoundException;
import com.rest.springbootemployee.repository.CompanyRepository;
import com.rest.springbootemployee.repository.JpaCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private JpaCompanyRepository jpaCompanyRepository;

    public List<Company> findAllCompanies() {
        return jpaCompanyRepository.findAll();
    }

    public Company findCompanyById(Integer id) {
        return jpaCompanyRepository.findById(id).orElseThrow(() -> new NotFoundException(Company.class.getSimpleName()));
    }


    public List<Employee> findCompanyAllEmployeesByCompanyId(Integer id) {
        return jpaCompanyRepository.findById(id).orElseThrow(() -> new NotFoundException(Company.class.getSimpleName())).getEmployees();
    }

    public List<Company> findCompaniesByPageAndPageSize(Integer page, Integer pageSize) {
        return jpaCompanyRepository.findAll(PageRequest.of(page, pageSize)).toList();
    }


    public Company save(Company company) {

        return jpaCompanyRepository.save(company);
    }

    public Company update(Integer id, Company company) {
        Company oldCompany = findCompanyById(id);
        oldCompany.update(company);
        return jpaCompanyRepository.save(oldCompany);
    }

    public void delete(Integer id) {
        jpaCompanyRepository.deleteById(id);
    }
}
