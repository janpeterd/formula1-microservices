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
                .build();
    }

    public void createDriver(DriverRequest driverRequest) {
        Driver driver = Driver.builder()
                // Generate a UUID as driver code
                .driverCode(UUID.randomUUID().toString())
                .firstName(driverRequest.getFirstName())
                .lastName(driverRequest.getLastName())
                .country(driverRequest.getCountry()).build();
        driverRepository.save(driver);
    }

    public List<DriverResponse> getAllDriversByDriverCode(List<String> driverCode) {
        List<Driver> drivers = driverRepository.getAllByDriverCodeIn(driverCode);
        return drivers.stream().map(this::mapToDriverResponse).toList();
    }
}
