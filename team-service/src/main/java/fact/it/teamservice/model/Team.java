package fact.it.teamservice.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "teams")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Team {
  private String id;
  private String teamCode;
  private String name;
  private Integer points;
}
