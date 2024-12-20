package fact.it.gpservice.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GpRequest {
    private String gpCode;
    private String name;
    private String country;
    private String city;
    private Integer distanceMeters;
    private Integer laps;
    private String winningDriverCode;
    private String secondDriverCode;
    private String thirdDriverCode;
    private String winningTeamCode;
    private LocalDate raceDate;
    private String imageUrl;
    private String trackImageUrl;
}
