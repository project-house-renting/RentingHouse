package fact.it.application.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Controller
public class AuthController {

    @GetMapping("/auth/callback")
    public void grantCode(String code, HttpServletResponse response, HttpSession session) throws IOException {
        String token = getOauthAccessTokenGoogle(code);
        session.setAttribute("accessToken", token);
        response.sendRedirect("/admin");
    }

    private String getOauthAccessTokenGoogle(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("redirect_uri", "http://localhost:8080/auth/callback");
        params.add("client_id", "750906741361-ve7862ovm9mflgnar64542j99gohu25q.apps.googleusercontent.com");
        params.add("client_secret", "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");
        params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile");
        params.add("scope", "https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email");
        params.add("scope", "openid");
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, httpHeaders);

        String url = "https://oauth2.googleapis.com/token";
        ResponseEntity<GoogleOAuthTokenResponse> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, GoogleOAuthTokenResponse.class);

        GoogleOAuthTokenResponse tokenResponse = response.getBody();
        return tokenResponse.getId_token();
    }

    @Setter
    @Getter
    public static class GoogleOAuthTokenResponse {
        private String id_token;
    }
}
