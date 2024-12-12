package fact.it.application.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HomeResponse {
    private String id;

    private String address;
    private String type;
    private String yearOfConstruction;

    private Long userId;
}
