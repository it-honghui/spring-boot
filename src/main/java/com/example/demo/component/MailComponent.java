package com.example.demo.component;

import cn.hutool.core.io.IoUtil;
import com.example.demo.config.CcEmail;
import com.example.demo.domain.ERR;
import com.example.demo.domain.Recode;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author honghui 2022/3/14
 */
@Slf4j
@Component
public class MailComponent {

  @Value("${spring.mail.username}")
  private String from;
  @Value("${email.adminEmail}")
  private String admin;
  @Value("${spring.profiles.active}")
  private String springProfileActive;

  private final ExecutorService threadPool = Executors.newCachedThreadPool();
  private final JavaMailSender emailSender;
  private final CcEmail ccEmail;
  private final Handlebars handlebars;
  private final String mailContentTemplate;

  public MailComponent(JavaMailSender emailSender, CcEmail ccEmail) {
    this.emailSender = emailSender;
    this.ccEmail = ccEmail;
    this.handlebars = new Handlebars();
    ClassPathResource html = new ClassPathResource("mail.html");
    try {
      mailContentTemplate = IoUtil.read(html.getInputStream(), StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("mail html cannot transfer to string!");
    }
  }

  @PostConstruct
  private void postConstruct() {
    log.info("mailSender: {}, adminEmail: {}, ccEmailList: {}", from, admin, ccEmail.getCcEmailList());
  }

  public void sendAsync(MailSending s) {
    threadPool.submit(() -> {
      try {
        send(s);
      } catch (Exception e) {
        log.error("邮件发送异常", e);
      }
    });
  }

  public void send(MailSending s) throws Exception {
    MimeMessage mimeMessage = emailSender.createMimeMessage();
    // 通过MimeMessageHelper进行邮件内容的设置
    MimeMessageHelper h = new MimeMessageHelper(mimeMessage, true);
    h.setSubject(s.getSubject() + ("prod".equalsIgnoreCase(springProfileActive) ? "" : "（非生产环境）"));
    // 通过html模板发送邮件
    if (s.getHtml() && s.getHtmlTemplate()) {
      String mailContent;
      try {
        Template template = handlebars.compileInline(mailContentTemplate);
        mailContent = template.apply(s.getParams());
        mailContent = StringEscapeUtils.unescapeXml(mailContent);
        h.setText(mailContent, s.getHtml());
      } catch (IOException e) {
        throw new ERR(Recode.FAIL_RENDER_MAIL);
      }
    } else {
      h.setText(s.getContent(), s.getHtml());
    }

    if (s.getAttachments() != null) {
      for (MailSending.Attachment attachment : s.getAttachments()) {
        h.addAttachment(attachment.getName(), attachment.getDataSource());
      }
    }
    // 发件人
    h.setFrom(from);
    // 收件人
    if (s.getTo() != null && s.getTo().size() > 0) {
      h.setTo(s.getTo().toArray(new String[0]));
    } else {
      h.setTo(new String[]{admin});
    }
    // 抄送
    if (s.getCcAdmin()) {
      for (String email : ccEmail.getCcEmailList()) {
        s.addCc(email);
      }
    }
    if (s.getCc() != null && s.getCc().size() > 0) {
      h.setCc(s.getCc().toArray(new String[0]));
    }
    // 密送
    if (s.getBccAdmin()) {
      s.addBcc(admin);
    }
    if (s.getBcc() != null && s.getBcc().size() > 0) {
      h.setBcc(s.getBcc().toArray(new String[0]));
    }

    emailSender.send(mimeMessage);
    log.info("send mail success, {}", s);
  }

  @Getter
  @Setter
  public static class MailSending {
    private String subject;
    private String content;
    private Map<String, String> params;
    private Boolean html = false;
    private Boolean htmlTemplate = false;
    private List<Attachment> attachments;
    private Set<String> to;
    private Set<String> cc;
    private Set<String> bcc;
    private Boolean ccAdmin = true;
    private Boolean bccAdmin = false;

    public static MailSending simple(String subject, String content, String... to) {
      MailSending s = new MailSending();
      s.subject = subject;
      s.content = content;
      s.to = new HashSet<>(Arrays.asList(to));
      return s;
    }

    public static MailSending simpleTemplate(String subject, Map<String, String> params, String... to) {
      MailSending s = new MailSending();
      s.subject = subject;
      s.params = params;
      s.to = new HashSet<>(Arrays.asList(to));
      return s;
    }

    public static MailSending html(String subject, String content, String... to) {
      MailSending s = simple(subject, content, to);
      s.html = true;
      return s;
    }

    public static MailSending htmlTemplate(String subject, Map<String, String> params, String... to) {
      MailSending s = simpleTemplate(subject, params, to);
      s.html = true;
      s.htmlTemplate = true;
      return s;
    }

    public void addCc(String email) {
      if (containsEmail(to, email)) {
        return;
      }
      if (cc == null) {
        cc = new HashSet<>();
      }
      cc.add(email);
    }

    public void addBcc(String email) {
      if (containsEmail(to, email) ||
          containsEmail(cc, email)) {
        return;
      }
      if (bcc == null) {
        bcc = new HashSet<>();
      }
      bcc.add(email);
    }

    private boolean containsEmail(Set<String> set, String email) {
      return set != null && set.contains(email);
    }

    public static MailSending simpleCcAdmin(String subject, String content, String... to) {
      MailSending s = simple(subject, content, to);
      s.ccAdmin = true;
      return s;
    }

    @Getter
    public static class Attachment {
      private String name;
      private DataSource dataSource;
    }

    @Override
    public String toString() {
      return subject + "|" + to;
    }
  }
}
