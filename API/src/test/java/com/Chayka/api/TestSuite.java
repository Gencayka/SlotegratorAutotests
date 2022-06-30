package com.Chayka.api;

import com.Chayka.api.requests.AuthorizeTests;
import com.Chayka.api.requests.GetClientTokenTests;
import com.Chayka.api.requests.GetPlayerProfileTests;
import com.Chayka.api.requests.RegPlayerTests;
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
