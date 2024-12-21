package fact.it.contractservice;

import fact.it.contractservice.dto.ContractResponse;
import fact.it.contractservice.dto.TenantResponse;
import fact.it.contractservice.model.Contract;
import fact.it.contractservice.repository.ContractRepository;
import fact.it.contractservice.service.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class ContractServiceApplicationTests {

    @InjectMocks
    private ContractService contractService;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllContractsByHomeId() {
        // Arrange
        String homeId = "home1";
        List<Contract> mockContracts = List.of(
                Contract.builder()
                        .homeId(homeId)
                        .tenantId(1L)
                        .startDate(LocalDate.of(2024, 12, 1))
                        .endDate(LocalDate.of(2025, 12, 1))
                        .build()
        );

        when(contractRepository.findContractsByHomeId(homeId)).thenReturn(mockContracts);

        // Act
        List<ContractResponse> contractResponses = contractService.getAllContractsByHomeId(homeId);

        // Assert
        assertNotNull(contractResponses);
        assertEquals(1, contractResponses.size());
        assertEquals(homeId, contractResponses.get(0).getTenant().getName()); // Mock name in mapToContractResponse
        verify(contractRepository, times(1)).findContractsByHomeId(homeId);
    }

    @Test
    void testGetCurrentContractByHomeId() {
        // Arrange
        String homeId = "home1";
        Contract activeContract = Contract.builder()
                .homeId(homeId)
                .tenantId(1L)
                .startDate(LocalDate.now().minusDays(10))
                .endDate(LocalDate.now().plusDays(10))
                .build();

        when(contractRepository.findContractsByHomeId(homeId)).thenReturn(List.of(activeContract));
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(TenantResponse.class)).thenReturn(Mono.just(
                TenantResponse.builder().name("John Doe").email("johndoe@example.com").build()));

        // Act
        ContractResponse response = contractService.getCurrentContractByHomeId(homeId);

        // Assert
        assertNotNull(response);
        assertEquals("John Doe", response.getTenant().getName());
        verify(contractRepository, times(1)).findContractsByHomeId(homeId);
    }

    @Test
    void testIsActiveContract() {
        // Arrange
        Contract activeContract = Contract.builder()
                .startDate(LocalDate.now().minusDays(5))
                .endDate(LocalDate.now().plusDays(5))
                .build();

        Contract inactiveContract = Contract.builder()
                .startDate(LocalDate.now().minusMonths(2))
                .endDate(LocalDate.now().minusMonths(1))
                .build();

        // Act & Assert
        assertTrue(contractService.isActiveContract(activeContract));
        assertFalse(contractService.isActiveContract(inactiveContract));
    }

}
