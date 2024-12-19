package fact.it.teamservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import fact.it.teamservice.dto.DriverResponse;
import fact.it.teamservice.dto.TeamRequest;
import fact.it.teamservice.dto.TeamResponse;
import fact.it.teamservice.model.Team;
import fact.it.teamservice.repository.TeamRepository;
import fact.it.teamservice.service.TeamService;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class TeamServiceApplicationTests {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;
    @InjectMocks
    private TeamService teamService;

    private Team team;
    private TeamRequest teamRequest;

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(teamService, "driverServiceBaseUrl", "localhost:8081");

        team = Team.builder()
                .teamCode(UUID.randomUUID().toString())
                .name("Test team")
                .points(10)
                .imageUrl("images/testTeam.png")
                .logoUrl("images/testTeamLogo.png")
                .build();

        teamRequest = TeamRequest.builder()
                .name("Test team")
                .points(10)
                .imageUrl("images/testTeamRequest.png")
                .logoUrl("images/testTeamRequestLogo.png")
                .driverCodes(List.of(UUID.randomUUID().toString()))
                .build();
    }

    @Test
    void mapToTeamResponse() {

    }

    @Test
    void createTeam() {
        teamService.createTeam(teamRequest);
        verify(teamRepository).save(any(Team.class));
    }

    @Test
    void getAllTeams() {
        DriverResponse driverResponse = DriverResponse.builder()
                .driverCode(UUID.randomUUID().toString())
                .firstName("Test First")
                .lastName("Test Last")
                .country("Test Country")
                .teamCode(team.getTeamCode())
                .seasonPoints(10)
                .imageUrl("images/testDriverResponse.png")
                .build();

        when(teamRepository.findAll()).thenReturn(List.of(team));
        // Mock WebClient interactions for team
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Add this line to handle the ParameterizedTypeReference<List<DriverResponse>>
        when(responseSpec.bodyToMono(new ParameterizedTypeReference<List<DriverResponse>>() {
        }))
                .thenReturn(Mono.just(Arrays.asList(driverResponse)));

        // act
        List<TeamResponse> response = teamService.getAllTeams();

        // assert
        assertThat(response).hasSize(1);
        assertThat(response.get(0).getTeamCode()).isEqualTo(team.getTeamCode());
        assertThat(response.get(0).getName()).isEqualTo("Test team");
        assertThat(response.get(0).getPoints()).isEqualTo(10);
        assertThat(response.get(0).getImageUrl()).isEqualTo("images/testTeam.png");
        assertThat(response.get(0).getLogoUrl()).isEqualTo("images/testTeamLogo.png");
        assertThat(response.get(0).getDrivers()).hasSize(1);
    }

    @Test
    void getTeamByTeamCode() {
        DriverResponse driverResponse = DriverResponse.builder()
                .driverCode(UUID.randomUUID().toString())
                .firstName("Test First")
                .lastName("Test Last")
                .country("Test Country")
                .teamCode(team.getTeamCode())
                .seasonPoints(10)
                .imageUrl("images/testDriverResponse.png")
                .build();

        when(teamRepository.findTeamByTeamCode(team.getTeamCode())).thenReturn(Optional.of(team));
        // Mock WebClient interactions for team
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        // Add this line to handle the ParameterizedTypeReference<List<DriverResponse>>
        when(responseSpec.bodyToMono(new ParameterizedTypeReference<List<DriverResponse>>() {
        }))
                .thenReturn(Mono.just(Arrays.asList(driverResponse)));

        // act
        TeamResponse response = teamService.getTeamByTeamCode(team.getTeamCode());

        // assert
        assertThat(response.getTeamCode()).isEqualTo(team.getTeamCode());
        assertThat(response.getName()).isEqualTo("Test team");
        assertThat(response.getPoints()).isEqualTo(10);
        assertThat(response.getImageUrl()).isEqualTo("images/testTeam.png");
        assertThat(response.getLogoUrl()).isEqualTo("images/testTeamLogo.png");
        assertThat(response.getDrivers()).hasSize(1);
    }
}
