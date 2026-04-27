package org.example.Pages;

import io.qameta.allure.testng.TestInstanceParameter;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.example.Utils.DriverManager;
import java.time.Duration;
import java.util.Set;

public class UserPage {
    public WebDriver driver;
    private String personID;
    public UserPage() {
        this.driver = DriverManager.getDriver();
    }
    public void NavigateToOpportunity(String personID) {
        WaitUtils.waitForPageLoad(driver,20);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 10);
        String currentFrame = (String) ((JavascriptExecutor) driver).executeScript("return window.frameElement? window.frameElement.name : 'default';");
        System.out.println("Current frame switched to: " + currentFrame);

        WaitUtils.waitAndClick(driver, (By.id("IM_menuButton")), 30);
        WaitUtils.getWait(driver,20);
        By[] menuSequence1 = {
                By.xpath("//li[@id='mainMenu']"), By.xpath("//li[@id='CI_MAINMENU_topMenuItem0x28']"), By.xpath("(//span[contains(text(),'Add')])[9]")};
        for (By menuItem1 : menuSequence1) {
            WaitUtils.waitAndClick(driver, menuItem1, 15);
            WaitUtils.sleep(3000);
        }
        WaitUtils.waitForVisible(driver, By.xpath("//input[@id='Bundefined']"));
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        String secondFrame = (String) ((JavascriptExecutor) driver).executeScript("return window.frameElement? window.frameElement.name : 'default';");
        System.out.println("Current frame switched to: " + secondFrame);
        WaitUtils.waitAndClick(driver, (By.id("IM_menuButton")), 15);
        By[] menuSequence2 = {
                By.xpath("//li[@id='mainMenu']"), By.xpath("//li[@id='CI_MAINMENU_topMenuItem0x9']"), By.xpath("(//span[contains(text(),'Add')])[12]")};// By.xpath("//input[@id=\"PER_ID\"]")};
        for (By menuItem2 : menuSequence2) {
            WaitUtils.waitAndClick(driver, menuItem2, 20);
            WaitUtils.sleep(2000);
        }
        WaitUtils.waitForPageLoad(driver, 20);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 15);
        driver.switchTo().frame("tabPage");
        String mainWindow = driver.getWindowHandle();
        WaitUtils.waitAndClick(driver, By.xpath("//img[@id='IM_PER_ID']"), 20);
        System.out.println(mainWindow);
        String childwindow = null;
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
        Set<String> allWindows = driver.getWindowHandles();
        System.out.println(allWindows);
        for (String window : allWindows) {
            if (!window.equals(mainWindow)) {
                childwindow = window;
                break;
            }
        }
        System.out.println(childwindow);
        if(childwindow==null) {
            System.out.println("No new window opened after clicking person search");
        }
        driver.switchTo().window(childwindow);
        new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.titleContains("Person Search"));
        WebElement personField = WaitUtils.waitForPVisible(driver, By.id("PER_ID"), 10);
        personField.clear();
        personField.sendKeys(personID);

//        WaitUtils.waitAndClick(driver, By.id("BU_Main_search"), 10);
//        System.out.println(driver.getWindowHandle());
//        driver.switchTo().window(mainWindow);
    }
    public void navigateToOpportunity(String PersonID){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitAndClick(driver, (By.id("IM_menuButton")), 5);
        By[] menuSequence1 = {
                By.xpath("//span[text()='Main Menu']"), By.xpath("//span[text()='Sales & Marketing']"), By.xpath("(//span[text()='Add'])[9]")};
        for (By menuItem1 : menuSequence1) {
            WaitUtils.waitAndClick(driver, menuItem1, 10);
        }
        WaitUtils.waitForVisible(driver,By.xpath("//input[@id='Bundefined']"));
        By[] menuSequence2 = {
                By.id("IM_menuButton"), By.xpath("//span[text()='Main Menu']"), By.xpath("//span[text()='Customer Information']"), By.xpath("//span[text()='Person']")};
        for (By menuItem2 : menuSequence2) {
            WaitUtils.waitAndClick(driver, menuItem2, 10);
        }
        WaitUtils.waitForPageLoad(driver,20);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
        for (String window : driver.getWindowHandles()){
            driver.switchTo().window(window);
            if (driver.getTitle().contains("Person Search")){
                break;
            }
        }
        System.out.println("Switched to new window: " + driver.getTitle());
        WebElement personField = WaitUtils.waitForPVisible(driver, By.id("PER_ID"), 10);
        personField.sendKeys(PersonID);
        WaitUtils.sleep(2000);
    }
    public void clickSearch() {
        WaitUtils.waitAndClick(driver, By.xpath("//input[@id='BU_Main_search']"), 5);
        String mainHandle = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(mainHandle);
        WaitUtils.sleep(5000);
    }
    public void personSearch(String personID){
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        By[] menuSequence2 = {
                By.id("IM_menuButton"), By.id("CI_MAINMENU_topMenuItem0x9"), By.id("CI_CUSTOMERINFORMATION_subMenuItem1x27"), By.xpath("//span[text()='Search']")};
        for (By menuItem2 : menuSequence2) {
            WaitUtils.waitAndClick(driver, menuItem2, 10);
        }
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
        for (String window : driver.getWindowHandles()){
            driver.switchTo().window(window);
            if (driver.getTitle().contains("Person Search")){
                break;
            }
        }
        System.out.println("Switched to new window: " + driver.getTitle());
        WebElement personField = WaitUtils.waitForPVisible(driver, By.id("PER_ID"), 10);
        personField.sendKeys(personID);
    }
    public void personTab (String contact){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabMenu", 5);
        WaitUtils.waitAndClick(driver, By.xpath("//td[@title='Persons']"), 5);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "PTP_LIST", 5);
        WebElement relationshipType = driver.findElement(By.xpath("//select[@name='PTP:1$PER_REL_TYPE_CD']"));
        Select s4 = new Select(relationshipType);
        s4.selectByVisibleText(contact);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitAndClick(driver, By.cssSelector("input[id='IM_SAVE']"),5);
    }
}

