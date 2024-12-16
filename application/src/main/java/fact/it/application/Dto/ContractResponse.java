package fact.it.application.Dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ContractResponse {
    private TenantResponse tenant;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<PaymentResponse> payments;
    private boolean isActive;
}
