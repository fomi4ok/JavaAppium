package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class MyListTests extends CoreTestCase {

  private String name_of_the_folder = "Learning programming";
  private static final String login = "mfomi4ok",
  password = "Password135";


  @Test
  public void testSaveFirstArticleToMyList()  {

    SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

    ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
    ArticlePageObject.waitForTitleElement();
    String article_title = ArticlePageObject.getArticleTitle();

    if (Platform.getInstance().isAndroid()) {
      ArticlePageObject.addArticleToMyList(name_of_the_folder);
    } else if (Platform.getInstance().isOS()) {
      ArticlePageObject.addArticleToMySaved();
      ArticlePageObject.closeThePopUp();
    } else {
      ArticlePageObject.addArticleToMySaved();
    }
    if (Platform.getInstance().isMW()) {

      AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
      Auth.clickAuthButton();
      Auth.enterLoginData(login, password);
      Auth.submitForm();

      ArticlePageObject.waitForTitleElement();
      assertEquals("We are not on the same page after login", article_title, ArticlePageObject.getArticleTitle());
      ArticlePageObject.addArticleToMySaved();

    }
    ArticlePageObject.closeArticle();

    NavigationUI NavigationUI = NavigationUIFactory.get(driver);
    NavigationUI.openNavigation();
    NavigationUI.clickMyList();

    MyListPageObject MyListPageObject = MyListPageObjectFactory.get(driver);
    if (Platform.getInstance().isAndroid()) {
      MyListPageObject.openFolderByName(name_of_the_folder);
      MyListPageObject.openFolderByName(name_of_the_folder);
    }

    MyListPageObject.swipeByArticleToDelete(article_title);


  }

  /// ex 5
  @Test
  public void testSaveTwoArticleToMyList()  {

    //adding first article to the list

    SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
    SearchPageObject.initSearchInput();
    SearchPageObject.typeSearchLine("Java");
    SearchPageObject.clickByArticleWithSubstring("bject-oriented programming language");

    ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
    ArticlePageObject.waitForTitleElement();
    String article_title = ArticlePageObject.getArticleTitle();

    if (Platform.getInstance().isAndroid()) {
      ArticlePageObject.addArticleToMyList(name_of_the_folder);
    } else if (Platform.getInstance().isOS()) {
      ArticlePageObject.addArticleToMySaved();
      ArticlePageObject.closeThePopUp();
    } else {
      ArticlePageObject.addArticleToMySaved();
    }

    if (Platform.getInstance().isMW()) {
      AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
      Auth.clickAuthButton();
      Auth.enterLoginData(login, password);
      Auth.submitForm();

      ArticlePageObject.waitForTitleElement();
      assertEquals("We are not on the same page after login", article_title, ArticlePageObject.getArticleTitle());
      ArticlePageObject.addArticleToMySaved();

    }
    ArticlePageObject.closeArticle();

    //adding second article to the list


    SearchPageObject.initSearchInput();
    if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {
      SearchPageObject.typeSearchLine("Java");
    }

    SearchPageObject.clickByArticleWithSubstring("sland of Indonesia");
    ArticlePageObject.waitForTitleElement();
    String second_article_title = ArticlePageObject.getArticleTitle();

    if (Platform.getInstance().isAndroid()) {
      ArticlePageObject.addArticleToExistingList(name_of_the_folder);
    } else {
      ArticlePageObject.addArticleToMySaved();

    }
    ArticlePageObject.closeArticle();


    NavigationUI NavigationUI = NavigationUIFactory.get(driver);
    NavigationUI.openNavigation();
    NavigationUI.clickMyList();


    MyListPageObject MyListPageObject = MyListPageObjectFactory.get(driver);
    if (Platform.getInstance().isAndroid()) {
      MyListPageObject.openFolderByName(name_of_the_folder);
      MyListPageObject.openFolderByName(name_of_the_folder);
    }
    MyListPageObject.swipeByArticleToDelete(article_title);

    if (Platform.getInstance().isAndroid()) {
      MyListPageObject.openArticleFromFolder(second_article_title);
      String title_of_the_article_saved = ArticlePageObject.getArticleTitle();
      assertEquals("Title doesn't match", second_article_title, title_of_the_article_saved);
    } else  {
      ArticlePageObject.assertArticleIsDeleted();

    }
}}
