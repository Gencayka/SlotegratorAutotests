package com.Chayka.pageObjects;

import com.Chayka.TestConfig;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public abstract class SlotegratorPageObject {
    @Autowired
    protected SoftAssertions softAssertions;
    @Autowired
    protected TestConfig testConfig;

    public void finishTest(){
        softAssertions.assertAll();
    }

    /**
     * Launches a delay (haven't found a method like this in Selenide)
     *
     * @param millis delay length in milliseconds
     */
    public void launchDelay(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignore) {
        }
    }

    /**
     * Launches a delay with default length (haven't found a method like this in Selenide)
     */
    public void launchDelay() {
        launchDelay(testConfig.getDefaultDelayLength());
    }
}
