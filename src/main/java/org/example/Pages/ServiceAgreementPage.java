package org.example.Pages;

import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
}
