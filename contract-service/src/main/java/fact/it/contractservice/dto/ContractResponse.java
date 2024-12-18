package fact.it.contractservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContractResponse {
    private TenantResponse tenant;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<PaymentResponse> payments;
}
