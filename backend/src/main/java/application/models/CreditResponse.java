package application.models;

import javax.persistence.*;

@Entity
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

    @ManyToOne
    @JoinColumn(name ="id_client", referencedColumnName = "id_client")
    private Client client;


    /*
    @Column(name = "idrequest")
    private Integer idrequest;

    @Column(name = "name")
    private String name;

    @Column(name = "pasport")
    private String pasport;
     */

    public CreditResponse() {
    }

    public CreditResponse(Integer id_creditresponse, Integer idrequest, String name, String pasport, Integer period, Integer sum, String status) {
        this.id_creditresponse = id_creditresponse;
        //this.idrequest = idrequest;
        //this.name = name;
        //this.pasport = pasport;
        this.period = period;
        this.sum = sum;
        this.status = status;
    }

    public Integer getId() {
        return id_creditresponse;
    }

    public void setId(Integer id) {
        this.id_creditresponse = id_creditresponse;
    }

    //public Integer getIdrequest() {return idrequest;}

    //public void setIdrequest(Integer idrequest) {this.idrequest = idrequest;}

    //public String getName() {return name;}

    //public void setName(String name) {this.name = name;}

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //public String getPasport() {return pasport;}

    //public void setPasport(String pasport) {this.pasport = pasport;}

    @Override
    public String toString() {
        return "CreditResponse{" +
                "id_creditresponse =" + id_creditresponse +
               // ", idrequest=" + idrequest +
               // ", name='" + name + '\'' +
                ", period=" + period +
                ", sum=" + sum +
                ", status='" + status + '\'' +
                '}';
    }
}