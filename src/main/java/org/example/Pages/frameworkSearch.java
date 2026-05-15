package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class frameworkSearch {
    private WebDriver driver;

    public frameworkSearch(){
        this.driver = DriverManager.getDriver();
    }

    public void navigationToFrameworkSearch(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);

        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);
        WaitUtils.waitAndClick(driver,By.xpath("//li[@id='mainMenu']"),10);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Customer Information']"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Framework Search']"), 5);
    }

    public void searchByFrameworkSearch(String frameworkID){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);

        WebElement frameworkTextBox = driver.findElement(By.xpath("//input[@id='filter4.F1']"));
        frameworkTextBox.sendKeys(frameworkID);
        WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Search']"),5);

    }

}
