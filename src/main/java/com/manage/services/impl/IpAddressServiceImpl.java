package com.manage.services.impl;

import com.manage.model.IpAddress;
import com.manage.repository.IpAddressRepository;
import com.manage.services.IpAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpAddressServiceImpl implements IpAddressService {

  @Autowired
  private IpAddressRepository ipAddressRepository;

  @Override
  public List<IpAddress> getAllIpAddress() {
    return ipAddressRepository.findAll();
  }
}
