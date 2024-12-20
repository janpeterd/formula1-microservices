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
            Driver driver = Driver.builder()
                    .driverCode("919e46b3-57d3-4775-b5a8-8dc5b2776d0c")
                    .firstName("Lewis")
                    .lastName("Hamilton")
                    .country("United Kingdom")
                    .teamCode("e600a035-1f38-4319-99d2-01607db9c980")
                    .seasonPoints(50)
                    .imageUrl("images/hamilton.jpg")
                    .build();

            Driver driver1 = Driver.builder()
                    .driverCode("c5709243-aadb-4c10-bafb-cdb8ed767790")
                    .firstName("George")
                    .lastName("Russel")
                    .country("United Kingdom")
                    .teamCode("e600a035-1f38-4319-99d2-01607db9c980")
                    .imageUrl("images/george_russel.jpg")
                    .seasonPoints(20)
                    .build();

            Driver driver2 = Driver.builder()
                    .driverCode("6baeb4c9-f7d1-4176-9854-f50ff290aae0")
                    .firstName("Max")
                    .lastName("Verstappen")
                    .country("The Netherlands")
                    .teamCode("ac879010-39cd-4562-badf-732664ea68c3")
                    .imageUrl("images/max-verstappen.webp")
                    .seasonPoints(10)
                    .build();
            Driver driver3 = Driver.builder()
                    .driverCode("1bdd16ec-3a29-484c-9c44-abcaa8cdb23a")
                    .firstName("Carlos")
                    .lastName("Sainz Jr.")
                    .country("Spain")
                    .teamCode("ac879010-39cd-4562-badf-732664ea68c3")
                    .imageUrl("images/sainz.jpg")
                    .seasonPoints(4)
                    .build();

            driverRepository.save(driver);
            driverRepository.save(driver1);
            driverRepository.save(driver2);
            driverRepository.save(driver3);
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
