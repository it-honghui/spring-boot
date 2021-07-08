package com.example.demo.controller;

import com.example.demo.component.ReCAPTCHA;
import com.example.demo.domain.P;
import com.example.demo.domain.R;
import com.example.demo.domain.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.PageUtil;
import org.springframework.web.bind.annotation.*;

/**
 * @author honghui 2021/07/07
 */
@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;
  private final ReCAPTCHA reCAPTCHA;

  public UserController(UserService userService,
                        ReCAPTCHA reCAPTCHA) {
    this.userService = userService;
    this.reCAPTCHA = reCAPTCHA;
  }


  @PostMapping
  public R<User> createContactDetails(@RequestParam String token,
                                      @RequestParam String action,
                                      @RequestParam String username,
                                      @RequestParam String password) {
    reCAPTCHA.check(token, action);
    return R.ok(userService.createUser(User.builder()
        .username(username)
        .password(password).build()));
  }

  @GetMapping
  public R<P<User>> findContactDetailsList(@RequestParam(required = false, defaultValue = "0") Integer page,
                                           @RequestParam(required = false, defaultValue = "10") Integer size) {
    return R.ok(userService.findUserList(PageUtil.of(page, size)));
  }
}
