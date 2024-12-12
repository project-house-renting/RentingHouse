package fact.it.homeservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomeRequest {
    private String address;
    private String type;
    private String yearOfConstruction;
    private Long userId;
}
