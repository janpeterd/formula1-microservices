package fact.it.driverservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverRequest {
    private String firstName;
    private String lastName;
    private String country;
    private String teamCode;
    private Integer seasonPoints;
    private String imageUrl;
}
