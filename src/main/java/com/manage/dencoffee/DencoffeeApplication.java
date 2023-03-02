package com.manage.dencoffee;

import com.manage.controller.GreetingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.manage"})
public class DencoffeeApplication {

  public static void main(String[] args) {
    SpringApplication.run(DencoffeeApplication.class, args);
  }

}
