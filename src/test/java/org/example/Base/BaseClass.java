package org.example.Base;

import org.example.Utils.ConfigReader;
import org.example.Utils.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

import static org.example.Utils.DriverManager.getDriver;
import static org.example.Utils.DriverManager.setDriver;
public class BaseClass {

    public WebDriver driver;
    protected String browser;
    protected String url;
    protected String username;
    protected String password;
    protected String authMode;
    @BeforeClass(alwaysRun = true)
    public void setup(ITestContext context) {
        String envFromSuite = context.getSuite().getParameter("env");
        ConfigReader.overrideEnvFromTestNG(envFromSuite);
        browser  = ConfigReader.get("browser");
        url      = ConfigReader.get("base.url");
        username = ConfigReader.get("username");
        password = ConfigReader.get("password");
        System.out.println("=== Test Boot ===");
        System.out.println("Env:     " + ConfigReader.getEnv());
        System.out.println("Browser: " + browser);
        System.out.println("URL:     " + url);
        System.out.println("Auth:    " + authMode);
        WebDriver newDriver = createDriver(browser);
        DriverManager.setDriver(newDriver);
        WebDriver driver = DriverManager.getDriver();//.get(ConfigReader.get("url"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.get(url);
    }
    public WebDriver createDriver(String browserName) {
        browserName = browserName.toLowerCase();
        WebDriver driver;
        switch (browserName.toLowerCase()) {
            case "chrome": {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--ignore-certificate-errors");
                options.addArguments("--allow-insecure-localhost");
                options.addArguments("--disable-web-security");
                options.addArguments("--allow-running-insecure-content");
                driver = new ChromeDriver(options);
                break;
            }
            case "firefox": {
                FirefoxOptions ff = new FirefoxOptions();
                ff.addArguments("--ignore-certificate-errors");
                driver = new FirefoxDriver(ff);
                break;
            }
            case "edge": {
                EdgeOptions edge = new EdgeOptions();
                edge.addArguments("--ignore-certificate-errors");
                edge.addArguments("--allow-insecure-localhost");
                edge.addArguments("--disable-web-security");
                edge.addArguments("--allow-running-insecure-content");
                driver = new EdgeDriver(edge);
                break;
            }
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }
        return driver;
    }
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        WebDriver d = getDriver();
        if (d != null) {
            try {
                d.quit();
                System.out.println("WebDriver quit successfully");
            } catch (Exception e) {
                System.out.println("Error quitting WebDriver: " + e.getMessage());
            }
        }
    }
}