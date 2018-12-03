package lib.ui;
import lib.Platform;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract  public class NavigationUI extends MainPageObject {

  public NavigationUI(RemoteWebDriver driver) {

    super(driver);

  }

  protected static  String
  MY_LISTS_LINK,
  OPEN_NAVIGATION;


  public void clickMyList() {

    if (Platform.getInstance().isMW() ) {
      this.tryClickElementWithFewAttempts(MY_LISTS_LINK,
              "cannot find navigation button to My lists",
              5);
    }

    this.waitForElementAndClick(
            (MY_LISTS_LINK),
            "cannot find navigation button to My lists",
            15);
  }

  public void openNavigation() {

    if (Platform.getInstance().isMW()) {

      this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigation button", 5);

    } else {
      System.out.println("method openNavigation() do nothing for platform" + Platform.getInstance().getPlatformVar());
    }
  }






}
