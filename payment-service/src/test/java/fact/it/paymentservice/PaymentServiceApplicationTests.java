package fact.it.paymentservice;

import fact.it.paymentservice.controller.PaymentController;
import fact.it.paymentservice.dto.PaymentResponse;
import fact.it.paymentservice.model.Payment;
import fact.it.paymentservice.repository.PaymentRepository;
import fact.it.paymentservice.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceApplicationTests {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;



    @Test
    void testGetAllPaymentsFromTenant() {
        // Arrange
        Long tenantId = 1L;
        String homeId = "home1";

        List<PaymentResponse> paymentResponses = List.of(
                new PaymentResponse(500, "Credit card",  LocalDate.of(2024, 12, 20), "TXN-298765QLSD7831"),
                new PaymentResponse(300, "Cash", LocalDate.of(2024, 11, 15), "TXN-438190ZKGH1245")
        );

        when(paymentService.getAllPaymentsFromTenant(tenantId, homeId)).thenReturn(paymentResponses);

        // Act
        List<PaymentResponse> responses = paymentController.getAllPaymentsFromTenant(tenantId, homeId);

        // Assert
        assertEquals(2, responses.size());
        assertEquals(500.0, responses.get(0).getAmount());

        verify(paymentService, times(1)).getAllPaymentsFromTenant(tenantId, homeId);
    }

}
