package fact.it.contractservice.service;

import fact.it.contractservice.dto.ContractResponse;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractService {
    private final ContractRepository contractRepository;
    private final WebClient webClient;

    @Value("${tenantService.baseurl}")
    private String tenantServiceBaseUrl;

    @Value("${homeService.baseurl}")
    private String homeServiceBaseUrl;

    @PostConstruct
    public void loadData() {
        if (contractRepository.count() <= 0) {
            Contract contract = Contract.builder()
                    .homeId("6759a5649843d777d2041343")
                    .tenantId(1L)
                    .startDate(LocalDate.now().minusDays(26))
                    .endDate(LocalDate.now().minusDays(4))
                    .build();

            Contract contract1 = Contract.builder()
                    .homeId("6759a5649843d777d2041343")
                    .tenantId(2L)
                    .startDate(LocalDate.now().minusDays(7))
                    .endDate(LocalDate.now().minusDays(2))
                    .build();

            Contract contract2 = Contract.builder()
                    .homeId("6759a5649843d777d2041343")
                    .tenantId(1L)
                    .startDate(LocalDate.now().minusDays(1))
                    .endDate(LocalDate.now().plusDays(30))
                    .build();

            contractRepository.save(contract);
            contractRepository.save(contract1);
            contractRepository.save(contract2);
        }
    }

    public List<ContractResponse> getAll() {
        List<Contract> tenants = contractRepository.findAll();
        return tenants.stream().map(this::mapToContractResponse).toList();
    }

    public List<ContractResponse> getAllContractsByHomeId(String id) {
        List<Contract> contracts = contractRepository.findContractsByHomeId(id);
        return contracts.stream().map(this::mapToContractResponse).toList();
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
                .isActive(isActiveContract(contract))
                .build();
    }

    public boolean isActiveContract(Contract contract) {
        LocalDate today = LocalDate.now();
        return !contract.getStartDate().isAfter(today) && !contract.getEndDate().isBefore(today);
    }
}
