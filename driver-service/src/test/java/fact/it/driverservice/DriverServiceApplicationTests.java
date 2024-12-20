package fact.it.driverservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fact.it.driverservice.dto.DriverRequest;
import fact.it.driverservice.dto.DriverResponse;
import fact.it.driverservice.model.Driver;
import fact.it.driverservice.repository.DriverRepository;
import fact.it.driverservice.service.DriverService;

@ExtendWith(MockitoExtension.class)
class DriverServiceApplicationTests {

    @InjectMocks
    private DriverService driverService;

    @Mock
    private DriverRepository driverRepository;

    private Driver driver;
    private DriverRequest driverRequest;

    @BeforeEach
    void setUp() {

        driver = Driver.builder()
                .driverCode(UUID.randomUUID().toString())
                .firstName("Test Driver")
                .lastName("Test Driver Last")
                .country("Test Country")
                .teamCode(UUID.randomUUID().toString())
                .seasonPoints(10)
                .imageUrl("images/testDriver.png")
                .build();

        driverRequest = DriverRequest.builder()
                .firstName("Test Driver Request")
                .lastName("Test Driver Request Last")
                .country("Test Country")
                .teamCode(UUID.randomUUID().toString())
                .seasonPoints(10)
                .imageUrl("images/testDriverRequest.png")
                .build();
    }

    @Test
    void testMapToDriverResponse() {
        DriverResponse response = driverService.mapToDriverResponse(driver);
        assertThat(response.getFirstName()).isEqualTo("Test Driver");
        assertThat(response.getLastName()).isEqualTo("Test Driver Last");
        assertThat(response.getCountry()).isEqualTo("Test Country");
        assertThat(response.getTeamCode()).isEqualTo(driver.getTeamCode());
        assertThat(response.getSeasonPoints()).isEqualTo(10);
        assertThat(response.getImageUrl()).isEqualTo("images/testDriver.png");
    }

    @Test
    void testCreateDriver() {
        driverService.createDriver(driverRequest);
        verify(driverRepository).save(any(Driver.class));
    }

    @Test
    void testGetDriverByDriverCode() {
        when(driverRepository.findDriverByDriverCode(driver.getDriverCode())).thenReturn(Optional.of(driver));
        // Test the service
        DriverResponse response = driverService.getDriverByDriverCode(driver.getDriverCode());

        assertThat(response.getFirstName()).isEqualTo(driver.getFirstName());
        assertThat(response.getLastName()).isEqualTo(driver.getLastName());
        assertThat(response.getCountry()).isEqualTo(driver.getCountry());
        assertThat(response.getTeamCode()).isEqualTo(driver.getTeamCode());
        assertThat(response.getSeasonPoints()).isEqualTo(driver.getSeasonPoints());
        assertThat(response.getImageUrl()).isEqualTo(driver.getImageUrl());

    }

    @Test
    void testGetAllDrivers() {
        when(driverRepository.findAll()).thenReturn(List.of(driver));

        List<DriverResponse> drivers = driverService.getAllDrivers();
        assertThat(drivers).hasSize(1);
        assertThat(drivers.get(0).getFirstName()).isEqualTo("Test Driver");
        assertThat(drivers.get(0).getLastName()).isEqualTo("Test Driver Last");
        assertThat(drivers.get(0).getCountry()).isEqualTo("Test Country");
        assertThat(drivers.get(0).getTeamCode()).isEqualTo(driver.getTeamCode());
        assertThat(drivers.get(0).getSeasonPoints()).isEqualTo(driver.getSeasonPoints());
        assertThat(drivers.get(0).getImageUrl()).isEqualTo("images/testDriver.png");

        verify(driverRepository).findAll();
    }

    @Test
    void testUpdateDriver() {

        when(driverRepository.findDriverByDriverCode(driver.getDriverCode())).thenReturn(Optional.of(driver));
        Optional<DriverResponse> response = driverService.updateDriver(driverRequest, driver.getDriverCode());

        assertThat(response.isPresent()).isTrue();
        assertThat(response.get().getFirstName()).isEqualTo(driverRequest.getFirstName());
        assertThat(response.get().getLastName()).isEqualTo(driverRequest.getLastName());
        assertThat(response.get().getCountry()).isEqualTo(driverRequest.getCountry());
        assertThat(response.get().getImageUrl()).isEqualTo(driverRequest.getImageUrl());
        assertThat(response.get().getTeamCode()).isEqualTo(driverRequest.getTeamCode());
    }

    @Test
    void testGetDriversByTeamCode() {
        when(driverRepository.findDriversByTeamCode(driver.getTeamCode())).thenReturn(Optional.of(List.of(driver)));

        // Test the service
        List<DriverResponse> drivers = driverService.getDriversByTeamCode(driver.getTeamCode());

        assertThat(drivers).hasSize(1);
        assertThat(drivers.get(0).getFirstName()).isEqualTo("Test Driver");
        assertThat(drivers.get(0).getLastName()).isEqualTo("Test Driver Last");
        assertThat(drivers.get(0).getCountry()).isEqualTo("Test Country");
        assertThat(drivers.get(0).getTeamCode()).isEqualTo(driver.getTeamCode());
        assertThat(drivers.get(0).getSeasonPoints()).isEqualTo(driver.getSeasonPoints());
        assertThat(drivers.get(0).getImageUrl()).isEqualTo("images/testDriver.png");

    }

    @Test
    void testAddDriverToTeam() {
        when(driverRepository.findDriverByDriverCode(driver.getDriverCode())).thenReturn(Optional.of(driver));

        String newTeamCode = UUID.randomUUID().toString();
        DriverResponse driverResponse = driverService.addDriverToTeam(driver.getDriverCode(),
                newTeamCode);

        verify(driverRepository).findDriverByDriverCode(driver.getDriverCode());
        assertThat(driverResponse.getTeamCode()).isEqualTo(newTeamCode);
        verify(driverRepository).save(any(Driver.class));
    }
}
