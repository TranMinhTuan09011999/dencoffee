package com.manage.services.bl;

import com.manage.model.Payroll;
import com.manage.services.PayrollService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import javax.transaction.Transactional;

@Service
public class UpdateBonusPayrollService {

    private static final Logger logger = LoggerFactory.getLogger(UpdateBonusPayrollService.class);

    @Autowired
    private PayrollService payrollService;

    @Transactional
    public boolean updateBonusPayroll(Long payrollId, Double bonus) throws SystemException {
        try {
            Payroll payroll = payrollService.getPayrollByPayrollId(payrollId);
            payroll.setBonus(bonus);
            payrollService.save(payroll);
            return true;
        } catch (Exception e) {
            logger.error("Error", e);
            throw new SystemException();
        }
    }

}
