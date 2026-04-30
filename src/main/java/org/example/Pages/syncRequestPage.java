package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

public class syncRequestPage {
    private WebDriver driver;

    public syncRequestPage() {
        this.driver = DriverManager.getDriver();
    }

    public void NavigateTosyncRequestQuery()  {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//li[@id='mainMenu']"), 5);
        WaitUtils.waitAndClick(driver, By.id("CI_MAINMENU_topMenuItem0x11"), 5);
        WaitUtils.waitAndClick(driver,By.id("ci_mainmenu_topmenuitem0x11SyncRequestOutbound"),10);
        //WaitUtils.waitForFrameAndSwitch(driver,"tabPage",2);
        WaitUtils.waitForPVisible(driver,By.xpath("//span[text()='Sync Request Search']"),10);
    }

    public void dropdownSyncRequestID(String SyncReqID){
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",10);
        Select search_by = new Select(driver.findElement(By.xpath("//select[@id='multiQueryZoneFilters1']")));
        search_by.selectByIndex(3);
        WaitUtils.sleep(1000);
        driver.findElement(By.xpath("//input[@id='filter1.F1']")).sendKeys(SyncReqID);
        WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Search']"),5);
        WaitUtils.waitAndClick(driver,By.cssSelector("span[title='Go To Sync Request ']"),5);
    }

    public void validation(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",2);
        WaitUtils.waitForFrameAndSwitch(driver,"zoneMapFrame_1",5);
        //driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='zoneMapFrame_1']")));
        WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Validate']"),5);

        //driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='zoneMapFrame_3']")));
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",2);
        WaitUtils.waitForFrameAndSwitch(driver,"zoneMapFrame_3",5);
        WaitUtils.waitForTextTiBePresent(driver, By.xpath("//td[text()='Completed']"), "Completed", 20);

    }
}
