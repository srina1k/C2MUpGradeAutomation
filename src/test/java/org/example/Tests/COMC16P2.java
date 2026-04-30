package org.example.Tests;


import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.*;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.SQLException;

public class COMC16P2 extends BaseClass {

    String oppID;
    String mpan;
    String flow1 = "1";
    String flow2 = "2";
    String flow4 = "4";
    String contractID;

    @Test
    public void testLogin(){
        String fileName  = "COMC16P2.docx";
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage lp = new LoginPage(DriverManager.getDriver());
        lp.Logincredentials();
    }

    @Test (dependsOnMethods = "testLogin")
    public void marketMessageValidation() throws SQLException, SQLException {
        ExcelUtils.loadExcel("D:\\Users\\sunag1a\\IdeaProjects\\AM_Tasks\\src\\main\\resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
        oppID = ExcelUtils.getCellData(3, 2);

        String query = String.format(DBQueries.syncReq, oppID);
        String syncRequestID = DBUtils.getSingleDate(query, "F1_SYNC_REQ_ID");
        System.out.println("F1_SYNC_REQ_ID: " + syncRequestID);

        syncRequestPage syncReq = new syncRequestPage();
        syncReq.NavigateTosyncRequestQuery();
        syncReq.dropdownSyncRequestID(syncRequestID);
        ScreenShotUtils.captureScreenshotToWord("COMC16P2.docx","Quote Accepted and Sync Request Created in system");
        syncReq.validation();
        ScreenShotUtils.captureScreenshotToWord("COMC16P2.docx","Sync Request Validation completed");

        String quoteQuery1 = String.format(DBQueries.IsolateQuote, oppID);
        DBUtils.UpdateQuery(quoteQuery1);

        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMMONOPBatch("CM-MONOP");
        batchP.clickRefresh();
        ScreenShotUtils.captureScreenshotToWord("COMC16P2.docx","Batch Completed");

        String quoteQuery2 = String.format(DBQueries.DeIsolateQuote, oppID);
        DBUtils.UpdateQuery(quoteQuery2);

        String contractQuery = String.format(DBQueries.fetchContract, oppID);
        String contractID = DBUtils.getSingleDate(contractQuery, "CM_CONTRACT_ID");
        System.out.println("Contract_ID: " + contractID);

        contractSearch contract = new contractSearch();
        contract.navigateToContract(contractID);
        ScreenShotUtils.captureScreenshotToWord("COMC16P2.docx","Contract is created in Set-Up Inprogress status");
        contract.ContractValidation();
        ScreenShotUtils.captureScreenshotToWord("COMC16P2.docx","Contract is validated and Accepted");

        String marketMsgQuery = String.format(DBQueries.marketMessage, oppID);
        String MarketMsgID = DBUtils.getSingleDate(marketMsgQuery, "CM_MKTMSG_ID");
        System.out.println("MKTMSG_ID: " + MarketMsgID);

        marketMessageSearch mktmsg = new marketMessageSearch();
        mktmsg.navigateToMarketMessage();
        mktmsg.OdropdownoMarketMessageId(MarketMsgID);
        ScreenShotUtils.captureScreenshotToWord("COMC16P2.docx","Market Message is created in Pending status");

        mktmsg.OmarketMsgValidation();
        ScreenShotUtils.captureScreenshotToWord("COMC16P2.docx","Market Message Validated and moved to message requested status");
        mpan = mktmsg.fetchMpan();
        System.out.println("MPAN: " + mpan);
    }

    @Test (dependsOnMethods = "marketMessageValidation")
    public void xaiSubmission() {
        XAISubmissionPage xai = new XAISubmissionPage();
        xai.navigateToXAISubmission();
        String xaiRequest1 = xaiUtils.SComcFlow(flow1, mpan);
        xai.submitXAI(xaiRequest1);

        String InmarketMsgQuery1 = String.format(DBQueries.InMktMessage1, contractID);
        String InMarketMsgID1 = DBUtils.getSingleDate(InmarketMsgQuery1, "CM_MKTMSG_ID");
        System.out.println("InboundMarketMessage_ID1: " + InMarketMsgID1);

        WindowHandlesUtils.duplicateCurrentTab();
        marketMessageSearch mktmsg = new marketMessageSearch();
        mktmsg.navigateToMarketMessage();
        mktmsg.IdropdownoMarketMessageId(InMarketMsgID1);
        ScreenShotUtils.captureScreenshotToWord("COMC16P2.docx", "Inbound Market Message is created in Pending status");

        mktmsg.ImarketMsgValidation();
        ScreenShotUtils.captureScreenshotToWord("COMC16P2.docx", "Inbound Market Message is processed");

        xai.clearXai();
        xai.navigateToXAISubmission();
        String xaiRequest2 = xaiUtils.SComcFlow(flow2, mpan);
        xai.submitXAI(xaiRequest2);

        String InmarketMsgQuery2 = String.format(DBQueries.InMktMessage2, contractID);
        String InMarketMsgID2 = DBUtils.getSingleDate(InmarketMsgQuery2, "CM_MKTMSG_ID");
        System.out.println("InboundMarketMessage_ID2: " + InMarketMsgID2);

        WindowHandlesUtils.switchToSecondWindow();
        mktmsg.navigateToMarketMessage();
        mktmsg.IdropdownoMarketMessageId(InMarketMsgID2);
        mktmsg.ImarketMsgValidation();

        WindowHandlesUtils.switchToFirstWindow();
        xai.clearXai();
        xai.navigateToXAISubmission();
        String xaiRequest4 = xaiUtils.SComcFlow(flow4, mpan);
        xai.submitXAI(xaiRequest4);

        String InmarketMsgQuery4 = String.format(DBQueries.InMktMessage4, contractID);
        String InMarketMsgID4 = DBUtils.getSingleDate(InmarketMsgQuery4, "CM_MKTMSG_ID");
        System.out.println("InboundMarketMessage_ID4: " + InMarketMsgID4);

        WindowHandlesUtils.switchToSecondWindow();
        mktmsg.navigateToMarketMessage();
        mktmsg.IdropdownoMarketMessageId(InMarketMsgID4);
        mktmsg.ImarketMsgValidation();

        WindowHandlesUtils.switchToFirstWindow();
        PersonPage perpage = new PersonPage();
        perpage.gobackClickRefresh();
        ScreenShotUtils.captureScreenshotToWord("COMC16P2.docx","Outbound Market Message is moved to  MTD Received status");

        String SAquery = String.format(DBQueries.SACheckQuery, oppID);
        String SA = DBUtils.getSingleDate(SAquery, "SA_ID");
        System.out.println("ServiceAgreementID: " + SA);

        ServiceAgreementPage sapage = new ServiceAgreementPage();
        sapage.navigateToSA(SA);
        sapage.clickSearch();
        ScreenShotUtils.captureScreenshotToWord("COMC16P2.docx","SA is created in pending Start");

    }
}
