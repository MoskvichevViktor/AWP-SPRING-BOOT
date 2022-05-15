package application.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contract")
public class Contract {
    @Id
    @Column(name = "id_contract")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_contract;

    @ManyToOne
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

    public Contract() {
    }

    public Contract(Integer id_contract, String name, String pasport, Integer period, Integer sum, String status) {
        this.id_contract = id_contract;
        //this.name = name;
        //this.pasport = pasport;
        this.period = period;
        this.sum = sum;
        this.status = status;
    }

    public Integer getId() {
        return id_contract;
    }

    public void setId(Integer id) {
        this.id_contract = id_contract;
    }

    //public String getName() {return name;}

    //public void setName(String name) this.name = name;}

    //public String getPasport() {return pasport;}

    //public void setPasport(String pasport) {this.pasport = pasport;}

    public int getPeriod() {
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

    @Override
    public String toString() {
        return "Contract{" +
                "id_contract=" + id_contract +
                // ", name='" + name + '\'' +
                // ", pasport='" + pasport + '\'' +
                ", period=" + period +
                ", sum=" + sum +
                ", status='" + status + '\'' +
                '}';
    }
}