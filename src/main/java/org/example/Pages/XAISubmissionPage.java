package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class XAISubmissionPage {
    private WebDriver driver;
    public XAISubmissionPage(){
        this.driver = DriverManager.getDriver();
    }
    public void navigateToXAISubmission(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitAndClick(driver, By.xpath("//span[@id='IM_adminButton']"), 5);
        WaitUtils.waitAndClick(driver, By.id("CI_ADMINMENU_topMenuItem0x23"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='XAI Submission']"), 5);
        WaitUtils.waitForTextTiBePresent(driver,By.id("ptitle"),"XAI Submission",15);
    }
    public void submitXAI(String xaiRequest){
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        By xaiField = By.xpath("//textarea[@onclick='main.onTextAreaClick(event, document)']");
        driver.findElement(xaiField).clear();
        driver.findElement(xaiField).sendKeys(xaiRequest);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        //driver.findElement(By.xpath("//input[@id='IM_SAVE']")).click();
    }
    public void clearXai(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabMenu",8);
        WaitUtils.waitAndClick(driver, By.cssSelector("td[title='Main-This tab is selected']"),5);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Clear']"),5);
    }
}
