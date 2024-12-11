package fact.it.gpservice.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverResponse {
    private Long id; // only include the ID in the response
    private String driverCode; // unique code to use in api call
    private String firstName;
    private String lastName;
    private String country;
    private String teamCode;
    private Date date;
    private Integer seasonPoints;
}
