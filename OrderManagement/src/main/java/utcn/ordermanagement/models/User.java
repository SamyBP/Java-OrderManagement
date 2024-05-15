package utcn.ordermanagement.models;

import utcn.ordermanagement.data_access.annotations.Id;
import utcn.ordermanagement.data_access.annotations.Table;

@Table(name = "user")
public class User {
    @Id
    private Long id;
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
