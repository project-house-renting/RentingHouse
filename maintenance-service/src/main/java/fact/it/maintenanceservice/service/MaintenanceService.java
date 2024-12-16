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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;


    @Autowired
    private MongoTemplate mongoTemplate;

    public List<MaintenanceResponse> getAllMaintenances(){
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        return maintenances.stream().map(this::mapToMaintenanceResponse).toList();
    }

    public MaintenanceResponse getHomeById(Long id) {
        Maintenance maintenance = maintenanceRepository.findMaintenanceById(id);
        return mapToMaintenanceResponse(maintenance);
    }

    public List<MaintenanceResponse> findMaintenanceByUserId(Long id) {
        List<Maintenance> home = maintenanceRepository.findMaintenanceByUserId(id);
        return home.stream().map(this::mapToMaintenanceResponse).toList();
    }

    public void addMaintenance(MaintenanceRequest maintenanceRequest) {
        Maintenance maintenance = Maintenance.builder()
                .description(maintenanceRequest.getDescription())
                .maintenanceDate(maintenanceRequest.getMaintenanceDate())
                .homeId(maintenanceRequest.getHomeId())
                .build();

        maintenanceRepository.save(maintenance);
    }

    private MaintenanceResponse mapToMaintenanceResponse(Maintenance maintenance) {
        return MaintenanceResponse.builder()
                .id(maintenance.getId())
                .description(maintenance.getDescription())
                .homeId(maintenance.getHomeId())
                .maintenanceDate(maintenance.getMaintenanceDate())
                .build();
    }


}
