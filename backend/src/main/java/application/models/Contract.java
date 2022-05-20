package application.models;

import application.constants.ContractStatus;
import application.models.abstractentity.AbstractContractTemplate;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "contract")
public class Contract extends AbstractContractTemplate {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @Column(name = "percent")
    private Float percent;

    @Column(name = "client_id")
    Long clientId;
}

