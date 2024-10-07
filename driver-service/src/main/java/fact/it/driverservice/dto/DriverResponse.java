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
    private Long id; // only include the ID in the response
    private String driverCode; // unique code to use in api call
    private String firstName;
    private String lastName;
    private String country;
//    private String teamCode; TODO: I dont return the team code in the response RIGHT?
//    private Integer seasonPoints; // TODO: This should probably be linked to a season, instead of just a property on driver
    // TODO: how do I link with team? Probably through some unique ID?
}
