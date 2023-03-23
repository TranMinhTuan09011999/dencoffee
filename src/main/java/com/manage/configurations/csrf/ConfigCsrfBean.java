package com.manage.configurations.csrf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ConfigCsrfBean {

  @Autowired
  private Environment env;

  public List<String> getAllowedOrigins() {
    // config web.cors.allowed-origins in file application.properties
    return Arrays.asList(env.getProperty("web.cors.allowed-origins").split(","));
  }

  public List<String> getAllowedMethod() {
    // config web.cors.allowed-methods in file application.properties
    return Arrays.asList(env.getProperty("web.cors.allowed-methods").split(","));
  }

  public List<String> getAllowedHeader() {
    return Arrays.asList("*");
  }

}
