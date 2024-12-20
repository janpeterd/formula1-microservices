package fact.it.teamservice.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
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
                    .imageUrl("images/mercedes_car.avif")
                    .logoUrl("images/mercedes.avif")
                    .build();

            Team team1 = Team.builder()
                    .teamCode("ac879010-39cd-4562-badf-732664ea68c3")
                    .name("Red Bull Racing")
                    .points(20)
                    .imageUrl("images/red-bull-racing_car.avif")
                    .logoUrl("images/red_bull.avif")
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
                .imageUrl(team.getImageUrl())
                .logoUrl(team.getLogoUrl())
                .drivers(drivers)
                .build();
        return response;
    }

    public void createTeam(TeamRequest teamRequest) {
        // Build a new team entity
        Team team = Team.builder()
                .teamCode(UUID.randomUUID().toString())
                .name(teamRequest.getName())
                .points(teamRequest.getPoints())
                .imageUrl(teamRequest.getImageUrl())
                .logoUrl(teamRequest.getLogoUrl())
                .build();

        // Update drivers with the team code
        teamRequest.getDriverCodes().forEach(driverCode -> {
            try {
                String response = webClient.post()
                        .uri("http://" + driverServiceBaseUrl + "/api/driver/" + driverCode + "/team")
                        .bodyValue(team.getTeamCode())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block(); // Blocking for simplicity; consider using async if applicable
                System.out.println("UPDATE DRIVERTEAM RESPONSE :: " + response);
            } catch (Exception e) {
                // Log the error and decide if you want to fail the whole operation or continue
                System.err.println("Failed to update driver " + driverCode + " with team code: " + e.getMessage());
                throw new RuntimeException("Failed to update driver: " + driverCode, e);
            }
        });

        // Save the team in the repository
        teamRepository.save(team);
    }

    public List<TeamResponse> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(this::mapToTeamResponse)
                .toList();
    }

    public TeamResponse getTeamByTeamCode(String teamCode) {
        Optional<Team> team = teamRepository.findTeamByTeamCode(teamCode);
        return team.map(this::mapToTeamResponse).orElse(null);
    }

}
