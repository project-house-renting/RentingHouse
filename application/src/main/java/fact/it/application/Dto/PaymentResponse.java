package fact.it.application.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PaymentResponse {
    private float amount;
    private String method;
    private LocalDate paymentDate;
}
