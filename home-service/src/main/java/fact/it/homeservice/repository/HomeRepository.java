package fact.it.homeservice.repository;

import fact.it.homeservice.model.Home;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HomeRepository extends MongoRepository<Home, String> {
    @Query("{ '_id': ?0 }")
    Home findHomeById(String id);

    @Query("{ 'userId': ?0 }")
    List<Home> findHomesByUserId(Long userId);
}
