package springtest.security;

import springtest.model.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by sylvain on 11/12/16.
 */
public interface MongoUserRepository extends MongoRepository<Utilisateur, String> {

    List<Utilisateur> findByEmail(String email);

}
