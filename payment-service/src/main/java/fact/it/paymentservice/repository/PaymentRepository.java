package fact.it.paymentservice.repository;

import fact.it.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.tenantId = :tenantId")
    List<Payment> findAllByTenantId(@Param("tenantId") Long tenantId);
}
