package fact.it.gpservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TeamResponse {
    private String teamCode;
    private String name;
    private Integer points;
    private List<DriverResponse> drivers;
    private String imageUrl;
    private String logoUrl;
}
