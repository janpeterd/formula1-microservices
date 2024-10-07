package fact.it.driverservice.controller;

import fact.it.driverservice.dto.DriverRequest;
import fact.it.driverservice.dto.DriverResponse;
import fact.it.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DriverResponse> getAllDriversByDriverCode
            (@RequestParam List<String> driverCode) {
        return driverService.getAllDriversByDriverCode(driverCode);
    }
}
