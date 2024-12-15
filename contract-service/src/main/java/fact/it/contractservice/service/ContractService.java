package fact.it.contractservice.service;

import fact.it.contractservice.dto.ContractResponse;
import fact.it.contractservice.dto.HomeResponse;
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
                    .homeId("675ee8c67c035e371751f7bc")
                    .tenantId(1L)
                    .startDate(LocalDate.now().minusDays(26))
                    .endDate(LocalDate.now().minusDays(4))
                    .isActive(false)
                    .build();

            Contract contract1 = Contract.builder()
                    .homeId("675ee8c67c035e371751f7bc")
                    .tenantId(2L)
                    .startDate(LocalDate.now().minusDays(7))
                    .endDate(LocalDate.now().minusDays(2))
                    .isActive(false)
                    .build();

            Contract contract2 = Contract.builder()
                    .homeId("675ee8c67c035e371751f7bc")
                    .tenantId(1L)
                    .startDate(LocalDate.now().minusDays(1))
                    .endDate(LocalDate.now().plusDays(30))
                    .isActive(true)
                    .build();

            Contract contract3 = Contract.builder()
                    .homeId("675ee8c67c035e371751f7ba")
                    .tenantId(3L)
                    .startDate(LocalDate.now().minusDays(90))
                    .endDate(LocalDate.now().minusDays(30))
                    .isActive(false)
                    .build();

            Contract contract4 = Contract.builder()
                    .homeId("675ee8c67c035e371751f7bb")
                    .tenantId(3L)
                    .startDate(LocalDate.now().minusDays(15))
                    .endDate(LocalDate.now().plusMonths(2))
                    .isActive(true)
                    .build();

            contractRepository.save(contract);
            contractRepository.save(contract1);
            contractRepository.save(contract2);
            contractRepository.save(contract3);
            contractRepository.save(contract4);
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
                .isActive(contract.isActive())
                .build();
    }

//    public boolean isActiveContract(Contract contract) {
//        LocalDate today = LocalDate.now();
//        return !contract.getStartDate().isAfter(today) && !contract.getEndDate().isBefore(today);
//    }
}
