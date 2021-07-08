package com.example.demo.component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author honghui 2021/07/07
 */
@Slf4j
@Component
public class MailComponent {

  private final ExecutorService threadPool = Executors.newCachedThreadPool();
  private final JavaMailSender emailSender;
  @Value("${spring.mail.username}")
  private String from;
  @Value("${admin_email}")
  private String admin;
  @Value("${spring.profiles.active}")
  private String springProfileActive;

  public MailComponent(JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @PostConstruct
  private void postConstruct() {
    log.info("mailSender: {}, adminEmail: {}", from, admin);
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

  private void send(MailSending s) throws Exception {
    MimeMessage mimeMessage = emailSender.createMimeMessage();
    // 通过MimeMessageHelper进行邮件内容的设置
    MimeMessageHelper h = new MimeMessageHelper(mimeMessage, true);
    h.setSubject(s.getSubject() + ("prod".equalsIgnoreCase(springProfileActive) ? "" : "（非生产环境）"));
    h.setText(s.getContent(), s.getHtml());
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
      s.addCc(admin);
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
    private Boolean html = false;
    private List<Attachment> attachments;
    private Set<String> to;
    private Set<String> cc;
    private Set<String> bcc;
    private Boolean ccAdmin = false;
    private Boolean bccAdmin = false;

    public static MailSending simple(String subject, String content, String... to) {
      MailSending s = new MailSending();
      s.subject = subject;
      s.content = content;
      s.to = new HashSet<>(Arrays.asList(to));
      return s;
    }

    public static MailSending html(String subject, String content, String... to) {
      MailSending s = simple(subject, content, to);
      s.html = true;
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
