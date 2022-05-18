package application.models;

import application.constants.ContractStatus;
import application.models.abstractentity.AbstractContractTemplate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name = "contract")
public class Contract extends AbstractContractTemplate {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private CreditResponse creditResponse;

    @Override
    public String toString() {
        return "Empty toString method";
    }

}
