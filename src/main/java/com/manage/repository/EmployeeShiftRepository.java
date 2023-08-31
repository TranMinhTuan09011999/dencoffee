package com.manage.repository;

import com.manage.model.EmployeeShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeShiftRepository extends JpaRepository<EmployeeShift, Long> {

    @Query(value = "SELECT e.* FROM employee_shift e"
            + " ORDER BY e.employee_shift_id DESC",
            nativeQuery = true)
    List<EmployeeShift> findAllOrderDESC();

}
