package fact.it.maintenanceservice.repository;

import fact.it.maintenanceservice.model.Maintenance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MaintenanceRepository extends MongoRepository<Maintenance, Long> {
    @Query("{ '_id': ?0 }")
    Maintenance findMaintenanceById(Long id);

    @Query("{ 'userId': ?0 }")
    List<Maintenance> findMaintenanceByUserId(Long userId);
}
