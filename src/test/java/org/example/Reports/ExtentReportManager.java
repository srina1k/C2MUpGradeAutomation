package org.example.Reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.example.Utils.ConfigReader;
import org.example.Listener.RetryAnalyzer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class ExtentReportManager {
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> extentTest=new ThreadLocal<>();
    public static void initReport(){
        String timeStamp= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String reportPath=System.getProperty("user.dir")+"/test-output/ExtentReport_"+timeStamp+".html";
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("C2M UpGrade Automation Report");
        spark.config().setReportName("Regression");
        spark.config().setTheme(Theme.DARK);
        spark.config().setTimeStampFormat("MMM dd,yyyy HH:mm:ss");
        extent=new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Environment", ConfigReader.getEnv());
        extent.setSystemInfo("Browser",ConfigReader.get("browser"));
        extent.setSystemInfo("Retry Count",String.valueOf(RetryAnalyzer.max_retry_count));
    }
    public static void createTest(String testName, String description){
        ExtentTest test=extent.createTest(testName,description);
        extentTest.set(test);
    }
    public static ExtentTest getTest(){
        return extentTest.get();
    }
    public static void flushReport(){
        if(extent!=null){
            extent.flush();
        }
    }
}
