package fact.it.gpservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fact.it.gpservice.model.Gp;

public interface GpRepository extends JpaRepository<Gp, Long> {
    boolean existsByGpCode(String GpCode);

    void deleteByGpCode(String GpCode);

    Optional<Gp> findGpByGpCode(String GpCode);
}
