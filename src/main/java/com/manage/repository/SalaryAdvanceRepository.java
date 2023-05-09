package com.manage.repository;

import com.manage.model.SalaryAdvance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalaryAdvanceRepository extends JpaRepository<SalaryAdvance, Long> {

  @Query(value = "SELECT sa.* FROM salary_advance sa WHERE sa.employee_id = :employeeId"
          + " AND DATE_FORMAT(sa.salary_advance_date,'%m') = :month"
          + " AND DATE_FORMAT(sa.salary_advance_date,'%Y') = :year"
          + " ORDER BY sa.salary_advance_id ASC",
          nativeQuery = true)
  List<SalaryAdvance> getSalaryAdvanceByMonthAndEmployee(@Param("month") Integer month, @Param("year") Integer year, @Param("employeeId") Long employeeId);

}
