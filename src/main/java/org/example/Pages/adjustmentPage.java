package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Wait;


import java.text.SimpleDateFormat;
import java.util.Date;

public class adjustmentPage {

    private WebDriver driver;
    public adjustmentPage() {
        this.driver = DriverManager.getDriver();
    }

    public void navigation(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);

        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Financial']"),5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Adjustment']"),5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Add']"),5);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        WaitUtils.waitForVisible(driver, By.cssSelector("input[id='ADJ_TYPE_CD']"));
    }

    public void adjustmentDetails(String ServiceAgreementId){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        WaitUtils.waitForPresence(driver,By.xpath("//input[@id='ADJ_TYPE_CD']"));
        WaitUtils.getWait(driver,10);
        driver.findElement(By.xpath("//input[@id='SA_ID']")).sendKeys(ServiceAgreementId);
//        driver.findElement(By.xpath("//input[@id='ADJ_TYPE_CD']")).sendKeys("CM-GOOD");
//        WaitUtils.sleep(2000);
        WebElement amountField = driver.findElement(By.cssSelector("input[id='BASE_AMT_WRK']"));
        amountField.click();
        WaitUtils.sleep(1000);
        amountField.clear();
        //amountField.sendKeys(Keys.DELETE);
        amountField.sendKeys("50");
        driver.findElement(By.xpath("//input[@id='ADJ_TYPE_CD']")).sendKeys("CM-GOOD");
        WaitUtils.sleep(2000);
    }

    public void adjustmentCharTab(){
//        driver.switchTo().defaultContent();
//        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
//        WaitUtils.waitForFrameAndSwitch(driver,"tabMenu",8);
//        driver.findElement(By.cssSelector("td[title='Characteristics']")).click();
//
//        driver.switchTo().defaultContent();
//        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
//        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
//        WaitUtils.getWait(driver,20);
//        WebElement adjframe= driver.findElement(By.cssSelector("iframe[id='ADJ_CHAR']"));
//        driver.switchTo().frame(adjframe);
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//        String currentDate = formatter.format(date);
//        //driver.findElement(By.cssSelector("input[id='ADJ_CHAR:0$CHAR_TYPE_CD']")).sendKeys("CM-RCNAM");
//       // WaitUtils.waitAndClick(driver,By.id("IM_ADJ_CHAR:0$CHAR_TYPE_CD"),10);
//        WaitUtils.getWait(driver,10);
//        WebElement chars = driver.findElement(By.cssSelector("input[id='ADJ_CHAR:0$ADHOC_CHAR_VAL']"));
//        chars.click();
//        chars.clear();
//        WaitUtils.getWait1(driver,20);
//        chars.sendKeys("50");
//        //driver.findElement(By.cssSelector("img[id='IM_ADJ_CHAR:0$ADD']")).click();
//        //driver.findElement(By.cssSelector("input[id='ADJ_CHAR:1$CHAR_TYPE_CD']")).sendKeys("CM-RCNDT");
//        WaitUtils.waitForVisible(driver,By.cssSelector("input[id='ADJ_CHAR:1$ADHOC_CHAR_VAL']"));
//        driver.findElement(By.cssSelector("input[id='ADJ_CHAR:1$ADHOC_CHAR_VAL']")).click();
//        driver.findElement(By.cssSelector("input[id='ADJ_CHAR:1$ADHOC_CHAR_VAL']")).sendKeys(currentDate);
//
//        driver.switchTo().defaultContent();
//        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
//        WaitUtils.waitForFrameAndSwitch(driver,"tabMenu",8);
//        driver.findElement(By.cssSelector("td[title='Main']")).click();
//        WaitUtils.getWait(driver,20);
//        driver.switchTo().defaultContent();
//        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
//        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",10);
//        driver.findElement(By.xpath("//input[@id='SA_ID']")).sendKeys(ServiceAgreementId);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",10);
        driver.findElement(By.cssSelector("input[id='IM_SAVE']")).click();
    }

    public String adjustmentID(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.getWait(driver,20);
        WaitUtils.waitForPageLoad(driver,10);
        WebElement adjustmentIDtxtField =driver.findElement(By.xpath("//input[@name='ADJ_ID']"));
        WaitUtils.waitForTextToBePresentInValue(driver,adjustmentIDtxtField);
        return adjustmentIDtxtField.getAttribute("value");
    }
}
