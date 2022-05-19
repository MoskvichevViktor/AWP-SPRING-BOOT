package application.models;

import application.constants.RequestStatus;
import application.models.abstractentity.AbstractContractTemplate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "credit_response")
public class CreditResponse extends AbstractContractTemplate {

    @Column(name = "percent")
    private Float percent;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @OneToOne()
    @JoinColumn(name = "id")
    private CreditRequest creditRequest;

    public CreditResponse() {
    }
}
