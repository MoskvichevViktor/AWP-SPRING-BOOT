package application.models.security;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity

@Table(name = "roles")
public class Role extends BaseSecurityEntity{

    @Column(name = "name")
    private String name;
    public Role() {
    }


}
