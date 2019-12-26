package pacman.gateway.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import pacman.gateway.model.User;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> { }
