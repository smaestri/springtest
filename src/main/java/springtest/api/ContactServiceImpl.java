package springtest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springtest.model.Contact;

import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    MongoContactRespository mongoContactRespository;

    public String getAdress(String userId) throws Exception {
        Optional<Contact> contacts = mongoContactRespository.findByUserId(userId);

        if (!contacts.isPresent()) {
            throw new Exception("contact not found");
        }
        return contacts.get().getAddress();

    }
}
