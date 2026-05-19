package org.example.Tests;



import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.*;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.Driver;
import java.sql.SQLException;

public class FlexBasketP2 extends BaseClass {

    String contractID;
    String storeOppID1;
    String storeOppID2;
    String storeOppID3;
    String mmID;
    String frameWorkID;

    @Test
    public void testLogin() {
        String fileName = "FlexBasket-P2.docx";
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
        ExcelUtils.openWorkBook("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
        storeOppID1 = ExcelUtils.getCellData(16, 7);
        storeOppID2 = ExcelUtils.getCellData(17, 7);
        storeOppID3 = ExcelUtils.getCellData(18, 7);

        String query1 = String.format(DBQueries.syncReq, storeOppID1);
        String syncRequestID1 = DBUtils.getSingleDate(query1, "F1_SYNC_REQ_ID");
        System.out.println("F1_SYNC_REQ_ID1: " + syncRequestID1);

        String query2 = String.format(DBQueries.syncReq, storeOppID2);
        String syncRequestID2 = DBUtils.getSingleDate(query2, "F1_SYNC_REQ_ID");
        System.out.println("F1_SYNC_REQ_ID2: " + syncRequestID2);

        String query3 = String.format(DBQueries.syncReq, storeOppID3);
        String syncRequestID3 = DBUtils.getSingleDate(query3, "F1_SYNC_REQ_ID");
        System.out.println("F1_SYNC_REQ_ID3: " + syncRequestID3);

        syncRequestPage syncReq = new syncRequestPage();
        syncReq.NavigateTosyncRequestQuery();
        syncReq.dropdownSyncRequestID(syncRequestID2);
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P2.docx","Quote Accepted and Sync Request Created in system");
//
        //String isolateOpportunity = String.format(DBQueries.isolateOpp, storeOppID1);
        String isolateOpportunity1 = String.format(DBQueries.IsolateQuote, storeOppID1);
        DBUtils.UpdateQuery(isolateOpportunity1);

        String isolateOpportunity2=String.format(DBQueries.IsolateQuote,storeOppID2);
        DBUtils.UpdateQuery(isolateOpportunity2);

        String isolateOpportunity3=String.format(DBQueries.IsolateQuote,storeOppID3);
        DBUtils.UpdateQuery(isolateOpportunity3);

        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMQRSYN2Batch("CMQRSYN2");
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P2.docx","Running Quote Response Sync Request Monitor Batch to process Sync Request");
        batchP.clickRefresh();
        batchP.BatchPage();
        batchP.CMMONOPBatch("CM-MONOP");
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P2.docx","Running Monitor Opportunity Batch to process Opportunity ID to WOn-PA");
        batchP.clickRefresh();

        String DeisolateOpportunity1=String.format(DBQueries.DeIsolateQuote,storeOppID1);
        DBUtils.UpdateQuery(DeisolateOpportunity1);

        String DeisolateOpportunity2=String.format(DBQueries.DeIsolateQuote,storeOppID2);
        DBUtils.UpdateQuery(DeisolateOpportunity2);

        String DeisolateOpportunity3=String.format(DBQueries.DeIsolateQuote,storeOppID3);
        DBUtils.UpdateQuery(DeisolateOpportunity3);
        batchP.BatchPage();
        batchP.CMMCONIBatch("CM-MCONI");
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P2.docx","Running Monitor Process for Contract Batch to process Conract to Accepted ");
        batchP.clickRefresh();
//
//        //There are number of Contract created, will take one
        String contractQuery = String.format(DBQueries.fetchContract, storeOppID1);
        contractID = DBUtils.getSingleDate(contractQuery, "CM_CONTRACT_ID");
        System.out.println("Contract_ID: " + contractID);
//
        contractSearch contract = new contractSearch();
        contract.navigateToContract(contractID);
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P2.docx","Contract is created in Set-Up Inprogress status");
    }

    @Test (dependsOnMethods = "contractValidation")
    public void marketMessageValidation () throws SQLException {

        String mmQuery = String.format(DBQueries.marketMessage, storeOppID1);
        mmID = DBUtils.getSingleDate(mmQuery, "CM_MKTMSG_ID");
        System.out.println("Market Message_ID: " + mmID);
//
        marketMessageSearch mktmsg = new marketMessageSearch();
        mktmsg.navigateToMarketMessage();
        mktmsg.OdropdownoMarketMessageId(mmID);
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P2.docx","Market Message created in Pending status");
//
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMOBMMDBatch("CM-OBMMD");
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P2.docx","Batch Completed.");
        batchP.clickRefresh();
//
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P2.docx","Market message Moved to Completed Status ");
        mktmsg.OMMScreenshot();
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P2.docx","Service agreement created");
//
        frameWorkID = ExcelUtils.getCellData(19, 7);
        frameworkSearch frm = new frameworkSearch();
        frm.navigationToFrameworkSearch();
        frm.searchByFrameworkSearch(frameWorkID);
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P2.docx","Dates are not added");

        //Mail to QM team for RL acceptance
        //After RL acceptance Part 3 will start
    }
}
