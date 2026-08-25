package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DeclarationPage {
    private final WebDriver driver;

    public DeclarationPage() {
        this.driver = DriverManager.getDriver();
    }

    public void navigateToDeclarationPage() {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 8);
        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//li[@id='mainMenu']"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Customer Information']"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("(//span[text()='Add'])[9]"),5);
    }

    public void validateDeclaration(String declarationId) {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        String parentWindow = driver.getWindowHandle();
        WaitUtils.waitAndClick(driver, By.xpath("//img[@id='IM_DCL_ID']"),5);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> driver.getWindowHandles().size() > 1);
        for(String window:driver.getWindowHandles()){
            if(!window.equals(parentWindow)){
                driver.switchTo().window(window);
            break;
            }
        }
        WaitUtils.waitForVisible(driver,By.cssSelector("input[id='DCL_ID']")).sendKeys(declarationId);
        WaitUtils.waitAndClick(driver,By.cssSelector("input[id='BU_mainSearch']"),10);
        driver.switchTo().window(parentWindow);
    }
    public void navigateToCase(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[id='dashboard']")));
        WaitUtils.waitForVisible(driver,By.cssSelector("img[title='Show Account Context Menu']"));
        WaitUtils.waitAndClick(driver,By.cssSelector("img[title='Show Account Context Menu']"),5);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitAndClick(driver,By.xpath("//span[text()='Go To Case']"),5);
        WaitUtils.waitAndClick(driver,By.xpath("//span[text()='Search']"),5);
    }

}
