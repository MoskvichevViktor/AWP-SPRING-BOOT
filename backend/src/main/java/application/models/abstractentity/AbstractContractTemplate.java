package application.models.abstractentity;

import application.models.Client;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;


@MappedSuperclass
@Setter
@Getter
public abstract class AbstractContractTemplate extends AbstractEntity {
    @ManyToOne
    @JoinTable(name = "clients",
            joinColumns = @JoinColumn(name = "id"))
    private Client client;

    @Column(name = "period")
    private Integer period;

    @Column(name = "sum")
    private BigDecimal sum;

    public AbstractContractTemplate() {
    }
}