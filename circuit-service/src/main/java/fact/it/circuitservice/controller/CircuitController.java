package fact.it.circuitservice.controller;

import fact.it.circuitservice.dto.CircuitRequest;
import fact.it.circuitservice.model.Circuit;
import fact.it.circuitservice.service.CircuitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/api/circuit")
@RequiredArgsConstructor
public class CircuitController {
    private final CircuitService circuitService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createCircuit(@RequestBody CircuitRequest circuitRequest) {
        circuitService.createCircuit(circuitRequest);
    }
}
