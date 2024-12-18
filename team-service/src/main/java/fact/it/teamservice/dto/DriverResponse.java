package fact.it.teamservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DriverResponse {
    private String driverCode;
    private String firstName;
    private String lastName;
    private String country;
    private String teamCode;
    private Integer seasonPoints;
    private String imageUrl;
}
