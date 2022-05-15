package application.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "creditrequest")
public class CreditRequest {
    @Id
    @Column(name = "id_creditrequest")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_creditrequest;

    @ManyToOne
    @JoinColumn(name ="id_client", referencedColumnName = "id_client")
    private Client client;

    /*
    @NotEmpty(message = "Should not be empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Should not be empty")
    @Column(name = "pasport")
    private String pasport;


    @NotEmpty(message = "Should not be empty")
    @Column(name = "adress")
    private String adress;

    @NotEmpty(message = "Should not be empty")
    @Column(name = "phone")
    private String phone;

    */

    @NotEmpty(message = "Should not be empty")
    @Column(name = "maritalstatus")
    private String maritalstatus;


    @NotEmpty(message = "Should not be empty")
    @Column(name = "jobdetails")
    private String jobdetails;

    @NotEmpty(message = "Should not be empty")
    @Column(name = "creditsum")
    private Integer creditsum;

    public CreditRequest() {
    }

    public CreditRequest(Integer id_creditrequest, String name, String pasport,
                         String maritalstatus, String adress, String phone,
                         String jobdetails, Integer creditsum) {
        this.id_creditrequest = id_creditrequest;
        //this.name = name;
        //this.pasport = pasport;
        //this.adress = adress;
        //this.phone = phone;
        this.maritalstatus = maritalstatus;
        this.jobdetails = jobdetails;
        this.creditsum = creditsum;
    }

    public Integer getId() {
        return id_creditrequest;
    }

    public void setId(Integer id_creditrequest) {
        this.id_creditrequest = id_creditrequest;
    }
/*
    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getPasport() {
        return pasport;
    }

    public void setPasport(String pasport) {
        this.pasport = pasport;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String address) {
        this.adress = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
*/
    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public String getJobdetails() {
        return jobdetails;
    }

    public void setJobdetails(String jobdetails) {
        this.jobdetails = jobdetails;
    }

    public Integer getCreditsum() {
        return creditsum;
    }

    public void setCreditsum(Integer creditsum) {
        this.creditsum = creditsum;
    }

    @Override
    public String toString() {
        return "CreditRequest{" +
                "id_creditrequest=" + id_creditrequest +
                //", name='" + name + '\'' +
                //", pasport='" + pasport + '\'' +
                //", adress='" + adress + '\'' +
                //", phone='" + phone + '\'' +
                ", maritalstatus='" + maritalstatus + '\'' +
                ", jobdetails='" + jobdetails + '\'' +
                ", creditsum=" + creditsum +
                '}';
    }
}