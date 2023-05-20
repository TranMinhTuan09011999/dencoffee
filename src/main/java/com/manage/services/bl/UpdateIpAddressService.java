package com.manage.services.bl;

import com.manage.dto.IpAddressDTO;
import com.manage.model.IpAddress;
import com.manage.repository.IpAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import javax.transaction.Transactional;

@Service
public class UpdateIpAddressService {

  private static final Logger logger = LoggerFactory.getLogger(UpdateIpAddressService.class);

  @Autowired
  private IpAddressRepository ipAddressRepository;

  @Transactional
  public Boolean deleteIpAddress(IpAddressDTO ipAddressDTO) throws SystemException {
    try {
      IpAddress ipAddress = ipAddressRepository.getOne(ipAddressDTO.getIpAddressId());
      ipAddressRepository.delete(ipAddress);
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  @Transactional
  public Boolean updateIpAddress(IpAddressDTO ipAddressDTO) throws SystemException {
    try {
      IpAddress ipAddress = ipAddressRepository.getOne(ipAddressDTO.getIpAddressId());
      ipAddress.setIpAddress(ipAddressDTO.getIpAddress());
      ipAddress.setLocation(ipAddressDTO.getLocation());
      ipAddressRepository.save(ipAddress);
      return true;
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

}
