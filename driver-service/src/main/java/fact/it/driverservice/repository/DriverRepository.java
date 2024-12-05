package fact.it.driverservice.repository;

import fact.it.driverservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findDriverByDriverCode(String driverCode);

    Optional<List<Driver>> findDriversByTeamCode(String teamCode);
}
