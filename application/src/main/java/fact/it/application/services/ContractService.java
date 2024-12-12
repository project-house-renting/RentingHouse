package fact.it.application.services;

import fact.it.application.Dto.ContractResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {
    private static final String BASE_URL = "http://localhost:8086/api/contract";

    public List<ContractResponse> getContractsFromHome(String homeId) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = BASE_URL + "/home/" + homeId;
        return Arrays.stream(restTemplate.getForObject(apiUrl, ContractResponse[].class)).toList();
    }

    public Optional<ContractResponse> getActiveContract(List<ContractResponse> contracts) {
        return contracts.stream()
                .filter(ContractResponse::isActive)
                .findFirst();
    }
}
