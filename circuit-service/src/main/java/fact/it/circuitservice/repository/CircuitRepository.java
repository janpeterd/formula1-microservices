package fact.it.circuitservice.repository;

import fact.it.circuitservice.model.Circuit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CircuitRepository extends JpaRepository<Circuit, Long> {
}
