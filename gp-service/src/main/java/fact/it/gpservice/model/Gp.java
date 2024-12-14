package fact.it.gpservice.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

import fact.it.gpservice.dto.*;
import lombok.*;

@Entity
@Table(name = "gps")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Gp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gpCode;
    private String name;
    private String country;
    private String city;
    private Integer distanceMeters;
    private Integer laps;
    @Temporal(TemporalType.DATE)
    private LocalDate raceDate; // Date of the GP
    private String winningTeamCode;
    private String winningDriverCode;
    private String secondDriverCode;
    private String thirdDriverCode;
    private String imageUrl;
}
