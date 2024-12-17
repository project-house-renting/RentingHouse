package fact.it.homeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeResponse {
    private String id;

    private String address;
    private String type;
    private String yearOfConstruction;
    private List<MaintenanceResponse> maintenances;
}
