package org.example.Tests;

import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.*;
import org.testng.annotations.Test;
import java.io.File;
import java.sql.SQLException;

public class smallFixedP2 extends BaseClass {

    String oppID;
    String contractID;

    @Test
    public void testLogin(){

        String fileName = "SmallFixed-P2.docx";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage lp = new LoginPage(DriverManager.getDriver());
        lp.Logincredentials();
    }
    @Test (dependsOnMethods = "testLogin")
    public void contractValidation() throws SQLException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
        oppID = ExcelUtils.getCellData(3, 1);

        String query = String.format(DBQueries.syncReq, oppID);
        String syncRequestID = DBUtils.getSingleDate(query, "F1_SYNC_REQ_ID");
        System.out.println("F1_SYNC_REQ_ID: " + syncRequestID);

        syncRequestPage syncReq = new syncRequestPage();
        syncReq.NavigateTosyncRequestQuery();
        syncReq.dropdownSyncRequestID(syncRequestID);
        ScreenShotUtils.captureScreenshotToWord("SmallFixed-P2.docx", "Quote Accepted and Sync Request Created in system");
        syncReq.validation();
        ScreenShotUtils.captureScreenshotToWord("SmallFixed-P2.docx", "Sync Request Validation completed");

        String quoteQuery1 = String.format(DBQueries.IsolateQuote, oppID);
        DBUtils.UpdateQuery(quoteQuery1);

        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMMONOPBatch("CM-MONOP");
        batchP.clickRefresh();
        ScreenShotUtils.captureScreenshotToWord("SmallFixed-P2.docx","Batch Completed");

        String quoteQuery2 = String.format(DBQueries.DeIsolateQuote, oppID);
        DBUtils.UpdateQuery(quoteQuery2);

        String contractQuery = String.format(DBQueries.fetchContract, oppID);
        contractID = DBUtils.getSingleDate(contractQuery, "CM_CONTRACT_ID");
        System.out.println("Contract_ID: " + contractID);

        contractSearch contract = new contractSearch();
        contract.navigateToContract(contractID);
        ScreenShotUtils.captureScreenshotToWord("SmallFixed-P2.docx","Contract is created in Set-Up Inprogress status");
        contract.ContractValidation();
        ScreenShotUtils.captureScreenshotToWord("SmallFixed-P2.docx","Contract is validated and Accepted");
    }

    @Test (dependsOnMethods = "contractValidation")
    public void marketMessageRequested() throws SQLException {

        marketMessageSearch mktmsg = new marketMessageSearch();
        mktmsg.navigateToMarketMessage();
        mktmsg.mmSearchContractID(contractID);
        ScreenShotUtils.captureScreenshotToWord("SmallFixed-P2.docx","Market Message is created in Pending status");

        //Isolate all OMMs in PENDING to PENDINGX
        String OmmIsolate = String.format(DBQueries.ommIsolate, oppID);
        DBUtils.UpdateQuery(OmmIsolate);

        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMOBMMDBatch("CM-OBMMD");
        ScreenShotUtils.captureScreenshotToWord("SmallFixed-P2.docx","Batch Completed. Market message Moved to Completed Status ");
        batchP.clickRefresh();

        mktmsg.mmclickRefresh();
        ScreenShotUtils.captureScreenshotToWord("SmallFixed-P2.docx","Batch Completed. Market message Moved to Completed Status ");

        //Next mail to AFMS for required flow..
        //Continue manual process by validating all flow droped from AFMS

    }
}
