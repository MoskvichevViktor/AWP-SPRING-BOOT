package application.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@Table(name = "creditrequest")
public class CreditRequest {
    @Id
    @Column(name = "id_creditrequest")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_creditrequest;


    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id_client")
    private Client client;


//    @NotEmpty(message = "Should not be empty")
//    @Column(name = "name")
//    private String name;

//    @NotEmpty(message = "Should not be empty")
//    @Column(name = "pasport")
//    private String pasport;


//    @NotEmpty(message = "Should not be empty")
//    @Column(name = "adress")
//    private String adress;

//    @NotEmpty(message = "Should not be empty")
//    @Column(name = "phone")
//    private String phone;


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


}