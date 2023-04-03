package com.manage.repository;

import com.manage.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  @Query(value = "SELECT e.* FROM employee e WHERE e.status = :status"
          + " ORDER BY e.employee_id ASC",
          nativeQuery = true)
  List<Employee> findAllByStatus(@Param("status") Integer status);

  @Query(value = "select max(employee_id) from employee", nativeQuery = true)
  Long getMaxId();

  Employee findEmployeeByEmployeeId(Long employeeId);
}
