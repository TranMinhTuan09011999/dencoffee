package com.manage.repository;

import com.manage.model.WorkHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkHistoryRepository extends JpaRepository<WorkHistory, Long> {

  @Query(value = "SELECT w.* FROM work_history w WHERE w.employee_id = :employeeId",
          nativeQuery = true)
  List<WorkHistory> getWorkHistoryByEmployeeId(@Param("employeeId") Long employeeId);

}
