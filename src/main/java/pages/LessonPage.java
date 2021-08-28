package pages;

import libs.ExcelDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextBlock;

import java.io.IOException;
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


    private String listOfLessonQuizesOnTopBarLocator = ".//div[@class='m-step-pin ember-view player__step-pin'][.//span[@class='svg-icon easy-quiz_icon ember-view step-pin-icon__icon']]";

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


    public LessonPage finishNoExamCourseWithDoingTests(String specificCourseTitle) throws InterruptedException, IOException {
        Map<String, String> listFoCoursesId = ExcelDriver.getData(ParentPage.configProperties.DATA_FILE_COURSES(), "coursesId");
        String courseId = listFoCoursesId.get(specificCourseTitle);
        for (int i = 0; i < 1; i++) {
            clickOnElement(listOfLessons.get(i), "lesson " + (i + 1));
            Thread.sleep(2000);
            webDriverWait10.until(ExpectedConditions.urlContains("/step/1"));
            logger.info("Lesson " + listOfLessons.get(i).getText());
            List<WebElement> listOfLessonSteps = webDriver.findElements(By.xpath(listOfLessonStepsOnTopBarLocator));
            ArrayList<Integer> numberOfQuizStep = new ArrayList<>();
            List<WebElement> listOfLessonQuizes = webDriver.findElements(By.xpath(listOfLessonQuizesOnTopBarLocator));
            for (int q = 0; q < listOfLessonQuizes.size(); q++) {
                numberOfQuizStep.add(Integer.parseInt(listOfLessonQuizes.get(q).getAttribute("data-step-position")));
            }
            System.out.println(numberOfQuizStep);
            for (int j = 0; j < listOfLessonSteps.size(); j++) {
                clickOnElement(listOfLessonSteps.get(j), "lesson " + (i + 1) + ", step " + (j + 1));
                if (numberOfQuizStep.contains(j + 1)) {
                    doTheTests(courseId, i + 1, j + 1);
                }
                webDriverWait10.until(ExpectedConditions.titleContains("Шаг " + (j + 1)));
            }
        }
        scrollToWebElement(buttonCourseNextStep);
        clickOnElement(buttonCourseNextStep);
        return this;
//        //Assert.assertTrue(webDriver.findElement(By.xpath(".//div[@class='modal-popup__container']")).isDisplayed());
    }

    public LessonPage doTheTestDragAndDrop(Map<String, String> dataForTests, int step) throws IOException {
        Assert.assertEquals(String.format("Шаг %s", step), lessonStepOnFooter.getText());
        String questionLocator = dataForTests.get("questionLocator");
        String answerLocator = dataForTests.get("answerLocator");
        String questionsFromFile = dataForTests.get("questions");
        String answersFromFile = dataForTests.get("answers");
        String[] questionsListFromFile = questionsFromFile.split(";");
        String[] answersListFromFile = answersFromFile.split(";");
        String doAnswerLocator = dataForTests.get("doAnswerLocator");
        Map<String, String> answerMap = new HashMap<>();
        for (int i = 0; i < questionsListFromFile.length; i++) {
            answerMap.put(questionsListFromFile[i], answersListFromFile[i]);
        }
        for (int i = 0; i < questionsListFromFile.length; i++) {
            String textToBe = answerMap.get(webDriver.findElements(By.xpath(questionLocator)).get(i).getText());
            if (!answerMap.get(webDriver.findElements(By.xpath(questionLocator)).get(i).getText())
                    .equals(webDriver.findElements(By.xpath(answerLocator)).get(i).getText())) {
                String fromLocator =
                        String.format(doAnswerLocator, answerMap.get(webDriver.findElements(By.xpath(questionLocator)).get(i).getText()));
                String toLocator =
                        String.format(doAnswerLocator, webDriver.findElements(By.xpath(answerLocator)).get(i).getText());
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

    public LessonPage doTheTests(String idCourse, int lesson, int step) throws IOException {
        Map<String, String> dataForTests = ExcelDriver.getData(
                ParentPage.configProperties.DATA_FILE_PATH() + idCourse + "testData.xls"
                , String.format("lesson%s_step%s", lesson, step));
        String actionToDoTheTest = dataForTests.get("action");
        if (actionToDoTheTest.equals("dragAndDrop")) {
            doTheTestDragAndDrop(dataForTests, step);
        } else if (actionToDoTheTest.equals("checkbox")) {
            doTheTestCheckbox(dataForTests, step);
        } else if (actionToDoTheTest.equals("input")) {
            doTheInput(dataForTests, step);
        } else if (actionToDoTheTest.equals("radiobutton")) {
            doTheRadiobutton(dataForTests, step);
        }
        return this;
    }

    public LessonPage doTheTestCheckbox(Map<String, String> dataForTests, int step) {
        Assert.assertEquals(String.format("Шаг %s", step), lessonStepOnFooter.getText());
        String optionsLocator = dataForTests.get("optionsLocator");
        String doAnswerLocator = dataForTests.get("doAnswerLocator");
        String answersFromFile = dataForTests.get("answers");
        String[] answersListFromFile = answersFromFile.split(";");
        List<WebElement> listOfCheckBoxOptions = webDriver.findElements(By.xpath(optionsLocator));
        for (int i = 0; i < listOfCheckBoxOptions.size(); i++) {
            for (int j = 0; j < answersListFromFile.length; j++) {
                if (listOfCheckBoxOptions.get(i).getText().contains(answersListFromFile[j])) {
                    clickOnElement(webDriver.findElement(By.xpath(String.format(doAnswerLocator
                            , listOfCheckBoxOptions.get(i).getText())))
                            , "Checkbox option " + listOfCheckBoxOptions.get(i).getText());
                }
            }
        }
        return this;
    }

    public LessonPage doTheInput(Map<String, String> dataForTests, int step) throws IOException {
        Assert.assertEquals(String.format("Шаг %s", step), lessonStepOnFooter.getText());
        String questionLocator = dataForTests.get("questionLocator");
        String answerLocator = dataForTests.get("answerLocator");
        String questionsFromFile = dataForTests.get("questions");
        String answersFromFile = dataForTests.get("answers");
        String[] questionsListFromFile = questionsFromFile.split(";");
        String[] answersListFromFile = answersFromFile.split(";");
        Map<String, String> answerMap = new HashMap<>();
        for (int i = 0; i < questionsListFromFile.length; i++) {
            answerMap.put(questionsListFromFile[i], answersListFromFile[i]);
        }
        for (int i = 0; i < questionsListFromFile.length; i++) {
            String textToEnter = answerMap.get(webDriver.findElements(By.xpath(questionLocator)).get(i).getText());
            enterTextToElement(webDriver.findElements(By.xpath(answerLocator)).get(i)
                    , textToEnter, " input " + webDriver.findElements(By.xpath(questionLocator)).get(i).getText());

        }
        return this;
    }

    public LessonPage doTheRadiobutton(Map<String, String> dataForTests, int step) throws IOException {
//        Assert.assertEquals(String.format("Шаг %s", step), lessonStepOnFooter.getText());
//        String questionLocator = dataForTests.get("questionLocator");
//        String answerLocator = dataForTests.get("answerLocator");
//        String questionsFromFile = dataForTests.get("questions");
//        String answersFromFile = dataForTests.get("answers");
//        String[] questionsListFromFile = questionsFromFile.split(";");
//        String[] answersListFromFile = answersFromFile.split(";");
//        Map<String, String> answerMap = new HashMap<>();
//        for (int i = 0; i < questionsListFromFile.length; i++) {
//            answerMap.put(questionsListFromFile[i], answersListFromFile[i]);
//        }
//        for (int i = 0; i < questionsListFromFile.length; i++) {
//            String textToBe = answerMap.get(webDriver.findElements(By.xpath(questionLocator)).get(i).getText());
//            enterTextToElement(webDriver.findElements(By.xpath(answerLocator)).get(i)
//                    , textToBe, " input " + webDriver.findElements(By.xpath(questionLocator)).get(i).getText());
//
//        }
        return this;
    }


}
