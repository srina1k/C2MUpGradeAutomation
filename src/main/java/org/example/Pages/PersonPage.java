package org.example.Pages;

import org.example.Utils.ScreenShotUtils;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.example.Utils.DriverManager;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.event.WindowAdapter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
public class PersonPage {
    private WebDriver driver;
    private Select s3;

    public PersonPage() {
        this.driver = DriverManager.getDriver();
    }

    public void AddOpportunityDetails(String _Scenarioname){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",20);
        WaitUtils.getWait(driver,20);
        //WebElement continueBtn=driver.findElement(By.xpath("//input[@id='Bundefined']"));
        if(!WaitUtils.isElementVisible(driver,By.xpath("//input[@id='Bundefined']"),10)){
            throw new NoSuchElementException("Element not visible");
        }
        WaitUtils.waitAndClick(driver, By.xpath("//input[@id='Bundefined']"),10);

        //WaitUtils.waitForFrameAndSwitch(driver,"uiMap",5);
        String currentFrame1 = (String) ((JavascriptExecutor) driver).executeScript("return window.frameElement? window.frameElement.name : 'default';");
        System.out.println("Current frame switched to: " + currentFrame1);
        WaitUtils.waitForPageLoad(driver,15);
        driver.switchTo().frame("uiMap");
        //WaitUtils.waitForPageLoad(driver,15);
        String month = LocalDate.now().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        WebElement ref = WaitUtils.waitForPVisible(driver, By.xpath("//textarea[@class='oraInput oraDefault']"), 20);
        ref.sendKeys("RT_" + month + "_2026" + _Scenarioname);

        WebElement managerDropdown = driver.findElement(By.xpath("//select[@id='user']"));
        Select s1 = new Select(managerDropdown);
        s1.selectByVisibleText("Elliott, David");

        WebElement priorityDropdown = driver.findElement(By.xpath("//select[@id='prio']"));
        Select s2 = new Select(priorityDropdown);
        s2.selectByVisibleText("Low");

        WebElement WinProb = driver.findElement(By.id("winProb"));
        Select s3 = new Select(WinProb);
        s3.selectByVisibleText("Eighty");
//        WebElement OpportunityType = driver.findElement(By.xpath("//select[@id='oppTypeFlag']"));
//        Select s4 = new Select(OpportunityType);
//        s4.selectByVisibleText("New Connection");

    }
    public void basketInd(){
        WebElement basInd = driver.findElement(By.xpath("//input[@name='basketIndicator']"));
        if (!basInd.isSelected()){
            basInd.click();
        }
    }
    public void MCTId(){
        driver.findElement(By.xpath("//input[@id='multiCustId']")).sendKeys("MCTS19-RT-2026");
    }
    public void opportunityType(String OppType){
        WebElement opptype = driver.findElement(By.id("oppTypeFlag"));
        Select s4 = new Select(opptype);
        s4.selectByVisibleText(OppType);
    }
    public void startEndDate(){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = today.format(formatter);
        WaitUtils.waitForPVisible(driver,By.xpath("//input[@id='opportunityStartDate']"),15);
        //WaitUtils.waitForVisible(driver,By.xpath("//input[@id='opportunityStartDate']"));
        driver.findElement(By.xpath("//input[@id='opportunityStartDate']")).sendKeys(formattedDate);

        LocalDate nextYear = today.plusYears(1);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate1 = nextYear.format(formatter1);
        WaitUtils.waitForPVisible(driver,By.xpath("//input[@id='opportunityEndDate']"),15);
        driver.findElement(By.xpath("//input[@id='opportunityEndDate']")).sendKeys(formattedDate1);
    }
    public void nonFlexProductname(String nonFlexProdName) {
        WebElement nonFlex = driver.findElement(By.id("nonFlexProId"));
        Select s4 = new Select(nonFlex);
        s4.selectByVisibleText(nonFlexProdName);

    }
    public void FlexProductname(String nonFlexProdName) {
        WebElement nonFlex = driver.findElement(By.xpath("//select[@id='flex']"));
        Select s4 = new Select(nonFlex);
        s4.selectByVisibleText(nonFlexProdName);
    }
    public void enterFrameworkID(String FrameWorkID){
      WebElement frmwrk = driver.findElement(By.xpath("//input[@id='frameworkId']"));
      frmwrk.sendKeys(FrameWorkID);

    }
    public void addressIndicator(){
        WebElement addressInd = driver.findElement(By.xpath("//select[@oraselect='lookup:CM_BILLING_ADDR_SOURCE_FLG']"));
        Select s5 = new Select(addressInd);
        s5.selectByVisibleText("Person Override Address");
        WaitUtils.getWait(driver,20);
       WaitUtils.clickAndSwitchIntoFrames(
                driver, By.xpath("//input[@onclick='save();']"), 20, java.util.Arrays.asList("main", "tabPage"));
        String currentFrame2 = (String) ((JavascriptExecutor) driver)
                .executeScript("return window.frameElement ? window.frameElement.name : 'no frame';");
        System.out.println("Current frame switched to: " + currentFrame2);
        WaitUtils.waitForVisible(driver, By.cssSelector("div[id='dataExplorerFilterText2'] span[class='label']"));
    }

    public String frameworkID(){
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='zoneMapFrame_3']")));
        WebElement FrameWork = driver.findElement(By.xpath("//td[@oraerrorelement='frameworkId']"));
        return FrameWork.getText().trim();
    }

    private static String saveOppID;

    public String captureOppID(){
        WaitUtils.sleep(2000);
        String id = driver.findElement(By.xpath("(//span[@class='label'])[2]")).getText().trim();
        saveOppID = id;
        return id;
    }
    public void clickSave(){
        WaitUtils.waitAndClick(driver,By.xpath("//input[@onclick='save();']"),5);
    }
    public String getSaveOppID(){
        return saveOppID;
    }
    public void addOppIDToWord(String filePath, String stepName){
        String id = getSaveOppID();
        String imageName = stepName + " (ID: " + id + " )";
        ScreenShotUtils.captureScreenshotToWord(filePath, imageName);
    }

    public void SiteVerify(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);

        WaitUtils.waitAndClick(driver, By.cssSelector("span[title='Go To Opportunity for Person ']"), 2);
        WaitUtils.waitForVisible(driver, By.xpath("//span[text()='Opportunity for Person Log']"));
        driver.findElement(By.xpath("//span[text()='Opportunity for Person Log']")).click();
    }
    public void firstPerson(){
        WaitUtils.sleep(5000);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitAndClick(driver, By.xpath("(//span[@title='Go To Opportunity for Person '])[1]"), 2);
    }
    public void secondPerson(){
        WaitUtils.sleep(5000);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitAndClick(driver, By.xpath("(//span[@title='Go To Opportunity for Person '])[2]"), 2);
    }

    public void thirdPerson(){
        WaitUtils.sleep(5000);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 3);
        //WaitUtils.waitAndClick(driver,By.id("IM_GOBACK"),10);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitAndClick(driver, By.xpath("(//span[@title='Go To Opportunity for Person '])[3]"), 2);
    }

    //Check Opportunity from application
    public void SearchOpp(String MULTI_CUSTOMER_TENDER_ID) throws IOException {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",10);
        WaitUtils.sleep(2000);
        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 10);
        WaitUtils.waitAndClick(driver, By.xpath("//li[@id='mainMenu']"), 10);
        WaitUtils.waitAndClick(driver, By.xpath("//li[@id='CI_MAINMENU_topMenuItem0x28']"), 10);
        WaitUtils.waitAndClick(driver, By.xpath("//span[normalize-space()='Opportunity']"), 10);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",10);
        WaitUtils.sleep(2000);
        Select s19 = new Select(driver.findElement(By.xpath("//select[@id='multiQueryZoneFilters1']")));
        s19.selectByIndex(3);
        WaitUtils.waitAndClick(driver, By.xpath("//input[@orafield='multiCustomerTenderId']"),5);
        driver.findElement(By.xpath("//input[@orafield='multiCustomerTenderId']")).sendKeys(MULTI_CUSTOMER_TENDER_ID);
        WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Search']"),5);
    }
    public void SearchOppByname(String customername){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",3);
        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//li[@id='mainMenu']"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//li[@id='CI_MAINMENU_topMenuItem0x28']"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//li[@id='ci_mainmenu_topmenuitem0x28Opportunity']"), 5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",5);
        WaitUtils.sleep(2000);

        driver.findElement(By.cssSelector("input[orafield='entityName']")).sendKeys(customername);
        Select s1=new Select(driver.findElement(By.xpath("//select[@class='oraDefault']")));
       // s1.selectByVisibleText("Identified");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String starDateValue = LocalDate.now().format(formatter);
        String endDateValue = LocalDate.now().plusYears(1).format(formatter);
        driver.findElement(By.cssSelector("input[orafield='startDate']")).sendKeys(starDateValue);
        driver.findElement(By.cssSelector("input[orafield='endDate']")).sendKeys(endDateValue);
       // WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Search']"),5);
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
        WebElement searchClick=driver.findElement(By.xpath("//input[@value='Search']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();",searchClick);
        WaitUtils.sleep(2000);

    }


    public String OpportunityId2(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",3);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",3);
        WaitUtils.waitForVisible(driver,By.xpath("//tbody[@id='dataExplorerTableBody1']/tr[1]/td[2]/span"));
        WebElement opportunity_2 = driver.findElement(By.xpath("//tbody[@id='dataExplorerTableBody1']/tr[1]/td[2]/span"));
        String OppId2 = opportunity_2.getText().trim();
        System.out.println("Opportunity ID2: " + OppId2);
        WaitUtils.sleep(5000);
        return OppId2;
    }
    public String OpportunityId3(){
        WaitUtils.waitForVisible(driver,By.xpath("//tbody[@id='dataExplorerTableBody1']/tr[2]/td[2]/span"));
        WebElement opportunity_3 = driver.findElement(By.xpath("//tbody[@id='dataExplorerTableBody1']/tr[2]/td[2]/span"));
        String OppId3 = opportunity_3.getText().trim();
        System.out.println("Opportunity ID3: " + OppId3);
        return OppId3;
    }
    public String OpportunityId4(){
        WaitUtils.waitForVisible(driver,By.xpath("//tbody[@id='dataExplorerTableBody1']/tr[4]/td[2]/span"));
        WebElement opportunity_4 = driver.findElement(By.xpath("//tbody[@id='dataExplorerTableBody1']/tr[4]/td[2]/span"));
        String OppId4 = opportunity_4.getText().trim();
        System.out.println("Opportunity ID4: " + OppId4);
        return OppId4;
    }
    public String Customer2(){
        WaitUtils.waitAndClick(driver, By.xpath("//tbody/tr[1]/th[1]/a"),8);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",5);
        WaitUtils.sleep(2000);
        WebElement CUSTID2 = WaitUtils.waitForVisible(driver, By.xpath("//span[@title='Go To Opportunity for Person ']"));
        String C2 = CUSTID2.getText();
        System.out.println("Customer ID2: " + C2);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitAndClick(driver, By.id("IM_GOBACK"), 5);
        WaitUtils.sleep(2000);
        return C2;
    }
    public String Customer3(){
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",5);
        WaitUtils.waitAndClick(driver, By.xpath("//tbody/tr[2]/th[1]/a"),10);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",5);
        WaitUtils.sleep(2000);
        WebElement CUSTID3 = driver.findElement(By.xpath("//span[@title='Go To Opportunity for Person ']"));
        String C3 = CUSTID3.getText();
        System.out.println("Customer ID3: " + C3);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitAndClick(driver, By.id("IM_GOBACK"), 5);
        WaitUtils.sleep(2000);
        return C3;
    }
    public String Customer4(){
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",5);
        WaitUtils.waitAndClick(driver, By.xpath("//tbody/tr[3]/th[1]/a"),8);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",5);
        WaitUtils.sleep(2000);
        WebElement CUSTID4 = driver.findElement(By.xpath("//span[@title='Go To Opportunity for Person ']"));
        String C4 = CUSTID4.getText();
        System.out.println("Customer ID4: " + C4);
        return C4;
    }
    public void goback(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitAndClick(driver, By.xpath("//ou-button[@id='IM_GOBACK']"),15);
    }

    public void gobackClickRefresh(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[@id='IM_GOBACK']"),15);
        WaitUtils.waitAndClick(driver, By.id("IM_REFRESH"), 5);
    }
}
