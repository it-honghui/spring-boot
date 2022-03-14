package com.example.demo.controller;

import com.example.demo.component.GuavaCacheComponent;
import com.example.demo.component.ReCAPTCHA;
import com.example.demo.domain.P;
import com.example.demo.domain.R;
import com.example.demo.domain.Recode;
import com.example.demo.domain.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.PageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author honghui 2021/07/07
 */
@RestController
@RequestMapping("/user")
public class UserController {

  private final GuavaCacheComponent guavaCacheComponent;
  private final UserService userService;
  private final ReCAPTCHA reCAPTCHA;

  public UserController(GuavaCacheComponent guavaCacheComponent,
                        UserService userService,
                        ReCAPTCHA reCAPTCHA) {
    this.guavaCacheComponent = guavaCacheComponent;
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

  @GetMapping("/page")
  public R<P<User>> findUserList(@RequestParam(required = false, defaultValue = "0") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer size) {
    return R.ok(userService.findUserList(PageUtil.of(page, size)));
  }

  @GetMapping
  public R<List<User>> findUserList() {
    return R.ok(guavaCacheComponent.findUserList());
  }

  @GetMapping("/query")
  public R<List<User>> findUserQuery(@RequestParam(required = false) String username,
                                     @RequestParam(required = false) String password) {
    return R.ok(userService.findUserQuery(username, password));
  }

  @GetMapping("/{id}")
  public R<User> findUser(@PathVariable Long id) {
    return R.ok(userService.findUser(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, Recode.NOT_FOUND.getMsg() + " user")));
  }

  @PostMapping("/updateUsername/{id}")
  public void updateUsername(@PathVariable Long id,
                             @RequestParam String username) {
    userService.updateUsername(id, username);
  }

}
