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
            List<Tenant> tenants = List.of(
                    Tenant.builder()
                            .name("Jos")
                            .description("Jos is an enthusiastic young man who is always looking for new challenges.")
                            .email("jos.jansen@example.com")
                            .gender("male")
                            .build(),
                    Tenant.builder()
                            .name("Marie")
                            .description("Marie is a passionate gardener who loves traveling to exotic destinations.")
                            .email("marie.douglas@example.com")
                            .gender("female")
                            .build(),
                    Tenant.builder()
                            .name("Sofie")
                            .description("Sofie is a freelance graphic designer with a passion for art and culture.")
                            .email("sofie.van.dam@example.com")
                            .gender("female")
                            .build()
            );

            tenantRepository.saveAll(tenants);
        }
    }

    public TenantResponse getTenantById(Long tenantId) {
        Tenant tenant = tenantRepository.findTenantById(tenantId);
        return mapToTenantResponse(tenant);
    }

    private TenantResponse mapToTenantResponse(Tenant tenant) {
        return TenantResponse.builder()
                .name(tenant.getName())
                .email(tenant.getEmail())
                .description(tenant.getDescription())
                .gender(tenant.getGender())
                .build();
    }
}
