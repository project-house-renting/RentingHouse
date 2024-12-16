package fact.it.paymentservice.service;

import fact.it.paymentservice.dto.PaymentResponse;
import fact.it.paymentservice.model.Payment;
import fact.it.paymentservice.repository.PaymentRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @PostConstruct
    public void loadData() {
        if (paymentRepository.count() <= 0) {
            Payment payment = Payment.builder()
                    .tenantId(1L)
                    .amount(125)
                    .build();
            paymentRepository.save(payment);
        }
    }

    public List<PaymentResponse> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(this::mapToPaymentResponse).toList();
    }

    public List<PaymentResponse> getAllPaymentsFromTenant(Long id) {
        List<Payment> payments = paymentRepository.findAllByTenantId(id);
        return payments.stream().map(this::mapToPaymentResponse).toList();
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .amount(payment.getAmount())
                .build();
    }
}
