package fact.it.teamservice.service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import fact.it.teamservice.dto.DriverRequest;
import fact.it.teamservice.dto.DriverResponse;
import fact.it.teamservice.dto.TeamRequest;
import fact.it.teamservice.dto.TeamResponse;
import fact.it.teamservice.model.Team;
import fact.it.teamservice.repository.TeamRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final WebClient webClient;

    @Value("${driverservice.baseurl}")
    private String driverServiceBaseUrl;

    @PostConstruct
    public void loadData() {
        if (teamRepository.count() <= 0) {
            Team team = Team.builder()
                    .teamCode("e600a035-1f38-4319-99d2-01607db9c980")
                    .name("Mercedes")
                    .points(10)
                    .build();

            Team team1 = Team.builder()
                    .teamCode("ac879010-39cd-4562-badf-732664ea68c3")
                    .name("Red Bull")
                    .points(20)
                    .build();

            teamRepository.save(team);
            teamRepository.save(team1);
        }
    }

    public TeamResponse mapToTeamResponse(Team team) {
        List<DriverResponse> drivers;
        // Fetch drivers from the external service
        drivers = webClient.get()
                .uri("http://" + driverServiceBaseUrl + "/api/driver/team", uriBuilder -> uriBuilder
                        .queryParam("teamCode", team.getTeamCode())
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<DriverResponse>>() {
                })
                .block();

        System.out.println("RESPONSE :: " + drivers);

        // Map to TeamResponse
        TeamResponse response = TeamResponse.builder()
                .teamCode(team.getTeamCode())
                .name(team.getName())
                .points(team.getPoints())
                .drivers(drivers)
                .build();
        return response;
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

    public TeamResponse getTeamByTeamCode(String teamCode) {
        return mapToTeamResponse(teamRepository.findByTeamCode(teamCode));
    }

}
