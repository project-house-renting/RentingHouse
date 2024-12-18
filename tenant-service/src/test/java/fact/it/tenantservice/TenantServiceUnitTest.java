package fact.it.tenantservice;

import fact.it.tenantservice.dto.TenantResponse;
import fact.it.tenantservice.model.Tenant;
import fact.it.tenantservice.repository.TenantRepository;
import fact.it.tenantservice.service.TenantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TenantServiceUnitTest {
    @InjectMocks
    private TenantService tenantService;

    @Mock
    private TenantRepository tenantRepository;

    @Test
    public void testGetTenantById() {
        // Arrange
        Long tenantId = 1L;
        Tenant tenant = new Tenant(tenantId, "John Doe", "johndoe@example.com", "hello", "male");

        when(tenantRepository.findTenantById(tenantId)).thenReturn(tenant);

        // Act
        TenantResponse result = tenantService.getTenantById(tenantId);

        // Assert
        assertEquals(tenant.getName(), result.getName());
        assertEquals(tenant.getEmail(), result.getEmail());
        assertEquals(tenant.getDescription(), result.getDescription());
        assertEquals(tenant.getGender(), result.getGender());

        verify(tenantRepository, times(1)).findTenantById(tenantId);
    }
}
