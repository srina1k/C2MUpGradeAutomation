package org.example.Utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitUtils {
    private static final long DEFAULT_TIMEOUT = 50;

    public static WebDriverWait getWait(WebDriver driver, long seconds){
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }
    public static WebElement waitForVisible(WebDriver driver, By locator){
        return getWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public static WebElement waitForClickable(WebDriver driver, By locator){
        return getWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(locator));
    }
    public static WebElement waitForPresence(WebDriver driver, By locator){
        return getWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    public static WebElement waitForPVisible(WebDriver driver, By locator, int seconds){
        return getWait(driver, seconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForTextTiBePresent(WebDriver driver, By locator, String expectedText, int timeoutSeconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));

    }
    public static void waitForPageLoad(WebDriver driver, int timeoutSec) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutSec)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete")
        );
    }
    public static void clickAndSwitchIntoFrames(WebDriver driver, By clickLocator, int timeoutSec, List<String> framesChain) {
        waitAndClick(driver, clickLocator, timeoutSec);
        driver.switchTo().defaultContent();
        for (String frameNameOrId : framesChain) {
            new WebDriverWait(driver, Duration.ofSeconds(timeoutSec))
                    .until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrId));
        }
    }
    public static void waitForFrameAndSwitch(WebDriver driver, String frameNameOrId, int timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameNameOrId));
    }
    public static void waitAndClick(WebDriver driver, By locator, int seconds){
        try{
            getWait(driver, seconds).until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (TimeoutException te){
            throw new NoSuchElementException("Element not clickable after " + seconds + "s: " + locator, te);
        }
    }
    public static void sleep(long millis){
        try{
            Thread.sleep(millis);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    public static void ElementToBeClickable(WebDriver driver,By locator,int timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    public static boolean isElementVisible(WebDriver driver, By locator, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static void waitForTextToChange(WebDriver driver, WebElement element, String oldText){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until((ExpectedCondition<Boolean>) d ->{
            try {
                return ! element.getText().trim().equals(oldText);
            } catch (Exception e){
                return false;
            }
        });
    }
}
