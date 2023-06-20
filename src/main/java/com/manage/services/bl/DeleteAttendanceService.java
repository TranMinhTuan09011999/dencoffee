package com.manage.services.bl;

import com.manage.model.Attendance;
import com.manage.repository.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import javax.transaction.Transactional;

@Service
public class DeleteAttendanceService {

    private static final Logger logger = LoggerFactory.getLogger(DeleteAttendanceService.class);

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Transactional
    public Boolean deleteAttendance(Long attendanceId) throws SystemException {
        try {
            Attendance attendance = attendanceRepository.getOne(attendanceId);
            attendanceRepository.delete(attendance);
            return true;
        } catch (Exception e) {
            logger.error("Error", e);
            throw new SystemException();
        }
    }

}
