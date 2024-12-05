package fact.it.driverservice.service;

import fact.it.driverservice.dto.DriverRequest;
import fact.it.driverservice.dto.DriverResponse;
import fact.it.driverservice.model.Driver;
import fact.it.driverservice.repository.DriverRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverService {
    private final DriverRepository driverRepository;
    private final WebClient webClient;

    private DriverResponse mapToDriverResponse(Driver driver) {
        return DriverResponse.builder()
                .id(driver.getId())
                .driverCode(driver.getDriverCode())
                .firstName(driver.getFirstName())
                .lastName(driver.getLastName())
                .country(driver.getCountry())
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
        Optional<Driver> driverOpt = driverRepository.findDriverByDriverCode(driverCode);
        if (driverOpt.isPresent()) {
            Driver driver = driverOpt.get();
            driver.setFirstName(updateDriver.getFirstName());
            driver.setLastName(updateDriver.getLastName());
            driver.setCountry(updateDriver.getCountry());
            driver.setTeamCode(updateDriver.getTeamCode());
            driverRepository.save(driver);
            return Optional.of(mapToDriverResponse(driver));
        }
        return Optional.empty();
    }

    public List<DriverResponse> getDriversByTeamCode(String teamCode) {
        return driverRepository.findDriversByTeamCode(teamCode).get().stream().map(this::mapToDriverResponse).toList();
    }
}
