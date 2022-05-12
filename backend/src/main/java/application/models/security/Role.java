package application.models.security;

import lombok.*;

import javax.persistence.*;

@Data
@Entity

@Table(name = "roles")
public class Role extends BaseSecurityEntity {

    @Column(name = "name")
    private String name;

    public Role() {
    }


}
