package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class OverdueProcessPage {
    private WebDriver driver;

    public OverdueProcessPage(){
        this.driver = DriverManager.getDriver();
    }

    public void navigateOverdueProcess(String overDueID){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        By[] menuSequence2 = {
                By.id("IM_menuButton"), By.xpath("//li[@id='mainMenu']"), By.id("CI_MAINMENU_topMenuItem0x6"), By.xpath("(//span[text()='Add'])[3]")};
        for (By menuItem2 : menuSequence2) {
            WaitUtils.sleep(1500);
            WaitUtils.waitAndClick(driver, menuItem2, 10);
            WaitUtils.getWait(driver,10);
        }
        WaitUtils.waitForFrameAndSwitch(driver,"tabPage",15);
        WaitUtils.waitAndClick(driver,By.xpath("//img[@id='IM_OD_PROC_ID']"),15);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
        for (String window : driver.getWindowHandles()){
            driver.switchTo().window(window);
            if (driver.getTitle().contains("Overdue Process Search")){
                break;
            }
        }
        System.out.println("Switched to new window: " + driver.getTitle());
        WebElement overdueIdField = WaitUtils.waitForPVisible(driver, By.xpath("//input[@id='OD_PROC_ID']"), 20);
        overdueIdField.sendKeys(overDueID);
        WaitUtils.sleep(3000);

        WaitUtils.waitAndClick(driver, By.id("BU_MAIN_search"), 2);
        String mainHandle = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(mainHandle);
        WaitUtils.sleep(3000);
    }

    public void navigateToBill(){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "OD_PROC_OBJ", 5);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,200);");
        WaitUtils.waitAndClick(driver, By.xpath("//a[@name='OD_PROC_OBJ|0']"),5);
        WaitUtils.sleep(3000);
    }

    public void eventsTab(int expectedSequence){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabMenu", 5);
        WaitUtils.waitAndClick(driver, By.xpath("//td[text()='Events']"),5);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
        WebElement arrow = driver.findElement(By.xpath("//img[@name='IM_NEXT_OD_EVT']"));
        WebElement eventSequence = driver.findElement(By.xpath("//input[@name='OD_EVT$EVT_SEQ']"));

        int maxAttempts = 50;
        for (int i = 0; i < maxAttempts; i++){
            String currentValue = eventSequence.getAttribute("value").trim();
            int currentSequence = Integer.parseInt(currentValue);
            if (currentSequence == expectedSequence){
                return;
            }
            arrow.click();
            WaitUtils.waitForTextToChange(driver, eventSequence,currentValue);
        }

        throw new RuntimeException("Expected Event Sequence " + expectedSequence + "not reached");
    }

}
