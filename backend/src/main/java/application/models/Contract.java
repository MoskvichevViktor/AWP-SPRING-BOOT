package application.models;

import application.constants.ContractStatus;
import application.models.baseentity.AbstractContractTemplate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Table(name = "contracts")
public class Contract extends AbstractContractTemplate {
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @Override
    public String toString() {
        return "Empty toString method";
    }
}
