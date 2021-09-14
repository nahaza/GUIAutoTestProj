package pages;

import libs.ExcelDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageWithElements.HeaderMenu;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.Image;
import ru.yandex.qatools.htmlelements.element.TextBlock;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class LessonPage extends ParentPage {

    @FindBy(xpath = ".//div[@class='lesson__footer-nav-buttons']")
    private Button buttonCourseNextStep;

    @FindBy(xpath = ".//a[@class='ember-link ember-view attempt__wrapper_next-link button success']")
    private Button buttonNextStepAfterCorrectAnswerSubmission;

    @FindBy(xpath = ".//button[@class='submit-submission']")
    private Button buttonSubmitAnswer;

    @FindBy(xpath = ".//span[@class='lesson__step-title']")
    private TextBlock lessonStepOnFooter;

    @FindBy(xpath = ".//div[@class='attempt__message']//span")
    private TextBlock testResultMessage;

    @FindBy(xpath = ".//span[@class='attempt-message_wrong']")
    private TextBlock testNegativeResultMessage;

    @FindBy(xpath = ".//span[@class='svg-icon wrong_icon ember-view attempt-wrapper__result-icon']")
    private Image testNegativeResultIcon;

    @FindBy(xpath = ".//span[@class='attempt-message_correct']")
    private TextBlock testPositiveResultMessage;

    @FindBy(xpath = ".//button[@class='again-btn white']")
    private Button buttonStartTestAgainAfterSuccess;

    @FindBy(xpath = ".//button[@class='again-btn success']")
    private Button buttonStartAgainAfterFailure;

    @FindBy(xpath = ".//div[@class='attempt__score-info']")
    private TextBlock scoreInfoBlock;

    @FindBy(xpath = ".//div[@class='attempt__score-info']//strong")
    private TextBlock scoreGetInTestFooter;

    @FindBy(xpath = ".//div[@class='top-tools__progress'][2]//span")
    private TextBlock scoreGetInTestHeader;

    @FindBy(xpath = ".//div[@class='modal-popup__container']")
    private Button finishModalPopup;

    @FindBy(xpath = ".//button[@class='modal-popup__close']")
    private Button closeFinishModalPopup;

    private String courseNameLinkLocator = ".//a[@title='%s']";

    private String listOfCourseModulesLocator = ".//div[@class='lesson-sidebar__toc-inner']";

    private String listOfCourseLessonsLocator = ".//a[contains(@class, 'ember-view lesson-sidebar__lesson')]";

    private String listOfLessonStepsOnTopBarLocator = ".//div[@class='player-topbar__step-pins']//div[@class='m-step-pin ember-view player__step-pin']";


    private String listOfLessonQuizesOnTopBarLocator = ".//div[@class='m-step-pin ember-view player__step-pin'][.//span[@class='svg-icon easy-quiz_" +
            "icon ember-view step-pin-icon__icon']]";

    private String checkboxOptionsLesson1TestStep4Locator = ".//div[@data-type='choice-quiz']//span[@class='choice-quiz-show__option s-checkbox__label']";

    private String questionLocatorDragAndDrop = ".//div[@class='dnd-quiz__item matching-quiz__item']";

    private String answerLocatorDragAndDrop = ".//div[@class='drag-and-drop-draggable smooth-dnd-draggable-wrapper ember-view dnd-quiz__item dnd-quiz__has-" +
            "controls matching-quiz__item']";

    private String answerLocatorDragAndDropSort = ".//div[@class='dnd-quiz__item dnd-quiz__has-controls sorting-quiz__item']";

    private String doAnswerLocatorDragAndDrop = ".//div[@class='smooth-dnd-container vertical drag-and-drop drag-and-replace ember-view matching-quiz__" +
            "right matching-quiz__drag-and-replace']//div[.//span[contains(text(), " + "\"%s\"" + ")]]//span[@class='svg-icon dragndrop_icon ember-view dnd-quiz__item-" +
            "handle matching-quiz__handle']";

    private String doAnswerLocatorDragAndDropSort = ".//div[@class='smooth-dnd-container vertical drag-and-drop ember-view sorting-quiz__drag-and-drop']//" +
            "div[.//span[contains(text(), '%s')]]//span[@class='svg-icon dragndrop_icon ember-view dnd-quiz__item-handle sorting-quiz__handle']";

    private String optionsLocatorCheckbox = ".//span[@class='choice-quiz-show__option s-checkbox__label']";

    private String doAnswerLocatorCheckbox = ".//div[@data-type='choice-quiz']//label[@class='s-checkbox'][.//span[contains(text(),'%s')]]//span[@class='s-" +
            "checkbox__border']";

    private String questionLocatorInput = ".//span[@class='fill-blanks-quiz__text']";

    private String answerLocatorInput = ".//input[@class='st-input fill-blanks-quiz__input']";

    private String questionLocatorRadiobutton = ".//table[@class='table-quiz__table']//tbody//td[@data-katex]";

    private String answerLocatorRadiobutton = ".//table[@class='table-quiz__table']//th[@data-katex]";

    private String answerLocatorRadiobuttonOneOption = ".//span[@class='choice-quiz-show__option s-radio__label']";

    private String doAnswerLocatorRadiobutton = ".//table[@class='table-quiz__table']//tr[.//td[contains(text(), " + "\"%s\"" + ")]]/td";

    private String doAnswerLocatorRadiobuttonOneOption = ".//div[@data-type='choice-quiz']//label[@class='s-radio'][.//span[contains(text(), " + "\"%s\"" + ")]]//span[@class='s-radio__border']";

    private String answerLocatorSingleInput = ".//html[@dir='ltr']";

    protected List<WebElement> listOfLessons = webDriver.findElements(By.xpath(listOfCourseLessonsLocator));


    protected LessonPage(WebDriver webDriver) {
        super(webDriver);
    }

    public HeaderMenu headerMenu = new HeaderMenu(webDriver);

    @Override
    String getRelativeUrl() {
        return "/lesson";
    }

    public LessonPage checkIsRedirectToLessonPage() {
        checkUrlWithPattern();
        return this;
    }

    public boolean isCourseNameLinkPresent(String courseName) {
        return actionsWithElements.isElementPresent(webDriver.findElement(By.xpath(String.format(
                courseNameLinkLocator, courseName))), "courseNameLink");
    }

    public LessonPage checkIsCourseNamePresent(String courseName) {
        Assert.assertTrue("CourseName is not present on the Page"
                , isCourseNameLinkPresent(courseName));
        return this;
    }

    public LessonPage checkIsCourseNextStepPresent() {
        Assert.assertTrue("CourseNextStep is not present on the Page"
                , actionsWithElements.isElementPresent(buttonCourseNextStep));
        return this;
    }

    public LessonPage finishNoExamCourseWithoutDoingTests() throws InterruptedException {
        for (int i = 0; i < listOfLessons.size(); i++) {
            actionsWithElements.clickOnElement(listOfLessons.get(i), "lesson " + (i + 1));
            Thread.sleep(2000);
            actionsWithElements.webDriverWait15.until(ExpectedConditions.urlContains("/step/1"));
            logger.info("Lesson " + listOfLessons.get(i).getText());
            List<WebElement> listOfLessonSteps = webDriver.findElements(By.xpath(listOfLessonStepsOnTopBarLocator));
            for (int j = 0; j < listOfLessonSteps.size(); j++) {
                actionsWithElements.clickOnElement(listOfLessonSteps.get(j), "lesson " + (i + 1) + ", step " + (j + 1));
                actionsWithElements.webDriverWait15.until(ExpectedConditions.titleContains("Шаг " + (j + 1)));
            }
        }
        actionsWithElements.scrollToWebElement(buttonCourseNextStep);
        actionsWithElements.clickOnElement(buttonCourseNextStep);
        Assert.assertTrue("Modal popup is not displayed", actionsWithElements.isElementPresent(finishModalPopup));
        logger.info("Finish course modal popup is displayed");
        actionsWithElements.clickOnElement(closeFinishModalPopup);
        return this;
    }

    public LessonPage countScore() {
        Integer scoreBefore = Integer.parseInt(scoreGetInTestHeader.getText());
        return this;
    }


    public LessonPage finishNoExamCourseWithDoingTests(String specificCourseTitle) throws InterruptedException, IOException {
        Map<String, String> listFoCoursesId = ExcelDriver.getData(actionsWithElements.configProperties.DATA_FILE_COURSES(), "coursesId");
        String courseId = listFoCoursesId.get(specificCourseTitle);
        for (int i = 0; i < listOfLessons.size(); i++) {
            actionsWithElements.clickOnElement(listOfLessons.get(i), "lesson " + (i + 1));
            Thread.sleep(2000);
            actionsWithElements.webDriverWait15.until(ExpectedConditions.urlContains("/step/1"));
            logger.info("Lesson " + listOfLessons.get(i).getText());
            List<WebElement> listOfLessonSteps = webDriver.findElements(By.xpath(listOfLessonStepsOnTopBarLocator));
            ArrayList<Integer> numberOfQuizStep = new ArrayList<>();
            List<WebElement> listOfLessonQuizes = webDriver.findElements(By.xpath(listOfLessonQuizesOnTopBarLocator));
            for (int q = 0; q < listOfLessonQuizes.size(); q++) {
                numberOfQuizStep.add(Integer.parseInt(listOfLessonQuizes.get(q).getAttribute("data-step-position")));
            }
            for (int j = 0; j < listOfLessonSteps.size(); j++) {
                actionsWithElements.clickOnElement(listOfLessonSteps.get(j), "lesson " + (i + 1) + ", step " + (j + 1));
                if (numberOfQuizStep.contains(j + 1)) {
                    doTheTests(courseId, i + 1, j + 1);
                }
                actionsWithElements.webDriverWait15.until(ExpectedConditions.titleContains("Шаг " + (j + 1)));
            }
        }
        actionsWithElements.scrollToWebElement(buttonCourseNextStep);
        actionsWithElements.clickOnElement(buttonCourseNextStep);
        Assert.assertTrue("Modal popup is not displayed", actionsWithElements.isElementPresent(finishModalPopup));
        logger.info("Finish course modal popup is displayed");
        actionsWithElements.clickOnElement(closeFinishModalPopup);
        return this;
    }

    public Map<String, String> getMapOfAnswersFromFile(Map<String, String> dataForTests) {
        String questionsFromFile = dataForTests.get("questions");
        String answersFromFile = dataForTests.get("answers");
        String[] questionsListFromFile = questionsFromFile.split(";");
        String[] answersListFromFile = answersFromFile.split(";");
        Map<String, String> answerMap = new HashMap<>();
        for (int i = 0; i < questionsListFromFile.length; i++) {
            answerMap.put(questionsListFromFile[i], answersListFromFile[i]);
        }
        return answerMap;
    }

    public LessonPage doTheTests(String idCourse, int lesson, int step) throws IOException, InterruptedException {
        Map<String, String> dataForTests = ExcelDriver.getData(
                actionsWithElements.configProperties.DATA_FILE_PATH() + idCourse + "testData.xls"
                , String.format("lesson%s_step%s", lesson, step));
        String actionToDoTheTest = dataForTests.get("action");
        if (actionToDoTheTest.equals("dragAndDrop")) {
            doTheTestDragAndDrop(dataForTests, step);
        } else if (actionToDoTheTest.equals("checkbox")) {
            doTheTestCheckbox(dataForTests, step);
        } else if (actionToDoTheTest.equals("input")) {
            doTheTestInput(dataForTests, step);
        } else if (actionToDoTheTest.equals("radiobutton")) {
            doTheTestRadiobutton(dataForTests, step);
        } else if (actionToDoTheTest.equals("radiobuttonOneOption")) {
            doTheRadiobuttonOneOption(dataForTests, step);
        } else if (actionToDoTheTest.equals("inputSingleInput")) {
            doTheTestSingleInput(dataForTests, step);
        } else if (actionToDoTheTest.equals("dragAndDropSort")) {
            doTheTestDragAndDropSort(dataForTests, step);
        } else if (actionToDoTheTest.equals("inputInTextArea")) {
            doTheTestInputInTextArea(dataForTests, step);
        }
        actionsWithElements.clickOnElement(buttonSubmitAnswer);
        actionsWithElements.webDriverWait5.until(ExpectedConditions.visibilityOf(testResultMessage));
        Assert.assertTrue(actionsWithElements.isElementPresent(testResultMessage, "Test result message "));
        return this;
    }

    public LessonPage doTheTestDragAndDrop(Map<String, String> dataForTests, int step) throws IOException, InterruptedException {
        Assert.assertEquals(String.format("Шаг %s", step), lessonStepOnFooter.getText());
        Map<String, String> answerMap = getMapOfAnswersFromFile(dataForTests);
        List<WebElement> listOfQuestionsOnThePage = webDriver.findElements(By.xpath(questionLocatorDragAndDrop));
        for (int i = 0; i < listOfQuestionsOnThePage.size(); i++) {
            String answerToBe = answerMap.get(webDriver.findElements(By.xpath(questionLocatorDragAndDrop)).get(i).getText());
            if (!webDriver.findElements(By.xpath(answerLocatorDragAndDrop)).get(i).getText()
                    .contains(answerMap.get(webDriver.findElements(By.xpath(questionLocatorDragAndDrop)).get(i).getText()))) {
                String fromLocator =
                        String.format(doAnswerLocatorDragAndDrop, answerMap.get(webDriver.findElements(By.xpath(questionLocatorDragAndDrop)).get(i).getText()));
                actionsWithElements.dragAndDropElements(webDriver.findElement(By.xpath(fromLocator))
                        , webDriver.findElements(By.xpath(answerLocatorDragAndDrop)).get(i));
                Thread.sleep(3000);
                if (webDriver.findElements(By.xpath(answerLocatorDragAndDrop)).get(i).getText().contains(answerToBe)) {
                    logger.info("Element " + webDriver.findElements(By.xpath(answerLocatorDragAndDrop)).get(i).getText() + " was dropped");
                } else {
                    logger.error("Element " +
                            answerMap.get(webDriver.findElements(By.xpath(questionLocatorDragAndDrop)).get(i).getText()) + " was not dropped properly");
                    Assert.fail("Element " +
                            answerMap.get(webDriver.findElements(By.xpath(questionLocatorDragAndDrop)).get(i).getText()) + " was not dropped properly");
                }
            }
        }
        return this;
    }

    public LessonPage doTheTestDragAndDropSort(Map<String, String> dataForTests, int step) throws IOException, InterruptedException {
        Assert.assertEquals(String.format("Шаг %s", step), lessonStepOnFooter.getText());
        String answersFromFile = dataForTests.get("answers");
        String[] answersListFromFile = answersFromFile.split(";");
        List<WebElement> listOfAnswersOnThePage = webDriver.findElements(By.xpath(answerLocatorDragAndDropSort));
        for (int i = 0; i < listOfAnswersOnThePage.size(); i++) {
            String answerToBe = answersListFromFile[i];
            if (!webDriver.findElements(By.xpath(answerLocatorDragAndDropSort)).get(i).getText()
                    .contains(answerToBe)) {
                String fromLocator =
                        String.format(doAnswerLocatorDragAndDropSort, answerToBe);
                actionsWithElements.dragAndDropElements(webDriver.findElement(By.xpath(fromLocator))
                        , webDriver.findElements(By.xpath(answerLocatorDragAndDropSort)).get(i));
                Thread.sleep(3000);
                if (webDriver.findElements(By.xpath(answerLocatorDragAndDropSort)).get(i).getText().contains(answerToBe)) {
                    logger.info("Element " + webDriver.findElements(By.xpath(answerLocatorDragAndDropSort)).get(i).getText() + " was dropped");
                } else {
                    logger.error("Element " +
                            answerToBe + " was not dropped properly");
                    Assert.fail("Element " +
                            answerToBe + " was not dropped properly");
                }
            }
        }
        actionsWithElements.webDriverWait15.until(ExpectedConditions.visibilityOf(buttonSubmitAnswer));
        return this;
    }

    public LessonPage doTheTestCheckbox(Map<String, String> dataForTests, int step) {
        Assert.assertEquals(String.format("Шаг %s", step), lessonStepOnFooter.getText());
        String answersFromFile = dataForTests.get("answers");
        String[] answersListFromFile = answersFromFile.split(";");
        List<WebElement> listOfCheckBoxOptions = webDriver.findElements(By.xpath(optionsLocatorCheckbox));
        for (int i = 0; i < listOfCheckBoxOptions.size(); i++) {
            for (int j = 0; j < answersListFromFile.length; j++) {
                if (listOfCheckBoxOptions.get(i).getText().contains(answersListFromFile[j])) {
                    actionsWithElements.clickOnElement(webDriver.findElement(By.xpath(String.format(doAnswerLocatorCheckbox
                            , answersListFromFile[j])))
                            , "Checkbox option " + listOfCheckBoxOptions.get(i).getText());
                }
            }
        }
        actionsWithElements.webDriverWait15.until(ExpectedConditions.visibilityOf(buttonSubmitAnswer));
        return this;
    }

    public LessonPage doTheTestSingleInput(Map<String, String> dataForTests, int step) throws IOException {
        actionsWithElements.webDriverWait15.until(ExpectedConditions.textToBe(By.xpath(".//span[@class='lesson__step-title']"), String.format("Шаг %s", step)));
        Assert.assertEquals(String.format("Шаг %s", step), lessonStepOnFooter.getText());
        String answersFromFile = dataForTests.get("answers");
        WebElement frame = webDriver.findElement(By.xpath(".//iframe"));
        webDriver.switchTo().frame(frame);
        WebElement body = webDriver.findElement(By.tagName("body"));
        body.sendKeys(answersFromFile);
        webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        webDriver.switchTo().defaultContent();
        actionsWithElements.webDriverWait15.until(ExpectedConditions.visibilityOf(buttonSubmitAnswer));
        return this;
    }

    public LessonPage doTheTestInputInTextArea(Map<String, String> dataForTests, int step) throws IOException {
        actionsWithElements.webDriverWait15.until(ExpectedConditions.textToBe(By.xpath(".//span[@class='lesson__step-title']"), String.format("Шаг %s", step)));
        Assert.assertEquals(String.format("Шаг %s", step), lessonStepOnFooter.getText());
        String answersFromFile = dataForTests.get("answers");
        WebElement textArea = webDriver.findElement(By.tagName("textarea"));
        actionsWithElements.enterTextToElement(textArea, answersFromFile, "textArea");
        actionsWithElements.webDriverWait15.until(ExpectedConditions.visibilityOf(buttonSubmitAnswer));
        return this;
    }


    public LessonPage doTheTestInput(Map<String, String> dataForTests, int step) throws IOException {
        Assert.assertEquals(String.format("Шаг %s", step), lessonStepOnFooter.getText());
        Map<String, String> answerMap = getMapOfAnswersFromFile(dataForTests);
        List<WebElement> listOfAnswersOnThePage = webDriver.findElements(By.xpath(questionLocatorInput));
        for (int i = 0; i < listOfAnswersOnThePage.size(); i++) {
            String textToEnter = answerMap.get(listOfAnswersOnThePage.get(i).getText());
            actionsWithElements.enterTextToElement(webDriver.findElements(By.xpath(answerLocatorInput)).get(i)
                    , textToEnter, " input " + listOfAnswersOnThePage.get(i).getText());
        }
        actionsWithElements.webDriverWait15.until(ExpectedConditions.visibilityOf(buttonSubmitAnswer));
        return this;
    }

    public LessonPage doTheTestRadiobutton(Map<String, String> dataForTests, int step) throws IOException, InterruptedException {
        Assert.assertEquals(String.format("Шаг %s", step), lessonStepOnFooter.getText());
        List<WebElement> listOfQuestionsOnThePage = webDriver.findElements(By.xpath(questionLocatorRadiobutton));
        List<WebElement> listOfAnswerOptions = webDriver.findElements(By.xpath(answerLocatorRadiobutton));
        Map<String, String> answerMap = getMapOfAnswersFromFile(dataForTests);
        for (int i = 0; i < listOfQuestionsOnThePage.size(); i++) {
            for (int j = 1; j < listOfAnswerOptions.size(); j++) {// options[0] is not relevant answer
                String questionsIs = listOfQuestionsOnThePage.get(i).getText();
                Assert.assertTrue(answerMap.containsKey(questionsIs));
                String answerToBe = answerMap.get(questionsIs);
                if (listOfAnswerOptions.get(j).getText().contains(answerToBe)) {
                    actionsWithElements.clickOnElement(webDriver.findElements(By.xpath(String.format(doAnswerLocatorRadiobutton, questionsIs))).get(j),
                            "Radiobutton " + answerToBe);
                    actionsWithElements.webDriverWait5.until(ExpectedConditions.visibilityOf(buttonSubmitAnswer));
                }
            }
        }
        actionsWithElements.webDriverWait5.until(ExpectedConditions.visibilityOf(buttonSubmitAnswer));
        return this;
    }

    public LessonPage doTheRadiobuttonOneOption(Map<String, String> dataForTests, int step) {
        Assert.assertEquals(String.format("Шаг %s", step), lessonStepOnFooter.getText());
        String answersFromFile = dataForTests.get("answers");
        List<WebElement> listOfAnswerOptions = webDriver.findElements(By.xpath(answerLocatorRadiobuttonOneOption));
        for (int i = 0; i < listOfAnswerOptions.size(); i++) {
            if (listOfAnswerOptions.get(i).getText().contains(answersFromFile)) {
                actionsWithElements.clickOnElement(webDriver.findElement(By.xpath(String.format(doAnswerLocatorRadiobuttonOneOption
                        , answersFromFile)))
                        , "Radiobutton one option " + answersFromFile);
                webDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            }
        }
        return this;
    }

    //work in progress
    public LessonPage doTestsForScoreCounting(String specificCourseTitle) throws InterruptedException, IOException {
        Map<String, String> listFoCoursesId = ExcelDriver.getData(actionsWithElements.configProperties.DATA_FILE_COURSES(), "coursesId");
        String courseId = listFoCoursesId.get(specificCourseTitle);
        for (int i = 0; i < listOfLessons.size(); i++) {
            actionsWithElements.clickOnElement(listOfLessons.get(i), "lesson " + (i + 1));
            Thread.sleep(2000);
            actionsWithElements.webDriverWait15.until(ExpectedConditions.urlContains("/step/1"));
            logger.info("Lesson " + listOfLessons.get(i).getText());
            List<WebElement> listOfLessonSteps = webDriver.findElements(By.xpath(listOfLessonStepsOnTopBarLocator));
            ArrayList<Integer> numberOfQuizStep = new ArrayList<>();
            List<WebElement> listOfLessonQuizes = webDriver.findElements(By.xpath(listOfLessonQuizesOnTopBarLocator));
            for (int q = 0; q < listOfLessonQuizes.size(); q++) {
                numberOfQuizStep.add(Integer.parseInt(listOfLessonQuizes.get(q).getAttribute("data-step-position")));
            }
            for (int j = 0; j < listOfLessonSteps.size(); j++) {
                actionsWithElements.clickOnElement(listOfLessonSteps.get(j), "lesson " + (i + 1) + ", step " + (j + 1));
                if (numberOfQuizStep.contains(j + 1)) {
                    doTheTests(courseId, i + 1, j + 1);
                }
                actionsWithElements.webDriverWait15.until(ExpectedConditions.titleContains("Шаг " + (j + 1)));
            }
        }
        actionsWithElements.scrollToWebElement(buttonCourseNextStep);
        actionsWithElements.clickOnElement(buttonCourseNextStep);
        Assert.assertTrue("Modal popup is not displayed", actionsWithElements.isElementPresent(finishModalPopup));
        logger.info("Finish course modal popup is displayed");
        actionsWithElements.clickOnElement(closeFinishModalPopup);
        return this;
    }
}
