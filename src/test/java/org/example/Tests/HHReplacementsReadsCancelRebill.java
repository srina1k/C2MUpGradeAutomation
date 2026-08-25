package org.example.Tests;

import org.example.Base.BaseClass;
import org.example.Pages.BatchJobSubmissionPage;
import org.example.Pages.LoginPage;
import org.example.Utils.DBQueries;
import org.example.Utils.DBUtils;
import org.example.Utils.DriverManager;
import org.example.Utils.WaitUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class HHReplacementsReadsCancelRebill extends BaseClass {
    String date;
    @Test
    public void LoginTest(){
//        String fileName = "HHReplacementsReadsCancelRebill.docx";
//        File file = new File(fileName);
//        if (file.exists()) {
//            file.delete();
//            System.out.println("Old word file deleted: " + fileName);
//        }
        LoginPage loginPage=new LoginPage(DriverManager.getDriver());
        loginPage.Logincredentials();
    }
    @Test(dependsOnMethods = "LoginTest")
    public void HHConsumeToQM() throws SQLException {
        date= WaitUtils.sysdate();
        System.out.println(date);
//        String IdentifyUsageQuery=String.format(DBQueries.identifyUsage,date,date);
//        List<Map<String, String>> usageList = DBUtils.executeQuery(IdentifyUsageQuery);
//        int count=0;
//        for (Map<String, String> row : usageList) {
//            String UsageId = row.get("D1_USAGE_ID");
//            String ObjectCD = row.get("BUS_OBJ_CD");
//            String BO_STATUS_CD = row.get("BO_STATUS_CD");
//            String USId= row.get("US_ID");
//            System.out.println("D1_USAGE_ID: " + UsageId + ", BUS_OBJ_CD: " + ObjectCD + ", BO_STATUS_CD: " + BO_STATUS_CD + ", US_ID: " + USId);
//            count++;
//        }
//        System.out.println("Total Usage Count: " + count);
//        String currentUsageQuery=String.format(DBQueries.currentUsage);
//        List<Map<String, String>> currentUsageList = DBUtils.executeQuery(currentUsageQuery);
//        int currentCount=0;
//        for (Map<String, String> row : currentUsageList) {
//            String status=row.get("BO_STATUS_CD");
//            String charval=row.get("CHAR_VAL");
//            String countcharval=row.get("COUNT_CHAR_VAL");
//            System.out.println("BO_STATUS_CD: " + status + ", CHAR_VAL: " + charval + ", COUNT_CHAR_VAL: " + countcharval);
//            currentCount++;
//        }
//        System.out.println("Total Current Usage Count: " + currentCount);
//        String f1outMsgQuery=String.format(DBQueries.f1outMsg);
//        String f1outMsgCount = DBUtils.getSingleDate(f1outMsgQuery, "COUNT(*)");
//        System.out.println("F1_OUTMSG Count: " + f1outMsgCount);
    }
    @Test(dependsOnMethods = "HHConsumeToQM")
    public void DMHHC2QM(){
//        BatchJobSubmissionPage batchPage=new BatchJobSubmissionPage();
//        batchPage.BatchPage();
//        batchPage.batchCode("DMHHC2QM");
//        batchPage.Threads("30");
//        batchPage.clickRefresh();
    }
    @Test(dependsOnMethods = "DMHHC2QM")
    public void DMMHC2QMValidation() throws SQLException {
        String IdentifyUsageQuery=String.format(DBQueries.identifyUsage,date,date);
        List<Map<String, String>> usageList = DBUtils.executeQuery(IdentifyUsageQuery);
        int count=0;
        for (Map<String, String> row : usageList) {
            String UsageId = row.get("D1_USAGE_ID");
            String ObjectCD = row.get("BUS_OBJ_CD");
            String BO_STATUS_CD = row.get("BO_STATUS_CD");
            String USId= row.get("US_ID");
            System.out.println("D1_USAGE_ID: " + UsageId + ", BUS_OBJ_CD: " + ObjectCD + ", BO_STATUS_CD: " + BO_STATUS_CD + ", US_ID: " + USId);
            count++;
        }
        System.out.println("Total Usage Count: " + count);
        String currentUsageQuery=String.format(DBQueries.currentUsage);
        List<Map<String, String>> currentUsageList = DBUtils.executeQuery(currentUsageQuery);
        int currentCount=0;
        for (Map<String, String> row : currentUsageList) {
            String status=row.get("BO_STATUS_CD");
            String charval=row.get("CHAR_VAL");
            String countcharval=row.get("COUNT_CHAR_VAL");
            System.out.println("BO_STATUS_CD: " + status + ", CHAR_VAL: " + charval + ", COUNT_CHAR_VAL: " + countcharval);
            currentCount++;
        }
        System.out.println("Total Current Usage Count: " + currentCount);
        String f1outMsgQuery=String.format(DBQueries.f1outMsgdate,date);
        String f1outMsgCount = DBUtils.getSingleDate(f1outMsgQuery, "COUNT(*)");
        System.out.println("F1_OUTMSG Count: " + f1outMsgCount);
    }
//    @Test(dependsOnMethods = "DMMHC2QMValidation")
//    public void cancelrebillcasecheck(){
//        
//    }

}
