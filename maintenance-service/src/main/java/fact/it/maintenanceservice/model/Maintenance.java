package fact.it.maintenanceservice.model;



import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(value = "maintenance")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Maintenance {
    private String id;
    private String description;

    private LocalDate maintenanceDate;

    private String homeId;
}
