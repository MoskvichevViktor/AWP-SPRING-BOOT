package application.models;


import application.models.abstractentity.AbstractEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;


@Data
@Entity
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private List<CreditRequest> creditRequests;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private List<CreditResponse> creditResponses;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private List<Contract> contracts;

    public Client() {
    }

}