package fact.it.driverservice.repository;

import fact.it.driverservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    List<Driver> getAllByDriverCodeIn(List<String> driverCode);
}
