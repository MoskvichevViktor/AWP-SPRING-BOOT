package application.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Should not be empty")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Should not be empty")
    @Column(name = "passport")
    private String passport;

    @NotEmpty(message = "Should not be empty")
    @Column(name = "address")
    private String address;

    @NotEmpty(message = "Should not be empty")
    @Column(name = "phone")
    private String phone;

    public Client() {
    }

    public Client(Integer id, String name, String passport, String address, String phone) {
        this.id = id;
        this.name = name;
        this.passport = passport;
        this.address = address;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String pasport) {
        this.passport = pasport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passport='" + passport + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}