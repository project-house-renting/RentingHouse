package fact.it.contractservice;

import fact.it.contractservice.dto.ContractResponse;
import fact.it.contractservice.dto.PaymentResponse;
import fact.it.contractservice.dto.TenantResponse;
import fact.it.contractservice.model.Contract;
import fact.it.contractservice.repository.ContractRepository;
import fact.it.contractservice.service.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

@ExtendWith(MockitoExtension.class)
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
        ReflectionTestUtils.setField(contractService, "tenantServiceBaseUrl", "http://localhost:8082");
        ReflectionTestUtils.setField(contractService, "paymentServiceBaseUrl", "http://localhost:8084");
    }

    @Test
    void testGetAllContractsByHomeId() {
        // Arrange
        String homeId = "home1";
        Long tenantId = 1L;

        List<Contract> mockContracts = List.of(
                Contract.builder()
                        .homeId(homeId)
                        .tenantId(tenantId)
                        .startDate(LocalDate.of(2024, 12, 1))
                        .endDate(LocalDate.of(2025, 12, 1))
                        .build()
        );

        TenantResponse mockTenant = new TenantResponse();
        mockTenant.setName("John Doe");
        mockTenant.setEmail("JohnDoe@gamil.com");
        mockTenant.setGender("male");
        mockTenant.setDescription("jkldfqsjklfjsdklfs");

        when(contractRepository.findContractsByHomeId(homeId)).thenReturn(mockContracts);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), eq(tenantId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(TenantResponse.class)).thenReturn(Mono.just(mockTenant));

        // Act
        List<ContractResponse> contractResponses = contractService.getAllContractsByHomeId(homeId);

        // Assert
        assertNotNull(contractResponses);

        verify(contractRepository, times(1)).findContractsByHomeId(homeId);
    }

    @Test
    void testGetCurrentContractByHomeId() {
        // Arrange
        String homeId = "home1";
        Long tenantId = 1L;

        List<Contract> mockContracts = List.of(
                Contract.builder()
                        .homeId(homeId)
                        .tenantId(tenantId)
                        .startDate(LocalDate.of(2024, 12, 1))
                        .endDate(LocalDate.of(2025, 12, 1))
                        .build()
        );


        TenantResponse mockTenant = new TenantResponse();
        mockTenant.setName("John Doe");
        mockTenant.setEmail("JohnDoe@gamil.com");
        mockTenant.setGender("male");
        mockTenant.setDescription("jkldfqsjklfjsdklfs");

        PaymentResponse mockPayment1 = new PaymentResponse();
        mockPayment1.setAmount(100.00f);
        mockPayment1.setDate(LocalDate.of(2024, 12, 10));

        PaymentResponse mockPayment2 = new PaymentResponse();
        mockPayment2.setAmount(150.00f);
        mockPayment2.setDate(LocalDate.of(2025, 1, 15));

        when(contractRepository.findContractsByHomeId(homeId)).thenReturn(mockContracts);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), eq(tenantId))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(TenantResponse.class)).thenReturn(Mono.just(mockTenant));

        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(responseSpec.bodyToMono(PaymentResponse[].class)).thenReturn(Mono.just(new PaymentResponse[]{mockPayment1, mockPayment2}));

        // Act
        ContractResponse contractResponses = contractService.getCurrentContractByHomeId(homeId);

        // Assert
        assertNotNull(contractResponses);

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
