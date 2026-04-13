package org.example.Tests;


import org.example.Base.BaseClass;
import org.example.Pages.LoginPage;
import org.example.Pages.PaymentEventUploadStaging;
import org.example.Utils.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.sql.SQLException;

import org.example.Pages.BatchJobSubmissionPage;

/* before executing this sceanrio ask Tech team and fusion team to
   drop HOCx file, and REMPT file respectively
 *
 */
public class PaymentReceiptAndAutomaticAllocation extends BaseClass {

    String transmitIDRemp;
    String extTransmitId;

    public static String HocxFileName;
    @Test
    public void testLogin(){

        String fileName = "PaymentReceiptAndAutomaticAllocation.docx";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage lp = new LoginPage(DriverManager.getDriver());
        lp.Logincredentials();
    }

    @Test(dependsOnMethods = "testLogin")
    public void fileVerification(){
        HocxFileName = WinScpServerUtils.fetchFileName();
        System.out.println("File Name: " + HocxFileName);
        Assert.assertNotNull(HocxFileName, "No file Found on server");
    }

    @Test(dependsOnMethods = "fileVerification")
    public void HocxFilebatchRun() throws SQLException {
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMHOCPAYBatch("CMHOCPAY", HocxFileName);
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "HOCSX payment upload batch is running");
        batchP.clickRefresh();

        String query = String.format(DBQueries.HocxFile);
        extTransmitId = DBUtils.getSingleDate(query, "EXT_TRANSMIT_ID");
        System.out.println("Transmit ID for HOCX file: " + extTransmitId);

        PaymentEventUploadStaging uploadStaging = new PaymentEventUploadStaging();
        uploadStaging.navigation();
        uploadStaging.searchExtTransmitID(extTransmitId);
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Enter EXT Transmit ID for HOCX file and click on search");
        uploadStaging.ExtclickSearch();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event upload staging");
        uploadStaging.scrollDown();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event upload staging is in 'Incomplete' status for HOCX payment");

    }

    @Test(dependsOnMethods = "HocxFilebatchRun")
    public void REMPFileExtract() throws SQLException {
        String rempQuery = String.format(DBQueries.REMPFile);
        transmitIDRemp = DBUtils.getSingleDate(rempQuery, "EXT_TRANSMIT_ID");
        System.out.println("Transmit ID for REMP file: " + transmitIDRemp);

        PaymentEventUploadStaging uploadStaging = new PaymentEventUploadStaging();
        uploadStaging.navigation();
        uploadStaging.searchExtTransmitIDREMP(transmitIDRemp);
        System.out.println();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event upload staging");
        uploadStaging.scrollDown();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event upload staging is in 'Incomplete' status for REMP payment");
    }

    @Test(dependsOnMethods = "REMPFileExtract")
    public void PEPL1batchRun() throws SQLException {

        WindowHandlesUtils.duplicateCurrentTab();
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("C1-PEPL1");
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event Upload Stage-1 batch is running");
        //batchP.clickRefresh();

        //REMP payment file Status moved to Pending
        WindowHandlesUtils.switchToFirstWindow();
        PaymentEventUploadStaging uploadStaging = new PaymentEventUploadStaging();
        uploadStaging.clickRefresh();
        uploadStaging.scrollDown();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event upload staging for REMP payment file is in 'Pending' status..");
        uploadStaging.navigateToTenderControl();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Tender Control is Created Open status for REMP payment");
        uploadStaging.goback();

        //HOCX payment file Status moved to Pending
        uploadStaging.navigation();
        uploadStaging.searchExtTransmitID(extTransmitId);
        uploadStaging.ExtclickSearch();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event upload staging is moved to 'pending' status for HOCX payment");
        uploadStaging.navigateToTenderControl();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Tender Control is Created Open status for HOCX payment");
        uploadStaging.goback();

    }
    @Test(dependsOnMethods = "PEPL1batchRun")
    public void PEPL2batchRun(){

        WindowHandlesUtils.switchToSecondWindow();
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("C1-PEPL2");
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event Upload Stage-2 batch is running");
        //batchP.clickRefresh();

        //HOCX payment file Status moved to Complete
        WindowHandlesUtils.switchToFirstWindow();
        PaymentEventUploadStaging uploadStaging = new PaymentEventUploadStaging();
        uploadStaging.clickRefresh();
        uploadStaging.scrollDown();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event upload staging for HOCX payment file is Completed");

        //REMP payment moved to Complete
        uploadStaging.navigation();
        uploadStaging.searchExtTransmitIDREMP(transmitIDRemp);
        uploadStaging.scrollDown();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event upload staging for REMP payment file is Completed");

    }
    @Test(dependsOnMethods = "PEPL2batchRun")
    public void PEPL3batchRun() {
        WindowHandlesUtils.switchToSecondWindow();
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("C1-PEPL3");
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Balance Tender Controls batch is running");
        //batchP.clickRefresh();

        //Tender Contraol is now balanced for REMP payment file.
        WindowHandlesUtils.switchToFirstWindow();
        PaymentEventUploadStaging uploadStaging = new PaymentEventUploadStaging();
        uploadStaging.navigateToTenderControl();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Tender Control is now Balanced for REMP payment");
        uploadStaging.goback();

        //Tender Contraol is now balanced for HOCX payment file.
        uploadStaging.navigation();
        uploadStaging.searchExtTransmitID(extTransmitId);
        uploadStaging.ExtclickSearch();
        uploadStaging.navigateToTenderControl();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Tender Control is now Balanced for HOCX payment");
    }
}
