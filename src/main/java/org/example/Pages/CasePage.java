package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;


public class CasePage {
    private final WebDriver driver;
    public CasePage(){
        this.driver= DriverManager.getDriver();
    }
    public void RemovefromProcess(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",10);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",10);
        WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Remove from Processing']"),10);
        WaitUtils.getWait(driver,10);
    }
    public void navigateToCase(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitAndClick(driver,By.xpath("IM_menuButton"),5);
        WaitUtils.waitAndClick(driver,By.xpath("//li[@id='mainMenu']"),5);
        WaitUtils.waitAndClick(driver,By.xpath("//span[text()='Customer Information']"),5);
        WaitUtils.waitAndClick(driver,By.xpath("//span[text()='Case']"),5);
    }
    public String getCaseID(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",5);
        String caseID=WaitUtils.waitForVisible(driver,By.xpath("(//span[@title='Go To Case '])[1]")).getText().trim();
        WaitUtils.waitAndClick(driver,By.xpath("(//span[@title='Go To Case '])[1]"),5);
        return caseID;
    }
    public void validateCaseLog(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",5);
        WaitUtils.waitForVisible(driver,By.cssSelector("iframe[title='zoneMapFrame_1']"));
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='zoneMapFrame_1']")));
        WaitUtils.waitForVisible(driver,By.xpath("//span[text()='Monitor for Multiple Cancel Re-Bills']"));
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[id='tabMenu']")));
        WaitUtils.waitAndClick(driver,By.cssSelector("td[title='Log']"),5);
    }
    public void validateCancelCaseLog(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",5);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='zoneMapFrame_1']")));
        WaitUtils.waitForVisible(driver,By.xpath("//span[text()='Completed']"));
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[id='tabMenu']")));
        WaitUtils.waitAndClick(driver,By.cssSelector("td[title='Log']"),5);
    }

}
