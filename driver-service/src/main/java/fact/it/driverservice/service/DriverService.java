package fact.it.driverservice.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import fact.it.driverservice.dto.DriverRequest;
import fact.it.driverservice.dto.DriverResponse;
import fact.it.driverservice.model.Driver;
import fact.it.driverservice.repository.DriverRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverService {
    private final DriverRepository driverRepository;

    @PostConstruct
    public void loadData() {
        if (driverRepository.count() <= 0) {
            List<Driver> drivers = List.of(
                    Driver.builder()
                            .driverCode("919e46b3-57d3-4775-b5a8-8dc5b2776d0c")
                            .firstName("Lewis")
                            .lastName("Hamilton")
                            .country("United Kingdom")
                            .teamCode("e600a035-1f38-4319-99d2-01607db9c980")
                            .seasonPoints(50)
                            .imageUrl("images/hamilton.jpg")
                            .build(),

                    Driver.builder()
                            .driverCode("f7035391-c9bf-4034-ac5d-268563e35551")
                            .firstName("George")
                            .lastName("Russel")
                            .country("United Kingdom")
                            .teamCode("e600a035-1f38-4319-99d2-01607db9c980")
                            .seasonPoints(20)
                            .imageUrl("images/george_russel.jpg")
                            .build(),

                    Driver.builder()
                            .driverCode("6baeb4c9-f7d1-4176-9854-f50ff290aae0")
                            .firstName("Max")
                            .lastName("Verstappen")
                            .country("The Netherlands")
                            .teamCode("ac879010-39cd-4562-badf-732664ea68c3")
                            .seasonPoints(10)
                            .imageUrl("images/max-verstappen.webp")
                            .build(),

                    Driver.builder()
                            .driverCode("1bdd16ec-3a29-484c-9c44-abcaa8cdb23a")
                            .firstName("Carlos")
                            .lastName("Sainz Jr.")
                            .country("Spain")
                            .teamCode("f41d8b77-7292-4d3a-8827-2b0fc09e3c24")
                            .seasonPoints(4)
                            .imageUrl("images/sainz.jpg")
                            .build(),

                    Driver.builder()
                            .driverCode("54cd93a4-bfd7-4f65-87f6-dce1d5d55df0")
                            .firstName("Charles")
                            .lastName("Leclerc")
                            .country("Monaco")
                            .teamCode("f41d8b77-7292-4d3a-8827-2b0fc09e3c24")
                            .seasonPoints(32)
                            .imageUrl("images/leclerc.jpg")
                            .build(),

                    Driver.builder()
                            .driverCode("fe38126c-47da-4f96-8816-8b268f6faca4")
                            .firstName("Oscar")
                            .lastName("Piastri")
                            .country("Australia")
                            .teamCode("4f5db9ab-c6f9-4a7b-a9bc-6d9fcd4ef842")
                            .seasonPoints(25)
                            .imageUrl("images/piastri.jpg")
                            .build(),

                    Driver.builder()
                            .driverCode("9f8e2b0c-8f58-44cf-bf03-2a4b9bcbcde2")
                            .firstName("Lance")
                            .lastName("Stroll")
                            .country("Canada")
                            .teamCode("5d9c95c1-7e4d-4f1b-b1b5-62d762fe1a4b")
                            .seasonPoints(292)
                            .imageUrl("images/stroll.jpg")
                            .build(),

                    Driver.builder()
                            .driverCode("dce84971-05de-4e4f-ad98-1978481618a8")
                            .firstName("Fernando")
                            .lastName("Alonso")
                            .country("Spain")
                            .teamCode("5d9c95c1-7e4d-4f1b-b1b5-62d762fe1a4b")
                            .seasonPoints(18)
                            .imageUrl("images/alonso.jpg")
                            .build(),

                    Driver.builder()
                            .driverCode("3c7b8e9f-6b8a-4e7d-9b6f-3a8c5e4b9a8d")
                            .firstName("Lando")
                            .lastName("Norris")
                            .country("United Kingdom")
                            .teamCode("4f5db9ab-c6f9-4a7b-a9bc-6d9fcd4ef842")
                            .seasonPoints(16)
                            .imageUrl("images/norris.jpg")
                            .build(),

                    Driver.builder()
                            .driverCode("8a7b9c6e-5b9d-4f7a-9e8c-3f7b9e8a6c7d")
                            .firstName("Sergio")
                            .lastName("Perez")
                            .country("Mexico")
                            .teamCode("ac879010-39cd-4562-badf-732664ea68c3")
                            .seasonPoints(28)
                            .imageUrl("images/perez.jpg")
                            .build(),

                    Driver.builder()
                            .driverCode("9d40a5af-a9d6-4588-ae9b-9588956a6132")
                            .firstName("Nico")
                            .lastName("Hulkenberg")
                            .country("Germany")
                            .teamCode("9334c873-d370-4777-9c18-4385627da6c2")
                            .seasonPoints(571)
                            .imageUrl("images/hulkenberg.jpg")
                            .build(),

                    Driver.builder()
                            .driverCode("8dd27ff0-30d1-4052-8d9d-086906323fc3")
                            .firstName("Kevin")
                            .lastName("Magnussen")
                            .country("Denmark")
                            .teamCode("9334c873-d370-4777-9c18-4385627da6c2")
                            .seasonPoints(202)
                            .imageUrl("images/magnussen.jpg")
                            .build());

            driverRepository.saveAll(drivers);
        }
    }

    public DriverResponse mapToDriverResponse(Driver driver) {
        return DriverResponse.builder()
                .driverCode(driver.getDriverCode())
                .firstName(driver.getFirstName())
                .lastName(driver.getLastName())
                .country(driver.getCountry())
                .seasonPoints(driver.getSeasonPoints())
                .teamCode(driver.getTeamCode())
                .imageUrl(driver.getImageUrl())
                .build();
    }

    public DriverResponse createDriver(DriverRequest driverRequest) {
        Driver driver = Driver.builder()
                // Generate a UUID as driver code
                .driverCode(UUID.randomUUID().toString())
                .firstName(driverRequest.getFirstName())
                .lastName(driverRequest.getLastName())
                .country(driverRequest.getCountry())
                .teamCode(driverRequest.getTeamCode())
                .seasonPoints(driverRequest.getSeasonPoints())
                .imageUrl(driverRequest.getImageUrl())
                .build();
        driverRepository.save(driver);
        return mapToDriverResponse(driver);
    }

    public DriverResponse getDriverByDriverCode(String driverCode) {
        Optional<Driver> driver = driverRepository.findDriverByDriverCode(driverCode);
        return driver.map(this::mapToDriverResponse).orElse(null);
    }

    public List<DriverResponse> getAllDrivers() {
        List<Driver> drivers = driverRepository.findAll();
        return drivers.stream().map(this::mapToDriverResponse).toList();
    }

    public Optional<DriverResponse> updateDriver(DriverRequest updateDriver, String driverCode) {
        System.out.println("updating driver");
        Optional<Driver> driverOpt = driverRepository.findDriverByDriverCode(driverCode);
        if (driverOpt.isPresent()) {
            Driver driver = driverOpt.get();
            driver.setFirstName(updateDriver.getFirstName());
            driver.setLastName(updateDriver.getLastName());
            driver.setCountry(updateDriver.getCountry());
            driver.setTeamCode(updateDriver.getTeamCode());
            driver.setSeasonPoints(updateDriver.getSeasonPoints());
            driver.setImageUrl(updateDriver.getImageUrl());
            driverRepository.save(driver);
            System.out.println("saved driver " + driver);
            return Optional.of(mapToDriverResponse(driver));
        }
        System.out.println("returning empty driver update (not present)");
        return Optional.empty();
    }

    public List<DriverResponse> getDriversByTeamCode(String teamCode) {
        Optional<List<Driver>> driver = driverRepository.findDriversByTeamCode(teamCode);
        if (driver.isPresent()) {
            return driver.get().stream().map(this::mapToDriverResponse).toList();
        } else {
            throw new RuntimeException("Error: finding drivers with teamCode: " + teamCode);
        }

    }

    public DriverResponse addDriverToTeam(String driverCode, String teamCode) {
        Optional<Driver> driver = driverRepository.findDriverByDriverCode(driverCode);
        if (driver.isPresent()) {
            driver.get().setTeamCode(teamCode);
            driverRepository.save(driver.get());
            return mapToDriverResponse(driver.get());
        } else {
            throw new RuntimeException("Error: Driver with driverCode " + driverCode + " not found.");
        }
    }
}
