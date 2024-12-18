package fact.it.maintenanceservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceRequest {
    private String description;
    private LocalDate date;
    private String urgency;

    private String homeId;
}
