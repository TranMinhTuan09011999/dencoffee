package com.manage.repository;

import com.manage.model.SalaryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalaryDetailRepository extends JpaRepository<SalaryDetail, Long> {

  @Query(value = "SELECT sd.* FROM salary_detail sd " +
          " WHERE IF(sd.end_date IS NOT NULL, sd.start_date <= :requiredDay AND sd.end_date >= :requiredDay, sd.start_date <= :requiredDay)" +
          " AND sd.position_id = :positionId",
          nativeQuery = true)
  List<SalaryDetail> getCurrentSalaryDetailByPosition(@Param("requiredDay") String requiredDay, @Param("positionId") Long positionId);

  @Query(value = "SELECT p.position_id, p.position_name, sd.salary_detail_id, sd.salary, sd.allowance FROM salary_detail sd " +
          "INNER JOIN position p ON sd.position_id = p.position_id" +
          " WHERE IF(sd.end_date IS NOT NULL, sd.start_date <= :currentDay AND sd.end_date >= :currentDay, sd.start_date <= :currentDay)" +
          " ORDER BY p.position_id ASC",
          nativeQuery = true)
  List<Object[]> findAllCurrentSalaryDetail(@Param("currentDay") String currentDay);

}
