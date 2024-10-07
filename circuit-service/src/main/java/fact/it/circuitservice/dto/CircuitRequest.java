package fact.it.circuitservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CircuitRequest {
    private String circuitCode;
    private String name;
    private String country;
    private Integer distanceMeters;
    private Integer laps;
}
