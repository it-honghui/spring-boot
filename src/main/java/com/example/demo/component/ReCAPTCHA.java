package com.example.demo.component;

import com.example.demo.domain.ERR;
import com.example.demo.domain.Recode;
import com.example.demo.util.ServerUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ReCAPTCHA v3
 * https://www.google.com/recaptcha/about/
 */
@Slf4j
@Component
public class ReCAPTCHA {
  @Value("${reCAPTCHA.url}")
  private String url;
  @Value("${reCAPTCHA.key}")
  private String key;
  @Value("${reCAPTCHA.threshold}")
  private float threshold;

  private final HttpServletRequest request;

  private static final RestTemplate REST_TEMPLATE = new RestTemplate();

  public ReCAPTCHA(HttpServletRequest request) {
    this.request = request;
  }

  public void check(String token, String action) {
    MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
    postParameters.add("secret", key);
    postParameters.add("response", token);
    postParameters.add("remoteip", ServerUtil.getIp(request));
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/x-www-form-urlencoded");
    HttpEntity<MultiValueMap<String, Object>> r = new HttpEntity<>(postParameters, headers);
    ResponseEntity<Resp> resp = REST_TEMPLATE.postForEntity(url, r, Resp.class);
    Resp re = resp.getBody();
    if (resp.getStatusCode().equals(HttpStatus.OK) && re != null) {
      if (!action.equals(re.action) || re.score < threshold) {
        log.info("reCAPTCHA action:{}, threshold:{}, {}", action, threshold, re);
        throw ERR.of(Recode.ACTION_EXCEPTION);
      }
    } else {
      throw ERR.of(Recode.SERVER_BUSY);
    }
  }
}

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Resp {
  String success;      // whether this request was a valid reCAPTCHA token for your site
  float score;         // the score for this request (0.0 - 1.0)
  String action;       // the action name for this request (important to verify)
  @JsonProperty("challenge_ts")
  String challengeTs;  // timestamp of the challenge load (ISO format yyyy-MM-dd'T'HH:mm:ssZZ)
  String hostname;     // the hostname of the site where the reCAPTCHA was solved
  @JsonProperty("error-codes")
  List<String> errorCodes; // optional
}
