package fact.it.maintenanceservice.service;


import fact.it.maintenanceservice.dto.MaintenanceRequest;
import fact.it.maintenanceservice.dto.MaintenanceResponse;
import fact.it.maintenanceservice.model.Maintenance;
import fact.it.maintenanceservice.repository.MaintenanceRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void loadData() {
        mongoTemplate.getDb().drop();

        if (maintenanceRepository.count() <= 0){
            Maintenance maintenance1 = Maintenance.builder()
                    .homeId("home1")
                    .date(LocalDate.now())
                    .description("Gas pipe leak")
                    .urgency("high")
                    .build();
            Maintenance maintenance2 = Maintenance.builder()
                    .homeId("home1")
                    .date(LocalDate.now())
                    .description("light bulp in kitchen broke")
                    .urgency("low")
                    .build();

            maintenanceRepository.save(maintenance1);
            maintenanceRepository.save(maintenance2);
        }
    }

    public List<MaintenanceResponse> getAllMaintenances(){
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        return maintenances.stream().map(this::mapToMaintenanceResponse).toList();
    }

    public List<MaintenanceResponse> findMaintenancesByHomeId(String id) {
        List<Maintenance> maintenances = maintenanceRepository.findMaintenanceByHomeId(id);
        return maintenances.stream().map(this::mapToMaintenanceResponse).toList();
    }

    public void addMaintenance(MaintenanceRequest maintenanceRequest) {
        Maintenance maintenance = Maintenance.builder()
                .description(maintenanceRequest.getDescription())
                .date(maintenanceRequest.getDate())
                .urgency(maintenanceRequest.getUrgency())
                .homeId(maintenanceRequest.getHomeId())
                .build();

        maintenanceRepository.save(maintenance);
    }

    private MaintenanceResponse mapToMaintenanceResponse(Maintenance maintenance) {
        return MaintenanceResponse.builder()
                .description(maintenance.getDescription())
                .homeId(maintenance.getHomeId())
                .urgency(maintenance.getUrgency())
                .date(maintenance.getDate())
                .build();
    }
}
