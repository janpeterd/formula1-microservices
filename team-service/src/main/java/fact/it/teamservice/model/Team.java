package fact.it.teamservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "team")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Team {
    private String id;
    private String teamCode;
    private String name;
    // TODO: link drivers
    // TODO: track season points
}
