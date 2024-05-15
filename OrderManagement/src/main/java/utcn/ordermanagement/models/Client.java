package utcn.ordermanagement.models;

import utcn.ordermanagement.data_access.annotations.Id;
import utcn.ordermanagement.data_access.annotations.ReferencedBy;
import utcn.ordermanagement.data_access.annotations.Table;

@Table(name = "client")
@ReferencedBy(table = Order.class)
public class Client {
    @Id
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;

    public Client() {
    }

    public Client(String name, String email, String address, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return name;
    }
}
