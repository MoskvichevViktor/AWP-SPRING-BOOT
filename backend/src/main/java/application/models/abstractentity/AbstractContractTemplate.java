package application.models.abstractentity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;


@NoArgsConstructor
@Data
@MappedSuperclass
public abstract class AbstractContractTemplate extends AbstractEntity {

    @Column(name = "period")
    private Integer period;

    @Column(name = "sum")
    private BigDecimal sum;

}

