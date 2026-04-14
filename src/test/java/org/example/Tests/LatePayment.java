package org.example.Tests;


import org.example.Base.BaseClass;
import org.example.Pages.BatchJobSubmissionPage;
import org.example.Pages.BillPage;
import org.example.Pages.LoginPage;
import org.example.Pages.OverdueProcessPage;
import org.example.Utils.DBQueries;
import org.example.Utils.DBUtils;
import org.example.Utils.DriverManager;
import org.example.Utils.ScreenShotUtils;
import org.testng.annotations.Test;


import java.io.File;
import java.sql.SQLException;
import java.util.Map;

public class LatePayment extends BaseClass {
    @Test
    public void testLogin(){
        String fileName = "LatePayment.docx";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage lp = new LoginPage(DriverManager.getDriver());
        lp.Logincredentials();
    }
    @Test (dependsOnMethods = "testLogin")
    public void latePaymentInterestAdjCreation() throws SQLException {
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.batchControl("CM-LTPMT");
        batchP.clickSearch();
        Map<String, String> dates = batchP.readLastBatchRunDate();

        String sqlDate = dates.get("SQL_DATE");
        String businessDate = dates.get("BUSINESS_DATE");
        System.out.println("Date for SQL query: " + sqlDate);
        System.out.println("Date for Batch Business Date: " + businessDate);

        String query = String.format(DBQueries.ODPROCID,sqlDate);
        String overDueID = DBUtils.getSingleDate(query, "OD_PROC_ID");
        System.out.println("Overdue ID: " + overDueID);

        OverdueProcessPage overdueProcess = new OverdueProcessPage();
        overdueProcess.navigateOverdueProcess(overDueID);
        ScreenShotUtils.captureScreenshotToWord("LatePayment.docx","Take any one overdue ID from DB and seacrh.");

        overdueProcess.navigateToBill();
        ScreenShotUtils.captureScreenshotToWord("LatePayment.docx","Bill is Completed and unpaid");

        BillPage billpage = new BillPage();
        billpage.BillChar();
        ScreenShotUtils.captureScreenshotToWord("LatePayment.docx","No char Present ");

        batchP.BatchPage();
        ScreenShotUtils.captureScreenshotToWord("LatePayment.docx","Run Batch ");
        batchP.CMLTMPTBatch("CM-LTPMT", businessDate);
        batchP.clickRefresh();
        ScreenShotUtils.captureScreenshotToWord("LatePayment.docx","Batch is completed and Characateristics added to the bill");
        billpage.BillMenu();
        billpage.navigateToAdjustment();
        ScreenShotUtils.captureScreenshotToWord("LatePayment.docx","Late Payment Interest Adjustment created.");
    }

    @Test (dependsOnMethods = "latePaymentInterestAdjCreation")
    public void latePaymentAdminFeeAdjCreation() {
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        ScreenShotUtils.captureScreenshotToWord("LatePayment.docx","Run Batch ");
        batchP.CMLPAFCTBatch("CM-LPAFC");
        batchP.clickRefresh();
        ScreenShotUtils.captureScreenshotToWord("LatePayment.docx","Batch is completed");

        BillPage billpage = new BillPage();
        billpage.BillMenu();
        billpage.navigateToAdjustment();
        ScreenShotUtils.captureScreenshotToWord("LatePayment.docx","Late Payment Admin Fee Adjustment created.");

    }
}
