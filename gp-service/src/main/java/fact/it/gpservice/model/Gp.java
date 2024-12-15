package fact.it.gpservice.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String trackImageUrl;
}
