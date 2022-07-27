package com.rest.springbootemployee.entity;

import java.util.List;

public class Company {
    private String companyName;
    private final List<Employee> employees;
    private Integer id;

    public Company(Integer id, String companyName, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void update(Company newCompany) {
        this.companyName = newCompany.getCompanyName();
    }
}
