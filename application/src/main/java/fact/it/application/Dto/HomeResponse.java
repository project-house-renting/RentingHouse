package fact.it.application.Dto;

import lombok.*;

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
    private List<MaintenanceResponse> maintenances;
}
