package org.example.Tests;


import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.*;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.SQLException;

public class MediumFixedP2 extends BaseClass {

    String oppID;
    String contractID;

    @Test
    public void testLogin(){

        String fileName = "MediumFixedP2.docx";
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
        oppID = ExcelUtils.getCellData(3, 7);

        String query = String.format(DBQueries.syncReq, oppID);
        String syncRequestID = DBUtils.getSingleDate(query, "F1_SYNC_REQ_ID");
        System.out.println("F1_SYNC_REQ_ID: " + syncRequestID);

        syncRequestPage syncReq = new syncRequestPage();
        syncReq.NavigateTosyncRequestQuery();
        syncReq.dropdownSyncRequestID(syncRequestID);
        ScreenShotUtils.captureScreenshotToWord("MediumFixedP2.docx","Quote Accepted and Sync Request Created in system");
        syncReq.validation();
        ScreenShotUtils.captureScreenshotToWord("MediumFixedP2.docx","Sync Request Validation completed");

        //String quoteQuery1 = String.format(DBQueries.IsolateQuote, oppID);
        //DBUtils.UpdateQuery(quoteQuery1);

        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMMONOPBatch("CM-MONOP");
        //batchP.clickRefresh();
        ScreenShotUtils.captureScreenshotToWord("MediumFixedP2.docx","Batch Completed");

        //String quoteQuery2 = String.format(DBQueries.DeIsolateQuote, oppID);
        //DBUtils.UpdateQuery(quoteQuery2);

        String contractQuery = String.format(DBQueries.fetchContract, oppID);
        contractID = DBUtils.getSingleDate(contractQuery, "CM_CONTRACT_ID");
        System.out.println("Contract_ID: " + contractID);

        contractSearch contract = new contractSearch();
        contract.navigateToContract(contractID);
        ScreenShotUtils.captureScreenshotToWord("MediumFixedP2.docx","Contract is created in Set-Up Inprogress status");
        contract.ContractValidation();
        ScreenShotUtils.captureScreenshotToWord("MediumFixedP2.docx","Contract is validated and Accepted");
    }

    @Test(dependsOnMethods = "contractValidation")
    public void SACreation() throws SQLException {

        marketMessageSearch mktmsg = new marketMessageSearch();
        mktmsg.navigateToMarketMessage();
        mktmsg.mmSearchContractID(contractID);
        ScreenShotUtils.captureScreenshotToWord("MediumFixedP2.docx","Market Message is created in Pending status");

        //Isolate all OMMs in PENDING to PENDINGX
        String OmmIsolate = String.format(DBQueries.ommIsolate, oppID);
        DBUtils.UpdateQuery(OmmIsolate);

        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMOBMMDBatch("CM-OBMMD");
        ScreenShotUtils.captureScreenshotToWord("MediumFixedP2.docx","Batch Completed. Market message Moved to Completed Status ");
        //batchP.clickRefresh();

        //DeIsolate all OMMs in PENDINGX to PENDING
        String DeIsolate = String.format(DBQueries.ommDeIsolate, oppID);
        DBUtils.UpdateQuery(DeIsolate);

        String SAQuery = String.format(DBQueries.newSACheckQuery, oppID);
        String saID = DBUtils.getSingleDate(SAQuery, "SA_ID");
        System.out.println("SA_ID: " + saID);

        ServiceAgreementPage saPage = new ServiceAgreementPage();
        saPage.navigateToSA(saID);
        saPage.clickSearch();
        ScreenShotUtils.captureScreenshotToWord("MediumFixedP2.docx","New SA created in Pending Start Status ");

        //Isolate all SAs in PENDING START and PENDING STOP
        String SAIsolate = String.format(DBQueries.saIsolatePendingStart, oppID);
        DBUtils.UpdateQuery(SAIsolate);

        String SAIsolate1 = String.format(DBQueries.saIsolatePendingStop, oppID);
        DBUtils.UpdateQuery(SAIsolate1);

        batchP.BatchPage();
        batchP.enterBatchCode("SAACT");
        ScreenShotUtils.captureScreenshotToWord("MediumFixedP2.docx","Running SAACT batch to Activate SA created in Pending Start");
        batchP.clickRefresh();

        saPage.clickRefresh();
        ScreenShotUtils.captureScreenshotToWord("MediumFixedP2.docx","After completion of SAACT batch SA is Activated");

        //DeIsolate all SAs in PENDING START and PENDING STOP
        String SADeIsolate = String.format(DBQueries.saDeIsolatePendingStart, oppID);
        DBUtils.UpdateQuery(SADeIsolate);

        String SADeIsolate1 = String.format(DBQueries.saDeIsolatePendingStop, oppID);
        DBUtils.UpdateQuery(SADeIsolate1);


    }
}
