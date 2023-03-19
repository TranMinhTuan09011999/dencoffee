package com.manage.dencoffee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@SpringBootApplication
@ComponentScan({"com.manage"})
@EnableJpaRepositories("com.manage")
@EntityScan("com.manage")
@EnableJdbcHttpSession
public class DencoffeeApplication {

  public static void main(String[] args) {
    SpringApplication.run(DencoffeeApplication.class, args);
  }

}
