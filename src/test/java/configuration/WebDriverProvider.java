package configuration;

import com.google.inject.Provider;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by n450777 on 30/11/2015.
 */
public class WebDriverProvider implements Provider<WebDriver> {
    WebDriver driver;

    @Override
    public WebDriver get() {
        if(driver != null)
            return driver;

        driver = new FirefoxDriver();
        return driver;
    }
}
