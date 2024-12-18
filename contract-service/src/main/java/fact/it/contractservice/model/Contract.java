package fact.it.contractservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "contract")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String homeId;
    private Long tenantId;
    private LocalDate startDate;
    private LocalDate endDate;
}
