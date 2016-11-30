package com.eugene.repository;

import com.eugene.domain.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Eugene on 11/29/2016.
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
