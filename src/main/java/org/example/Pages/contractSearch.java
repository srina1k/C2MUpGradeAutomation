package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;


public class contractSearch {

    private WebDriver driver;

    public contractSearch(){
        this.driver = DriverManager.getDriver();
    }

    public void navigateToContract(String ContractID){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);

        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//li[@id='mainMenu']"), 5);
        WaitUtils.waitAndClick(driver, By.id("CI_MAINMENU_topMenuItem0x9"), 5);
        WaitUtils.waitAndClick(driver,By.id("ci_mainmenu_topmenuitem0x9ContractSearch"),5);
        WaitUtils.waitForVisible(driver,By.xpath("//div[contains(text(),'Contract Search')]"));
        WaitUtils.getWait(driver,20);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        driver.findElement(By.xpath("//input[@orafield='contractId']")).sendKeys(ContractID);
        WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Search']"),5);
        WaitUtils.sleep(2000);
        WaitUtils.waitAndClick(driver, By.cssSelector("span[title='Go To Contract Navigation Option ']"), 5);
    }

    public void ContractValidation(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='zoneMapFrame_1']")));
        WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Validate']"), 5);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@id='zoneMapFrame_3']")));
        WaitUtils.waitForVisible(driver, By.xpath("//span[text()='Accepted']"));
    }
}
