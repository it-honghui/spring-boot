package com.example.demo.service.impl;

import com.example.demo.component.MailComponent;
import com.example.demo.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author honghui 2022/3/14
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

  private final MailComponent mailComponent;

  public EmailServiceImpl(MailComponent mailComponent) {
    this.mailComponent = mailComponent;
  }

  @Override
  public void sendEmail() throws Exception {
    String content = "    <tr style='border: 1px solid #ddd;padding: 5px;'>\n" +
        "        <td style='padding:10px;text-align: center;'>admin</td>\n" +
        "        <td style='padding:10px;text-align: center;'>123456</td>\n" +
        "    </tr>\n" +
        "    <tr style='border: 1px solid #ddd;padding: 5px;'>\n" +
        "        <td style='padding:10px;text-align: center;'>test</td>\n" +
        "        <td style='padding:10px;text-align: center;'>123456</td>\n" +
        "    </tr>\n";

    mailComponent.send(MailComponent.MailSending.htmlTemplate(
        "账号信息列表通知", getEmailParams(content)));
  }

  private Map<String, String> getEmailParams(String content) {
    Map<String, String> params = new HashMap<>();
    params.put("content", content);
    return params;
  }

}
