package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class PaymentEventUploadStaging {

    private WebDriver driver;
    public PaymentEventUploadStaging() {
        this.driver = DriverManager.getDriver();
    }

    public void navigation() {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 8);

        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);
        WaitUtils.waitAndClick(driver,By.xpath("//li[@id='mainMenu']"),10);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Financial']"),5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Payment Event Upload Staging']"),5);
    }

    public void searchExtTransmitID(String TransmitID){

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
        for (String window : driver.getWindowHandles()){
            driver.switchTo().window(window);
            if (driver.getTitle().contains("Payment Event Upload Staging Search")){
                break;
            }
        }
        System.out.println("Switched to new window: " + driver.getTitle());
        WebElement payEventField = WaitUtils.waitForPVisible(driver, By.cssSelector("input[id='EXT_TRANSMIT_ID']"), 10);
        payEventField.sendKeys(TransmitID);
    }

    public void ExtclickSearch() {
        WaitUtils.waitAndClick(driver, By.xpath("//input[@name='BU_MAIN_SEARCH']"), 5);
        String mainHandle = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(mainHandle);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
        WaitUtils.waitForVisible(driver,By.xpath("//option[text()='Complete']"));
    }

    public void searchExtTransmitIDREMP(String TransmitID){

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
        for (String window : driver.getWindowHandles()){
            driver.switchTo().window(window);
            if (driver.getTitle().contains("Payment Event Upload Staging Search")){
                break;
            }
        }
        System.out.println("Switched to new window: " + driver.getTitle());
        WebElement payEventField = WaitUtils.waitForPVisible(driver, By.cssSelector("input[id='EXT_TRANSMIT_ID']"), 10);
        payEventField.sendKeys(TransmitID);

        WaitUtils.waitAndClick(driver, By.xpath("//input[@name='BU_MAIN_SEARCH']"), 5);
//        WaitUtils.waitForFrameAndSwitch(driver, "dataframe", 5);
//        WaitUtils.waitAndClick(driver, By.id("SEARCH_RESULTS:0$DST_RULE_CD"),5);

        String mainHandle = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(mainHandle);
        WaitUtils.sleep(5000);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
    }

    public void scrollDown(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
        WebElement PayEventStagingStatus = driver.findElement(By.xpath("//select[@name='PEVT_STG_ST_FLG']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", PayEventStagingStatus);
    }
    public void scrollDownTenderControl(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
        WaitUtils.waitForVisible(driver,By.xpath("//option[text()='Incomplete']"));
    }
    public void clickRefresh(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitAndClick(driver, By.id("IM_REFRESH"), 5);
    }

    public void goback(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitAndClick(driver, By.id("IM_GOBACK"), 5);
    }

    public void navigateToTenderControl(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitAndClick(driver, By.xpath("//img[@name='IM_CTXT_TNDR_CTL_ID']"),5);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitAndClick(driver, By.id("CI_CONTEXTTENDERCONTROL_subMenuItem1x0"),5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Search']"),5);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitForVisible(driver,By.xpath("//input[@name='TNDR_CTL_ID']"));
    }
    public void pepl1TenderControlStatus(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WebElement PayEventStagingStatus = driver.findElement(By.xpath("//option[text()='Open']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", PayEventStagingStatus);
    }
    public void pepl3TenderControlStatus(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WebElement PayEventStagingStatus = driver.findElement(By.xpath("//select[@id='TNDR_CTL_ST_FLG']/option[text()='Balanced']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", PayEventStagingStatus);
    }
    public void pepl2payeventstatus(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitForVisible(driver,By.xpath("//option[text()='Complete']"));

    }

}
