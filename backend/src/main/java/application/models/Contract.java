package application.models;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "contract")
public class Contract {
    @Id
    @Column(name = "id_contract")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_contract;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="id_client", referencedColumnName = "id_client")
    private Client client;
/*
    @Column(name = "name")
    private String name;

    @Column(name = "pasport")
    private String pasport;
*/
    @Column(name = "period")
    private Integer period;

    @Column(name = "sum")
    private Integer sum;

    @Column(name = "status")
    private String status;

}