package fact.it.circuitservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CircuitResponse {
    private Long id;
    private String circuitCode;
    private String name;
    private String country;
    private Integer distanceMeters;
    private Integer laps;
}
