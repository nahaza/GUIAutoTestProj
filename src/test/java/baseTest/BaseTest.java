package baseTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import libs.ConfigProperties;
import libs.TestData;
import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import pageWithElements.HeaderMenu;
import pages.HomePage;
import pages.LoginPage;

import java.util.concurrent.TimeUnit;


public class BaseTest {
    WebDriver webDriver;
    protected LoginPage loginPage;
    protected HomePage homePage;
    protected TestData testData;
    protected HeaderMenu headerMenu;
    protected Logger logger = Logger.getLogger(getClass());
    protected static ConfigProperties configProperties = ConfigFactory.create(ConfigProperties.class);

    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() {
        logger.info("----------" + testName.getMethodName() + " was started-------");
        webDriver = initDriver();
        webDriver.manage().timeouts().implicitlyWait(configProperties.TIME_FOR_DFFAULT_WAIT(), TimeUnit.SECONDS);
        webDriver.manage().window().maximize();

        loginPage = new LoginPage(webDriver);
        homePage = new HomePage(webDriver);
        testData = new TestData();
        headerMenu = new HeaderMenu(webDriver);

    }

    @After
    public void tearDown() {
        webDriver.manage().deleteAllCookies();
        webDriver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        webDriver.quit();
        logger.info("----------" + testName.getMethodName() + " was finished-------");
    }



    private WebDriver initDriver() {
        String browser = System.getProperty("browser");
        if ((browser==null) || browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            webDriver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            webDriver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("ie")){
            WebDriverManager.iedriver().arch32().setup();
            return new InternetExplorerDriver();
        }
        return webDriver;
    }
}
