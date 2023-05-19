package com.manage.controller.user;

import com.manage.services.bl.CheckIpAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.SystemException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/api/user/ip")
public class IpAddressController {

  private static final Logger logger = LoggerFactory.getLogger(IpAddressController.class);

  @Autowired
  private CheckIpAddressService checkIpAddressService;

  @GetMapping("/ip-address-attendance")
  public ResponseEntity<?> getIpAddressForAttedance(HttpServletRequest request) throws SystemException, UnknownHostException, SocketException {
    String remoteAddr = getIpAdressByHeader(request);
    String macAddress = getClientMACAddress(remoteAddr);
    Boolean result = checkIpAddressService.checkIpAddress(macAddress);
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  public static String getIpAddress(HttpServletRequest request) throws UnknownHostException {
    String remoteAddr = request.getRemoteAddr();
    if (remoteAddr.equals("0:0:0:0:0:0:0:1")) {
      InetAddress localip = InetAddress.getLocalHost();
      remoteAddr = localip.getHostAddress();
    }

    logger.info("After process getRemoteAddr: {}", remoteAddr);
    return remoteAddr;
  }

  public static String getIpAdressByHeader(HttpServletRequest request) throws UnknownHostException {
    logger.info("getIpAdressByHeader - START");
    String ip = request.getHeader("X-Forwarded-For");
    if(!StringUtils.isEmpty(ip)) {
      logger.info("IP Access from X-Forwarded-For: " + ip);
      logger.info("getIpAdressByHeader - END");
      return ip;
    } else {
      logger.info("Before process getRemoteAddr: {}", request.getRemoteAddr());
      ip = getIpAddress(request);
      logger.info("After process getRemoteAddr: {}", ip);
      logger.info("getIpAdressByHeader - END");
      return ip;
    }
  }

  public String getClientMACAddress(String clientIp){
    String str = "";
    String macAddress = "";
    try {
      Process p = Runtime.getRuntime().exec("nbtstat -A " + clientIp);
      InputStreamReader ir = new InputStreamReader(p.getInputStream());
      LineNumberReader input = new LineNumberReader(ir);
      for (int i = 1; i <100; i++) {
        str = input.readLine();
        if (str != null) {
          if (str.indexOf("MAC Address") > 1) {
            macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());
            break;
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace(System.out);
    }
    logger.info("MAC-ADDRESS: " + macAddress);
    return macAddress;
  }
}
