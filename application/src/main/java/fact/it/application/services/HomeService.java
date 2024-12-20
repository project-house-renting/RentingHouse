package fact.it.application.services;


import fact.it.application.Dto.HomeRequest;
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
                .uri("http://" + baseUrl + "/home/available")
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
                .uri("http://" + baseUrl + "/home/all")
                .retrieve()
                .bodyToMono(HomeResponse[].class)
                .map(homeResponses -> new ArrayList<>(Arrays.asList(Objects.requireNonNull(homeResponses))))
                .block();
    }

    public HomeResponse getHomeDetailsById(String id, HttpSession session) {
        WebClient webClient = WebClient.builder()
                .defaultHeader("Authorization", "Bearer " + session.getAttribute("accessToken"))
                .build();

        return webClient.get()
                .uri("http://" + baseUrl + "/home/" + id + "/details")
                .retrieve()
                .bodyToMono(HomeResponse.class)
                .block();
    }

    public void deleteHome(String id, HttpSession session) {
        WebClient webClient = WebClient.builder()
                .defaultHeader("Authorization", "Bearer " + session.getAttribute("accessToken"))
                .build();

        webClient.delete()
                .uri("http://" + baseUrl + "/home/" + id)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void createHome(HomeRequest homeResponse, HttpSession session) {
        WebClient webClient = WebClient.builder()
                .defaultHeader("Authorization", "Bearer " + session.getAttribute("accessToken"))
                .build();

        webClient.post()
                .uri("http://" + baseUrl + "/home")
                .header("Authorization", "Bearer " + session.getAttribute("accessToken"))
                .bodyValue(homeResponse)
                .retrieve()
                .bodyToMono(HomeRequest.class)
                .block();
    }

    public void updateHome(String id, HomeResponse homeResponse, HttpSession session) {
        WebClient webClient = WebClient.builder()
                .defaultHeader("Authorization", "Bearer " + session.getAttribute("accessToken"))
                .build();

        webClient.put()
                .uri("http://" + baseUrl + "/home/{id}", id)
                .header("Authorization", "Bearer " + session.getAttribute("accessToken"))
                .bodyValue(homeResponse)
                .retrieve()
                .bodyToMono(HomeResponse.class)
                .block();
    }
}
