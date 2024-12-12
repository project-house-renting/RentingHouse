package fact.it.application.Dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ContractResponse {
    private TenantResponse tenant;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActive;
}
