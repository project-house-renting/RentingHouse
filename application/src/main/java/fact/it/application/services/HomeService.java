package fact.it.application.services;


import fact.it.application.Dto.HomeResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


@Service
public class HomeService {
    @Value("${apiGateway.baseurl}")
    private String baseUrl;

    public ArrayList<HomeResponse> getAvailableHomes() {
        WebClient webClient = WebClient.builder().build();

        return webClient.get()
                .uri("http://" + baseUrl + "/homes")
                .retrieve()
                .bodyToMono(HomeResponse[].class)
                .map(homeResponses -> new ArrayList<>(Arrays.asList(Objects.requireNonNull(homeResponses))))
                .block();
    }

    public ArrayList<HomeResponse> getAllHomes(HttpSession session) {
        WebClient webClient = WebClient.builder()
                .defaultHeader("Authorization", "Bearer " + session.getAttribute("accessToken"))
                .build();

        return webClient.get()
                .uri("http://" + baseUrl + "/homes/all")
                .retrieve()
                .bodyToMono(HomeResponse[].class)
                .map(homeResponses -> new ArrayList<>(Arrays.asList(Objects.requireNonNull(homeResponses))))
                .block();
    }

    public HomeResponse getHomeById(String id, HttpSession session) {
        WebClient webClient = WebClient.builder()
                .defaultHeader("Authorization", "Bearer " + session.getAttribute("accessToken"))
                .build();

        return webClient.get()
                .uri("http://" + baseUrl + "/home/" + id)
                .retrieve()
                .bodyToMono(HomeResponse.class)
                .block();
    }
}
