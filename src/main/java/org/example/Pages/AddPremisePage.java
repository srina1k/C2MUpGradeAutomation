package org.example.Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.example.Utils.DriverManager;
import org.example.Utils.*;
import java.time.Duration;
import java.util.Set;

public class AddPremisePage {
    private final WebDriver driver;

    public AddPremisePage() {
        this.driver = DriverManager.getDriver();
    }
    private By customerHyperLink = By.xpath("//span[@title='Go To Opportunity for Person ']");
    public void NavigateToPremise(String premiseID) throws ElementClickInterceptedException,NoSuchElementException {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",20);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",15);
        //driver.switchTo().frame("tabPage");
        String currentFrame = (String) ((JavascriptExecutor) driver).executeScript("return window.frameElement? window.frameElement.name : 'default';");
        System.out.println("Current frame switched to: " + currentFrame);
        WaitUtils.sleep(3000);
        WaitUtils.waitAndClick(driver, customerHyperLink, 20);
        WaitUtils.sleep(3000);
        try {
            WaitUtils.waitForPresence(driver, By.xpath("//a[normalize-space()='Add Site']"));
            WaitUtils.waitAndClick(driver, By.xpath("//a[normalize-space()='Add Site']"), 20);
            WaitUtils.sleep(3000);
        } catch (NoSuchElementException e) {
            System.out.println("Element not found"+e.getMessage());
        } driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 10);
        WaitUtils.getWait(driver,20);
        By[] menuSequence2 = {
                By.id("IM_menuButton"), By.xpath("//li[@id='mainMenu']"), By.xpath("//li[@id='CI_MAINMENU_topMenuItem0x9']"), By.xpath("(//span[contains(text(),'Add')])[13]")};
        for (By menuItem2 : menuSequence2){
            WaitUtils.waitAndClick(driver, menuItem2, 2);
        }
        String mainWindow = driver.getWindowHandle();
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",15);
        WaitUtils.waitAndClick(driver, By.xpath("//img[@id='IM_PREM_ID']"), 30);
        System.out.println(mainWindow);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
        for (String window : driver.getWindowHandles()) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
        new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.titleContains("Premise"));
        WebElement premiseField = WaitUtils.waitForPVisible(driver, By.xpath("//input[@id='PREM_ID']"), 10);
        premiseField.clear();
        premiseField.sendKeys(premiseID);
//        WaitUtils.waitAndClick(driver, By.id("BU_premId_premSrch"), 10);
//        System.out.println(driver.getWindowHandle());
//        driver.switchTo().window(mainWindow);
    }
    public void clickSearch() {
        WaitUtils.waitAndClick(driver, By.id("BU_premId_premSrch"), 2);
        WaitUtils.sleep(5000);
        String mainHandle = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(mainHandle);
        driver.switchTo().defaultContent();
        WaitUtils.getWait(driver,20);
        WaitUtils.waitForFrameAndSwitch(driver,"main",20);
        WaitUtils.getWait(driver,30);
        try {
            WaitUtils.ElementToBeClickable(driver, By.xpath("//input[@id='Bundefined']"), 20);
            WaitUtils.waitForVisible(driver, By.xpath("//input[@id='Bundefined']"));
            WaitUtils.waitAndClick(driver, By.xpath("//input[@id='Bundefined']"), 20);
            WaitUtils.sleep(4000);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(e.getMessage());
        } //driver.findElement(By.xpath("//input[@id='Bundefined']")).click();
        WaitUtils.sleep(10000);

        for(String premise_details : driver.getWindowHandles()){
            driver.switchTo().window(premise_details);
        }
    }
    public void servicePointSelect(){
        WebElement service_point = WaitUtils.waitForPVisible(driver, By.xpath("/html/body/table[2]/tbody/tr[4]/td[2]/select"),5 );
        Select dropdown_service_point = new Select(service_point);
        dropdown_service_point.selectByIndex(1);
        driver.findElement(By.xpath("//input[@id='annualConsumption']")).sendKeys("100");
        driver.findElement(By.xpath("//input[@id='agreedCapacity']")).sendKeys("120");
    }
    public void premiseNavigation(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        By[] menuSequence2 = {
                By.id("IM_menuButton"), By.xpath("//li[@id='mainMenu']"), By.xpath("//li[@id='CI_MAINMENU_topMenuItem0x9']"), By.xpath("(//span[contains(text(),'Add')])[13]")};
        for (By menuItem2 : menuSequence2){
            WaitUtils.waitAndClick(driver, menuItem2, 2);
        }
    }
    public void premiseDetails(){

        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitForVisible(driver,By.xpath("//select[@id='PREM_TYPE_CD']"));
        WebElement premise_type = driver.findElement(By.xpath("//select[@id='PREM_TYPE_CD']"));
        Select dropdown_premise_type = new Select(premise_type);
        dropdown_premise_type.selectByVisibleText("Commercial - Electric");

        WebElement addresLine1 = driver.findElement(By.xpath("//input[@id='ADDRESS1']"));
        addresLine1.clear();
        addresLine1.sendKeys("uk");

        driver.switchTo().defaultContent();
        driver.switchTo().frame("main");
        driver.findElement(By.xpath("//input[@id='IM_SAVE']")).click();
    }
    public String premiseID(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WebElement premisetxtField = driver.findElement(By.xpath("//input[@name='PREM_ID']"));;
        WaitUtils.waitForTextToBePresentInValue(driver,premisetxtField);
        return premisetxtField.getAttribute("value");
    }
    public void newMeasurementClassCOMC15(){
        WebElement New_Msmt_Class = driver.findElement(By.xpath("//select[@id='measurementClassDropDown']"));
        Select dropdown_New_Msmt_Class = new Select(New_Msmt_Class);
        dropdown_New_Msmt_Class.selectByVisibleText("A - Non Half Hourly Metered");
    }
    public void newMeasurementClassCOMC16(){
        WebElement New_Msmt_Class = driver.findElement(By.xpath("//select[@id='measurementClassDropDown']"));
        Select dropdown_New_Msmt_Class = new Select(New_Msmt_Class);
        dropdown_New_Msmt_Class.selectByVisibleText("C - HH Metered in 100kW Premises");
    }
    public void newProfileClassCOMC15(){
        WebElement New_Profile_Class = driver.findElement(By.xpath("//select[@oraselect='charType:CM-PCLSS;']"));
        Select dropdown_New_Profile_Class = new Select(New_Profile_Class);
        dropdown_New_Profile_Class.selectByVisibleText("3 - Non-domestic Unrestricted");
    }
    public void newProfileClassCOMC16(){
        WebElement New_Profile_Class = driver.findElement(By.xpath("//select[@oraselect='charType:CM-PCLSS;']"));
        Select dropdown_New_Profile_Class = new Select(New_Profile_Class);
        dropdown_New_Profile_Class.selectByVisibleText("00 - Half Hourly MPAN");
    }
    public void NewMTCCOMC15(){
        WebElement New_MTC = driver.findElement(By.xpath("//select[@oraselect='charType:CM-MTSCD;']"));
        Select dropdown_New_MTC = new Select(New_MTC);
        dropdown_New_MTC.selectByVisibleText("801 - NHH Unrestricted 1-rate Non-Prog Credit Meter");
    }
    public void NewMTCCOMC16(){
        WebElement New_MTC = driver.findElement(By.xpath("//select[@oraselect='charType:CM-MTSCD;']"));
        Select dropdown_New_MTC = new Select(New_MTC);
        dropdown_New_MTC.selectByVisibleText("845 - HH COP5 And Above With Comms");
    }
    public void LLFCCOMC15(){
        WebElement New_LLFC = driver.findElement(By.xpath("//select[@oraselect='charType:CM-LLF;']"));
        Select dropdown_New_LLFC = new Select(New_LLFC);
        dropdown_New_LLFC.selectByVisibleText("557 - Look in MDD for description for LLF/DNO combination.");
    }
    public void LLFCCOMC16(){
        WebElement New_LLFC = driver.findElement(By.xpath("//select[@oraselect='charType:CM-LLF;']"));
        Select dropdown_New_LLFC = new Select(New_LLFC);
        dropdown_New_LLFC.selectByVisibleText("H87-Refer to Elexon MDD Tables for DNO-specific descriptions");
    }
    public void SSCCOMC15(){
        WebElement New_SSC = driver.findElement(By.xpath("//select[@oraselect='charType:CM-SSCD;']"));
        Select dropdown_New_SSC = new Select(New_SSC);
        dropdown_New_SSC.selectByVisibleText("0393 - Unrestricted");
    }
    public void SSCCOMC16(){
        WebElement New_SSC = driver.findElement(By.xpath("//select[@oraselect='charType:CM-SSCD;']"));
        Select dropdown_New_SSC = new Select(New_SSC);
        dropdown_New_SSC.selectByVisibleText("0000");
    }
    public void retrevalAMRRequest(){
        WebElement RETRIEVAL_METHOD = driver.findElement(By.xpath("//select[@oraselect='charType:CM-RTVMD;']"));
        Select dropdown_RETRIEVAL_METHOD = new Select(RETRIEVAL_METHOD);
        dropdown_RETRIEVAL_METHOD.selectByVisibleText("R - Remote reading");

        WebElement AMR_FLAG = driver.findElement(By.xpath("//select[@oraselect='charType:CM-AMRFL;']"));
        Select dropdown_AMR_FLAG = new Select(AMR_FLAG);
        dropdown_AMR_FLAG.selectByVisibleText("No");

        WebElement REQST_E_Status = driver.findElement(By.xpath("//select[@oraselect='charType:CM-RQENS;']"));
        Select dropdown_REQST_E_Status = new Select(REQST_E_Status);
        dropdown_REQST_E_Status.selectByVisibleText("E - Energised");
        driver.findElement(By.xpath("//input[@id='agreedCapacityCOMC']")).sendKeys("120");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/estimatedAnnualConsumption']")).sendKeys("100");
    }
    public void DaDcMopCOMC16() {
        WebElement DATA_Collector = driver.findElement(By.xpath("//select[@orafield='boGroup/changeOfMeasurementClass/dataCollector']"));
        Select dropdown_DATA_Collector = new Select(DATA_Collector);
        dropdown_DATA_Collector.selectByVisibleText("UKDC-IMServ Europe Ltd");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/contractReferenceDc']")).sendKeys("LONDHHDC01");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/serviceReferenceDc']")).sendKeys("CNCT");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/serviceLevelReferenceDc']")).sendKeys("PRFD");

        WebElement DATA_AGGREGATOR = driver.findElement(By.xpath("//select[@orafield='boGroup/changeOfMeasurementClass/dataAggregator']"));
        Select dropdown_DATA_AGGREGATOR = new Select(DATA_AGGREGATOR);
        dropdown_DATA_AGGREGATOR.selectByVisibleText("UKDC-IMServ Europe Ltd");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/contractReferenceDa']")).sendKeys("LONDHHDA01");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/serviceReferenceDa']")).sendKeys("CNCT");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/serviceLevelReferenceDa']")).sendKeys("PRFD");

        WebElement METER_OPERATOR = driver.findElement(By.xpath("//select[@orafield='boGroup/changeOfMeasurementClass/meterOperator']"));
        Select dropdown_METER_OPERATOR = new Select(METER_OPERATOR);
        dropdown_METER_OPERATOR.selectByVisibleText("LOND-EDF Energy plc");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/contractReferenceMo']")).sendKeys("LONDHHMOP1");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/serviceReferenceMo']")).sendKeys("0000");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/serviceLevelReferenceMo']")).sendKeys("0000");

        ScreenShotUtils.captureScreenshotToWord("COMC16P1.docx", "Adding DA, DC and MOP details");
    }
    public void DataColNewConnection1(){
        Select s2 = new Select(driver.findElement(By.xpath("//select[@orafield='boGroup/dataCollector']")));
        s2.selectByVisibleText("SWEB-EDF Energy plc");
        driver.findElement(By.id("contDC")).sendKeys("SWEBDCLOND");
        driver.findElement(By.id("svcDC")).sendKeys("1");
        driver.findElement(By.id("svcLvlDC")).sendKeys("1");
    }
    public void DataAggNewConnection1(){
        Select s7 = new Select(driver.findElement(By.xpath("//select[@orafield='boGroup/dataAggregator']")));
        s7.selectByVisibleText("SWEB-EDF Energy plc");
        driver.findElement(By.id("contDA")).sendKeys("SWEBDALOND");
        driver.findElement(By.id("svcDA")).sendKeys("1");
        driver.findElement(By.id("svcLvlDA")).sendKeys("1");
    }
    public void MopNewConnection1(){
        Select s8 = new Select(driver.findElement(By.xpath("//select[@orafield='boGroup/meterOperator']")));
        s8.selectByVisibleText("LOND-EDF Energy plc");
        driver.findElement(By.id("contMO")).sendKeys("LONDNHHMO1");
        driver.findElement(By.id("svcMO")).sendKeys("0000");
        driver.findElement(By.id("svcLvlMO")).sendKeys("0000");
    }
    public void DataColNewConnection2(){
        Select s2 = new Select(driver.findElement(By.xpath("//select[@orafield='boGroup/dataCollector']")));
        s2.selectByVisibleText("UKDC-IMServ Europe Ltd");
        driver.findElement(By.id("contDC")).sendKeys("LONDHHDC01");
        driver.findElement(By.id("svcDC")).sendKeys("CNCT");
        driver.findElement(By.id("svcLvlDC")).sendKeys("PRFD");
    }
    public void DataAggNewConnection2(){
        Select s7 = new Select(driver.findElement(By.xpath("//select[@orafield='boGroup/dataAggregator']")));
        s7.selectByVisibleText("UKDC-IMServ Europe Ltd");
        driver.findElement(By.id("contDA")).sendKeys("LONDHHDA01");
        driver.findElement(By.id("svcDA")).sendKeys("CNCT");
        driver.findElement(By.id("svcLvlDA")).sendKeys("PRFD");
    }
    public void MopNewConnection2(){
        Select s8 = new Select(driver.findElement(By.xpath("//select[@orafield='boGroup/meterOperator']")));
        s8.selectByVisibleText("LOND-EDF Energy plc");
        driver.findElement(By.id("contMO")).sendKeys("LONDHHMOP1");
        driver.findElement(By.id("svcMO")).sendKeys("0000");
        driver.findElement(By.id("svcLvlMO")).sendKeys("0000");
    }
    public void addressPostal(){
        WaitUtils.sleep(5000);
        WebElement premiseAddress = driver.findElement(By.xpath(" //td[@id='premId']"));
        String fullAddress = premiseAddress.getText();
        String addressLine = fullAddress.split(",")[0].trim();

        String postalCode = "";
        for (String part : fullAddress.split(",")){
            String value = part.trim().toUpperCase().replaceAll("\\s+"," ");
            if (part.trim().matches("^[A-Z]{1,2}[0-9][0-9A-Z]?\\s?[0-9][1-Z]{2}$")){
                postalCode = value;
                break;
            }
        }
        if (postalCode.isEmpty()){
            throw new RuntimeException("Postal code not found in address: " + fullAddress);
        }

        WebElement addressLineField = driver.findElement(By.xpath("//input[@orafield='boGroup/billingAddressLine1']"));
        addressLineField.sendKeys(addressLine);

        WebElement postalCodeField = driver.findElement(By.xpath("//input[@orafield='boGroup/billingPostal']"));
        postalCodeField.sendKeys(postalCode);

    }
    public void DaDcMopCOMC15(){
        WebElement DATA_Collector = driver.findElement(By.xpath("//select[@orafield='boGroup/changeOfMeasurementClass/dataCollector']"));
        Select dropdown_DATA_Collector = new Select(DATA_Collector);
        dropdown_DATA_Collector.selectByVisibleText("SWEB-EDF Energy plc");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/contractReferenceDc']")).sendKeys("SWEBDCLOND");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/serviceReferenceDc']")).sendKeys("1");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/serviceLevelReferenceDc']")).sendKeys("1");

        WebElement DATA_AGGREGATOR = driver.findElement(By.xpath("//select[@orafield='boGroup/changeOfMeasurementClass/dataAggregator']"));
        Select dropdown_DATA_AGGREGATOR = new Select(DATA_AGGREGATOR);
        dropdown_DATA_AGGREGATOR.selectByVisibleText("SWEB-EDF Energy plc");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/contractReferenceDa']")).sendKeys("SWEBDALOND");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/serviceReferenceDa']")).sendKeys("1");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/serviceLevelReferenceDa']")).sendKeys("1");

        WebElement METER_OPERATOR = driver.findElement(By.xpath("//select[@orafield='boGroup/changeOfMeasurementClass/meterOperator']"));
        Select dropdown_METER_OPERATOR = new Select(METER_OPERATOR);
        dropdown_METER_OPERATOR.selectByVisibleText("LOND-EDF Energy plc");

        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/contractReferenceMo']")).sendKeys("LONDHHMOP1");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/serviceReferenceMo']")).sendKeys("0000");
        driver.findElement(By.xpath("//input[@orafield='boGroup/changeOfMeasurementClass/serviceLevelReferenceMo']")).sendKeys("0000");

        ScreenShotUtils.captureScreenshotToWord( "COMC15P1.docx","Adding DA, DC and MOP details");
    }
    public void clickSave(){

        driver.findElement(By.xpath("//input[@value='Save']")).click();
        for(String premise : driver.getWindowHandles()) {
            driver.switchTo().window(premise);
            if(driver.getTitle().equals("Premise")){
                break;
            }
        }
        WaitUtils.waitForFrameAndSwitch(driver,"main",20);
        WaitUtils.waitAndClick(driver, By.id("scriptClose1"),15);
        System.out.println("Site Added Successfully");
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",10);
        driver.findElement(By.xpath("//ou-button[@id='IM_GOBACK']")).click();

        WaitUtils.sleep(5000);
        driver.findElement(By.xpath("//ou-button[@id='IM_GOBACK']")).click();

        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",10);
        WaitUtils.waitForVisible(driver, By.xpath("//span[text()='Opportunity for Person Log']"));
        driver.findElement(By.xpath("//span[text()='Opportunity for Person Log']")).click();
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",15);
    }
    public void siteDetails(){
//        WebElement personClick=driver.findElement(By.xpath("//a[@navoptcd='cmoppptlTabMenu']"));
//        JavascriptExecutor js=(JavascriptExecutor)driver;
//        js.executeScript("arguments[0].click();",personClick);
        WaitUtils.waitAndClick(driver, By.xpath("//a[@navoptcd='cmoppptlTabMenu']"),15);
        WaitUtils.sleep(2000);
        WaitUtils.waitAndClick(driver, By.xpath("//span[@title='Go To Opportunity for Person ']"),5);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",3);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",3);
        WaitUtils.waitAndClick(driver, By.xpath("(//span[@title='Opportunity for Person at SP - Maintenance'])[2]"),10);
        WaitUtils.sleep(5000);
        for(String premise_details : driver.getWindowHandles()){
            driver.switchTo().window(premise_details);
        }
        WebElement consu =  driver.findElement(By.xpath("//input[@id='annualConsumption']"));
        WebElement cap =  driver.findElement(By.xpath("//input[@id='agreedCapacity']"));
        consu.clear();
        consu.sendKeys("100");
        WaitUtils.getWait(driver,10);
        cap.clear();
        cap.sendKeys("120");
        WaitUtils.getWait(driver,10);
        driver.findElement(By.xpath("//input[@value='Save']")).click();
        for(String premise : driver.getWindowHandles()) {
            driver.switchTo().window(premise);
            if(driver.getTitle().equals("Opportunity for Person")){
                break;
            }
        }
    }
    public void customerHyperlink(){
        WaitUtils.waitAndClick(driver, customerHyperLink, 2);
    }
    public void addSiteMpan(String mpan){
        WaitUtils.waitAndClick(driver, By.xpath("//a[normalize-space()='Add Site']"), 5);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForVisible(driver,By.xpath("//input[@id='Bundefined']"));

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WebElement dropdown = driver.findElement(By.id("multiQueryZoneFilters1"));
        Select s = new Select(dropdown);
        s.selectByIndex(4);
        WaitUtils.sleep(2000);

        WebElement geoType = driver.findElement(By.id("geographicType1"));
        Select s1 = new Select(geoType);
        s1.selectByIndex(1);

        WebElement mpanText = driver.findElement(By.xpath("//input[@id='geographicValue1']"));
        mpanText.sendKeys(mpan);
        WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Search']"),5);
    }
    public void siteDetailsForLiveBilling(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitAndClick(driver,By.cssSelector("input[id='Bundefined']"),5);
        WaitUtils.sleep(5000);

        for(String premise_details : driver.getWindowHandles()){
            driver.switchTo().window(premise_details);
        }
    }
    public void servicePointSelectLiveBilling(){
        WebElement service_point = WaitUtils.waitForPVisible(driver, By.xpath("//select[@id='servicePointIdDisplay']"),10 );
        Select dropdown_service_point = new Select(service_point);
        dropdown_service_point.selectByIndex(1);
        driver.findElement(By.xpath("//input[@orafield='boGroup/customerOwnReference']")).sendKeys("DD");
        driver.findElement(By.xpath("//input[@id='annualConsumption']")).sendKeys("100");
        driver.findElement(By.xpath("//input[@id='agreedCapacity']")).sendKeys("120");
    }
}
