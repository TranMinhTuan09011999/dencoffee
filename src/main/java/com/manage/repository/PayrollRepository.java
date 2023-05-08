package com.manage.repository;

import com.manage.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

  @Query(value = "SELECT p.position_id, p.position_name, pr.payroll_id, pr.salary, pr.allowance, pr.bonus FROM position p " +
          "INNER JOIN payroll pr ON pr.position_id = p.position_id" +
          " WHERE IF(pr.end_date IS NOT NULL, pr.start_date < :currentDay AND pr.end_date > :currentDay, pr.start_date < :currentDay)" +
          " ORDER BY p.position_id ASC",
          nativeQuery = true)
  List<Object[]> findAllCurrentPayroll(@Param("currentDay") String currentDay);

  @Query(value = "SELECT p.* FROM payroll p WHERE p.position_id = :positionId"
          + " ORDER BY p.payroll_id DESC",
          nativeQuery = true)
  List<Payroll> findAllByPositionId(@Param("positionId") Long positionId);

}
