package com.manage.controller;

import com.manage.model.Greeting;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  @GetMapping("/greeting")
  public ResponseEntity<?> greeting() {
    return ResponseEntity.ok(new Greeting(1, "MinhTuan"));
  }
}
