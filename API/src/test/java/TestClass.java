import com.Chayka.SpringStarter;
import com.Chayka.requests.authorize.AuthorizeNegativeResponseValues;
import com.Chayka.requests.authorize.AuthorizeTester;
import com.Chayka.requests.getClientToken.GetClientTokenNegativeResponseValues;
import com.Chayka.requests.getClientToken.GetClientTokenTester;
import com.Chayka.requests.registerPlayer.RegPlayerNegativeResponseValues;
import com.Chayka.requests.registerPlayer.RegPlayerTester;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest(classes = SpringStarter.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("API")
public class TestClass {
    @Autowired
    private GetClientTokenTester getClientTokenTester;
    @Autowired
    private RegPlayerTester regPlayerTester;
    @Autowired
    private AuthorizeTester authorizeTester;

    @BeforeEach
    public void beforeEach(@Autowired GetClientTokenTester getClientTokenTester,
                           @Autowired RegPlayerTester regPlayerTester,
                           @Autowired AuthorizeTester authorizeTester) {
        this.getClientTokenTester = getClientTokenTester;
        this.regPlayerTester = regPlayerTester;
        this.authorizeTester = authorizeTester;
    }

    @Test
    public void test() throws IOException {
        getClientTokenTester
                .sendPositiveRequest()
                .checkPositiveResponseHttpCode()
                .checkPositiveResponseValidation()
                .checkPositiveResponseBody()
                .assertAll();
        //Assertions.assertEquals(1,2);
    }

    @Test
    public void negativeTest() {
        getClientTokenTester
                .sendRequest("bebebe")
                .checkNegativeResponseHttpCode(GetClientTokenNegativeResponseValues.CLIENT_AUTHENTICATION_FAILED)
                .checkNegativeResponseValidation()
                .checkNegativeResponseBody(GetClientTokenNegativeResponseValues.CLIENT_AUTHENTICATION_FAILED)
                .assertAll();
        //Assertions.assertEquals(1,2);
    }

    @Disabled
    @DisplayName("Register new player")
    @ParameterizedTest
    @ValueSource(strings = {
            "userAB",
            "userCD",
            "userEF"})
    public void regPlayerTest(String playerUsername) throws IOException {
        regPlayerTester
                .sendPositiveRequest(playerUsername)
                .checkPositiveResponseHttpCode()
                .checkPositiveResponseValidation()
                .assertAll();
    }

    @DisplayName("Register new player")
    @Test
    public void regPlayerTest() throws IOException {
        regPlayerTester
                .sendPositiveRequest("userCDA")
                .checkPositiveResponseHttpCode()
                .checkPositiveResponseValidation()
                .checkPositiveResponseBody()
                .assertAll();
    }

    @DisplayName("Register new player, negative")
    @Test
    public void regPlayerTestNegative() {
        regPlayerTester
                .sendPostRequest("")
                .checkNegativeResponseHttpCode(RegPlayerNegativeResponseValues.UNAUTHORIZED)
                .checkNegativeResponseValidation()
                .checkNegativeResponseBody(RegPlayerNegativeResponseValues.UNAUTHORIZED)
                .assertAll();
    }

    @Test
    public void authorizePositiveTest() throws IOException {
        authorizeTester
                .sendPositiveRequest()
                .checkPositiveResponseHttpCode()
                .checkPositiveResponseValidation()
                .checkPositiveResponseBody()
                .assertAll();
        //Assertions.assertEquals(1,2);
    }

    @Test
    public void authorizeNegativeTest() throws IOException {
        authorizeTester
                .sendRequest("user", "password")
                .checkNegativeResponseHttpCode(AuthorizeNegativeResponseValues.INCORRECT_USER_CREDENTIALS)
                .checkNegativeResponseValidation()
                .checkNegativeResponseBody(AuthorizeNegativeResponseValues.INCORRECT_USER_CREDENTIALS)
                .assertAll();
        //Assertions.assertEquals(1,2);
    }
}
