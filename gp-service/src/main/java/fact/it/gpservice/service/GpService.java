package fact.it.gpservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import fact.it.gpservice.dto.DriverResponse;
import fact.it.gpservice.dto.GpRequest;
import fact.it.gpservice.dto.GpResponse;
import fact.it.gpservice.dto.TeamResponse;
import fact.it.gpservice.model.Gp;
import fact.it.gpservice.repository.GpRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GpService {
    private final GpRepository gpRepository;
    private final WebClient webClient;

    @Value("${driverservice.baseurl}")
    private String driverServiceBaseUrl;
    @Value("${teamservice.baseurl}")
    private String teamServiceBaseUrl;

    @PostConstruct
    public void loadData() {

        if (gpRepository.count() <= 0) {
            Gp gp = Gp.builder()
                    .gpCode(UUID.randomUUID().toString())
                    .name("Circuit de Spa-Francorchamps")
                    .country("Belgium")
                    .city("Stavelot")
                    .distanceMeters(7004)
                    .laps(44)
                    .raceDate(LocalDate.now())
                    .winningTeamCode("e600a035-1f38-4319-99d2-01607db9c980")
                    .winningDriverCode("919e46b3-57d3-4775-b5a8-8dc5b2776d0c")
                    .secondDriverCode("c5709243-aadb-4c10-bafb-cdb8ed767790")
                    .thirdDriverCode("6baeb4c9-f7d1-4176-9854-f50ff290aae0")
                    .imageUrl("/assets/test.png")
                    .build();

            Gp gp1 = Gp.builder()
                    .gpCode(UUID.randomUUID().toString())
                    .name("Autodromo Nazionale Monza")
                    .country("Italy")
                    .city("Monza")
                    .distanceMeters(5793)
                    .laps(53)
                    .raceDate(LocalDate.of(2023, 9, 10))
                    .winningTeamCode("e600a035-1f38-4319-99d2-01607db9c980")
                    .winningDriverCode("c5709243-aadb-4c10-bafb-cdb8ed767790")
                    .secondDriverCode("919e46b3-57d3-4775-b5a8-8dc5b2776d0c")
                    .thirdDriverCode("6baeb4c9-f7d1-4176-9854-f50ff290aae0")
                    .imageUrl("/assets/test.png")
                    .build();

            Gp gp2 = Gp.builder()
                    .gpCode(UUID.randomUUID().toString())
                    .name("Circuit de Monaco")
                    .country("Monaco")
                    .city("Monte Carlo")
                    .distanceMeters(3337)
                    .laps(78)
                    .raceDate(LocalDate.of(2023, 5, 28))
                    .winningTeamCode("e600a035-1f38-4319-99d2-01607db9c980")
                    .winningDriverCode("6baeb4c9-f7d1-4176-9854-f50ff290aae0")
                    .secondDriverCode("919e46b3-57d3-4775-b5a8-8dc5b2776d0c")
                    .thirdDriverCode("c5709243-aadb-4c10-bafb-cdb8ed767790")
                    .imageUrl("/assets/test.png")
                    .build();

            gpRepository.save(gp);
            gpRepository.save(gp1);
            gpRepository.save(gp2);
        }
    }

    public List<GpResponse> getAllGps() {
        List<Gp> gps = gpRepository.findAll();
        return gps.stream().map(this::mapToGpResponse).toList();
    }

    public void createGp(GpRequest gpRequest) {
        Gp gp = Gp.builder()
                .gpCode(UUID.randomUUID().toString())
                .name(gpRequest.getName())
                .distanceMeters(gpRequest.getDistanceMeters())
                .country(gpRequest.getCountry())
                .city(gpRequest.getCity())
                .raceDate(gpRequest.getRaceDate())
                .laps(gpRequest.getLaps())
                .winningDriverCode(gpRequest.getWinningDriverCode())
                .secondDriverCode(gpRequest.getSecondDriverCode())
                .thirdDriverCode(gpRequest.getThirdDriverCode())
                .winningTeamCode(gpRequest.getWinningDriverCode())
                .imageUrl(gpRequest.getImageUrl())
                .build();

        gpRepository.save(gp);
    }

    // helper function
    private TeamResponse getTeamByCode(String teamCode) {
        return webClient.get()
                .uri("http://" + teamServiceBaseUrl + "/api/team/by-id/" + teamCode)
                .retrieve()
                .bodyToMono(TeamResponse.class)
                .block();
    }

    // helper function
    private DriverResponse getDriverByCode(String driverCode) {

        return webClient.get()
                .uri("http://" + driverServiceBaseUrl + "/api/driver/by-id/" + driverCode)
                .retrieve()
                .bodyToMono(DriverResponse.class)
                .block();

    }

    private GpResponse mapToGpResponse(Gp gp) {
        return GpResponse.builder()
                .id(gp.getId())
                .gpCode(gp.getGpCode())
                .country(gp.getCountry())
                .city(gp.getCity())
                .distanceMeters(gp.getDistanceMeters())
                .laps(gp.getLaps())
                .raceDate(gp.getRaceDate())
                .winningTeam(getTeamByCode(gp.getWinningTeamCode()))
                .winningDriver(getDriverByCode(gp.getWinningDriverCode()))
                .secondDriver(getDriverByCode(gp.getSecondDriverCode()))
                .thirdDriver(getDriverByCode(gp.getThirdDriverCode()))
                .imageUrl(gp.getImageUrl())
                .build();
    }
}
