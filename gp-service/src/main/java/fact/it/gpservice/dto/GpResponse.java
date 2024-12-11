package fact.it.gpservice.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GpResponse {
    private Long id;
    private String gpCode;
    private String name;
    private String country;
    private String city;
    private Integer distanceMeters;
    private Integer laps;
    private DriverResponse winningDriver;
    private DriverResponse secondDriver;
    private DriverResponse thirdDriver;
    private TeamResponse winningTeam;
    private LocalDate raceDate;
}
