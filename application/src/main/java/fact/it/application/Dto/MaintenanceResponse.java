package fact.it.application.Dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceResponse {
    private String description;
    private LocalDate date;
    private String urgency;
    private String homeId;
}
