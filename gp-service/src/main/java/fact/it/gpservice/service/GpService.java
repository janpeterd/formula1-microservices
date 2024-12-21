package fact.it.gpservice.service;

import fact.it.gpservice.dto.DriverResponse;
import fact.it.gpservice.dto.GpRequest;
import fact.it.gpservice.dto.GpResponse;
import fact.it.gpservice.dto.TeamResponse;
import fact.it.gpservice.model.Gp;
import fact.it.gpservice.repository.GpRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
            List<Gp> gps = List.of(
                    Gp.builder()
                            .gpCode("a0471956-f215-4273-b428-3f52ac382ad2")
                            .name("Circuit de Spa-Francorchamps")
                            .country("Belgium")
                            .city("Stavelot")
                            .distanceMeters(7004)
                            .laps(44)
                            .raceDate(LocalDate.now())
                            .winningTeamCode("e600a035-1f38-4319-99d2-01607db9c980")
                            .winningDriverCode("919e46b3-57d3-4775-b5a8-8dc5b2776d0c")
                            .secondDriverCode("1bdd16ec-3a29-484c-9c44-abcaa8cdb23a")
                            .thirdDriverCode("6baeb4c9-f7d1-4176-9854-f50ff290aae0")
                            .imageUrl("images/Belgium.avif")
                            .trackImageUrl("images/Belgium_Circuit.png")
                            .build(),

                    Gp.builder()
                            .gpCode("03dfb751-0f69-4a34-85b5-c2bc52d3e0b7")
                            .name("Autodromo Nazionale Monza")
                            .country("Italy")
                            .city("Monza")
                            .distanceMeters(5793)
                            .laps(53)
                            .raceDate(LocalDate.of(2023, 9, 10))
                            .winningTeamCode("e600a035-1f38-4319-99d2-01607db9c980")
                            .winningDriverCode("1bdd16ec-3a29-484c-9c44-abcaa8cdb23a")
                            .secondDriverCode("919e46b3-57d3-4775-b5a8-8dc5b2776d0c")
                            .thirdDriverCode("6baeb4c9-f7d1-4176-9854-f50ff290aae0")
                            .imageUrl("images/Italy.jpg")
                            .trackImageUrl("images/Italy_Circuit.avif")
                            .build(),

                    Gp.builder()
                            .gpCode("435e387d-9851-4b2c-8273-be7c513a417f")
                            .name("Circuit de Monaco")
                            .country("Monaco")
                            .city("Monte Carlo")
                            .distanceMeters(3337)
                            .laps(78)
                            .raceDate(LocalDate.of(2023, 5, 28))
                            .winningTeamCode("4f5db9ab-c6f9-4a7b-a9bc-6d9fcd4ef842")
                            .winningDriverCode("3c7b8e9f-6b8a-4e7d-9b6f-3a8c5e4b9a8d")
                            .secondDriverCode("919e46b3-57d3-4775-b5a8-8dc5b2776d0c")
                            .thirdDriverCode("fe38126c-47da-4f96-8816-8b268f6faca4")
                            .imageUrl("images/Monaco.jpg")
                            .trackImageUrl("images/Monaco_Circuit.avif")
                            .build(),

                    Gp.builder()
                            .gpCode("9f8b1e6c-85da-4e6b-9c7d-4f9b7c8a5e6d")
                            .name("Suzuka International Racing Course")
                            .country("Japan")
                            .city("Suzuka")
                            .distanceMeters(5807)
                            .laps(53)
                            .raceDate(LocalDate.of(2023, 10, 8))
                            .winningTeamCode("ac879010-39cd-4562-badf-732664ea68c3")
                            .winningDriverCode("6baeb4c9-f7d1-4176-9854-f50ff290aae0")
                            .secondDriverCode("8a7b9c6e-5b9d-4f7a-9e8c-3f7b9e8a6c7d")
                            .thirdDriverCode("54cd93a4-bfd7-4f65-87f6-dce1d5d55df0")
                            .imageUrl("images/Japan.avif")
                            .trackImageUrl("images/Japan_Circuit.avif")
                            .build(),

                    Gp.builder()
                            .gpCode("7a6e4f2c-3d9b-4f8a-8b7d-9c4b5e6a3d7f")
                            .name("Silverstone Circuit")
                            .country("Great Britain")
                            .city("Silverstone")
                            .distanceMeters(5891)
                            .laps(52)
                            .raceDate(LocalDate.of(2023, 7, 9))
                            .winningTeamCode("e600a035-1f38-4319-99d2-01607db9c980")
                            .winningDriverCode("919e46b3-57d3-4775-b5a8-8dc5b2776d0c")
                            .secondDriverCode("3c7b8e9f-6b8a-4e7d-9b6f-3a8c5e4b9a8d")
                            .thirdDriverCode("8a7b9c6e-5b9d-4f7a-9e8c-3f7b9e8a6c7d")
                            .imageUrl("images/Great_Britain.avif")
                            .trackImageUrl("images/Great_Britain_Circuit.avif")
                            .build(),

                    Gp.builder()
                            .gpCode("5f6b4c9e-8a7d-4e6f-9b2c-3d7f5e6a4b9c")
                            .name("Circuit of the Americas")
                            .country("United States")
                            .city("Austin")
                            .distanceMeters(5513)
                            .laps(56)
                            .raceDate(LocalDate.of(2023, 10, 22))
                            .winningTeamCode("4f5db9ab-c6f9-4a7b-a9bc-6d9fcd4ef842")
                            .winningDriverCode("3c7b8e9f-6b8a-4e7d-9b6f-3a8c5e4b9a8d")
                            .secondDriverCode("fe38126c-47da-4f96-8816-8b268f6faca4")
                            .thirdDriverCode("919e46b3-57d3-4775-b5a8-8dc5b2776d0c")
                            .imageUrl("images/USA.avif")
                            .trackImageUrl("images/USA_Circuit.avif")
                            .build());

            gpRepository.saveAll(gps);
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

    public GpResponse createGp(GpRequest gpRequest) {
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
        return mapToGpResponse(gp);
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

    public GpResponse mapToGpResponse(Gp gp) {
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
        System.out.println("finding by gpCode: " + gpCode);
        Optional<Gp> gp = gpRepository.findGpByGpCode(gpCode);
        if (gp.isPresent()) {
            return mapToGpResponse(gp.get());
        } else {
            throw new IllegalArgumentException("GP with code " + gpCode + " does not exist.");
        }
    }

}
