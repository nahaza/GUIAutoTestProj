package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextBlock;

import java.util.*;


public class LessonPage extends ParentPage {

    @FindBy(xpath = ".//button[@aria-label='Profile']")
    private Button buttonProfile;

    @FindBy(xpath = ".//button[text()='Выход']")
    private Button buttonSignOut;

    private String courseNameLinkLocator = ".//a[@title='%s']";

    //    @FindBy(xpath = ".//button[@class='lesson__next-btn button has-icon']")
    @FindBy(xpath = ".//div[@class='lesson__footer-nav-buttons']//span[text()='Следующий шаг']")
    private Button buttonCourseNextStep;

    @FindBy(xpath = ".//a[@class='ember-link ember-view attempt__wrapper_next-link button success']")
    private Button buttonNextStepAfterCorrectAnswerSubmission;

    @FindBy(xpath = ".//button[@class='submit-submission']")
    private Button submitAnswer;

    @FindBy(xpath = ".//span[@class='lesson__step-title']")
    private TextBlock lessonStepOnFooter;

    private String listOfCourseModulesLocator = ".//div[@class='lesson-sidebar__toc-inner']";

    private String listOfCourseLessonsLocator = ".//a[contains(@class, 'ember-view lesson-sidebar__lesson')]";

    private String listOfLessonStepsOnTopBarLocator = ".//div[@class='player-topbar__step-pins']//div[@class='m-step-pin ember-view player__step-pin']";


    private String listOfLessonQuizesOnTopBarLocator = "svg-icon easy-quiz_icon ember-view step-pin-icon__icon";

    private String checkboxOptionsLesson1TestStep4Locator = ".//div[@data-type='choice-quiz']//span[@class='choice-quiz-show__option s-checkbox__label']";

    protected List<WebElement> listOfLessons = webDriver.findElements(By.xpath(listOfCourseLessonsLocator));


    protected LessonPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    String getRelativeUrl() {
        return "/lesson";
    }

    public LessonPage checkIsSignOutButtonPresent() {
        clickOnElement(buttonProfile);
        Assert.assertTrue("SignOut is not present in the Profile menu", isElementPresent(buttonSignOut));
        return this;
    }


    public LessonPage checkIsRedirectToLessonPage() {
        checkUrlWithPattern();
        //checkIsSignOutButtonPresent();
        return this;
    }

    public boolean isCourseNameLinkPresent(String courseName) {
        return isElementPresent(webDriver.findElement(By.xpath(String.format(
                courseNameLinkLocator, courseName))), "courseNameLink");
    }

    public LessonPage checkIsCourseNamePresent(String courseName) {
        Assert.assertTrue("CourseName is not present on the Page"
                , isCourseNameLinkPresent(courseName));
        return this;
    }

    public LessonPage checkIsCourseNextStepPresent() {
        Assert.assertTrue("CourseNextStep is not present on the Page"
                , isElementPresent(buttonCourseNextStep));
        return this;
    }

    public LessonPage finishNoExamCourseWithoutDoingTests() throws InterruptedException {
        for (int i = 0; i < listOfLessons.size(); i++) {
            clickOnElement(listOfLessons.get(i), "lesson " + (i + 1));
            Thread.sleep(2000);
            webDriverWait10.until(ExpectedConditions.urlContains("/step/1"));
            logger.info("Lesson " + listOfLessons.get(i).getText());
            List<WebElement> listOfLessonSteps = webDriver.findElements(By.xpath(listOfLessonStepsOnTopBarLocator));
            for (int j = 0; j < listOfLessonSteps.size(); j++) {
                clickOnElement(listOfLessonSteps.get(j), "lesson " + (i + 1) + ", step " + (j + 1));
                webDriverWait10.until(ExpectedConditions.titleContains("Шаг " + (j + 1)));
            }
        }
        scrollToWebElement(buttonCourseNextStep);
        clickOnElement(buttonCourseNextStep);
        Assert.assertTrue(webDriver.findElement(By.xpath(".//div[@class='modal-popup__container']")).isDisplayed());
        return this;
    }

    public LessonPage countScore() {

        return this;
    }

//    public LessonPage completeLesson1Course95468() throws InterruptedException {
//        if (isCourseNameLinkPresent("АА - Активный Английский от Екатерины Зак (для начинающих А0-А1)")) {
//            //scrollToWebElement(webDriver.findElement(By.partialLinkText("Быстрый старт")));
//            WebElement lesson1Course95468 = webDriver.findElement(By.partialLinkText("Быстрый старт"));
//            clickOnElement(lesson1Course95468);
//            Thread.sleep(2000);
//            webDriverWait10.until(ExpectedConditions.urlContains("/step/1"));
//            Assert.assertEquals("1.1  Быстрый старт", webDriver.findElement(By.xpath(".//div[@class='top-tools__lesson-name']")).getText());
//            List<WebElement> listOfLessonSteps = webDriver.findElements(By.xpath(".//span[@class='svg-icon null_icon ember-view step-pin-icon__icon']"));
//            for (int j = 0; j < listOfLessonSteps.size(); j++) {
//                clickOnElement(listOfLessonSteps.get(j), "unit 1, lesson " + (j + 1));
//                Thread.sleep(1000);
//            }
//            List<WebElement> listOfLessonTests = webDriver.findElements(By.xpath(".//span[@class='svg-icon easy-quiz_icon ember-view step-pin-icon__icon']"));
//            clickOnElement(listOfLessonTests.get(1));
//            Thread.sleep(1000);
//
//        }
//        scrollToWebElement(buttonCourseNextStep);
//        clickOnElement(buttonCourseNextStep);
//        Assert.assertEquals("1.2 Глагол to be", webDriver.findElement(By.xpath(".//div[@class='top-tools__lesson-name']")).getText());
//        return this;
//    }

    public LessonPage doTheTestDragAndDrop() {
        Assert.assertEquals("Шаг 2", lessonStepOnFooter.getText());
        String quizLocator = ".//span[@class='svg-icon easy-quiz_icon ember-view step-pin-icon__icon']";
        String questionLocator = ".//div[@class='dnd-quiz__item matching-quiz__item']";
        String answerLocator = ".//div[@class='drag-and-drop-draggable smooth-dnd-draggable-wrapper ember-view dnd-quiz__item dnd-quiz__has-controls matching-quiz__item']";
        String[] questionsFile = {"I", "You", "He", "She", "It"};
        String[] answersFile = {"Я", "Ты, Вы", "Он", "Она", "Оно, Это"};
        Map<String, String> answerMap = new HashMap<>();
        for (int i = 0; i < questionsFile.length; i++) {
            answerMap.put(questionsFile[i], answersFile[i]);
        }
        for (int i = 0; i < questionsFile.length; i++) {
            String textToBe = answerMap.get(webDriver.findElements(By.xpath(questionLocator)).get(i).getText());
            if (!answerMap.get(webDriver.findElements(By.xpath(questionLocator)).get(i).getText()).equals(webDriver.findElements(By.xpath(answerLocator)).get(i).getText())) {
                String fromLocator =
                        String.format(".//div[@class='smooth-dnd-container vertical drag-and-drop drag-and-replace ember-" +
                                        "view matching-quiz__right matching-quiz__drag-and-replace']//div[.//span[text()='%s']]" +
                                        "//span[@class='svg-icon dragndrop_icon ember-view dnd-quiz__item-handle matching-quiz__handle']"
                                , answerMap.get(webDriver.findElements(By.xpath(questionLocator)).get(i).getText()));
                String toLocator =
                        String.format(".//div[@class='smooth-dnd-container vertical drag-and-drop drag-and-replace ember-view matching-" +
                                        "quiz__right matching-quiz__drag-and-replace']//div[.//span[text()='%s']]//span[@class='svg-" +
                                        "icon dragndrop_icon ember-view dnd-quiz__item-handle matching-quiz__handle']"
                                , webDriver.findElements(By.xpath(answerLocator)).get(i).getText());
                dragAndDropElements(fromLocator, toLocator);
                if (webDriver.findElements(By.xpath(answerLocator)).get(i).getText().equals(textToBe)) {
                    logger.info("Element " + webDriver.findElements(By.xpath(answerLocator)).get(i).getText() + " was dropped");
                } else {
                    logger.error("Element " +
                            answerMap.get(webDriver.findElements(By.xpath(questionLocator)).get(i).getText()) + " was not dropped properly");
                    Assert.fail("Element " +
                            answerMap.get(webDriver.findElements(By.xpath(questionLocator)).get(i).getText()) + " was not dropped properly");
                }
            }
        }
        return this;
    }

    public LessonPage doTheTestcheckBox() {
        Assert.assertEquals("Шаг 4", lessonStepOnFooter.getText());
        String quizLocator = ".//span[@class='svg-icon easy-quiz_icon ember-view step-pin-icon__icon']";
        String checkBoxLocator = ".//label[@class='s-checkbox']";
        List<WebElement> listOfCheckBoxOptions = webDriver.findElements(By.xpath(checkBoxLocator));
        String[] questionsFile = {"Определенный артикль употребляется только с единственным числом"
                , "В английском с абстрактными понятиями нужно употреблять нулевой артикль"
                , "Неопределенный артикль употребляют когда речь идет о неизвестном предмете"
                , "Неопределенный артикль можно ипользовать только со словами, начинающимися с согласного"};
        String[] answersFile = {"unchecked", "checked", "checked", "unchecked"};
        Map<String, String> answerMap = new HashMap<>();
        for (int i = 0; i < questionsFile.length; i++) {
            answerMap.put(questionsFile[i], answersFile[i]);
        }
        for (int i = 0; i < listOfCheckBoxOptions.size(); i++) {
            if (answerMap.containsKey(listOfCheckBoxOptions.get(i).getText())) {
                final List<String> checkboxStatusPossible = Arrays.asList("check", "uncheck");
//                if (checkboxStatusPossible.contains(requiredUniquePostCheckBoxStatus)) {
//                    if (checkboxUniquePost.isSelected()) {
//                        if (requiredUniquePostCheckBoxStatus.equalsIgnoreCase(checkboxStatusPossible.get(0))) {
//                            logger.info("CheckboxUniquePost keeps being selected");
//                        } else {
//                            clickOnElement(checkboxUniquePost);
//                            webDriverWait10.until(ExpectedConditions.elementSelectionStateToBe(checkboxUniquePost, false));
//                            logger.info("CheckboxUniquePost was deselected");
//                        }
//                    } else {
//                        if (requiredUniquePostCheckBoxStatus.equalsIgnoreCase(checkboxStatusPossible.get(0))) {
//                            clickOnElement(checkboxUniquePost);
//                            webDriverWait10.until(ExpectedConditions.elementSelectionStateToBe(checkboxUniquePost, true));
//                            logger.info("CheckboxUniquePost was selected");
//                        } else {
//                            logger.info("CheckboxUniquePost keeps being not selected");
//                        }
//                    }
//                } else {
//                    logger.error("Check required uniquePostCheckBoxStatus. Only check, uncheck are possible");
//                    Assert.fail("Check required uniquePostCheckBoxStatus. Only check, uncheck are possible");
//                }
            }
        }
        return this;
    }

    public LessonPage clickOnStep2() {
        clickOnElement(webDriver.findElement(By.xpath(".//div[@class='m-step-pin ember-view player__step-pin'][2]")));
        return this;
    }

}
