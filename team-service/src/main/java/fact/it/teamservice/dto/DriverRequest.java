package fact.it.teamservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverRequest {
    private String firstName;
    private String lastName;
    private String country;
    private String teamCode;
    private Integer seasonPoints;
    private String imageUrl;
}
