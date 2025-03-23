package mk.poplaki.repository;

import mk.poplaki.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {
    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByRole(String role);

    boolean existsByEmailIgnoreCase(String email);

    long countByRole(String role);

    Optional<User> findById(String userId);
}
