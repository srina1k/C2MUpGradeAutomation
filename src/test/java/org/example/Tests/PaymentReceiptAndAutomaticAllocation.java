package org.example.Tests;


import org.example.Base.BaseClass;
import org.example.Pages.LoginPage;
import org.example.Pages.PaymentEventUploadStaging;
import org.example.Utils.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.sql.SQLException;
import java.util.Map;

import org.example.Pages.BatchJobSubmissionPage;

/* before executing this sceanrio ask AWS team
   drop HOCx file respectively
 *
 */
public class PaymentReceiptAndAutomaticAllocation extends BaseClass {

    String extTransmitId="26225 HO.EXPRESS";
    String tenderctlId;
    String HocxFileName;
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
        String filepath="/c2m/HOCPayments/in/";
        HocxFileName = WinScpServerUtils.fetchFileName(filepath,"HOCX");
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
        uploadStaging.scrollDownTenderControl();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event upload staging is in 'Incomplete' status for HOCX payment");
    }
    @Test(dependsOnMethods = "HocxFilebatchRun")
    public void PEPL1batchRun() throws Exception {
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("C1-PEPL1");
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event Upload Stage-1 batch is running");
        PaymentEventUploadStaging uploadStaging = new PaymentEventUploadStaging();
        uploadStaging.navigation();
        uploadStaging.searchExtTransmitID(extTransmitId);
        uploadStaging.ExtclickSearch();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event upload staging is moved to 'pending' status for HOCX payment");
        uploadStaging.navigateToTenderControl();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Tender Control is Created Open status for HOCX payment");
        uploadStaging.pepl1TenderControlStatus();
        uploadStaging.goback();
        String PEVTPEPL1Query=String.format(DBQueries.PayEventStatus,"20",extTransmitId);
        String PEVTPEPL1Status = DBUtils.getSingleDate(PEVTPEPL1Query, "PEVT_STG_ST_FLG");
        tenderctlId=DBUtils.getSingleDate(PEVTPEPL1Query, "TNDR_CTL_ID");
        System.out.println("Tender Control ID for HOCX file: " + tenderctlId);
        System.out.println("Payment Event Upload Staging Status for HOCX file: " + PEVTPEPL1Status);
        Assert.assertEquals(PEVTPEPL1Status.trim(), "20", "Payment Event Upload Staging Status is not in 'Pending' status for HOCX file");
        String tenderControlQuery=String.format(DBQueries.tenderControl,tenderctlId);
        String tenderControlStatus = DBUtils.getSingleDate(tenderControlQuery, "TNDR_CTL_ST_FLG");
        System.out.println("Tender Control Status for HOCX file: " + tenderControlStatus);
        Assert.assertEquals(tenderControlStatus.trim(), "10", "Tender Control is not in 'Open' status for HOCX file");
    }
    @Test(dependsOnMethods = "PEPL1batchRun")
    public void PEPL2batchRun() throws Exception {
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("C1-PEPL2");
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event Upload Stage-2 batch is running");
        PaymentEventUploadStaging uploadStaging = new PaymentEventUploadStaging();
        uploadStaging.clickRefresh();
        uploadStaging.pepl2payeventstatus();
        uploadStaging.scrollDown();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Payment Event upload staging for HOCX payment file is Completed");
        String PEVTPEPL2Query=String.format(DBQueries.PayEventStatus,"40",extTransmitId);
        String PEVTPEPL2Status = DBUtils.getSingleDate(PEVTPEPL2Query, "PEVT_STG_ST_FLG");
        Assert.assertEquals(PEVTPEPL2Status.trim(), "40", "Payment Event Upload Staging Status is not in 'Complete' status for HOCX file");
    }
    @Test(dependsOnMethods = "PEPL2batchRun")
    public void PEPL3batchRun() throws Exception {
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("C1-PEPL3");
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Balance Tender Controls batch is running");
        PaymentEventUploadStaging uploadStaging = new PaymentEventUploadStaging();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Tender Control is now Balanced for REMP payment");
        uploadStaging.navigation();
        uploadStaging.searchExtTransmitID(extTransmitId);
        uploadStaging.ExtclickSearch();
        uploadStaging.navigateToTenderControl();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Tender Control is now Balanced for HOCX payment");
        uploadStaging.pepl3TenderControlStatus();
        ScreenShotUtils.captureScreenshotToWord("PaymentReceiptAndAutomaticAllocation.docx", "Tender Control is now Balanced for HOCX payment");
        String tenderControlQuery=String.format(DBQueries.tenderControl,tenderctlId);
        String tenderControlStatus = DBUtils.getSingleDate(tenderControlQuery, "TNDR_CTL_ST_FLG");
        System.out.println("Tender Control Status for HOCX file: " + tenderControlStatus);
        Assert.assertEquals(tenderControlStatus.trim(), "30", "Tender Control is not in 'Balanced' status for HOCX file");
    }
}
