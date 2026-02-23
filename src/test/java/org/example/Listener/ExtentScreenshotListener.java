package org.example.Listener;

import com.aventstack.extentreports.Status;
import org.example.Base.BaseClass;
import org.example.Reports.ExtentReportManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentScreenshotListener implements ITestListener {
    @Override
    public void onStart(ITestContext context){
        ExtentReportManager.initReport();
    }
    @Override
    public void onTestStart(ITestResult result){
        ExtentReportManager.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription()!=null?result.getMethod().getDescription():"");
    }
    @Override
    public void onTestSuccess(ITestResult result){
        ExtentReportManager.getTest().log(Status.PASS,"Test Passed");
        // String screenshotPath= ScreenshotUtil.captureOnSuccess(((BaseClass)result.getInstance()).driver,result.getName());
//        if(screenshotPath!=null){
//            ExtentReportManager.getTest().addScreenCaptureFromPath(screenshotPath,"Success Screenshot");
//        }
    }
    @Override
    public void onTestFailure(ITestResult result){
        ExtentReportManager.getTest().log(Status.FAIL,"Test Failed" + result.getThrowable());
//        String screenshotPath = ScreenShotUtils.captureOnFailure(((BaseClass) result.getInstance()).driver,result.getName());
//        if(screenshotPath!=null){
//            ExtentReportManager.getTest().fail("Failure Screenshot", com.aventstack.extentreports.MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
//        }
        if(result.wasRetried()){
            ExtentReportManager.getTest().warning("This is a Retried attempt");
        }
    }
    @Override
    public void onTestSkipped(ITestResult result){
        ExtentReportManager.getTest().log(Status.SKIP,"Test Skipped");
    }
    @Override
    public void onFinish(ITestContext context){
        ExtentReportManager.flushReport();
    }
}
