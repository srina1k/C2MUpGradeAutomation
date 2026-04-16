package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;


public class marketMessageSearch {
    private WebDriver driver;

    public marketMessageSearch(){
        this.driver = DriverManager.getDriver();
    }

    public void navigateToMarketMessage(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);

        WaitUtils.waitAndClick(driver, By.id("IM_menuButton"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Workflow and Notification']"), 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[text()='Market Message Search']"), 5);
        WaitUtils.sleep(2000);
    }
    public void mmSearchContractID(String contractID){
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        Select searchByMktMsg = new Select(driver.findElement(By.cssSelector("select[id='multiQueryZoneFilters1']")));
        searchByMktMsg.selectByIndex(5);
        WaitUtils.sleep(2000);

        driver.findElement(By.xpath("//input[@orafield='contractId']")).sendKeys(contractID);
        //Select messageFlow = new Select(driver.findElement(By.cssSelector("select[onkeydown='dataExplorerDropDownKeyPress(event)']")));
        //messageFlow.selectByIndex(1);
        WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Search']"),5);
    }

    public void OdropdownoMarketMessageId(String marketMessagID){
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        Select searchByMktMsg = new Select(driver.findElement(By.cssSelector("select[id='multiQueryZoneFilters1']")));
        searchByMktMsg.selectByIndex(3);
        WaitUtils.sleep(2000);
        driver.findElement(By.cssSelector("input[onblur='dataExplorerTextBlur(event, document)']")).sendKeys(marketMessagID);

        Select messageFlow = new Select(driver.findElement(By.cssSelector("select[onkeydown='dataExplorerDropDownKeyPress(event)']")));
        messageFlow.selectByIndex(2);
        WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Search']"),5);

        WaitUtils.waitAndClick(driver, By.cssSelector("span[title='Go To Outbound Market Message Maintenance ']"),5);
    }
    public void mmclickRefresh(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitAndClick(driver, By.cssSelector("input[id='IM_REFRESH']"),5);
    }

    public void IdropdownoMarketMessageId(String marketMessagID){
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        Select searchByMktMsg = new Select(driver.findElement(By.cssSelector("select[id='multiQueryZoneFilters1']")));
        searchByMktMsg.selectByIndex(3);
        WaitUtils.sleep(2000);
        driver.findElement(By.cssSelector("input[onblur='dataExplorerTextBlur(event, document)']")).sendKeys(marketMessagID);

        Select messageFlow = new Select(driver.findElement(By.cssSelector("select[onkeydown='dataExplorerDropDownKeyPress(event)']")));
        messageFlow.selectByIndex(1);
        WaitUtils.waitAndClick(driver, By.xpath("//input[@value='Search']"),5);

        WaitUtils.waitAndClick(driver, By.cssSelector("span[title='Go To Inbound Market Message Maintenance ']"),5);
    }
    public void OmarketMsgValidation(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[id='zoneMapFrame_2']")));
        //WaitUtils.waitAndClick(driver, By.cssSelector("input[value='Validate']"),5);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[id='zoneMapFrame_3']")));
        //WaitUtils.waitForTextTiBePresent(driver, By.cssSelector("//td[text()='Message Requested']"),"Message Requested",20);
    }
    public void ImarketMsgValidation(){
        /*driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[id='zoneMapFrame_2']")));
        WaitUtils.waitAndClick(driver, By.cssSelector("input[value='Validate']"),5);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",8);
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",8);
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[id='zoneMapFrame_3']")));
        WaitUtils.waitForTextTiBePresent(driver, By.cssSelector("//td[text()='Processed']"),"Processed",20);*/
    }
    public String fetchMpan(){
        String mpan = driver.findElement(By.xpath("//td[@orafield='marketIdentifier']")).getText().trim();
        return mpan;
    }
}
