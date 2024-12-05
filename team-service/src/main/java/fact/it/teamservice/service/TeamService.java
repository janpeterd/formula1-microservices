package fact.it.teamservice.service;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import fact.it.teamservice.dto.DriverRequest;
import fact.it.teamservice.dto.DriverResponse;
import fact.it.teamservice.dto.TeamRequest;
import fact.it.teamservice.dto.TeamResponse;
import fact.it.teamservice.model.Team;
import fact.it.teamservice.repository.TeamRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final WebClient webClient;

    public TeamResponse mapToTeamResponse(Team team) {
        return TeamResponse.builder()
                .teamCode(team.getTeamCode())
                .name(team.getName())
                .points(team.getPoints())
                .build();
    }

    public void createTeam(TeamRequest teamRequest) {
        Team team = Team.builder()
                .name(teamRequest.getName())
                .points(teamRequest.getPoints())
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

    public TeamResponse addDriverToTeam(String driverCode, String teamCode) {
        Team team = teamRepository.findByTeamCode(teamCode);

        DriverResponse driver = webClient.get()
                .uri("http://localhost:8081/api/driver",
                        UriBuilder -> UriBuilder.queryParam("driverCode", driverCode).build())
                .retrieve()
                .bodyToMono(DriverResponse.class)
                .block();

        DriverResponse updatedDriver = webClient.put()
                .uri("http://localhost:8081/api/driver",
                        uriBuilder -> uriBuilder.queryParam("driverCode", driverCode).build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(mapToDriverRequest(driver))
                .retrieve()
                .bodyToMono(DriverResponse.class)
                .block();

        System.out.println("updated driver " + updatedDriver);
        return mapToTeamResponse(team);
    }

    public DriverRequest mapToDriverRequest(DriverResponse driverResponse) {
        return DriverRequest.builder()
                .firstName(driverResponse.getFirstName())
                .lastName(driverResponse.getLastName())
                .country(driverResponse.getCountry())
                .teamCode(driverResponse.getTeamCode())
                .seasonPoints(driverResponse.getSeasonPoints())
                .build();
    }

}
