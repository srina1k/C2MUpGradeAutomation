package org.example.Pages;

import org.example.Utils.ScreenShotUtils;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.example.Utils.DriverManager;
import java.io.IOException;

import java.time.Duration;

public class OppForPerson {
    private final WebDriver driver;

    public OppForPerson(){
        this.driver = DriverManager.getDriver();
    }

    private final By creditCheckBtn = By.xpath("//a[text()='Initiate Credit Check']");
    private final By notForQuote = By.xpath("//input[@value='Not for Quote']");
    private final By ForQuote = By.xpath("//input[@value='For Quote']");
    private final By goBackBtn = By.id("IM_GOBACK");

    public void CreditCheck() {
        WaitUtils.sleep(8000);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 10);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 10);
        WebElement creditbtn= driver.findElement(By.xpath("//a[text()='Initiate Credit Check']"));
        JavascriptExecutor js=(JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", creditbtn);
        //js.executeScript("arguments[0].click",creditCheckBtn);
        WaitUtils.waitAndClick(driver,By.xpath("//a[text()='Initiate Credit Check']"), 15);
        WaitUtils.getWait(driver,15);
        WaitUtils.waitForVisible(driver, By.xpath("//span[text()='Credit Check Log']"));
        WaitUtils.waitAndClick(driver,By.xpath("//span[text()='Credit Check Log']"),20);
        //driver.findElement(By.xpath("//span[text()='Credit Check Log']")).click();
        WaitUtils.sleep(4000);
    }
    public void TermSet(){

        int pixels = 0;
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,arguments[0]);", pixels);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);

        if (driver.findElements(ForQuote).size() > 0) {
            driver.findElement(ForQuote).click();
            System.out.println("Clicked 'For Quote' to add term set.");
        } else if (driver.findElements(notForQuote).size() > 0) {
            System.out.println("Term set already added. No action needed.");
        }
        for (int i = 0; i<2; i++){
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver,"main",2);
            WaitUtils.waitAndClick(driver, goBackBtn, 8);
            if (i == 0) WaitUtils.sleep(3000);
        }
    }
    public void createdCreditCheck(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitForVisible(driver, creditCheckBtn);
        WaitUtils.waitAndClick(driver, By.xpath("//span[@title='Go To Credit Check ']"),5);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='zoneMapFrame_2']")));
        WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Process']"),5);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",3);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 10);
        WaitUtils.waitForVisible(driver, By.xpath("//span[text()='Credit Check Log']"));
    }

    public void QualifyingOpportunity(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",2);
        WaitUtils.waitForPresence(driver,By.cssSelector("iframe[title='zoneMapFrame_1']"));
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='zoneMapFrame_1']")));

        driver.findElement(By.cssSelector("input[value='Contacted']")).click();
        WaitUtils.sleep(5000);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitAndClick(driver, By.id("IM_REFRESH"), 5);

        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",2);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='zoneMapFrame_1']")));
        WaitUtils.waitAndClick(driver, By.cssSelector("input[value='Qualified']"), 5);
        WaitUtils.sleep(15000);
    }

    public void holdingPenOverride(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",2);
        WaitUtils.waitAndClick(driver, By.cssSelector("span[title='Go To Opportunity for Person ']"), 2);

        WaitUtils.waitForVisible(driver, By.xpath("//input[@value='Override']"));
        ScreenShotUtils.captureScreenshotToWord("COMC15P1.docx","Step13:Click on Override Button");
        driver.findElement(By.xpath("//input[@value='Override']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        String parentWindow = driver.getWindowHandle();
        for(String InputAcceptOrRemoveReason : driver.getWindowHandles()){
            if(!InputAcceptOrRemoveReason.equals(parentWindow)){
                driver.switchTo().window(InputAcceptOrRemoveReason);
                break;
            }
        }
        System.out.println("Window count: " + driver.getWindowHandles().size());

        WaitUtils.sleep(3000);
        driver.findElement(By.xpath("//textarea[@oraerrorelement='removeReason']")).sendKeys("Test");
       // WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Save']"), 8);
        WaitUtils.sleep(15000);

        driver.switchTo().window(parentWindow);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitAndClick(driver, By.xpath("//span[@id='IM_GOBACK']"),5);
    }

    //online Mode
    public void qualifiedQuoteInProgress() throws IOException {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",2);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='zoneMapFrame_1']")));
        WaitUtils.waitAndClick(driver,By.cssSelector("input[value='Qualified - Quote in Process']"), 5);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        for(String Qualified_Quote_in_process : driver.getWindowHandles()){
            driver.switchTo().window(Qualified_Quote_in_process);
        }

        WaitUtils.sleep(4000);
        WebElement Process_Mode = driver.findElement(By.xpath("//select[@oraselect='lookup:CM_PROCESS_MODE_FLG']"));
        WaitUtils.waitForPVisible(driver, By.xpath("//select[@oraselect='lookup:CM_PROCESS_MODE_FLG']"), 10);
        Select dropdown_Process_Mode = new Select(Process_Mode);
        dropdown_Process_Mode.selectByVisibleText("Online");
        WaitUtils.sleep(3000);

        WaitUtils.waitAndClick(driver, By.cssSelector("input[value='Save']"),8);
        WaitUtils.sleep(10000);

        for(String opportunity : driver.getWindowHandles()) {
            driver.switchTo().window(opportunity);
            if(driver.getTitle().equals("Opportunity")){
                break;
            }
        }
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",2);
    }

    //Batch Mode
    public void qualifiedQuoteInProgressBatchMode() throws IOException {
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",2);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='zoneMapFrame_1']")));
        WaitUtils.waitAndClick(driver,By.cssSelector("input[value='Qualified - Quote in Process']"), 5);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(driver -> driver.getWindowHandles().size() > 1);

        for(String Qualified_Quote_in_process : driver.getWindowHandles()){
            driver.switchTo().window(Qualified_Quote_in_process);
        }

        WaitUtils.sleep(4000);
        WebElement Process_Mode = driver.findElement(By.xpath("//select[@oraselect='lookup:CM_PROCESS_MODE_FLG']"));
        WaitUtils.waitForPVisible(driver, By.xpath("//select[@oraselect='lookup:CM_PROCESS_MODE_FLG']"), 10);
        Select dropdown_Process_Mode = new Select(Process_Mode);
        dropdown_Process_Mode.selectByVisibleText("Batch");
        WaitUtils.sleep(3000);

        WaitUtils.waitAndClick(driver, By.cssSelector("input[value='Save']"),8);
        WaitUtils.sleep(10000);

        for(String opportunity : driver.getWindowHandles()) {
            driver.switchTo().window(opportunity);
            if(driver.getTitle().equals("Opportunity")){
                break;
            }
        }
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",2);
    }

    public void goBack(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitAndClick(driver, goBackBtn, 5);
    }
    public void goBack3times(){
        for (int i = 0; i<3; i++){
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver,"main",2);
            WaitUtils.waitAndClick(driver, goBackBtn, 5);
            if (i == 0) WaitUtils.sleep(5000);
        }
    }
    public String quote() {
        String Caseid = driver.findElement(By.xpath("//span[@title='Go To Case ']")).getText().trim();
        System.out.println("Case ID:" + Caseid);
        return Caseid;
    }
    public void DeemedWon(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",10);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",10);
        // driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='zoneMapFrame_1']")));
        WaitUtils.waitForFrameAndSwitch(driver,"zoneMapFrame_1",15);
//        WaitUtils.getWait(driver,20);
        WebElement PendingAnalysis=driver.findElement(By.xpath("//input[@value='Deemed Won - Pending Analysis']"));
        WaitUtils.getWait(driver,20);
        JavascriptExecutor js=(JavascriptExecutor)driver;
        js.executeScript("arguments[0].click()",PendingAnalysis);
        WaitUtils.getWait(driver,20);
//        //WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Deemed Won - Pending Analysis']"), 15);
//        WaitUtils.waitForVisible(driver, By.xpath("//input[@value='Won']"));
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitAndClick(driver, By.id("IM_REFRESH"), 5);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",2);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",2);
        WaitUtils.waitForFrameAndSwitch(driver,"zoneMapFrame_1",15);
        //driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='zoneMapFrame_1']")));
        WaitUtils.waitForVisible(driver, By.xpath("//input[@value='Won']"));
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",10);
        for (int i = 0; i < 3; i++) {
            WaitUtils.waitAndClick(driver, By.xpath("//ou-button[@id='IM_GOBACK']"), 15);
            WaitUtils.sleep(2000); // ~2–3 sec
        }
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",10);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",2);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='zoneMapFrame_1']")));
        driver.findElement(By.xpath("//input[@value='Deemed Won - Pending Analysis']")).click();
        //WaitUtils.waitForVisible(driver, By.xpath("//input[@value='Won']"));
    }
    public void CustomerhyperLink1(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",3);
        WaitUtils.waitAndClick(driver, By.id("IM_REFRESH"), 5);

        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitAndClick(driver, By.xpath("(//a[@navoptcd='cmoppptlTabMenu'])[1]"),15);
        WaitUtils.sleep(3000);
        WaitUtils.waitForVisible(driver, By.xpath("//span[@title='Go To Opportunity for Person ']"));
        WaitUtils.waitAndClick(driver, By.xpath("//span[@title='Go To Opportunity for Person ']"), 5);
        WaitUtils.waitForVisible(driver, creditCheckBtn);
    }
    public void CustomerhyperLink2(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",3);
        WaitUtils.waitAndClick(driver, goBackBtn, 5);

        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitAndClick(driver, By.xpath("(//a[@navoptcd='cmoppptlTabMenu'])[2]"),15);
        WaitUtils.sleep(3000);
        WaitUtils.waitForVisible(driver, By.xpath("//a[@navoptcd='cmoppperTabMenu']"));
        WaitUtils.waitAndClick(driver, By.xpath("//a[@navoptcd='cmoppperTabMenu']"), 5);
        WaitUtils.waitForVisible(driver, creditCheckBtn);
    }
    public void CustomerhyperLink3(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",3);
        WaitUtils.waitAndClick(driver, goBackBtn, 5);

        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitAndClick(driver, By.xpath("(//a[@navoptcd='cmoppptlTabMenu'])[3]"),10);
        WaitUtils.sleep(3000);
        WaitUtils.waitForVisible(driver, By.xpath("//span[@title='Go To Opportunity for Person ']"));
        WaitUtils.waitAndClick(driver, By.xpath("//span[@title='Go To Opportunity for Person ']"), 15);
        WaitUtils.waitForVisible(driver, creditCheckBtn);
    }
    public void CustomerhyperLink4(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",3);
        WaitUtils.waitAndClick(driver, goBackBtn, 5);

        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitAndClick(driver, By.xpath("(//a[@navoptcd='cmoppptlTabMenu'])[4]"),10);
        WaitUtils.sleep(3000);
        WaitUtils.waitForVisible(driver, By.xpath("//span[@title='Go To Opportunity for Person ']"));
        WaitUtils.waitAndClick(driver, By.xpath("//span[@title='Go To Opportunity for Person ']"), 10);
        WaitUtils.waitForVisible(driver, creditCheckBtn);
    }
    public void holdPenNavigation3(){
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitAndClick(driver, By.xpath("(//a[@navoptcd='cmoppptlTabMenu'])[3]"),10);
        WaitUtils.waitForVisible(driver, By.xpath("//span[@title='Go To Opportunity for Person ']"));
    }
    public void holdPenNavigation2(){
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitAndClick(driver, By.xpath("(//a[@navoptcd='cmoppptlTabMenu'])[2]"),10);
        WaitUtils.waitForVisible(driver, By.xpath("//span[@title='Go To Opportunity for Person ']"));
    }
    public void holdPenNavigation1(){
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 3);
        WaitUtils.waitAndClick(driver, By.xpath("(//a[@navoptcd='cmoppptlTabMenu'])[1]"),10);
        WaitUtils.waitForVisible(driver, By.xpath("//span[@title='Go To Opportunity for Person ']"));
    }
}
