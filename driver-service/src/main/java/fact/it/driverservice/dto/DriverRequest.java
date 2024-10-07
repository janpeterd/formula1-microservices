package fact.it.driverservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverRequest {
    private String driverCode; // unique code to use in api call
    private String firstName;
    private String lastName;
    private String country;
    private String teamCode;
    private Integer seasonPoints; // TODO: This should probably be linked to a season, instead of just a property on driver
    // TODO: how do I link with team? Probably through some unique ID?
}
