package fact.it.gpservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import fact.it.gpservice.dto.DriverResponse;
import fact.it.gpservice.dto.GpRequest;
import fact.it.gpservice.dto.GpResponse;
import fact.it.gpservice.dto.TeamResponse;
import fact.it.gpservice.model.Gp;
import fact.it.gpservice.repository.GpRepository;
import fact.it.gpservice.service.GpService;
import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
class GpServiceTest {

    @Mock
    private GpRepository gpRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private GpService gpService;

    private Gp gp;
    private GpRequest gpRequest;

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(gpService, "teamServiceBaseUrl", "localhost:8080");
        ReflectionTestUtils.setField(gpService, "driverServiceBaseUrl", "localhost:8081");

        gp = Gp.builder()
                .gpCode(UUID.randomUUID().toString())
                .name("Test Circuit")
                .country("Test Country")
                .city("Test City")
                .distanceMeters(5000)
                .laps(50)
                .raceDate(LocalDate.now())
                .winningTeamCode("teamCode")
                .winningDriverCode("driverCode1")
                .secondDriverCode("driverCode2")
                .thirdDriverCode("driverCode3")
                .imageUrl("test.jpg")
                .trackImageUrl("track.jpg")
                .build();

        gpRequest = GpRequest.builder()
                .name("Updated Circuit")
                .country("Updated Country")
                .city("Updated City")
                .distanceMeters(6000)
                .laps(60)
                .raceDate(LocalDate.of(2023, 12, 1))
                .winningTeamCode("newTeamCode")
                .winningDriverCode("newDriverCode1")
                .secondDriverCode("newDriverCode2")
                .thirdDriverCode("newDriverCode3")
                .imageUrl("updated.jpg")
                .trackImageUrl("updatedTrack.jpg")
                .build();
    }

    @Test
    void testLoadData() {
        when(gpRepository.count()).thenReturn(0L);

        gpService.loadData();

        verify(gpRepository, atLeast(1)).saveAll(any(List.class));
    }

    @Test
    void testGetAllGps() {
        when(gpRepository.findAll()).thenReturn(List.of(gp));

        // Prepare test data
        String winningTeamCode = gp.getWinningTeamCode();
        String winningDriverCode = gp.getWinningDriverCode();
        String secondDriverCode = gp.getSecondDriverCode();
        String thirdDriverCode = gp.getThirdDriverCode();

        // Prepare mock responses
        DriverResponse mockWinningDriver = DriverResponse.builder()
                .driverCode(winningDriverCode)
                .firstName("John")
                .lastName("Doe")
                .country("Belgium")
                .teamCode(winningTeamCode)
                .seasonPoints(42)
                .imageUrl("images/test")
                .build();

        DriverResponse mockSecondDriver = DriverResponse.builder()
                .driverCode(secondDriverCode)
                .firstName("Jane")
                .lastName("Smith")
                .country("Italy")
                .teamCode("another-team-code")
                .seasonPoints(38)
                .imageUrl("images/test2")
                .build();

        DriverResponse mockThirdDriver = DriverResponse.builder()
                .driverCode(thirdDriverCode)
                .firstName("Bob")
                .lastName("Brown")
                .country("France")
                .teamCode("third-team-code")
                .seasonPoints(35)
                .imageUrl("images/test3")
                .build();

        TeamResponse mockTeam = TeamResponse.builder()
                .teamCode(winningTeamCode)
                .name("Test team")
                .points(100)
                .drivers(Arrays.asList(mockWinningDriver, mockSecondDriver, mockThirdDriver))
                .imageUrl("images/testTeam.jpg")
                .logoUrl("images/testTeamLogo.jpg")
                .build();

        // Mock WebClient interactions for team
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        // Mock URI responses dynamically based on input
        when(requestHeadersUriSpec.uri(anyString())).thenAnswer(invocation -> {
            String uri = invocation.getArgument(0, String.class); // Correctly retrieve the URI string
            if (uri.contains("/api/driver/" + winningDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockWinningDriver));
            } else if (uri.contains("/api/driver/" + secondDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockSecondDriver));
            } else if (uri.contains("/api/driver/" + thirdDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockThirdDriver));
            } else if (uri.contains("/api/team/" + winningTeamCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(TeamResponse.class)).thenReturn(Mono.just(mockTeam));
            } else {
                throw new IllegalArgumentException("Unexpected URI: " + uri);
            }
            return requestHeadersSpec;
        });

        List<GpResponse> gps = gpService.getAllGps();

        assertThat(gps).hasSize(1);
        assertThat(gps.get(0).getName()).isEqualTo("Test Circuit");
        verify(gpRepository).findAll();
    }

    @Test
    void testUpdateGp() {
        // Prepare test data
        String winningTeamCode = gpRequest.getWinningTeamCode();
        String winningDriverCode = gpRequest.getWinningDriverCode();
        String secondDriverCode = gpRequest.getSecondDriverCode();
        String thirdDriverCode = gpRequest.getThirdDriverCode();

        // Mock repository response
        when(gpRepository.findGpByGpCode(gp.getGpCode())).thenReturn(Optional.of(gp));

        // Prepare mock responses
        DriverResponse mockWinningDriver = DriverResponse.builder()
                .driverCode(winningDriverCode)
                .firstName("John")
                .lastName("Doe")
                .country("Belgium")
                .teamCode(winningTeamCode)
                .seasonPoints(42)
                .imageUrl("images/test")
                .build();

        DriverResponse mockSecondDriver = DriverResponse.builder()
                .driverCode(secondDriverCode)
                .firstName("Jane")
                .lastName("Smith")
                .country("Italy")
                .teamCode("another-team-code")
                .seasonPoints(38)
                .imageUrl("images/test2")
                .build();

        DriverResponse mockThirdDriver = DriverResponse.builder()
                .driverCode(thirdDriverCode)
                .firstName("Bob")
                .lastName("Brown")
                .country("France")
                .teamCode("third-team-code")
                .seasonPoints(35)
                .imageUrl("images/test3")
                .build();

        TeamResponse mockTeam = TeamResponse.builder()
                .teamCode(winningTeamCode)
                .name("Test team")
                .points(100)
                .drivers(Arrays.asList(mockWinningDriver, mockSecondDriver, mockThirdDriver))
                .imageUrl("images/testTeam.jpg")
                .logoUrl("images/testTeamLogo.jpg")
                .build();

        // Mock WebClient interactions for team
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        // Mock URI responses dynamically based on input
        when(requestHeadersUriSpec.uri(anyString())).thenAnswer(invocation -> {
            String uri = invocation.getArgument(0, String.class); // Correctly retrieve the URI string
            if (uri.contains("/api/driver/" + winningDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockWinningDriver));
            } else if (uri.contains("/api/driver/" + secondDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockSecondDriver));
            } else if (uri.contains("/api/driver/" + thirdDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockThirdDriver));
            } else if (uri.contains("/api/team/" + winningTeamCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(TeamResponse.class)).thenReturn(Mono.just(mockTeam));
            } else {
                throw new IllegalArgumentException("Unexpected URI: " + uri);
            }
            return requestHeadersSpec;
        });

        GpResponse response = gpService.updateGp(gp.getGpCode(), gpRequest);

        assertThat(response.getName()).isEqualTo("Updated Circuit");
        assertThat(response.getCountry()).isEqualTo("Updated Country");
        verify(gpRepository).findGpByGpCode(gp.getGpCode());
    }

    @Test
    void testUpdateGpThrowsException() {
        when(gpRepository.findGpByGpCode("nonexistentCode")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gpService.updateGp("nonexistentCode", gpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("GP with code nonexistentCode does not exist.");
    }

    @Test
    void testCreateGp() {
        // Prepare test data
        String winningTeamCode = gpRequest.getWinningTeamCode();
        String winningDriverCode = gpRequest.getWinningDriverCode();
        String secondDriverCode = gpRequest.getSecondDriverCode();
        String thirdDriverCode = gpRequest.getThirdDriverCode();

        // Prepare mock responses
        DriverResponse mockWinningDriver = DriverResponse.builder()
                .driverCode(winningDriverCode)
                .firstName("John")
                .lastName("Doe")
                .country("Belgium")
                .teamCode(winningTeamCode)
                .seasonPoints(42)
                .imageUrl("images/test")
                .build();

        DriverResponse mockSecondDriver = DriverResponse.builder()
                .driverCode(secondDriverCode)
                .firstName("Jane")
                .lastName("Smith")
                .country("Italy")
                .teamCode("another-team-code")
                .seasonPoints(38)
                .imageUrl("images/test2")
                .build();

        DriverResponse mockThirdDriver = DriverResponse.builder()
                .driverCode(thirdDriverCode)
                .firstName("Bob")
                .lastName("Brown")
                .country("France")
                .teamCode("third-team-code")
                .seasonPoints(35)
                .imageUrl("images/test3")
                .build();

        TeamResponse mockTeam = TeamResponse.builder()
                .teamCode(winningTeamCode)
                .name("Test team")
                .points(100)
                .drivers(Arrays.asList(mockWinningDriver, mockSecondDriver, mockThirdDriver))
                .imageUrl("images/testTeam.jpg")
                .logoUrl("images/testTeamLogo.jpg")
                .build();

        // Mock WebClient interactions for team
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        // Mock URI responses dynamically based on input
        when(requestHeadersUriSpec.uri(anyString())).thenAnswer(invocation -> {
            String uri = invocation.getArgument(0, String.class); // Correctly retrieve the URI string
            if (uri.contains("/api/driver/" + winningDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockWinningDriver));
            } else if (uri.contains("/api/driver/" + secondDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockSecondDriver));
            } else if (uri.contains("/api/driver/" + thirdDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockThirdDriver));
            } else if (uri.contains("/api/team/" + winningTeamCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(TeamResponse.class)).thenReturn(Mono.just(mockTeam));
            } else {
                throw new IllegalArgumentException("Unexpected URI: " + uri);
            }
            return requestHeadersSpec;
        });

        gpService.createGp(gpRequest);

        verify(gpRepository).save(any(Gp.class));
    }

    @Test
    void testGetGp() {
        // Prepare test data
        String winningTeamCode = gp.getWinningTeamCode();
        String winningDriverCode = gp.getWinningDriverCode();
        String secondDriverCode = gp.getSecondDriverCode();
        String thirdDriverCode = gp.getThirdDriverCode();

        // Mock repository response
        when(gpRepository.findGpByGpCode(gp.getGpCode())).thenReturn(Optional.of(gp));

        // Prepare mock responses
        DriverResponse mockWinningDriver = DriverResponse.builder()
                .driverCode(winningDriverCode)
                .firstName("John")
                .lastName("Doe")
                .country("Belgium")
                .teamCode(winningTeamCode)
                .seasonPoints(42)
                .imageUrl("images/test")
                .build();

        DriverResponse mockSecondDriver = DriverResponse.builder()
                .driverCode(secondDriverCode)
                .firstName("Jane")
                .lastName("Smith")
                .country("Italy")
                .teamCode("another-team-code")
                .seasonPoints(38)
                .imageUrl("images/test2")
                .build();

        DriverResponse mockThirdDriver = DriverResponse.builder()
                .driverCode(thirdDriverCode)
                .firstName("Bob")
                .lastName("Brown")
                .country("France")
                .teamCode("third-team-code")
                .seasonPoints(35)
                .imageUrl("images/test3")
                .build();

        TeamResponse mockTeam = TeamResponse.builder()
                .teamCode(winningTeamCode)
                .name("Test team")
                .points(100)
                .drivers(Arrays.asList(mockWinningDriver, mockSecondDriver, mockThirdDriver))
                .imageUrl("images/testTeam.jpg")
                .logoUrl("images/testTeamLogo.jpg")
                .build();

        // Mock WebClient interactions for team
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        // Mock URI responses dynamically based on input
        when(requestHeadersUriSpec.uri(anyString())).thenAnswer(invocation -> {
            String uri = invocation.getArgument(0, String.class); // Correctly retrieve the URI string
            if (uri.contains("/api/driver/" + winningDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockWinningDriver));
            } else if (uri.contains("/api/driver/" + secondDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockSecondDriver));
            } else if (uri.contains("/api/driver/" + thirdDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockThirdDriver));
            } else if (uri.contains("/api/team/" + winningTeamCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(TeamResponse.class)).thenReturn(Mono.just(mockTeam));
            } else {
                throw new IllegalArgumentException("Unexpected URI: " + uri);
            }
            return requestHeadersSpec;
        });

        // Test the service
        GpResponse response = gpService.getGp(gp.getGpCode());

        // Assertions
        assertThat(response.getName()).isEqualTo(gp.getName());
        assertThat(response.getWinningDriver().getDriverCode()).isEqualTo(winningDriverCode);
        assertThat(response.getWinningTeam().getName()).isEqualTo("Test team");
        assertThat(response.getSecondDriver().getDriverCode()).isEqualTo(secondDriverCode);
        assertThat(response.getThirdDriver().getDriverCode()).isEqualTo(thirdDriverCode);

        // Verify interactions
        verify(gpRepository).findGpByGpCode(gp.getGpCode());
    }

    @Test
    void testGetGpThrowsException() {
        when(gpRepository.findGpByGpCode("nonexistentCode")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> gpService.getGp("nonexistentCode"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("GP with code nonexistentCode does not exist.");
    }

    @Test
    void testDeleteGp() {
        when(gpRepository.existsByGpCode(gp.getGpCode())).thenReturn(true);

        gpService.deleteGp(gp.getGpCode());

        verify(gpRepository).deleteByGpCode(gp.getGpCode());
    }

    @Test
    void testDeleteGpThrowsException() {
        when(gpRepository.existsByGpCode("nonexistentCode")).thenReturn(false);

        assertThatThrownBy(() -> gpService.deleteGp("nonexistentCode"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("GP with code nonexistentCode does not exist.");
    }

    @Test
    void testMapToGpResponse() {
        // Prepare test data
        String winningTeamCode = gp.getWinningTeamCode();
        String winningDriverCode = gp.getWinningDriverCode();
        String secondDriverCode = gp.getSecondDriverCode();
        String thirdDriverCode = gp.getThirdDriverCode();

        // Prepare mock responses
        DriverResponse mockWinningDriver = DriverResponse.builder()
                .driverCode(winningDriverCode)
                .firstName("John")
                .lastName("Doe")
                .country("Belgium")
                .teamCode(winningTeamCode)
                .seasonPoints(42)
                .imageUrl("images/test")
                .build();

        DriverResponse mockSecondDriver = DriverResponse.builder()
                .driverCode(secondDriverCode)
                .firstName("Jane")
                .lastName("Smith")
                .country("Italy")
                .teamCode("another-team-code")
                .seasonPoints(38)
                .imageUrl("images/test2")
                .build();

        DriverResponse mockThirdDriver = DriverResponse.builder()
                .driverCode(thirdDriverCode)
                .firstName("Bob")
                .lastName("Brown")
                .country("France")
                .teamCode("third-team-code")
                .seasonPoints(35)
                .imageUrl("images/test3")
                .build();

        TeamResponse mockTeam = TeamResponse.builder()
                .teamCode(winningTeamCode)
                .name("Test team")
                .points(100)
                .drivers(Arrays.asList(mockWinningDriver, mockSecondDriver, mockThirdDriver))
                .imageUrl("images/testTeam.jpg")
                .logoUrl("images/testTeamLogo.jpg")
                .build();

        // Mock WebClient interactions for team
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        // Mock URI responses dynamically based on input
        when(requestHeadersUriSpec.uri(anyString())).thenAnswer(invocation -> {
            String uri = invocation.getArgument(0, String.class); // Correctly retrieve the URI string
            if (uri.contains("/api/driver/" + winningDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockWinningDriver));
            } else if (uri.contains("/api/driver/" + secondDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockSecondDriver));
            } else if (uri.contains("/api/driver/" + thirdDriverCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(DriverResponse.class)).thenReturn(Mono.just(mockThirdDriver));
            } else if (uri.contains("/api/team/" + winningTeamCode)) {
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(TeamResponse.class)).thenReturn(Mono.just(mockTeam));
            } else {
                throw new IllegalArgumentException("Unexpected URI: " + uri);
            }
            return requestHeadersSpec;
        });

        GpResponse response = gpService.mapToGpResponse(gp);

        assertThat(response.getWinningTeam().getName()).isEqualTo("Test team");
        assertThat(response.getWinningDriver().getFirstName()).isEqualTo("John");
        assertThat(response.getWinningDriver().getLastName()).isEqualTo("Doe");
    }
}
