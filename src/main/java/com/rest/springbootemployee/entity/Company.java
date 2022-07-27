package com.rest.springbootemployee.entity;

import java.util.List;

public class Company {
    private final String companyName;
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

    public Boolean addEmployees(List<Employee> employees) {
        employees.forEach(employee -> this.getEmployees().add(employee));
        return true;
    }

}
