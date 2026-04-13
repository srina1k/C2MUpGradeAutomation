package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class servicePointPage {
    private WebDriver driver;
    public servicePointPage() {
        this.driver = DriverManager.getDriver();
    }

    public void navigateToCCBServicePoint() {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);

        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Customer Information']"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Service Point - CCB']"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Add']"), 5);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitForVisible(driver, By.cssSelector("input[id='PREM_ID']"));
    }

    public void CCBServicePointDetails(String premiseID, String MPANID) {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.sleep(2000);
        driver.findElement(By.cssSelector("input[id='PREM_ID']")).sendKeys(premiseID);
        WebElement spType = driver.findElement(By.cssSelector("input[id='SP_TYPE_CD']"));
        spType.click();
        WaitUtils.sleep(2000);
        spType.sendKeys("E-COM");

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabMenu", 3);
        driver.findElement(By.xpath("//td[text()='Characteristics']")).click();

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "spGrid_spChrGrid", 3);

        driver.findElement(By.cssSelector("input[id='SP_CHAR:0$CHAR_TYPE_CD']")).sendKeys("CM-MCLSS");
        driver.findElement(By.cssSelector("input[id='SP_CHAR:0$CHAR_VAL']")).click();
        WaitUtils.sleep(1000);
        driver.findElement(By.cssSelector("input[id='SP_CHAR:0$CHAR_VAL']")).sendKeys("A");

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabMenu", 3);
        driver.findElement(By.xpath("//td[text()='Geo']")).click();

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "dataGrid", 3);

        WebElement characteristicsB = driver.findElement(By.cssSelector("select[id='SP_GEO:0$GEO_TYPE_CD']"));
        Select dropdown_characteristicsB = new Select(characteristicsB);
        dropdown_characteristicsB.selectByVisibleText("MPAN Core Identifier");
        driver.findElement(By.cssSelector("input[id='SP_GEO:0$GEO_VAL']")).sendKeys(MPANID);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        driver.findElement(By.cssSelector("input[id='IM_SAVE']")).click();
    }

    public String servicePointID(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WebElement spIDtxtField = driver.findElement(By.xpath("//input[@name='SP_ID']"));;
        WaitUtils.waitForTextToBePresentInValue(driver,spIDtxtField);
        return spIDtxtField.getAttribute("value");
    }

}
