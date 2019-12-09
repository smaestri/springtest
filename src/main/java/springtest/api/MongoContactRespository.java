package springtest.api;

import org.springframework.data.mongodb.repository.MongoRepository;
import springtest.model.Contact;

import java.util.Optional;

public interface MongoContactRespository extends MongoRepository<Contact, String> {

    Optional<Contact> findByUserId(String userId);
}
