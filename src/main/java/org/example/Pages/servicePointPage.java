package org.example.Pages;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonEnumDefaultValue;
import org.example.Utils.DBQueries;
import org.example.Utils.DBUtils;
import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class servicePointPage {
    private WebDriver driver;
    String parentWindow;
    public servicePointPage() {
        this.driver = DriverManager.getDriver();
    }

    public void navigateToCCBServicePoint() {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);

        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);
        WaitUtils.waitAndClick(driver,By.xpath("//li[@id='mainMenu']"),10);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Customer Information']"), 10);
        WaitUtils.waitAndClick(driver, By.xpath("(//span[text()='Add'])[24]"), 10);
//        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Add']"), 5);

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
        //WaitUtils.waitForFrameAndSwitch(driver, "spGrid_spChrGrid", 3);
        WaitUtils.sleep(2000);
        WebElement spGridFrame=driver.findElement(By.cssSelector("iframe[id='spGrid_spChrGrid']"));
        driver.switchTo().frame(spGridFrame);
        driver.findElement(By.cssSelector("input[id='SP_CHAR:0$CHAR_TYPE_CD']")).sendKeys("CM-SUPLR");
        driver.findElement(By.cssSelector("input[id='SP_CHAR:0$CHAR_VAL']")).click();
        WaitUtils.sleep(1000);
        driver.findElement(By.cssSelector("input[id='SP_CHAR:0$CHAR_VAL']")).sendKeys("EDFE");
        driver.findElement(By.id("IM_SP_CHAR:0$Grid_btnAdd")).click();
        WaitUtils.sleep(1000);
        driver.findElement(By.cssSelector("input[id='SP_CHAR:1$CHAR_TYPE_CD']")).sendKeys("CM-MCLSS");
        driver.findElement(By.cssSelector("input[id='SP_CHAR:1$CHAR_VAL']")).click();
        WaitUtils.sleep(1000);
        driver.findElement(By.cssSelector("input[id='SP_CHAR:1$CHAR_VAL']")).sendKeys("C");
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabMenu", 3);
        driver.findElement(By.xpath("//td[text()='Geo']")).click();

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        //WaitUtils.waitForFrameAndSwitch(driver, "dataGrid", 3);
        WaitUtils.sleep(2000);
        WebElement dataGridFrame=driver.findElement(By.cssSelector("iframe[id='dataGrid']"));
        driver.switchTo().frame(dataGridFrame);
        WebElement characteristicsB = driver.findElement(By.cssSelector("select[id='SP_GEO:0$GEO_TYPE_CD']"));
        Select dropdown_characteristicsB = new Select(characteristicsB);
        dropdown_characteristicsB.selectByVisibleText("MPAN Core Identifier");
        driver.findElement(By.cssSelector("input[id='SP_GEO:0$GEO_VAL']")).sendKeys(MPANID);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        driver.findElement(By.cssSelector("input[id='IM_SAVE']")).click();
        if (WaitUtils.isAlertPresent(driver,20)) {
            Alert alert =driver.switchTo().alert();
            alert.accept();
        }
    }

    public String servicePointID(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.getWait(driver,10);
        WaitUtils.waitForPageLoad(driver,10);
        WebElement spIDtxtField = driver.findElement(By.xpath("//input[@name='SP_ID']"));;
        WaitUtils.waitForTextToBePresentInValue(driver,spIDtxtField);
        return spIDtxtField.getAttribute("value");
    }
    public void enterServicePoint(String servicepointid){
        String parentWindow= driver.getWindowHandle();
        WaitUtils.waitAndClick(driver,By.cssSelector("img[id='IM_SP_ID']"),10);
        WaitUtils.waitForNewWindow(driver,10);
        for(String window:driver.getWindowHandles()){
            if(!window.equals(parentWindow)){
                driver.switchTo().window(window);
                break;
            }
        }
        driver.findElement(By.xpath("//input[@id='SP_ID']")).sendKeys(servicepointid);
        WaitUtils.waitAndClickFluent(driver,By.xpath("//input[@id='BU_Main_spSearch']"),10);
        driver.switchTo().window(parentWindow);
        WaitUtils.sleep(2000);
    }
    public void characteristicsTab(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",10);
        WaitUtils.waitForFrameAndSwitch(driver,"tabMenu",10);
        WaitUtils.waitAndClick(driver,By.xpath("//td[contains(text(),'Characteristics')]"),10);

    }
    public void ValidateCharacteristics(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",10);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",10);
        WebElement GridFrame=driver.findElement(By.cssSelector("iframe[id='spGrid_spChrGrid']"));
        driver.switchTo().frame(GridFrame);
        WaitUtils.getWait(driver,10);
        WaitUtils.waitForVisible(driver,By.xpath("//span[text()='Gas Small Large Supply Point Indicator' and contains(text(),'Large Supply Point')]"));
    }

    public void NavigateToEmergencyBroadcastDetails(String Quantity,String supplypoint){
        parentWindow=driver.getWindowHandle();
        System.out.println(parentWindow);
        int actualQuantity=Integer.parseInt(Quantity);
        if((actualQuantity<=73200 && supplypoint.equals("LSP")||(actualQuantity>=73200 && supplypoint.equals("LSP")))) {
            System.out.println("Condition Passed");
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver, "main", 10);
            WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 10);
            WaitUtils.waitAndClick(driver, By.xpath("//img[@id='IM_SpInfo_spIdCntxt']"), 10);
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver, "main", 10);
            WaitUtils.waitAndClick2(driver, By.xpath("//span[contains(text(),'Go To Emergency and Broadcast Details')]"), 10);
            WaitUtils.getWait(driver, 20);
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
            for (String window : driver.getWindowHandles()) {
                if (!window.equals(parentWindow)) {
                    driver.switchTo().window(window);
                    break;
                    }
                }
            }

        }
//        public void EMCWarning(){
//        WaitUtils.getWait1(driver,20);
//        WaitUtils.waitForVisible(driver,By.xpath("//span[contains(text(),'EMC details are mandatory')]"));
//        }
        public void AcceptAlertAndCancel(){
           WaitUtils.getWait(driver,20);
           WaitUtils.waitAndClick(driver,By.xpath("//input[@value='Save']"),10);
           WaitUtils.isAlertPresent(driver,10);
            if(WaitUtils.isAlertPresent(driver,30)) {
                Alert alert = driver.switchTo().alert();
                alert.accept();
                WaitUtils.getWait(driver,10);
            }
            WaitUtils.getWait(driver,20);
            WaitUtils.waitAndClick(driver,By.xpath("//input[@value='Cancel']"),10);
            driver.switchTo().window(parentWindow);
        }
        public void validateMdm(){
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver, "main", 10);
            WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 10);
            WaitUtils.waitAndClick(driver, By.xpath("//img[@id='IM_SpInfo_spIdCntxt']"), 10);
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver, "main", 10);
            WaitUtils.waitAndClick(driver,By.xpath("//span[contains(text(),'Go To MDM')]"),10);
            WaitUtils.getWait1(driver,20);
            //WaitUtils.waitForPresence(driver,By.xpath("(//span[contains(text(),'Service Point')]/parent::td)[1]"));
            WebElement logFrame=driver.findElement(By.cssSelector("iframe[id='tabMenu']"));
            driver.switchTo().frame(logFrame);
            WaitUtils.sleep(1000);
            WaitUtils.waitAndClick(driver,By.xpath("//td[contains(text(),'Log')]"),10);
            WaitUtils.getWait(driver,20);
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver,"main",10);
            WaitUtils.waitForFrameAndSwitch(driver,"tabPage",10);
            WaitUtils.waitForPresence(driver,By.xpath("//span[contains(text(),'Service Point Log')]"));
        }
        public void goback(){
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
            WaitUtils.waitAndClick(driver, By.xpath("//ou-button[@id='IM_GOBACK']"),15);
        }
        public void fillcontactDetails(){
        WebElement contactType=driver.findElement(By.xpath("//select[@oraselect='lookup:CM_EMRCONTTYP']"));
        Select select=new Select(contactType);
        select.selectByVisibleText("Emergency");
        WebElement contactTitle=driver.findElement(By.xpath("(//select[@orafield='contactTitle'])[1]"));
        Select select1=new Select(contactTitle);
        select1.selectByIndex(3);
        WaitUtils.getWait(driver,10);
        driver.findElement(By.xpath("(//input[@orafield='contactSurname'])[1]")).sendKeys("mnrt");
        driver.findElement(By.xpath("(//input[@orafield='contactInitials'])[1]")).sendKeys("rt");
        driver.findElement(By.xpath("(//input[@orafield='contactFirstName'])[1]")).sendKeys("nt");
        driver.findElement(By.xpath("(//input[@orafield='contactJobTitle'])[1]")).sendKeys("tyur");
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = today.format(dateFormat);
            System.out.println(formattedDate);
            driver.findElement(By.xpath("(//input[@id='contactEffectiveDate_date_0'])[1]")).sendKeys(formattedDate);
            WebElement CommunicationType=driver.findElement(By.xpath("(//select[@oraselect='lookup:CMGLK0139'])[1]"));
            Select select2=new Select(CommunicationType);
            select2.selectByIndex(3);
            WaitUtils.getWait(driver,20);
            driver.findElement(By.xpath("(//input[@id='contactElectronicAddress_0_0'])[1]")).sendKeys("9576435623");
        }
        public void PriorityConsumerDetails(){
        WebElement ConsumerIndicator=driver.findElement(By.xpath("//select[@oraselect='charType:CM-PRCID;']"));
        Select select=new Select(ConsumerIndicator);
        select.selectByIndex(1);
        WaitUtils.getWait(driver,20);
        WebElement priorityconsumer= driver.findElement(By.xpath("//select[@oraselect='charType:CM-GSCON;']"));
        Select select1=new Select(priorityconsumer);
        select1.selectByIndex(2);
        }
        public void fillCustomerDetails(){
            WebElement contactType=driver.findElement(By.xpath("//select[@oraselect='lookup:CMGLK0136']"));
            Select select=new Select(contactType);
            select.selectByVisibleText("Emergency");
            WebElement contactTitle=driver.findElement(By.xpath("(//select[@orafield='contactTitle'])[2]"));
            Select select1=new Select(contactTitle);
            select1.selectByIndex(3);
            WaitUtils.getWait(driver,10);
            driver.findElement(By.xpath("(//input[@orafield='contactSurname'])[3]")).sendKeys("mnrt");
            driver.findElement(By.xpath("(//input[@orafield='contactInitials'])[3]")).sendKeys("rt");
            driver.findElement(By.xpath("(//input[@orafield='contactFirstName'])[2]")).sendKeys("nt");
            driver.findElement(By.xpath("(//input[@orafield='contactJobTitle'])[2]")).sendKeys("tyur");
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = today.format(dateFormat);
            System.out.println(formattedDate);
            driver.findElement(By.xpath("(//input[@id='contactEffectiveDate_0'])[2]")).sendKeys(formattedDate);
            driver.findElement(By.id("contactGeneralPriorityNeedsNotes_0")).sendKeys("na");
        }
    }