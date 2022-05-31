package application.models;

import application.constants.RequestStatus;
import application.models.abstractentity.AbstractContractTemplate;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Data
@Entity
@Table(name = "credit_request")
public class CreditRequest extends AbstractContractTemplate {

    //    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST },fetch = FetchType.LAZY)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(updatable = false, name = "response_id")
    private CreditResponse creditResponse;
}