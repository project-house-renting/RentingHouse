package fact.it.tenantservice.repository;

import fact.it.tenantservice.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TenantRepository extends JpaRepository<Tenant, Long> {
    @Query("SELECT t FROM Tenant t WHERE t.id = :id")
    Tenant findTenantById(Long id);
}
