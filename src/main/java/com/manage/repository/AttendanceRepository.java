package com.manage.repository;

import com.manage.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

  @Query(value = "SELECT a.* FROM attendance a WHERE " +
          "DATE_FORMAT(a.start_date_time,'%Y-%m-%d') = DATE_FORMAT(:today,'%Y-%m-%d')"
          + " ORDER BY a.start_date_time ASC",
          nativeQuery = true)
  List<Attendance> getAttendanceByToday(@Param("today") Date today);

  @Query(value = "SELECT a.* FROM attendance a " +
          "WHERE a.start_date_time >= :dateFrom AND a.start_date_time <= :dateTo"
          + " ORDER BY a.start_date_time ASC",
          nativeQuery = true)
  List<Attendance> getAttendanceForEmployee(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

  @Query(value = "SELECT a.* FROM attendance a " +
          "WHERE a.payroll_id = :payrollId"
          + " ORDER BY a.start_date_time ASC",
          nativeQuery = true)
  List<Attendance> getAttendanceForPayrollId(@Param("payrollId") Long payrollId);
}
