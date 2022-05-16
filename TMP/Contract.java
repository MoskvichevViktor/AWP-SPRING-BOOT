package application.models;


import application.models.baseentity.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "contract")
public class Contract extends BaseEntity {

    @ManyToOne
    @JoinColumn(name ="id_client", referencedColumnName = "id_client")
    private Client client;

    @Column(name = "name")
    private String name;

    @Column(name = "pasport")
    private String pasport;

    @Column(name = "period")
    private Integer period;

    @Column(name = "sum")
    private Integer sum;

    @Column(name = "status")
    private String status;

    public Contract() {
    }


}