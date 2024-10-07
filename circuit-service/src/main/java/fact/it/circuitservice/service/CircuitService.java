package fact.it.circuitservice.service;

import fact.it.circuitservice.dto.CircuitRequest;
import fact.it.circuitservice.model.Circuit;
import fact.it.circuitservice.repository.CircuitRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CircuitService {
    private final CircuitRepository circuitRepository;

    public void createCircuit(CircuitRequest circuitRequest) {
        Circuit circuit = Circuit.builder()
                .circuitCode(UUID.randomUUID().toString())
                .name(circuitRequest.getName())
                .distanceMeters(circuitRequest.getDistanceMeters())
                .country(circuitRequest.getCountry())
                .build();

        circuitRepository.save(circuit);
    }
}
