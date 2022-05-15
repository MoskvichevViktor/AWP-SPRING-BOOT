package application.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "creditresponse")
public class CreditResponse {
    @Id
    @Column(name = "id_creditresponse")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_creditresponse;

    @Column(name = "period")
    private Integer period;

    @Column(name = "sum")
    private Integer sum;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name ="id_creditrequest", referencedColumnName = "id_creditrequest")
    private CreditRequest creditRequest;

    @Column(name = "id_creditrequest", insertable = false, updatable = false)
    private Integer id_creditrequest;

    @ManyToOne
    @JoinColumn(name ="id_client", referencedColumnName = "id_client")
    private Client client;


    @Column(name = "name")
    private String name;

    @Column(name = "pasport")
    private String pasport;

    public CreditResponse() {
    }


}