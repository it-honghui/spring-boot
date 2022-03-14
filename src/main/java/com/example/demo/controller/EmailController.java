package com.example.demo.controller;

import com.example.demo.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author honghui 2022/03/14
 */
@RestController
@RequestMapping("/email")
public class EmailController {

  private final EmailService emailService;

  public EmailController(EmailService emailService) {
    this.emailService = emailService;
  }

  @PostMapping("/sendEmail")
  public void sendEmail() throws Exception {
    emailService.sendEmail();
  }

}
