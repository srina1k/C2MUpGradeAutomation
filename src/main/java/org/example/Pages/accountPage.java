package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class accountPage {

    private WebDriver driver;
    public accountPage() {
        this.driver = DriverManager.getDriver();
    }

    public void navigateToAccount() {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);

        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Customer Information']"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Account']"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Add']"), 5);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitForVisible(driver,By.cssSelector("select[id='CUST_CL_CD']"));
    }

    public void accountDtails(String premiseID) {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WebElement customer_class1 = driver.findElement(By.cssSelector("select[id='CUST_CL_CD']"));
        Select dropdown_customer_class1 = new Select(customer_class1);
        dropdown_customer_class1.selectByVisibleText("Electric Commercial");
        WaitUtils.sleep(2000);

        driver.switchTo().alert().accept();
        WebElement cis_division = driver.findElement(By.cssSelector("select[id='CIS_DIVISION']"));
        Select dropdown_cis_division = new Select(cis_division);
        dropdown_cis_division.selectByVisibleText("United Kingdom");
        WaitUtils.sleep(2000);

        driver.switchTo().alert().accept();
        driver.findElement(By.cssSelector("textarea[id='ALERT_INFO']")).sendKeys("Comment");

        WebElement bill_cycle = driver.findElement(By.cssSelector("select[id='BILL_CYC_CD']"));
        Select dropdown_bill_cycle = new Select(bill_cycle);
        dropdown_bill_cycle.selectByVisibleText("Monthly bill for NHH where read schedule is end of month");

        WebElement premID = driver.findElement(By.cssSelector("input[id='MAILING_PREM_ID']"));
        premID.click();
        WaitUtils.sleep(2000);
        premID.sendKeys(premiseID);
    }

    public void acctpersonsTab(String personID) {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabMenu", 3);
        driver.findElement(By.xpath("//td[text()='Persons']")).click();

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitForVisible(driver, By.xpath("//input[@name='ACCT_PER$PER_ID']"));

        WebElement perID = driver.findElement(By.xpath("//input[@name='ACCT_PER$PER_ID']"));
        perID.click();
        perID.sendKeys(personID);
    }

    public void acctCharTab(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabMenu", 3);
        driver.findElement(By.xpath("//td[text()='Characteristics']")).click();

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "CharGrid_character", 3);
        WebElement characteristics1 = driver.findElement(By.cssSelector("select[id='ACCT_CHAR:0$CHAR_TYPE_CD']"));
        Select dropdown_characteristics1 = new Select(characteristics1);
        dropdown_characteristics1.selectByVisibleText("Billing Frequency");
        WaitUtils.waitForVisible(driver,By.cssSelector("input[id='ACCT_CHAR:0$CHAR_VAL']"));
        driver.findElement(By.cssSelector("input[id='ACCT_CHAR:0$CHAR_VAL']")).sendKeys("M");

        driver.findElement(By.cssSelector("img[id='IM_ACCT_CHAR:0$Grid_btnAdd']")).click();
        WaitUtils.waitForVisible(driver,By.cssSelector("select[id='ACCT_CHAR:1$CHAR_TYPE_CD']"));
        WebElement characteristics11 = driver.findElement(By.cssSelector("select[id='ACCT_CHAR:1$CHAR_TYPE_CD']"));
        Select dropdown_characteristics11 = new Select(characteristics11);
        dropdown_characteristics11.selectByVisibleText("Billing User Allocated to Account");
        WaitUtils.waitForVisible(driver,By.cssSelector("input[id='ACCT_CHAR:1$CHAR_VAL_FK1']"));
        driver.findElement(By.cssSelector("input[id='ACCT_CHAR:1$CHAR_VAL_FK1']")).sendKeys("BATCHUSR");

        driver.switchTo().defaultContent();
        driver.switchTo().frame("main");
        driver.findElement(By.cssSelector("input[id='IM_SAVE']")).click();
    }

    public String accountID(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitForVisible(driver,By.xpath("//input[@name='ACCT_ID']"));
        WebElement accIDtxtField =driver.findElement(By.xpath("//input[@name='ACCT_ID']"));
        WaitUtils.waitForTextToBePresentInValue(driver,accIDtxtField);
        return accIDtxtField.getAttribute("value");
    }
}
