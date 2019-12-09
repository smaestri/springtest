package springtest.api;

import org.springframework.data.mongodb.repository.MongoRepository;
import springtest.model.Utilisateur;

import java.util.Optional;

public interface MongoUserRepository extends MongoRepository<Utilisateur, String> {

    Optional<Utilisateur> findByEmail(String email);

}
