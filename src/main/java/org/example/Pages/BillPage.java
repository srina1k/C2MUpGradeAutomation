package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class BillPage {
    private WebDriver driver;

    public BillPage(){
        this.driver = DriverManager.getDriver();
    }

    public void BillChar(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabMenu",8);
        WaitUtils.waitAndClick(driver, By.id("BILLCHAR_TLBL"),5);
        WaitUtils.sleep(3000);
    }

    public void BillMenu(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabMenu",8);
        WaitUtils.waitAndClick(driver, By.id("MAIN_TLBL"),5);
        WaitUtils.sleep(3000);
    }
    public void navigateToAdjustment(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        driver.findElement(By.id("IM_Acct_acctCtxt")).click();

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        By[] sequence = {By.id("CI_CONTEXTACCOUNT_subMenuItem1x9"),  By.xpath("(//li[@data-menuobj='contextAccount'])[1]")};
        for (By item : sequence){
            WaitUtils.waitAndClick(driver, item, 10);
        }

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
        for (String window : driver.getWindowHandles()){
            driver.switchTo().window(window);
            if (driver.getTitle().contains("Person Search")){
                break;
            }
        }
    }
}
