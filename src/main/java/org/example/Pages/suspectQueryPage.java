package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class suspectQueryPage {
    private final WebDriver driver;
    String perId;
    public suspectQueryPage(){
        this.driver= DriverManager.getDriver();
    }
    public void navigateSuspectQueryPortal(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);
        WaitUtils.waitAndClick(driver,By.xpath("//li[@id='mainMenu']"),5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Sales & Marketing']"), 5);
        WaitUtils.waitAndClick(driver,By.xpath("//span[text()='Suspect Query Portal']"),5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",5);
        WaitUtils.waitForVisible(driver,By.xpath("//a[text()='Add a Suspect']"));
    }
    public void suspectDetails(){
        WaitUtils.waitAndClick(driver,By.xpath("//a[text()='Add a Suspect']"),10);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",10);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[id='uiMap']")));
        String companyName=WaitUtils.generateName();
        driver.findElement(By.cssSelector("input[orafield='companyName']")).sendKeys(companyName);
        LocalDate datetime=LocalDate.now();
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date=datetime.format(dateTimeFormatter);
        driver.findElement(By.cssSelector("input[orafield='initialEnquiryDate']")).sendKeys(date);
        WebElement selectYes=driver.findElement(By.cssSelector("select[id='yes']"));
        WaitUtils.selectByVisibleText(selectYes,"Yes");
        WaitUtils.waitForVisible(driver,By.cssSelector("select[orafield='hasConsultantAgreement']"));
        WebElement SelectConsultant= driver.findElement(By.cssSelector("select[orafield='hasConsultantAgreement']"));
        WaitUtils.selectByVisibleText(SelectConsultant,"No");
        driver.findElement(By.cssSelector("input[orafield='keyDecisionMaker']")).sendKeys("Indranil, Mukherjee");
        driver.findElement(By.cssSelector("input[id='checkUnincorporated']")).click();
        WebElement EnquirySource=driver.findElement(By.cssSelector("select[orafield='enquirySource']"));
        WaitUtils.selectByVisibleText(EnquirySource,"Phone");
        driver.findElement(By.cssSelector("input[orafield='natureOfEnquiry']")).sendKeys("Mail");
        WebElement AreaOfInterest=driver.findElement(By.cssSelector("select[oraerrorelement='areaOfInterest']"));
        WaitUtils.selectByVisibleText(AreaOfInterest,"Gas Supply");
        LocalDate date1 = LocalDate.now().plusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = date1.format(formatter);
        System.out.println(formattedDate);
        driver.findElement(By.cssSelector("input[orafield='contractRenewalDate']")).sendKeys(formattedDate);
        WebElement SiteUsage= driver.findElement(By.cssSelector("select[orafield='clobDetails/siteUsage']"));
        WaitUtils.selectByVisibleText(SiteUsage,"Gas");
        driver.findElement(By.cssSelector("input[orafield='approximateEnergyVolumeRequired']")).sendKeys("3400");
        driver.findElement(By.cssSelector("input[orafield='approximateEnergyValueConsumption']")).sendKeys("1200");
        driver.findElement(By.cssSelector("input[orafield='approximateNumberOfSites']")).sendKeys("3");
        driver.findElement(By.cssSelector("input[orafield='approximateNumberOfGasSites']")).sendKeys("2");
        driver.findElement(By.cssSelector("input[orafield='approximateVolumeForGas']")).sendKeys("230");
        WebElement IndustrySector=driver.findElement(By.cssSelector("select[orafield='clobDetails/industrySector']"));
        WaitUtils.selectByVisibleText(IndustrySector,"B007 - Manufacture (Basic Materials)");
        driver.findElement(By.xpath("//input[@orafield='clobDetails/estimatedAnnualSpend']")).sendKeys("89700");
        driver.findElement(By.xpath("//input[@id='numEmp']")).sendKeys("2");
        driver.findElement(By.xpath("//input[@orafield='leadSource']")).sendKeys("1");
        driver.findElement(By.xpath("//input[@orafield='numberOfExportSite']")).sendKeys("3");
        driver.findElement(By.xpath("//input[@orafield='approximateExportVolume']")).sendKeys("1080");
        WebElement BusinessType=driver.findElement(By.xpath("//select[@orafield='industrySegmentation/businessType']"));
        WaitUtils.selectByVisibleText(BusinessType,"B010 - Construction");
        WebElement siccode=driver.findElement(By.xpath("//select[@orafield='industrySegmentation/SICCode']"));
        WaitUtils.selectByVisibleText(siccode,"acts of credit bureaus");
        WebElement importSegment=driver.findElement(By.xpath("//select[@orafield='industrySegmentation/operationalSegment']"));
        WaitUtils.selectByVisibleText(importSegment,"Multi Customers");
        WebElement ExportSegment=driver.findElement(By.xpath("//select[@orafield='industrySegmentation/exportOperationalSegment']"));
        WaitUtils.selectByVisibleText(ExportSegment,"Export");
        WebElement PreferCommunication= driver.findElement(By.xpath("//select[@orafield='preferredCommunicationMethod']"));
        WaitUtils.selectByVisibleText(PreferCommunication,"Mail");
        WebElement allowEmail=driver.findElement(By.xpath("//select[@orafield='clobDetails/allowEmail']"));
        WaitUtils.selectByVisibleText(allowEmail,"Allow");
        WebElement BulkEmail=driver.findElement(By.xpath("//select[@orafield='clobDetails/allowBulkEmail']"));
        WaitUtils.selectByVisibleText(BulkEmail,"Allow");
        WebElement allowPhone=driver.findElement(By.xpath("//select[@orafield='clobDetails/allowPhone']"));
        WaitUtils.selectByVisibleText(allowPhone,"Allow");
        WebElement allowMail= driver.findElement(By.xpath("//select[@orafield='clobDetails/allowMail']"));
        WaitUtils.selectByVisibleText(allowMail,"Allow");
        WebElement sendMarketingDetails=driver.findElement(By.xpath("//select[@orafield='companyContactDetails/sendMarketingMaterials']"));
        WaitUtils.selectByVisibleText(sendMarketingDetails,"Yes");
        driver.findElement(By.xpath("//input[@orafield='companyContactDetails/email']")).sendKeys("add@edfenergy.com");
        driver.findElement(By.xpath("//input[@orafield='companyContactDetails/address1']")).sendKeys("BOSTON");
        driver.findElement(By.xpath("//input[@orafield='companyContactDetails/city']")).sendKeys("WALES");
        WebElement country=driver.findElement(By.xpath("//select[@orafield='companyContactDetails/country']"));
        WaitUtils.selectByVisibleText(country,"United Kingdom");
        driver.findElement(By.xpath("//input[@orafield='companyContactDetails/postal']")).sendKeys("S12GU");
        WebElement phoneType=driver.findElement(By.xpath("//select[@orafield='phoneType']"));
        WaitUtils.selectByVisibleText(phoneType,"Business Phone - 11 digits");
        driver.findElement(By.xpath("//input[@orafield='phone']")).sendKeys("76897577453");
        WaitUtils.waitAndClick2(driver,By.xpath("//input[@oramdlabel='SAVE_BTN_LBL']"),10);
    }
    public String personid(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",10);
        WaitUtils.waitForVisible(driver,By.xpath("//div[text()='Suspects Portal']"));
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        //driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[id='zoneMapFrame_2']")));
        WebElement personId=driver.findElement(By.xpath("//div[@id='dataExplorerFilterText3']/span[2]"));
        perId=personId.getText().trim();
        System.out.println("Created Person Id="+perId);
        return perId;
    }
    public void Creditcheck(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",10);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[id='zoneMapFrame_2']")));
        WebElement creditCheck=driver.findElement(By.xpath("//input[@value='Initiate Credit Check']"));
        JavascriptExecutor js=(JavascriptExecutor)driver;
        js.executeScript("arguments[0].click();",creditCheck);
        WaitUtils.sleep(4000);
        //WaitUtils.waitAndClick(driver,By.xpath("//input[@value='Initiate Credit Check']"),20);
    }
    public void salesDetails(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",10);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[id='zoneMapFrame_2']")));
        String parentWindow=driver.getWindowHandle();
        WaitUtils.waitAndClick(driver,By.xpath("//input[@value='Assign / Update Owner']"),10);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
        for(String window: driver.getWindowHandles()){
            if(!window.equals(parentWindow)){
                driver.switchTo().window(window);
                break;
            }
        }
        WebElement salesTeam=driver.findElement(By.xpath("//select[@id='userGroupSelect']"));
        WaitUtils.selectByVisibleText(salesTeam,"07_Sales - Mid Markets 1");
        WebElement salesContact=driver.findElement(By.xpath("//select[@id='user']"));
        WaitUtils.selectByVisibleText(salesContact,"AAL07, Development");
        WebElement salesTeam1=driver.findElement(By.xpath("//select[@id='userGroupSelect1']"));
        WaitUtils.selectByVisibleText(salesTeam1,"Export Sales");
        WebElement salesContact2= driver.findElement(By.xpath("//select[@id='user1']"));
        WaitUtils.selectByVisibleText(salesContact2,"AAL07, Development");
        WaitUtils.waitAndClick(driver,By.xpath("//input[@value='Save']"),10);
        driver.switchTo().window(parentWindow);
        WaitUtils.sleep(3000);
    }
    public void qualify(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",10);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[id='zoneMapFrame_2']")));
        String parentWindow=driver.getWindowHandle();
        WaitUtils.waitAndClick(driver,By.xpath("//input[@oramdlabel='CM_UPD_STATUS']"),10);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
        for(String window: driver.getWindowHandles()){
            if(!window.equals(parentWindow)){
                driver.switchTo().window(window);
                break;
            }
        }
        WebElement QualificationStatus= driver.findElement(By.xpath("//select[@orafield='qualificationStatus']"));
        WaitUtils.selectByVisibleText(QualificationStatus,"Qualified");
        driver.findElement(By.xpath("//textarea[@orafield='changeReason']")).sendKeys("Testing");
        WaitUtils.waitAndClick(driver,By.xpath("//input[@value='Save']"),10);
        driver.switchTo().window(parentWindow);
    }



}
