package fact.it.application.Dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantResponse {
    private String name;
    private String email;
    private String description;
    private String gender;
}
