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
          + " ORDER BY a.attendance_id ASC",
          nativeQuery = true)
  List<Attendance> getAttendanceByToday(@Param("today") Date today);

  @Query(value = "SELECT a.* FROM attendance a " +
          "WHERE a.start_date_time >= :dateFrom AND a.start_date_time <= :dateTo"
          + " ORDER BY a.attendance_id ASC",
          nativeQuery = true)
  List<Attendance> getAttendanceForEmployee(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);

  @Query(value = "SELECT a.* FROM attendance a " +
          "WHERE MONTH(a.start_date_time) = :month AND YEAR(a.start_date_time) = :year"
          + " ORDER BY a.attendance_id ASC",
          nativeQuery = true)
  List<Attendance> getAttendanceForMonthYear(@Param("month") Integer month, @Param("year") Integer year);

  @Query(value = "SELECT a.* FROM attendance a " +
          "WHERE MONTH(a.start_date_time) = :month AND YEAR(a.start_date_time) = :year AND a.employee_id = :employeeId"
          + " ORDER BY a.attendance_id ASC",
          nativeQuery = true)
  List<Attendance> getAttendanceForMonthYearAndEmployeeId(@Param("employeeId") Long employeeId, @Param("month") Integer month,
                                                          @Param("year") Integer year);
}
