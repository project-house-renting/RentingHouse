package fact.it.paymentservice.repository;

import fact.it.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByHomeIdAndTenantId(@Param("homeId") String homeId, @Param("tenantId") Long tenantId);
}
