package fact.it.circuitservice.controller;

import fact.it.circuitservice.dto.CircuitRequest;
import fact.it.circuitservice.dto.CircuitResponse;
import fact.it.circuitservice.service.CircuitService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/circuit")
@RequiredArgsConstructor
public class CircuitController {
    private final CircuitService circuitService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CircuitResponse> getAllCircuits() {
        return circuitService.getAllCircuits();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createCircuit(@RequestBody CircuitRequest circuitRequest) {
        circuitService.createCircuit(circuitRequest);
    }
}
