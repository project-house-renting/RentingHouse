package fact.it.maintenanceservice.controller;


import fact.it.maintenanceservice.dto.MaintenanceResponse;
import fact.it.maintenanceservice.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// docker run --name mongo-maintenance -p 27018:27017 -d mongo ????

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MaintenanceResponse> getAllMaintenances() {return maintenanceService.getAllMaintenances();}


}
