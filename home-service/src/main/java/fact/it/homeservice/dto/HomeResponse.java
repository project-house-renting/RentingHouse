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
    private String description;
    private float rentalPrice;
    private String yearOfConstruction;
    private boolean isRentable;
    private List<MaintenanceResponse> maintenances;
}
