package com.manage.repository;

import com.manage.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

  @Query(value = "SELECT p.* FROM payroll p"
          + " WHERE p.month = :month"
          + " AND p.year = :year"
          + " AND p.employee_id = :employeeId",
          nativeQuery = true)
  List<Payroll> getPayrollByMonthAndYearAndEmployeeId(@Param("month") Integer month,
                                                      @Param("year") Integer year,
                                                      @Param("employeeId") Long employeeId);

  @Query(value = "SELECT p.* FROM payroll p"
          + " WHERE p.month = :month"
          + " AND p.year = :year",
          nativeQuery = true)
  List<Payroll> getPayrollByMonthAndYear(@Param("month") Integer month,
                                                      @Param("year") Integer year);

}
