package fact.it.gpservice.repository;

import fact.it.gpservice.model.Gp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpRepository extends JpaRepository<Gp, Long> {
}
