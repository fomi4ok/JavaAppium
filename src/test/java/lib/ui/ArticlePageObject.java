package lib.ui;


import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.WebElement;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class ArticlePageObject extends MainPageObject {

  public ArticlePageObject(RemoteWebDriver driver) {

    super(driver);

  }

  protected static  String
  TITLE,
  FOOTER_ELEMENT,
  OPTIONS_BUTTON,
  OPTIONS_ADD_TO_MY_LIST_BUTTON,
          ADD_TO_MY_LIST_OVERLAY,
  OPTIONS_REMOVE_FROM_MY_LIST_BUTTON,
  MY_LIST_NAME_INPUT,
  MY_LIST_OK_BUTTON_INPUT,
  CLOSE_ARTICLE_BUTTON,
  CLOSE_POP_UP,
  FOLDER_BY_NAME_TPL;


  private static String getFolderXpathByName(String name_of_the_folder) {
    return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}",name_of_the_folder);
  }



  public WebElement waitForTitleElement() {

    return this.waitForElementPresent(TITLE, "cannot find article title on page", 15);
  }




  public String getArticleTitle() {

    WebElement title_element = waitForTitleElement();

    if (Platform.getInstance().isAndroid()) {
    return title_element.getAttribute("text");
  } else if (Platform.getInstance().isOS()) {
      return title_element.getAttribute("name");
    } else {

      return title_element.getText();

    }
  }



  public void swipeToFooter() {

    if (Platform.getInstance().isAndroid()) {

    this.swipeUpToFindElement(FOOTER_ELEMENT, "Cannot find the end of article", 200);
  } else if (Platform.getInstance().isOS()){
      this.swipeUpTillElementAppear(FOOTER_ELEMENT, "cannot find end of article", 200);
    } else {
      this.scrollWebPageTillElementNotVisible(FOOTER_ELEMENT, "Cannot find the end of article", 200);
    }


  }


  public void addArticleToMyList(String name_of_the_folder) {

    this.waitForElementAndClick
            (OPTIONS_BUTTON,
            "cannot find button to open article options",
            15);
    this.waitForElementAndClick(
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            "cannot find option to add to the reading list",
            5
    );

    this.waitForElementAndClick(
            ADD_TO_MY_LIST_OVERLAY,
            "cannot find 'got it' tip overlay",
            5);

    this.waitForElementClear(MY_LIST_NAME_INPUT,
            "cannot find input to set name of articles folder",
            5);


    this.waitForElementAndSendKeys(MY_LIST_NAME_INPUT,
            name_of_the_folder,
            "cannot put text in the article folder input",
            5);
    this.waitForElementAndClick(
            MY_LIST_OK_BUTTON_INPUT,
            "Cannot press OK button",
            5);

  }

  public void addArticleToExistingList(String name_of_the_folder) {

    this.waitForElementAndClick
            (OPTIONS_BUTTON,
            "cannot find button to open article options",
            15);
    this.waitForElementAndClick(
            OPTIONS_ADD_TO_MY_LIST_BUTTON,
            "cannot find option to add to the reading list",
            5
    );
    this.selectExistingFolder(name_of_the_folder);
  }



    public void selectExistingFolder(String name_of_the_folder) {
    String folder_name_xpath = getFolderXpathByName(name_of_the_folder);
    this.waitForElementAndClick(
            (folder_name_xpath),
            "cannot find folder by name " + name_of_the_folder,
            15);

  }

  public void closeArticle() {

    if (Platform.getInstance().isOS()|| Platform.getInstance().isAndroid()) {
    this.waitForElementAndClick(
            CLOSE_ARTICLE_BUTTON,
            "cannot close article, cannot find X link",
            5);
  } else {

    System.out.println("method closeArticle() do nothing for platform " + Platform.getInstance().getPlatformVar());
  }
  }



  public void assertThereIsATitle() {
    this.assertElementPresent(TITLE,
            "We didnt' find any results by request ");


  }

  public void assertArticleIsDeleted() {
    this.assertElementNotPresent(TITLE,
            "Article is still present");
  }


  public void addArticleToMySaved() {

    if (Platform.getInstance().isMW()) {
      this.removeArticleFromSavedIfItAdded();
    }
    this.waitForElementAndClick(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find option to add article to reading list", 5);

  }

  public void removeArticleFromSavedIfItAdded() {
    if (this.isElementPresent(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON)) {
      this.waitForElementAndClick(OPTIONS_REMOVE_FROM_MY_LIST_BUTTON, "Cannot click button to remove an article", 1);
      this.waitForElementPresent(OPTIONS_ADD_TO_MY_LIST_BUTTON, "Cannot find button to add an article to saved list after removing it from this list before");
    }
  }




  public void closeThePopUp() {
    this.waitForElementAndClick(CLOSE_POP_UP, "cannot find option to close pop up window", 5);
  }









}
