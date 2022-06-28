import com.Chayka.SpringStarter;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = SpringStarter.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("API")
public class TestClass {
    @Test
    public void test() {
        Assertions.assertEquals(1,2);
    }
}
