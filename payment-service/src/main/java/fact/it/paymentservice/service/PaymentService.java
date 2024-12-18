package fact.it.paymentservice.service;

import fact.it.paymentservice.dto.PaymentResponse;
import fact.it.paymentservice.model.Payment;
import fact.it.paymentservice.repository.PaymentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @PostConstruct
    public void loadData() {
        if (paymentRepository.count() <= 0) {
            List<Payment> payments = List.of(
                    Payment.builder()
                            .tenantId(1L)
                            .homeId("home1")
                            .amount(1200.00f)
                            .method("Credit Card")
                            .date(LocalDate.of(2024, 1, 15))
                            .transactionId("TXN-12395XBCT6789")
                            .build(),
                    Payment.builder()
                            .tenantId(1L)
                            .homeId("home1")
                            .amount(1200.00f)
                            .method("Credit Card")
                            .date(LocalDate.of(2024, 2, 10))
                            .transactionId("TXN-128965QEHD6729")
                            .build(),
                    Payment.builder()
                            .tenantId(1L)
                            .homeId("home2")
                            .amount(1150.00f)
                            .method("Paypal")
                            .date(LocalDate.of(2024, 3, 5))
                            .transactionId("TXN-96762XMZ4111")
                            .build()
            );
            paymentRepository.saveAll(payments);
        }
    }

    public List<PaymentResponse> getAllPaymentsFromTenant(Long tenantId, String homeId) {
        List<Payment> payments = paymentRepository.findAllByHomeIdAndTenantId(homeId, tenantId);
        return payments.stream().map(this::mapToPaymentResponse).toList();
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .date(payment.getDate())
                .transactionId(payment.getTransactionId())
                .build();
    }
}
