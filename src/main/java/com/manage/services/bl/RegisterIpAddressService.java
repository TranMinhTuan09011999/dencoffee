package com.manage.services.bl;

import com.manage.model.IpAddress;
import com.manage.repository.IpAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.SystemException;
import javax.transaction.Transactional;

@Service
public class RegisterIpAddressService {

  private static final Logger logger = LoggerFactory.getLogger(RegisterIpAddressService.class);

  @Autowired
  private IpAddressRepository ipAddressRepository;

  @Transactional
  public Boolean registerIpAddress(String ipAddress, String location) throws SystemException {
    try {
      saveIpAddress(ipAddress, location);
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  private void saveIpAddress(String ipAddess, String location) {
    IpAddress ipAddress = new IpAddress();
    ipAddress.setIpAddress(ipAddess);
    ipAddress.setLocation(location);
    ipAddressRepository.save(ipAddress);
  }

}
