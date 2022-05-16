package application.models;

import application.constants.RequestStatus;
import application.models.baseentity.AbstractContractTemplate;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "credit_request")
public class CreditRequest extends AbstractContractTemplate {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

}