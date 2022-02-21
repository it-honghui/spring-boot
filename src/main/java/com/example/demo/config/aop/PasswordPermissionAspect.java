package com.example.demo.config.aop;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.demo.config.annotation.PasswordPermission;
import com.example.demo.constant.Constant;
import com.example.demo.domain.ERR;
import com.example.demo.domain.Recode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author honghui 2022/1/14
 */
@Aspect
@Component
public class PasswordPermissionAspect {

  @Before("@annotation(com.example.demo.config.annotation.PasswordPermission)")
  private void before(JoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    PasswordPermission permission = method.getAnnotation(PasswordPermission.class);
    if (permission.requirePassword()) {
      ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      if (attributes != null) {
        HttpServletRequest request = attributes.getRequest();
        Object paramObj = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        JSONObject jsonObject = JSONUtil.parseObj(paramObj);
        String password = jsonObject.getStr("password", null);
        if (!password.equals(Constant.PASSWORD)) {
          throw ERR.of(Recode.PASSWORD_ERROR);
        }
      }
    }
  }

}
