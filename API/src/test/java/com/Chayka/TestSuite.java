package com.Chayka;

import com.Chayka.requests.AuthorizeTests;
import com.Chayka.requests.GetClientTokenTests;
import com.Chayka.requests.GetPlayerProfileTests;
import com.Chayka.requests.RegPlayerTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@SelectClasses({
        GetClientTokenTests.class,
        RegPlayerTests.class,
        AuthorizeTests.class,
        GetPlayerProfileTests.class})
@Suite
public class TestSuite {
}
