package fact.it.maintenanceservice.controller;


import fact.it.maintenanceservice.dto.MaintenanceResponse;
import fact.it.maintenanceservice.service.MaintenanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// docker run --name mongo-maintenance -p 27018:27017 -d mongo ????

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
public class MaintenanceController {
    private final MaintenanceService maintenanceService;

    @GetMapping("/home/{id}/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MaintenanceResponse> getAllMaintenances(@PathVariable String id)
    {
        return maintenanceService.findMaintenancesByHomeId(id);
    }
}
