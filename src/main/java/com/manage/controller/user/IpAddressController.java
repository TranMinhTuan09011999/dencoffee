package com.manage.controller.user;

import com.manage.services.bl.CheckIpAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.SystemException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/api/user/ip")
public class IpAddressController {

  @Autowired
  private CheckIpAddressService checkIpAddressService;

  @GetMapping("/ip-address-attendance")
  public ResponseEntity<?> getIpAddressForAttedance(HttpServletRequest request) throws SystemException, UnknownHostException {
    String ipAddress = request.getRemoteAddr();
    System.out.println(ipAddress);
    InetAddress localip = InetAddress.getLocalHost();
    System.out.println(localip);
    ipAddress = localip.getHostAddress();
    System.out.println(ipAddress);
    Boolean result = checkIpAddressService.checkIpAddress(ipAddress);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }
}
