package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class PaymentEventPage {

    private WebDriver driver;
    public PaymentEventPage() {
        this.driver = DriverManager.getDriver();
    }

    public void navigation() {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 8);
        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);

        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Financial']"),5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Payment Event']"),5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Search']"),5);
    }

    public void searchPayEventID(String PaymentEventID){

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
        for (String window : driver.getWindowHandles()){
            driver.switchTo().window(window);
            if (driver.getTitle().contains("Payment Event Search")){
                break;
            }
        }
        System.out.println("Switched to new window: " + driver.getTitle());
        WebElement payEventField = WaitUtils.waitForPVisible(driver, By.cssSelector("input[id='PAY_EVENT_ID']"), 10);
        payEventField.sendKeys(PaymentEventID);
    }

    public void payclickSearch() {
        WaitUtils.waitAndClick(driver, By.xpath("//input[@name='BU_Main_payEvtSrch']"), 5);
        String mainHandle = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(mainHandle);
        WaitUtils.sleep(5000);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "payGridpaymentGri", 5);
        WaitUtils.waitForVisible(driver, By.id("IM_PAY:0$ADD_BTN"));
    }

    public void payEventDetails(String AccountID, String payment){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "payGridpaymentGri", 5);

        WaitUtils.waitAndClick(driver, By.id("IM_PAY:0$ADD_BTN"),5);
        WaitUtils.waitForVisible(driver, By.cssSelector("input[id='PAY:1$ACCT_ID']"));
        WebElement accountIdField = driver.findElement(By.cssSelector("input[id='PAY:1$ACCT_ID']"));
        accountIdField.clear();
        accountIdField.sendKeys(AccountID);

        WaitUtils.waitForVisible(driver, By.xpath("//input[@name='PAY:1$PAY_AMT']"));
        WebElement payAmountField = driver.findElement(By.xpath("//input[@name='PAY:1$PAY_AMT']"));
        payAmountField.clear();
        payAmountField.sendKeys(payment);

        WaitUtils.sleep(2000);
        WebElement MatchType = driver.findElement(By.cssSelector("select[id='PAY:1$MATCH_TYPE_CD']"));
        Select dropdown_MatchType = new Select(MatchType);
        dropdown_MatchType.selectByIndex(1);

        WebElement matchVal = driver.findElement(By.xpath("//input[@name='PAY:1$MATCH_VAL']"));
        matchVal.sendKeys(AccountID);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        driver.findElement(By.cssSelector("input[id='IM_SAVE']")).click();
        WaitUtils.sleep(2000);
        driver.switchTo().alert().accept();
        WaitUtils.sleep(2000);
    }

    public String getPaymentID(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "payGridpaymentGri", 3);

        WaitUtils.waitForVisible(driver, By.xpath("//span[@id='PAY:0$PAY_ID']"));
        String paymentID = driver.findElement(By.xpath("//span[@id='PAY:0$PAY_ID']")).getText();
        return paymentID;
    }

    public void paymentSearchPage(String PaymentID){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);

        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Financial']"),5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Payment']"),5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Search']"),5);

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
        for (String window : driver.getWindowHandles()){
            driver.switchTo().window(window);
            if (driver.getTitle().contains("Payment Search")){
                break;
            }
        }
        System.out.println("Switched to new window: " + driver.getTitle());
        WebElement paymentField = WaitUtils.waitForPVisible(driver, By.xpath("//input[@name='PAY_ID']"), 10);
        paymentField.sendKeys(PaymentID);
    }

    public void paymentSearch() {
        WaitUtils.waitAndClick(driver, By.xpath("//input[@name='BU_Main_payEvtSrch']"), 5);
        String mainHandle = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(mainHandle);
        WaitUtils.sleep(5000);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
        WaitUtils.waitForVisible(driver, By.xpath("//input[@name='PAY_ID']"));
    }
}
