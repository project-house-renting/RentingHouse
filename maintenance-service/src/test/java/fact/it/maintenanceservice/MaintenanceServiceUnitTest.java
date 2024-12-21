package fact.it.maintenanceservice;


import fact.it.maintenanceservice.dto.MaintenanceRequest;
import fact.it.maintenanceservice.dto.MaintenanceResponse;
import fact.it.maintenanceservice.model.Maintenance;
import fact.it.maintenanceservice.repository.MaintenanceRepository;
import fact.it.maintenanceservice.service.MaintenanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MaintenanceServiceUnitTest {
    @InjectMocks
    private MaintenanceService maintenanceService;


    @Mock
    private MaintenanceRepository maintenanceRepository;


    @Test
    public void testCreateMaintenance(){
        // Arrange
        MaintenanceRequest maintenance = new MaintenanceRequest();
        maintenance.setDescription("licht kapot in de woonkamer");
        maintenance.setDate(LocalDate.now());
        maintenance.setUrgency("Very Urgent");
        maintenance.setHomeId("home2");

        // Act
        maintenanceService.addMaintenance(maintenance);


        // Assert
        verify(maintenanceRepository, times(1)).save(any(Maintenance.class));
    }

    @Test
    public void testGetAllMaintenances() {
        // Arrange
        Maintenance maintenance = Maintenance.builder()
                .description("licht kapot in de living")
                .date(LocalDate.of(2024, 12, 18))
                .urgency("not Urgent")
                .homeId("home2")
                .build();

        when(maintenanceRepository.findAll()).thenReturn(List.of(maintenance));

        // Act
        List<MaintenanceResponse> maintenanceResponses = maintenanceService.getAllMaintenances();

        // Assert
        assertEquals(1, maintenanceResponses.size());
        MaintenanceResponse response = maintenanceResponses.get(0);

        assertEquals("licht kapot in de living", response.getDescription());
        assertEquals("home2", response.getHomeId());
        assertEquals(LocalDate.of(2024, 12, 18), response.getDate());
        assertEquals("not Urgent", response.getUrgency());

        verify(maintenanceRepository, times(1)).findAll();
    }

    @Test
    public void testFindMaintenancesByHomeId() {
        // Arrange
        String homeId = "home1";
        Maintenance maintenance1 = Maintenance.builder()
                .description("Gas pipe leak")
                .date(LocalDate.of(2024, 12, 18))
                .urgency("high")
                .homeId(homeId)
                .build();
        Maintenance maintenance2 = Maintenance.builder()
                .description("Light bulb in kitchen broke")
                .date(LocalDate.of(2024, 12, 19))
                .urgency("low")
                .homeId(homeId)
                .build();

        when(maintenanceRepository.findMaintenanceByHomeId(homeId)).thenReturn(List.of(maintenance1, maintenance2));

        // Act
        List<MaintenanceResponse> responses = maintenanceService.findMaintenancesByHomeId(homeId);

        // Assert
        assertEquals(2, responses.size());

        assertEquals("Gas pipe leak", responses.get(0).getDescription());
        assertEquals("Light bulb in kitchen broke", responses.get(1).getDescription());
        assertEquals(LocalDate.of(2024, 12, 18), responses.get(0).getDate());
        assertEquals(LocalDate.of(2024, 12, 19), responses.get(1).getDate());

        verify(maintenanceRepository, times(1)).findMaintenanceByHomeId(homeId);
    }
}
