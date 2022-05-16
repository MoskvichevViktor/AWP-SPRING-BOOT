package application.models;

import application.constants.UserRole;
import application.models.baseentity.AbstractEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Override
    public String toString() {
        return "User{}";
    }
}