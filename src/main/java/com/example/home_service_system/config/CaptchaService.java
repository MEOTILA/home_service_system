package com.example.home_service_system.config;

import com.example.home_service_system.exceptions.CustomApiException;
import com.example.home_service_system.exceptions.CustomApiExceptionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//@RequiredArgsConstructor
@Service
public class CaptchaService {
    private static final String RECAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify";

    @Value("${google.recaptcha.secret-key}")
    private String secretKey;

    private final RestTemplate restTemplate;

    public CaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void verify(String captchaToken) {
        if (captchaToken == null || captchaToken.trim().isEmpty()) {
            throw new CustomApiException("CAPTCHA token is missing or invalid!", CustomApiExceptionType.BAD_REQUEST);
        }

        String url = RECAPTCHA_URL + "?secret=" + secretKey + "&response=" + captchaToken;

        CaptchaResponse response = restTemplate.postForObject(url, null, CaptchaResponse.class);

        if (response == null || !response.isSuccess()) {
            throw new CustomApiException("CAPTCHA verification failed!", CustomApiExceptionType.BAD_REQUEST);
        }

        if (!"localhost".equals(response.getHostname())) {
            throw new CustomApiException("Invalid hostname in CAPTCHA response!", CustomApiExceptionType.BAD_REQUEST);
        }
    }

    private static class CaptchaResponse {
        private boolean success;
        private String challenge_ts;
        private String hostname;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getChallenge_ts() {
            return challenge_ts;
        }

        public void setChallenge_ts(String challenge_ts) {
            this.challenge_ts = challenge_ts;
        }

        public String getHostname() {
            return hostname;
        }

        public void setHostname(String hostname) {
            this.hostname = hostname;
        }
    }
}