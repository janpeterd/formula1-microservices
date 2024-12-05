package fact.it.circuitservice.service;

import fact.it.circuitservice.dto.CircuitRequest;
import fact.it.circuitservice.dto.CircuitResponse;
import fact.it.circuitservice.model.Circuit;
import fact.it.circuitservice.repository.CircuitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CircuitService {
    private final CircuitRepository circuitRepository;

    public List<CircuitResponse> getAllCircuits() {
        List<Circuit> circuits = circuitRepository.findAll();
        System.out.println("Circuits " + circuits);
        return circuits.stream().map(this::mapToCircuitResponse).toList();
    }

    public void createCircuit(CircuitRequest circuitRequest) {
        Circuit circuit = Circuit.builder()
                .circuitCode(UUID.randomUUID().toString())
                .name(circuitRequest.getName())
                .distanceMeters(circuitRequest.getDistanceMeters())
                .country(circuitRequest.getCountry())
                .laps(circuitRequest.getLaps())
                .build();

        circuitRepository.save(circuit);
    }

    private CircuitResponse mapToCircuitResponse(Circuit circuit) {
        return CircuitResponse.builder().id(circuit.getId()).circuitCode(circuit.getCircuitCode())
                .country(circuit.getCountry()).distanceMeters(circuit.getDistanceMeters()).laps(circuit.getLaps())
                .build();
    }
}
