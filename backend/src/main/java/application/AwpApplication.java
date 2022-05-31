package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableJpaAuditing
public class AwpApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwpApplication.class, args);
        System.out.println("See OpenAPI definition  http://localhost:8080/swagger-ui/index.html#/");
        System.out.println("In test mode, see data console from url: http://localhost:8080/h2-console/");
    }

}
