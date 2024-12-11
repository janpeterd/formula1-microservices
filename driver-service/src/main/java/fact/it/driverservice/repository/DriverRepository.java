package fact.it.driverservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fact.it.driverservice.model.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Optional<Driver> findDriverByDriverCode(String driverCode);

    Optional<List<Driver>> findDriversByTeamCode(String teamCode);
}
