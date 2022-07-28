package com.rest.springbootemployee.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;
    // @JoinColumn(name = "companyId")
    @OneToMany(orphanRemoval = true ,mappedBy = "companyId")
    private List<Employee> employees = new ArrayList<>();

    public Company(Integer id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public Company(Integer id, String companyName, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employees;
    }
    public Company() {
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
