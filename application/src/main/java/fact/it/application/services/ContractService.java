package fact.it.application.services;

import fact.it.application.Dto.ContractResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ContractService {
    @Value("${apiGateway.baseurl}")
    private String baseUrl;

    public List<ContractResponse> getContractsFromHome(String homeId, HttpSession session) {

        WebClient webClient = WebClient.builder()
                .baseUrl("http://" + baseUrl)
                .defaultHeader("Authorization", "Bearer " + session.getAttribute("accessToken"))
                .build();

        return webClient.get()
                .uri("/home/" + homeId + "/contracts")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> clientResponse.createException().flatMap(Mono::error))
                .bodyToMono(ContractResponse[].class)
                .onErrorResume(e -> Mono.just(new ContractResponse[0]))
                .map(homeResponses -> new ArrayList<>(Arrays.asList(Objects.requireNonNull(homeResponses))))
                .block();
    }

    public Optional<ContractResponse> getActiveContract(List<ContractResponse> contracts) {
        return contracts.stream()
                .filter(ContractResponse::isActive)
                .findFirst();
    }
}
