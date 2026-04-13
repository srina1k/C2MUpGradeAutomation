package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ServiceAgreementPage {
    private WebDriver driver;
    public ServiceAgreementPage() {
        this.driver = DriverManager.getDriver();
    }

    public void navigateToSA(String  SAID){

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);

        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);
        WaitUtils.waitAndClick(driver, By.id("CI_MAINMENU_topMenuItem0x9"), 5);
        WaitUtils.waitAndClick(driver, By.id("CI_CUSTOMERINFORMATION_subMenuItem1x35"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Search']"), 5);
        WaitUtils.sleep(2000);

        new WebDriverWait(driver, Duration.ofSeconds(20)).until(d -> d.getWindowHandles().size() > 1);
        for (String window : driver.getWindowHandles()){
            driver.switchTo().window(window);
            if (driver.getTitle().contains("Service Agreement Search")){
                break;
            }
        }
        WaitUtils.sleep(5000);
        System.out.println("Switched to new window: " + driver.getTitle());
        WebElement saField = WaitUtils.waitForPVisible(driver, By.xpath("//input[@name='SA_ID']"), 10);
        saField.sendKeys(SAID);
    }

    public void clickSearch() {
        WaitUtils.waitAndClick(driver, By.id("BU_Main_search"), 5);
        String mainHandle = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(mainHandle);
        WaitUtils.sleep(5000);
    }
    public void navigatToSAFromAccountDropdown(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        driver.findElement(By.xpath("//img[@name='IM_Main_acctCntxt']")).click();

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        driver.findElement(By.xpath("//span[text()='Go To Service Agreement']")).click();
        driver.findElement(By.xpath("//span[text()='Add']")).click();
    }
    public void saMainTab(String premiseID){

        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        WaitUtils.waitForVisible(driver, By.cssSelector("select[id='CIS_DIVISION']"));
        WebElement CIS_DIVISION = driver.findElement(By.cssSelector("select[id='CIS_DIVISION']"));
        Select dropdown_CIS_DIVISION = new Select(CIS_DIVISION);
        dropdown_CIS_DIVISION.selectByIndex(1);

        WaitUtils.waitForVisible(driver, By.xpath("//input[@name='SA_TYPE_CD']"));
        WebElement saTypeCd = driver.findElement(By.xpath("//input[@name='SA_TYPE_CD']"));
        saTypeCd.click();
        saTypeCd.sendKeys("E-MAJOR");
        WaitUtils.sleep(4000);
        WebElement cutOffTime =  driver.findElement(By.cssSelector("input[id='IB_SA_CUTOFF_TM']"));
        cutOffTime.click();
        WaitUtils.sleep(2000);
        cutOffTime.sendKeys("0");


        WebElement start_day_option = driver.findElement(By.cssSelector("select[id='IB_BASE_TM_DAY_FLG']"));
        Select dropdown_start_day_option = new Select(start_day_option);
        dropdown_start_day_option.selectByIndex(1);

        WebElement premID = driver.findElement(By.id("CHAR_PREM_ID"));
        premID.click();
        WaitUtils.sleep(2000);
        premID.sendKeys(premiseID);
    }
    public void saSpTab(String spID) {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabMenu",5);
        driver.findElement(By.cssSelector("td[title='SA/SP']")).click();

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",5);
        WaitUtils.waitForVisible(driver,By.xpath("//input[@name='SA_SP$SP_ID']"));
        WebElement spIDxtField = driver.findElement(By.xpath("//input[@name='SA_SP$SP_ID']"));
        spIDxtField.sendKeys(spID);
    }
    public void chargesQtyTab() {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabMenu",5);
        driver.findElement(By.cssSelector("td[title='Chars, Qty & Rec. Charges']")).click();

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",5);
        WaitUtils.waitForFrameAndSwitch(driver,"SA_CHAR",5);
        WebElement CHARACTERISTIC_TYPE1 = driver.findElement(By.cssSelector("select[id='SA_CHAR:0$CHAR_TYPE_CD']"));
        Select dropdown_CHARACTERISTIC_TYPE1 = new Select(CHARACTERISTIC_TYPE1);
        dropdown_CHARACTERISTIC_TYPE1.selectByVisibleText("Bill Cycle");
        WaitUtils.waitForVisible(driver,By.cssSelector("input[id='SA_CHAR:0$CHAR_VAL_FK1']"));
        driver.findElement(By.cssSelector("input[id='SA_CHAR:0$CHAR_VAL_FK1']")).sendKeys("MHH1");

        driver.findElement(By.id("IM_SA_CHAR:0$Grid_btnAdd")).click();

        WaitUtils.waitForVisible(driver,By.cssSelector("select[id='SA_CHAR:1$CHAR_TYPE_CD']"));
        WebElement CHARACTERISTIC_TYPE2 = driver.findElement(By.cssSelector("select[id='SA_CHAR:1$CHAR_TYPE_CD']"));
        Select dropdown_CHARACTERISTIC_TYPE2 = new Select(CHARACTERISTIC_TYPE2);
        dropdown_CHARACTERISTIC_TYPE2.selectByVisibleText("Pricing Point");
        WaitUtils.waitForVisible(driver,By.cssSelector("input[id='SA_CHAR:1$CHAR_VAL']"));
        driver.findElement(By.cssSelector("input[id='SA_CHAR:1$CHAR_VAL']")).sendKeys("CTFI");

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        driver.findElement(By.cssSelector("input[id='IM_SAVE']")).click();
    }
    public void VerifychargesQty() {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabMenu", 5);
        driver.findElement(By.cssSelector("td[title='Chars, Qty & Rec. Charges']")).click();
    }
    public String SaID(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.sleep(2000);
        WebElement accIDtxtField =driver.findElement(By.xpath("//input[@name='SA_ID']"));
        WaitUtils.waitForTextToBePresentInValue(driver,accIDtxtField);
        return accIDtxtField.getAttribute("value");
    }

    public void navigateToAdjustment(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        driver.findElement(By.xpath("//img[@name='IM_SaInfo_saCnxt']")).click();

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        driver.findElement(By.xpath("//span[text()='Go To Adjustment']")).click();
        driver.findElement(By.xpath("//span[text()='Add']")).click();
    }

}
