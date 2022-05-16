package application.models;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "clients")
public class Client {
    @Id
    @Column(name = "id_client")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_client;

   // @NotEmpty(message = "Should not be empty")
    @Column(name = "name")
    private String name;

    //@NotEmpty(message = "Should not be empty")
    @Column(name = "passport")
    private String passport;

    //@NotEmpty(message = "Should not be empty")
    @Column(name = "address")
    private String address;

   // @NotEmpty(message = "Should not be empty")
    @Column(name = "phone")
    private String phone;


}