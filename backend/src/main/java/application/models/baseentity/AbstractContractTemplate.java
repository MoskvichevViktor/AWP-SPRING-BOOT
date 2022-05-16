package application.models.baseentity;

import application.models.Client;
import lombok.Data;

import javax.persistence.*;


@MappedSuperclass
@Data
public abstract class AbstractContractTemplate extends AbstractEntity {
    @ManyToOne
    @JoinTable(name = "clients",
            joinColumns = @JoinColumn(name = "id"))
    private Client client;

    @Column(name = "period")
    private Integer period;

    @Column(name = "sum")
    private Integer sum;

}
