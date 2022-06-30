package com.Chayka;

import com.Chayka.commons.ApiTester;
import com.Chayka.commons.RestApiTester;
import com.Chayka.requests.getClientToken.GetClientTokenTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SpringStarter.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ApiTests <T extends RestApiTester> {
    protected T tester;

    @BeforeEach
    public void beforeEach(@Autowired T tester) {
        this.tester = tester;
    }
}
