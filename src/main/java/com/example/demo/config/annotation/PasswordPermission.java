package com.example.demo.config.annotation;

import java.lang.annotation.*;

/**
 * @author honghui 2022/1/14
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PasswordPermission {

  boolean requirePassword() default false;

}
