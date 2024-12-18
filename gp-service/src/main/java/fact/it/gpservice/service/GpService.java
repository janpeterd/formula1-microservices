package fact.it.gpservice.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
                    .imageUrl("images/Belgium.avif")
                    .trackImageUrl("images/Belgium_Circuit.png")
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
                    .imageUrl("images/Italy.jpg")
                    .trackImageUrl("images/Italy_Circuit.avif")
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
                    .imageUrl("images/Monaco.jpg")
                    .trackImageUrl("images/Monaco_Circuit.avif")
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

    public GpResponse updateGp(String gpCode, GpRequest gpRequest) {
        Optional<Gp> gp = gpRepository.findGpByGpCode(gpCode);
        if (gp.isPresent()) {
            Gp gp_value = gp.get();
            gp_value.setName(gpRequest.getName());
            gp_value.setDistanceMeters(gpRequest.getDistanceMeters());
            gp_value.setCountry(gpRequest.getCountry());
            gp_value.setCity(gpRequest.getCity());
            gp_value.setRaceDate(gpRequest.getRaceDate());
            gp_value.setLaps(gpRequest.getLaps());
            gp_value.setWinningTeamCode(gpRequest.getWinningTeamCode());
            gp_value.setWinningDriverCode(gpRequest.getWinningDriverCode());
            gp_value.setSecondDriverCode(gpRequest.getSecondDriverCode());
            gp_value.setThirdDriverCode(gpRequest.getThirdDriverCode());
            gp_value.setImageUrl(gpRequest.getImageUrl());
            gp_value.setTrackImageUrl(gpRequest.getTrackImageUrl());
            return mapToGpResponse(gp_value);
        } else {
            throw new IllegalArgumentException("GP with code " + gpCode + " does not exist.");
        }
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
                .winningTeamCode(gpRequest.getWinningTeamCode())
                .winningDriverCode(gpRequest.getWinningDriverCode())
                .secondDriverCode(gpRequest.getSecondDriverCode())
                .thirdDriverCode(gpRequest.getThirdDriverCode())
                .imageUrl(gpRequest.getImageUrl())
                .trackImageUrl(gpRequest.getTrackImageUrl())
                .build();

        gpRepository.save(gp);
    }

    // helper function
    private TeamResponse getTeamByCode(String teamCode) {
        return webClient.get()
                .uri("http://" + teamServiceBaseUrl + "/api/team/" + teamCode)
                .retrieve()
                .bodyToMono(TeamResponse.class)
                .block();
    }

    // helper function
    private DriverResponse getDriverByCode(String driverCode) {

        return webClient.get()
                .uri("http://" + driverServiceBaseUrl + "/api/driver/" + driverCode)
                .retrieve()
                .bodyToMono(DriverResponse.class)
                .block();

    }

    private GpResponse mapToGpResponse(Gp gp) {
        return GpResponse.builder()
                .gpCode(gp.getGpCode())
                .name(gp.getName())
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
                .trackImageUrl(gp.getTrackImageUrl())
                .build();
    }

    public void deleteGp(String gpCode) {
        if (!gpRepository.existsByGpCode(gpCode)) {
            throw new IllegalArgumentException("GP with code " + gpCode + " does not exist.");
        }
        gpRepository.deleteByGpCode(gpCode);
    }

    public GpResponse getGp(String gpCode) {
        Optional<Gp> gp = gpRepository.findGpByGpCode(gpCode);
        return gp.map(this::mapToGpResponse).orElse(null);
    }

}
