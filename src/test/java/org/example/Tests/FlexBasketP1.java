package org.example.Tests;


import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.*;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;


public class FlexBasketP1 extends BaseClass {

    String storeOppID1;
    String storeOppID2;
    String storeOppID3;
    String frameWorkID;
    String caseid1;
    String caseid2;
    String caseid3;

    @Test
    public void testLogin() {
        String fileName = "FlexBasket-P1.docx";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.Logincredentials();
    }

    @Test(dependsOnMethods = "testLogin")
    public void opportunityCreation() throws Exception {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String personID1 = ExcelUtils.getCellData(11, 7);
        UserPage userpage = new UserPage();
        userpage.NavigateToOpportunity(personID1);
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Step1:Entering Person ID");

        userpage.clickSearch();
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Step2:Person Details");
        PersonPage perpage = new PersonPage();
        perpage.AddOpportunityDetails("FlexBasketOpp1");
        perpage.basketInd();
        perpage.opportunityType("Cold Prospect");
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Step3:Entering Opportunity details");
        perpage.startEndDate();
        perpage.FlexProductname("FLEX_BASKET");
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Step4:Enter Start and end date");
        perpage.addressIndicator();
        storeOppID1 = perpage.captureOppID();
        frameWorkID = perpage.frameworkID();
        perpage.addOppIDToWord("FlexBasket-P1.docx", "Step5:1st Opportunity created in Identified status:" + storeOppID1);

        System.out.println("OPp ID: " + storeOppID1);
        System.out.println("Frame Work ID: " + frameWorkID);

        //2nd Opportunity Creation
        String personID2 = ExcelUtils.getCellData(12, 7);
        userpage.NavigateToOpportunity(personID2);
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Step1:Entering Person ID");

        userpage.clickSearch();
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Step2:Person Details");
        perpage.AddOpportunityDetails("FlexBasketOpp2");
        perpage.basketInd();
        perpage.opportunityType("Cold Prospect");
        perpage.startEndDate();
        perpage.FlexProductname("FLEX_BASKET");
        perpage.enterFrameworkID(frameWorkID);
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Step3:Enter Start and end date");
        perpage.addressIndicator();
        storeOppID2 = perpage.captureOppID();
        perpage.addOppIDToWord("FlexBasket-P1.docx", "Step4:2nd Opportunity created in Identified status:" + storeOppID2);
        System.out.println("OPp ID: " + storeOppID2);

        //3rd Opportunity Creation
        String personID3 = ExcelUtils.getCellData(13, 7);
        userpage.NavigateToOpportunity(personID3);
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Step1:Entering Person ID");

        userpage.clickSearch();
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Step2:Person Details");
        perpage.AddOpportunityDetails("FlexBasketOpp3");
        perpage.basketInd();
        perpage.opportunityType("Cold Prospect");
        perpage.startEndDate();
        perpage.FlexProductname("FLEX_BASKET");
        perpage.enterFrameworkID(frameWorkID);
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Step3:Enter Start and end date");
        perpage.addressIndicator();
        storeOppID3 = perpage.captureOppID();
        perpage.addOppIDToWord("FlexBasket-P1.docx", "Step4:3rd Opportunity created in Identified status:" + storeOppID3);
        System.out.println("OPp ID: " + storeOppID3);
        String CUUB2File = ExcelUtils.getCellData(14, 7);
        List<String> newOppIDs = Arrays.asList(storeOppID1, storeOppID2, storeOppID3);
        Iterator<String> newIDIterator = newOppIDs.iterator();
        Map<String, String> replaceMap = new HashMap<>();
        List<String> updatedLines = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(CUUB2File))) {
            if (line.startsWith("DTL")) {
                String[] cols = line.split(",", -1);
                String existingOppID = cols[cols.length - 1].trim();
                if (!replaceMap.containsKey(existingOppID)) {
                    if (!newIDIterator.hasNext()) {
                        throw new RuntimeException("Error: Not enoung new opp ID's provided. ");
                    }
                    replaceMap.put(existingOppID, newIDIterator.next());
                }
                cols[cols.length - 1] = " " + replaceMap.get(existingOppID);
                line = String.join(",", cols);
            }
            updatedLines.add(line);
        }
        Files.write(Paths.get(CUUB2File), updatedLines);
        System.out.println("Opp IDs replaced successfully");
        String CMBLRNOPFilePath = ExcelUtils.getCellData(15, 7);
        FileRenameUtils.replaceOPPID(CMBLRNOPFilePath, newOppIDs);
    }

    @Test(dependsOnMethods = "opportunityCreation")
    public void batchRun() throws SQLException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String CUUB2File = ExcelUtils.getCellData(14, 7);
        String CMBLRNOPfile = ExcelUtils.getCellData(15, 7);

        String fileName1 = FileRenameUtils.getFileNameFromPath(CUUB2File);
        System.out.println(fileName1);
        String fileName2 = FileRenameUtils.getFileNameFromPath(CMBLRNOPfile);

        String remotePath1 = "/ccbfsx/Common/customer_upload/";
        String remotePath2 = "/ccbfsx/Central-Pricing/renewal_site_details_upload/";
        WinScpServerUtils.uploadFile(CUUB2File, remotePath1);
        WinScpServerUtils.uploadFile(CMBLRNOPfile, remotePath2);
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMCUUB2Batch("CM-CUUB2", fileName1);
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Customer Upload batch is running");
        //batchP.clickRefresh();
        batchP.BatchPage();
        batchP.CMBLRNOPBatch("CMBLRNOP", fileName2);
        //batchP.clickRefresh();
        batchP.BatchPage();
        batchP.enterBatchCode("CMCROPSP");
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Running CMCROPSP Batch to move Staging data from Pending to In progress");
        //batchP.clickRefresh();
        batchP.BatchPage();
        batchP.enterBatchCode("CMRPGEN");
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Running CMRPGEN Batch to Complete Staging data");
       // batchP.clickRefresh();
        String query = String.format(DBQueries.OPP_FOR_PER_AT_SP, frameWorkID);
        DBUtils.executeSelectQuery(query);
    }

    @Test(dependsOnMethods = "batchRun")
    public void CCTermSetForThirdOpp() throws IOException {
        PersonPage perpage = new PersonPage();
        OppForPerson oppPer = new OppForPerson();
        perpage.secondPerson();
        oppPer.CreditCheck();
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Credit Check processed");
        oppPer.TermSet();
        perpage.thirdPerson();
        oppPer.createdCreditCheck();
        oppPer.TermSet();
        oppPer.QualifyingOpportunity();
        //oppPer.QualifyingFlexBasketOpportunity();
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Opportunity Qualified");
        oppPer.qualifiedQuoteInProgressBatchMode();
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "3rd Opportunity Moved to QRBATCH mode..");
//        caseid3 = oppPer.quote();
//        System.out.println("Quote generated for 3rd Opp: " + caseid3);
    }

    @Test(dependsOnMethods = "CCTermSetForThirdOpp")
    public void CCTermSetForSecondOpp() throws IOException {
        PersonPage perpage = new PersonPage();
        OppForPerson oppPer = new OppForPerson();
        oppPer.goBack3times();
        perpage.firstPerson();
        oppPer.createdCreditCheck();
        oppPer.TermSet();
        perpage.thirdPerson();
        oppPer.CreditCheck();
        oppPer.TermSet();
        oppPer.QualifyingOpportunity();
        oppPer.qualifiedQuoteInProgressBatchMode();
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Here, I am adding evidence for only one Opportunity details, 2nd Opportunity Moved to QRBATCH mode..");
////        caseid2=oppPer.quote();
////        System.out.println("CaseId for Opportunity ID2:" +caseid2);
    }
    @Test(dependsOnMethods = "CCTermSetForSecondOpp")
    public void CCTermSetForFirstOpp() throws IOException {
        PersonPage perpage = new PersonPage();
        OppForPerson oppPer = new OppForPerson();
        oppPer.goBack3times();
        perpage.firstPerson();
        oppPer.createdCreditCheck();
        oppPer.TermSet();
        perpage.secondPerson();
        oppPer.CreditCheck();
        oppPer.TermSet();
        oppPer.QualifyingOpportunity();
        oppPer.qualifiedQuoteInProgressBatchMode();
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "1st Opportunity Moved to QRBATCH mode..");
//////        caseid1=oppPer.quote();
//////        System.out.println("caseID3:" +caseid1);
   }
    @Test(dependsOnMethods = "CCTermSetForFirstOpp")
    public void batchForQuote() throws IOException {
        WindowHandlesUtils.duplicateCurrentTab();
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("CM-MOPPB");
        ScreenShotUtils.captureScreenshotToWord("FlexBasket-P1.docx", "Running CM-MOPPB batch to generate Quote ID's for all 3 Opportunities");
        WindowHandlesUtils.switchToFirstWindow();
        ExcelUtils.openWorkBook("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        ExcelUtils.setCellData(16,7, storeOppID1);
        ExcelUtils.setCellData(17,7, storeOppID2);
        ExcelUtils.setCellData(18,7, storeOppID3);
        ExcelUtils.setCellData(19,7, frameWorkID);
        ExcelUtils.saveAndClose();
        //Take The Quote ID's and mail to QM team to accept Quote

    }
}