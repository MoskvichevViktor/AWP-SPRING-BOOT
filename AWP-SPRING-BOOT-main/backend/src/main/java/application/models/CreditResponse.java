package application.models;

import application.constants.ResponseStatus;
import application.models.abstractentity.AbstractContractTemplate;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "credit_response")
public class CreditResponse extends AbstractContractTemplate {

    @Column(name = "percent")
    private Float percent;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ResponseStatus status;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id")
    Contract contract;

    @Column(name = "client_id")
    Long clientId;

}
