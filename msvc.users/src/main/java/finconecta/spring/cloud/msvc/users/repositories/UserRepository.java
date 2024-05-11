package finconecta.spring.cloud.msvc.users.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import finconecta.spring.cloud.msvc.users.models.collections.User;

public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
