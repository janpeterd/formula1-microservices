package fact.it.teamservice.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "team")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Team {
    private String id;
    private String teamCode;
    private String name;
    private Integer points;
    private String imageUrl;
    private String logoUrl;
}
