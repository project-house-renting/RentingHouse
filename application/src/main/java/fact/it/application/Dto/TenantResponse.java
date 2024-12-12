package fact.it.application.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TenantResponse {
    private Long id;

    private String name;
    private String email;
    private String description;
}
