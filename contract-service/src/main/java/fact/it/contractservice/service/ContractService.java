package fact.it.contractservice.service;

import fact.it.contractservice.dto.ContractResponse;
import fact.it.contractservice.dto.PaymentResponse;
import fact.it.contractservice.dto.TenantResponse;
import fact.it.contractservice.model.Contract;
import fact.it.contractservice.repository.ContractRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractService {
    private final ContractRepository contractRepository;
    private final WebClient webClient;

    @Value("${tenantService.baseurl}")
    private String tenantServiceBaseUrl;

    @Value("${paymentService.baseurl}")
    private String paymentServiceBaseUrl;

    @PostConstruct
    public void loadData() {
        // Jos, Marie, Sofie

        if (contractRepository.count() <= 0) {
            // Oude Veerlebaan
            Contract contract = Contract.builder()
                    .homeId("home1")
                    .tenantId(1L)
                    .startDate(LocalDate.now().minusDays(10))
                    .endDate(LocalDate.now().plusMonths(2))
                    .isActive(true)
                    .build();

            Contract contract1 = Contract.builder()
                    .homeId("home1")
                    .tenantId(3L)
                    .startDate(LocalDate.now().minusDays(42))
                    .endDate(LocalDate.now().minusDays(8))
                    .isActive(false)
                    .build();
            // Oude Veerlebaan

            // Kerkhofweg
            Contract contract2 = Contract.builder()
                    .homeId("home2")
                    .tenantId(1L)
                    .startDate(LocalDate.now().minusDays(64))
                    .endDate(LocalDate.now().minusDays(20))
                    .isActive(false)
                    .build();

            Contract contract3 = Contract.builder()
                    .homeId("home2")
                    .tenantId(2L)
                    .startDate(LocalDate.now().minusDays(210))
                    .endDate(LocalDate.now().minusDays(110))
                    .isActive(false)
                    .build();
            // Kerkhofweg

            contractRepository.save(contract);
            contractRepository.save(contract1);
            contractRepository.save(contract2);
            contractRepository.save(contract3);
        }
    }

    public List<ContractResponse> getAllContractsByHomeId(String id) {
        List<Contract> contracts = contractRepository.findContractsByHomeId(id);
        return contracts.stream().map(this::mapToContractResponse).toList();
    }

    public ContractResponse getActiveContractByHomeId(String id) {
       return contractRepository.findContractsByHomeId(id).stream()
                .filter(Contract::isActive)
                .map(this::mapToContractResponseWithPayments)
                .findFirst().orElse(null);
    }

    private ContractResponse mapToContractResponseWithPayments(Contract contract) {
        ContractResponse response = mapToContractResponse(contract);

        List<PaymentResponse> payments = Arrays.stream(Objects.requireNonNull(webClient.get()
                .uri("http://" + paymentServiceBaseUrl + "/api/payment/tenant/{id}", contract.getTenantId())
                .retrieve()
                .bodyToMono(PaymentResponse[].class)
                .block())).toList();

        response.setPayments(payments);
        return response;
    }

    private ContractResponse mapToContractResponse(Contract contract) {
        TenantResponse tenant = webClient.get()
                .uri("http://" + tenantServiceBaseUrl + "/api/tenant/{id}", contract.getTenantId())
                .retrieve()
                .bodyToMono(TenantResponse.class)
                .block();

        return ContractResponse.builder()
                .tenant(tenant)
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .isActive(contract.isActive())
                .build();
    }
}
