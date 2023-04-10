package com.manage.controller.admin;

import com.manage.dto.IpAddressDTO;
import com.manage.dto.IpAddressRequestDTO;
import com.manage.model.IpAddress;
import com.manage.services.IpAddressService;
import com.manage.services.bl.CheckIpAddressService;
import com.manage.services.bl.RegisterIpAddressService;
import com.manage.services.bl.UpdateIpAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.SystemException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/ip-address")
public class IpAddressForAdminController {

  @Autowired
  private RegisterIpAddressService registerIpAddressService;

  @Autowired
  private IpAddressService ipAddressService;

  @Autowired
  private UpdateIpAddressService updateIpAddressService;

  @Autowired
  private CheckIpAddressService checkIpAddressService;

  @PostMapping("/register-ip-address")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> registerIpAddress(@RequestBody IpAddressRequestDTO ipAddressRequestDTO) throws SystemException {
    Boolean result = registerIpAddressService.registerIpAddress(ipAddressRequestDTO.getIpAddress());
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/get-ip-address")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> getAllIpAddessByStatus() throws SystemException {
    List<IpAddress> ipAddressList = ipAddressService.getAllIpAddress();
    return ResponseEntity.status(HttpStatus.OK).body(ipAddressList);
  }

  @PostMapping("/update-ip-address")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> updateIpAddress(@RequestBody IpAddressDTO ipAddressDTO) throws SystemException {
    Boolean result = updateIpAddressService.updateIpAddress(ipAddressDTO);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @PostMapping("/delete-ip-address")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> deleteIpAddress(@RequestBody IpAddressDTO ipAddressDTO) throws SystemException {
    Boolean result = updateIpAddressService.updateIpAddressStatus(ipAddressDTO);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping("/check-exist-ip-address/{ipAddress}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> checkExistIpAddress(@PathVariable(value = "ipAddress") String ipAddress) throws SystemException {
    Boolean result = checkIpAddressService.checkIpAddress(ipAddress);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

}
