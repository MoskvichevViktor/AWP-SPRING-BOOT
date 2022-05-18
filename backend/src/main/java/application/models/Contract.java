package application.models;

import application.constants.ContractStatus;
import application.models.abstractentity.AbstractContractTemplate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "contract")
public class Contract extends AbstractContractTemplate {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @OneToOne()
    @JoinColumn(name = "id")
    private CreditResponse creditResponse;

    @Override
    public String toString() {
        return "Empty toString method";
    }

    public Contract() {
    }
}
