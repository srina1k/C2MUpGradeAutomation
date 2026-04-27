package org.example.Tests;



import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.*;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.SQLException;

public class COTP2 extends BaseClass {

    String oppID;
    String contractID;
    @Test
    public void testLogin(){
        String fileName = "COTP2.docx";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage lp = new LoginPage(DriverManager.getDriver());
        lp.Logincredentials();
    }

    @Test(dependsOnMethods = "testLogin")
    public void marketMessageValidation() throws SQLException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
        oppID = ExcelUtils.getCellData(8, 9);

        String query = String.format(DBQueries.syncReq, oppID);
        String syncRequestID = DBUtils.getSingleDate(query, "F1_SYNC_REQ_ID");
        System.out.println("F1_SYNC_REQ_ID: " + syncRequestID);

        syncRequestPage syncReq = new syncRequestPage();
        syncReq.NavigateTosyncRequestQuery();
        syncReq.dropdownSyncRequestID(syncRequestID);
        ScreenShotUtils.captureScreenshotToWord("COMC15P2.docx", "Quote Accepted and Sync Request Created in system");
        syncReq.validation();
        ScreenShotUtils.captureScreenshotToWord("COMC15P2.docx", "Sync Request Validation completed");

        String quoteQuery1 = String.format(DBQueries.IsolateQuote, oppID);
        DBUtils.UpdateQuery(quoteQuery1);

        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMMONOPBatch("CM-MONOP");
        ScreenShotUtils.captureScreenshotToWord("COTP2.docx", "Batch is running");
        batchP.clickRefresh();

        String quoteQuery2 = String.format(DBQueries.DeIsolateQuote, oppID);
        DBUtils.UpdateQuery(quoteQuery2);

        String contractQuery = String.format(DBQueries.fetchContract, oppID);
        contractID = DBUtils.getSingleDate(contractQuery, "CM_CONTRACT_ID");
        System.out.println("Contract_ID: " + contractID);

        batchP.BatchPage();
        batchP.CMMCONIBatch("CM-MCONI");
        ScreenShotUtils.captureScreenshotToWord("COTP2.docx", "To accept the contract run below batch");
        batchP.clickRefresh();

        String marketMsgQuery = String.format(DBQueries.marketMessage1, contractID);
        String MarketMsgID = DBUtils.getSingleDate(marketMsgQuery, "CM_MKTMSG_ID");
        System.out.println("MKTMSG_ID: " + MarketMsgID);

        marketMessageSearch mktmsg = new marketMessageSearch();
        mktmsg.navigateToMarketMessage();
        mktmsg.OdropdownoMarketMessageId(MarketMsgID);
        ScreenShotUtils.captureScreenshotToWord("COTP2.docx", "Market Message is created in Pending status");

        mktmsg.OmarketMsgValidation1();
        ScreenShotUtils.captureScreenshotToWord("COTP2.docx", "Market Message Validated and moved to message requested status");
    }
        @Test (dependsOnMethods = "marketMessageValidation")
        public void saCreation() {

        String saCheckingQuery = String.format(DBQueries.ServiceAgreement, contractID);
        String SAID = DBUtils.getSingleDate(saCheckingQuery, "SA_ID");
        System.out.println("Service Agreement_ID: " + SAID);

        ServiceAgreementPage sapage = new ServiceAgreementPage();
        sapage.navigateToSA(SAID);
        sapage.clickSearch();
        ScreenShotUtils.captureScreenshotToWord("COTP2.docx","SA is created in pending Start");
        sapage.VerifysaSp();
        ScreenShotUtils.captureScreenshotToWord("COTP2.docx","SA/SP realtion");
        sapage.VerifychargesQty();
        ScreenShotUtils.captureScreenshotToWord("COTP2.docx","Chars added to new SA");

    }
}
