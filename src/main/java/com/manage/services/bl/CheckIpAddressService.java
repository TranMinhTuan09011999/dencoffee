package com.manage.services.bl;

import com.manage.model.IpAddress;
import com.manage.services.IpAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.util.List;
import java.util.Objects;

@Service
public class CheckIpAddressService {

  @Autowired
  private IpAddressService ipAddressService;

  private static final Logger logger = LoggerFactory.getLogger(CheckIpAddressService.class);

  public Boolean checkIpAddress(String ipAddress) throws SystemException {
    try {
      Boolean checkIp = false;
      List<IpAddress> ipAddressList = ipAddressService.getAllIpAddress();
      if (Objects.nonNull(ipAddressList) && !ipAddressList.isEmpty()) {
        for(int i=0; i<ipAddressList.size(); i++) {
          if (Objects.equals(ipAddressList.get(i).getIpAddress(), ipAddress)) {
            checkIp = true;
          }
        }
      }
      return checkIp;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }
}
