package fact.it.teamservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TeamRequest {
    private String name;
    private Integer points;
    private List<String> driverCodes;
}
