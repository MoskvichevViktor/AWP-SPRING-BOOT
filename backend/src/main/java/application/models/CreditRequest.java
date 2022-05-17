package application.models;

import application.constants.RequestStatus;
import application.models.abstractentity.AbstractContractTemplate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "credit_request")
public class CreditRequest extends AbstractContractTemplate {
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    public CreditRequest() {
    }

    @Override
    public String toString() {
        return "CreditRequest{}";
    }
}