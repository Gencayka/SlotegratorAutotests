import com.Chayka.SpringStarter;
import com.Chayka.requests.getClientToken.GetClientTokenNegativeResponseValues;
import com.Chayka.requests.getClientToken.GetClientTokenTester;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = SpringStarter.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("API")
public class TestClass {
    @Autowired
    private GetClientTokenTester getClientTokenTester;

    @BeforeEach
    public void beforeEach(@Autowired GetClientTokenTester getClientTokenTester) {
        this.getClientTokenTester = getClientTokenTester;
    }

    @Test
    public void test() {
        getClientTokenTester
                .sendPositiveRequest()
                .checkPositiveResponseHttpCode()
                .checkPositiveResponseValidation()
                .checkPositiveResponseBody()
                .assertAll();
        Assertions.assertEquals(1,2);
    }

    @Test
    public void negativeTest() {
        getClientTokenTester
                .sendRequest("bebebe")
                .checkNegativeResponseHttpCode(GetClientTokenNegativeResponseValues.CLIENT_AUTHENTICATION_FAILED)
                .checkNegativeResponseValidation()
                .checkNegativeResponseBody(GetClientTokenNegativeResponseValues.CLIENT_AUTHENTICATION_FAILED)
                .assertAll();
        Assertions.assertEquals(1,2);
    }

    @Test
    public void test2() {
        String ff = getClientTokenTester.getToken();
        Assertions.assertEquals(1,2);
    }
}
