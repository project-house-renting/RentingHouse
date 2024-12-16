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
public class MaintenanceResponse {
    private Long id;
    private String description;

    private LocalDate maintenanceDate;

    private String homeId;
}
