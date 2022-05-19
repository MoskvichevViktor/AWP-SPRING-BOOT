package application.models;

import application.constants.ContractStatus;
import application.models.abstractentity.AbstractContractTemplate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@Entity
@Table(name = "contract")
public class Contract extends AbstractContractTemplate {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private CreditResponse creditResponse;

    @Column(name = "percent")
    private Float percent;

    public Contract() {
    }

}
