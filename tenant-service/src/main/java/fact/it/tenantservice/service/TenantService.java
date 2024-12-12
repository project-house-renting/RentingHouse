package fact.it.tenantservice.service;

import fact.it.tenantservice.dto.TenantResponse;
import fact.it.tenantservice.model.Tenant;
import fact.it.tenantservice.repository.TenantRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class TenantService {
    private final TenantRepository tenantRepository;

    @PostConstruct
    public void loadData() {
        if (tenantRepository.count() <= 0) {
            Tenant tenant = Tenant.builder()
                    .name("Jos")
                    .description("fffssqfssqsqfsq")
                    .email("jos@jos.com")
                    .build();

            Tenant tenant1 = Tenant.builder()
                    .name("Marie")
                    .description("hello")
                    .email("marie@dfqfqs.com")
                    .build();

            tenantRepository.save(tenant);
            tenantRepository.save(tenant1);
        }
    }

    public List<TenantResponse> getAllTenants() {
        List<Tenant> tenants = tenantRepository.findAll();
        return tenants.stream().map(this::mapToTenantResponse).toList();
    }

    public TenantResponse getTenantById(Long tenantId) {
        Tenant tenant = tenantRepository.findTenantById(tenantId);
        return mapToTenantResponse(tenant);
    }

    private TenantResponse mapToTenantResponse(Tenant tenant) {
        return TenantResponse.builder()
                .id(tenant.getId())
                .name(tenant.getName())
                .email(tenant.getEmail())
                .description(tenant.getDescription())
                .build();
    }
}
