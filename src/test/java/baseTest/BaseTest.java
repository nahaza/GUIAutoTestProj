package baseTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LandingPage;

import java.util.concurrent.TimeUnit;

public class BaseTest {
    WebDriver webDriver;
    protected LandingPage landingPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.manage().window().maximize();

        landingPage = new LandingPage(webDriver);

    }

    @After
    public void tearDown() {
        webDriver.manage().deleteAllCookies();
        webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        webDriver.quit();
    }
}
