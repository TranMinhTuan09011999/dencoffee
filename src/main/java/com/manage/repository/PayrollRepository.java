package com.manage.repository;

import com.manage.model.PayRoll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PayrollRepository extends JpaRepository<PayRoll, Long> {

  @Query(value = "SELECT p.* FROM payroll p WHERE p.employee_id = :employeeId"
          + " ORDER BY p.payroll_id DESC",
          nativeQuery = true)
  List<PayRoll> findAllByEmployeeId(@Param("employeeId") Long employeeId);

}
