package application.models;


import application.models.abstractentity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Setter
@Getter
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

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<CreditRequest> creditRequests;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<CreditResponse> creditResponses;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Contract> contracts;

    @Override
    public String toString() {
        return "Empty toString method";
    }
}