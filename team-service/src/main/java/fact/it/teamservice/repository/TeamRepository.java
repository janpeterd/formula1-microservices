package fact.it.teamservice.repository;

import fact.it.teamservice.model.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends MongoRepository<Team, String> {
    public List<Team> getAllByTeamCodeIn(List<String> teamCodes);

    Optional<Team> findTeamByTeamCode(String driverCode);
}
