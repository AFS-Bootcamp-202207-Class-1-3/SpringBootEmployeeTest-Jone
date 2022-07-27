package com.rest.springbootemployee.entity;

public class Employee {
    private Integer id;
    private final String name;
    private final Integer age;
    private final String gender;
    private Integer salary;

    public Employee(Integer id, String name, Integer age, String gender, Integer salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public void update(Employee employee) {
        this.salary = employee.getSalary();
    }
}
