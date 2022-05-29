
import application.AwpApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = AwpApplication.class)
@TestPropertySource(locations = "classpath:app_test.properties")
@AutoConfigureMockMvc
class GraduationProjectApplicationTests {

    @Test
    void contextLoads() {
    }

}
