package com.manage.services.bl;

import com.manage.model.IpAddress;
import com.manage.repository.IpAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.SystemException;

@Service
public class RegisterIpAddressService {

  private static final Logger logger = LoggerFactory.getLogger(RegisterIpAddressService.class);

  @Autowired
  private IpAddressRepository ipAddressRepository;

  public Boolean registerIpAddress(String ipAddress) throws SystemException {
    try {
      saveIpAddress(ipAddress);
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  private void saveIpAddress(String ipAddess) {
    IpAddress ipAddress = new IpAddress();
    ipAddress.setIpAddress(ipAddess);
    ipAddress.setStatus(1);
    ipAddressRepository.save(ipAddress);
  }

}
