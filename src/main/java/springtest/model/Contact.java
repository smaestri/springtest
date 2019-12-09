package springtest.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Contact {

    private String address;
    private String userId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
