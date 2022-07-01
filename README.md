# SlotegratorAutotests
Test assignment to apply for a QA Automation Engineer job. Project is written to test some features of the following website: http://test-app.d6.dev.devcaz.com
Sadly I didn't get acces to the DB, so no DB checks(
!!ATTENTION!! Cloned code won't work as I replaced all authentication data with "***", to launch tests they are need to be written back to the config .yml files

# API tests
To launch tests, run the TestSuite class

Tech Stack:
Name | Purpose
--- | --- 
Spring Boot | Dependecy Injections + .yml config files parsing + Downloading JSON-schemas from .json files
JUnit | Making test checks
REST Assured | Requests sending
Lombok | Reducing amount of junk code
Logback | Logging

Short description:
<br>Every request has its own tester. Since tester stores test results info a new tester is created for each test case. Each tester is inherited from abstract RestApiTester, that contains some common methods and fields. RestApiTester contains generic parameters for *Tester (so common methods could return corresponded types) and *ResponseBody (for storing deserialized response body)
Also every request has its own config class (all configs are parsed from single application.yml file)

# UI tests
To launch tests, run the RunCucumberTest class

Tech Stack:
Name | Purpose
--- | --- 
Spring Boot | Dependecy Injections + .yml config files parsing
Selenide | User's actions emulation + Making test checks
Cucumber | BDD
JUnit | Making test checks (where Selenide can't)
Lombok | Reducing amount of junk code
Logback | Logging

Short description:
Gees, that Cucumber is weird
Cucumber is used to describe test cases. Page Objects are used to store methods to interact with different pages. If some UI element needs to be searched by CSS class, then it's written to corresponded enum for easer updating in case of UI changes. All pages have only one config class
