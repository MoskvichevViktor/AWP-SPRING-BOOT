package application.models.abstractentity;

import application.models.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;



@NoArgsConstructor
@Data
@MappedSuperclass
public abstract class AbstractContractTemplate extends AbstractEntity {

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "client_id", referencedColumnName = "id")
//    private Client client;


    @Column(name = "period")
    private Integer period;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "client_id")
    private Long clientId;

}
