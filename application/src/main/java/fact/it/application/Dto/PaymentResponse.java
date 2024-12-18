package fact.it.application.Dto;


import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private float amount;
    private String method;
    private LocalDate date;
    private String transactionId;
}
