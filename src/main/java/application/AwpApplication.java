package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AwpApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwpApplication.class, args);
        System.out.println("See OpenAPI definition  http://localhost:8080/awp/swagger-ui/index.html#/");
    }

}
