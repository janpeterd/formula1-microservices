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
    private Long id; // only include the ID in the response
    private String driverCode; // unique code to use in api call
    private String firstName;
    private String lastName;
    private String country;
    private Integer seasonPoints; // TODO: This should probably be linked to a
}
