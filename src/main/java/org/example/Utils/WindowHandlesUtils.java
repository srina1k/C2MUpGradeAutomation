package org.example.Utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Set;

public class WindowHandlesUtils {

    private static ThreadLocal<String> parentWindow = new ThreadLocal<>();
    private static ThreadLocal<String> childWindow = new ThreadLocal<>();


    public static void duplicateCurrentTab() {
        WebDriver driver = DriverManager.getDriver();
        parentWindow.set(driver.getWindowHandle());
        String currentUrl = driver.getCurrentUrl();
        ((JavascriptExecutor) driver).executeScript("window.open(arguments[0], '_blank');", currentUrl);
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows){
            if (! window.equals(parentWindow.get())){
                childWindow.set(window);
                driver.switchTo().window(window);
                break;
            }
        }
    }
    public static void switchToFirstWindow() {
        DriverManager.getDriver().switchTo().window(parentWindow.get());
    }
    public static void switchToSecondWindow() {
        DriverManager.getDriver().switchTo().window(childWindow.get());
    }
}
