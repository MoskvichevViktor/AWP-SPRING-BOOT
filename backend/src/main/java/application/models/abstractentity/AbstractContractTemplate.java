package application.models.abstractentity;

import application.models.Client;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.ConstraintMode.PROVIDER_DEFAULT;


@MappedSuperclass
@Setter
@Getter
public abstract class AbstractContractTemplate extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinTable(name = "clients")
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;


    @Column(name = "period")
    private Integer period;

    @Column(name = "sum")
    private BigDecimal sum;

    public AbstractContractTemplate() {
    }
}
