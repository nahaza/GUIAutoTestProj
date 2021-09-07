package pages;

import libs.ActionsWithElements;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;


import static org.hamcrest.CoreMatchers.containsString;

public abstract class ParentPage {
    Logger logger = Logger.getLogger(getClass());
    ActionsWithElements actionsWithElements;
    WebDriver webDriver;
    protected final String baseUrl = actionsWithElements.configProperties.base_url();

    protected ParentPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        actionsWithElements = new ActionsWithElements(webDriver);
        PageFactory.initElements(
                new HtmlElementDecorator(
                        new HtmlElementLocatorFactory(webDriver))
                , this);
    }

    abstract String getRelativeUrl();

    protected void checkUrlWithPattern() {
        Assert.assertThat("Invalid page"
                , webDriver.getCurrentUrl()
                , containsString(baseUrl + getRelativeUrl()));
    }
}
