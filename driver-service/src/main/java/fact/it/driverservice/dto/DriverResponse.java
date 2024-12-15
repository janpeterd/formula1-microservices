package fact.it.driverservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverResponse {
    private String driverCode; // unique code to use in api call
    private String firstName;
    private String lastName;
    private String country;
    private String teamCode;
    private Integer seasonPoints;
    private String imageUrl;
}
