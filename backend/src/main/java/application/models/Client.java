package application.models;


import application.models.baseentity.AbstractEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@Table(name = "clients")
public class Client extends AbstractEntity {

    @NotEmpty(message = "Should not be empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Should not be empty")
    @Column(name = "passport")
    private String passport;

    @NotEmpty(message = "Should not be empty")
    @Column(name = "address")
    private String address;

    @NotEmpty(message = "Should not be empty")
    @Column(name = "phone")
    private String phone;

}