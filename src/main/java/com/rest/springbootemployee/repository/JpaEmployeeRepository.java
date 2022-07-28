package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEmployeeRepository extends JpaRepository<Employee, Integer> {
}
