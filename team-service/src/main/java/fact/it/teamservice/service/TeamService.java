package fact.it.teamservice.service;

import fact.it.teamservice.dto.TeamRequest;
import fact.it.teamservice.dto.TeamResponse;
import fact.it.teamservice.model.Team;
import fact.it.teamservice.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;


    public TeamResponse mapToTeamResponse(Team team) {
        return TeamResponse.builder()
                .id(team.getId())
                .teamCode(team.getTeamCode())
                .name(team.getName())
                .build();
    }

    public void createTeam(TeamRequest teamRequest) {
        Team team = Team.builder()
                .teamCode(teamRequest.getTeamCode())
                .name(teamRequest.getName())
                .build();

        teamRepository.save(team);
    }

    public List<TeamResponse> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(this::mapToTeamResponse)
                .toList();
    }


    public List<TeamResponse> getAllTeamsByTeamCode(List<String> teamCodes) {
        return teamRepository.getAllByTeamCodeIn(teamCodes)
                .stream()
                .map(this::mapToTeamResponse)
                .toList();
    }
}
