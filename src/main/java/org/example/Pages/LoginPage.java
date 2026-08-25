package org.example.Pages;

import org.example.Utils.ConfigReader;
import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.example.Utils.DriverManager.getDriver;

public class LoginPage{
    private final WebDriver driver;
    private final WebDriverWait wait;
    public LoginPage(WebDriver driver) {
        this.driver= DriverManager.getDriver();
        this.wait=new WebDriverWait(driver,Duration.ofSeconds(20));
    }
    public void Logincredentials() {
        String baseUrl= ConfigReader.get("base.url");
        String Username=ConfigReader.get("username");
        String password=ConfigReader.get("password");
        if(baseUrl==null||Username==null||password==null){
            throw new IllegalStateException("Missing base.url,username,password in config/env");
        }
        String authUrl=baseUrl.replaceFirst("https://","https://"+Username+":"+password+"@");
        System.out.println("Logging in with Basic Auth URL:"+authUrl);
        driver.get(authUrl);
//        driver.get(authUrl);
        String currentUrl=driver.getCurrentUrl();
        System.out.println(currentUrl);
        wait.until(ExpectedConditions.titleContains("Oracle Utilities Customer To Meter"));
        WaitUtils.getWait(driver,20);
    }
}