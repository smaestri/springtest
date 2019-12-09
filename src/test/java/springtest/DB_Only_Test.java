package springtest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import springtest.api.MongoContactRespository;
import springtest.model.Contact;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
public class DB_Only_Test {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    MongoContactRespository mongoContactRespository;

    @Test
    public void testWithFrenchIsbn() {

        // given
        Contact contact = new Contact();
        contact.setUserId("123");
        contact.setAddress("address1");
        mongoTemplate.save(contact);

        // when + then
        Optional<Contact> address = mongoContactRespository.findByUserId("123");
        assertEquals(address.get().getAddress(), "address1");

    }

}