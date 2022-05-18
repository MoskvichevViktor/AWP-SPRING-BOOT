package application.models.abstractentity;

import application.models.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.ConstraintMode.PROVIDER_DEFAULT;


@MappedSuperclass
@NoArgsConstructor
@Setter
@Getter
public abstract class AbstractContractTemplate extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @JsonIgnore
    private Client client;

    @Column(name = "period")
    private Integer period;

    @Column(name = "sum")
    private BigDecimal sum;
}
