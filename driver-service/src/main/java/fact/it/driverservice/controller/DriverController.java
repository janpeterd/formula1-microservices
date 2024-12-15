package fact.it.driverservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fact.it.driverservice.dto.DriverRequest;
import fact.it.driverservice.dto.DriverResponse;
import fact.it.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/driver")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createDriver(@RequestBody DriverRequest driverRequest) {
        driverService.createDriver(driverRequest);
    }

    @CrossOrigin
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DriverResponse> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @GetMapping("/by-id/{driverCode}")
    public ResponseEntity<DriverResponse> getDriverByDriverCode(@PathVariable String driverCode) {
        DriverResponse driver = driverService.getDriverByDriverCode(driverCode);
        if (driver != null) {
            return new ResponseEntity<>(driver, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{driverCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DriverResponse> updateDriver(@RequestBody DriverRequest updateDriver,
            @PathVariable String driverCode) {
        Optional<DriverResponse> updatedDriverOpt = driverService.updateDriver(updateDriver, driverCode);
        if (updatedDriverOpt.isPresent()) {
            return new ResponseEntity<>(updatedDriverOpt.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/team")
    @ResponseStatus(HttpStatus.OK)
    public List<DriverResponse> getDriversByTeamCode(@RequestParam String teamCode) {
        return driverService.getDriversByTeamCode(teamCode);
    }
}
