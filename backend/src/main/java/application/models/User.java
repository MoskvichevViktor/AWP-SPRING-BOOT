package application.models;

import application.constants.UserRole;
import application.models.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    @Column(name = "username")
    @Size(min = 3, max = 30)
    private String username;

    @Column(name = "password")
    @Size(min = 3, max = 200)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {
    }

}