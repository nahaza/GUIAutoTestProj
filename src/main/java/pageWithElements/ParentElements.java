package pageWithElements;

import libs.ActionsWithElements;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;


public abstract class ParentElements {
    Logger logger = Logger.getLogger(getClass());
    WebDriver webDriver;
    ActionsWithElements actionsWithElements;

    public ParentElements(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(
                new HtmlElementDecorator(
                        new HtmlElementLocatorFactory(webDriver))
                , this);
        actionsWithElements = new ActionsWithElements(webDriver);
    }

}
