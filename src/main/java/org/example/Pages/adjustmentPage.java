package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


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

    public void adjustmentDetails(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        WaitUtils.sleep(2000);
        driver.findElement(By.xpath("//input[@id='ADJ_TYPE_CD']")).sendKeys("CM-PVTW");

        WebElement amountField = driver.findElement(By.cssSelector("input[id='BASE_AMT_WRK']"));
        amountField.click();
        amountField.clear();
        //amountField.sendKeys(Keys.DELETE);
        WaitUtils.sleep(2000);
        amountField.sendKeys("50");
        amountField.clear();
        amountField.sendKeys("50");
    }

    public void adjustmentCharTab(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabMenu",8);
        driver.findElement(By.cssSelector("td[title='Characteristics']")).click();

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        WaitUtils.waitForFrameAndSwitch(driver,"ADJ_CHAR",8);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = formatter.format(date);

        WebElement chars = driver.findElement(By.cssSelector("input[id='ADJ_CHAR:0$ADHOC_CHAR_VAL']"));
        chars.sendKeys("50");

        driver.findElement(By.cssSelector("input[id='ADJ_CHAR:1$ADHOC_CHAR_VAL']")).click();
        driver.findElement(By.cssSelector("input[id='ADJ_CHAR:1$ADHOC_CHAR_VAL']")).sendKeys(currentDate);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        driver.findElement(By.cssSelector("input[id='IM_SAVE']")).click();
    }

    public String adjustmentID(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WebElement adjustmentIDtxtField =driver.findElement(By.xpath("//input[@name='ADJ_ID']"));
        WaitUtils.waitForTextToBePresentInValue(driver,adjustmentIDtxtField);
        return adjustmentIDtxtField.getAttribute("value");
    }
}
