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
    // private final WebClient webClient;

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
                    .build();

            Driver driver1 = Driver.builder()
                    .driverCode("c5709243-aadb-4c10-bafb-cdb8ed767790")
                    .firstName("George")
                    .lastName("Russel")
                    .country("United Kingdom")
                    .teamCode("e600a035-1f38-4319-99d2-01607db9c980")
                    .seasonPoints(20)
                    .build();

            Driver driver2 = Driver.builder()
                    .driverCode("6baeb4c9-f7d1-4176-9854-f50ff290aae0")
                    .firstName("Max")
                    .lastName("Verstappen")
                    .country("The Netherlands")
                    .teamCode("ac879010-39cd-4562-badf-732664ea68c3")
                    .seasonPoints(10)
                    .build();

            driverRepository.save(driver);
            driverRepository.save(driver1);
            driverRepository.save(driver2);
        }
    }

    private DriverResponse mapToDriverResponse(Driver driver) {
        return DriverResponse.builder()
                .id(driver.getId())
                .driverCode(driver.getDriverCode())
                .firstName(driver.getFirstName())
                .lastName(driver.getLastName())
                .country(driver.getCountry())
                .seasonPoints(driver.getSeasonPoints())
                .teamCode(driver.getTeamCode())
                .build();
    }

    public void createDriver(DriverRequest driverRequest) {
        Driver driver = Driver.builder()
                // Generate a UUID as driver code
                .driverCode(UUID.randomUUID().toString())
                .firstName(driverRequest.getFirstName())
                .lastName(driverRequest.getLastName())
                .country(driverRequest.getCountry())
                .teamCode(driverRequest.getTeamCode())
                .seasonPoints(driverRequest.getSeasonPoints())
                .build();
        driverRepository.save(driver);
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
            driverRepository.save(driver);
            System.out.println("saved driver " + driver);
            return Optional.of(mapToDriverResponse(driver));
        }
        System.out.println("returning empty driver update (not present)");
        return Optional.empty();
    }

    public List<DriverResponse> getDriversByTeamCode(String teamCode) {
        return driverRepository.findDriversByTeamCode(teamCode).get().stream().map(this::mapToDriverResponse).toList();
    }
}
