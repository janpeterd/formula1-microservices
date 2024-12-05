package fact.it.circuitservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "circuits")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Circuit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String circuitCode;
    private String name;
    private String country;
    private Integer distanceMeters;
    private Integer laps;
}
