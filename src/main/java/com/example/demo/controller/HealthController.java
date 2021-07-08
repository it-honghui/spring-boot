package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author honghui 2021/07/07
 */
@RestController
public class HealthController {

  @GetMapping
  public String health() {
    return "healthy!";
  }
}
