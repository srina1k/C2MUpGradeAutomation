package org.example.Tests;

import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.*;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

public class MCTOpportunity extends BaseClass {
    @Test
    public void testLogin(){
        String fileName = "MCTOpportunity.docx";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage LoginPage = new LoginPage(DriverManager.getDriver());
        LoginPage.Logincredentials();
    }
    @Test(dependsOnMethods = "testLogin")
    public void opportunityCreation() throws InterruptedException, SQLException, IOException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String personID1 = ExcelUtils.getCellData(1,4);

        UserPage userpage = new UserPage();
        userpage.NavigateToOpportunity(personID1);
        ScreenShotUtils.captureScreenshotToWord("MCTOpportunity.docx","Step1:Entering Person ID");
        userpage.clickSearch();
        WaitUtils.sleep(3000);
        ScreenShotUtils.captureScreenshotToWord("MCTOpportunity.docx","Step2:Person Details");

        PersonPage perpage = new PersonPage();
        perpage.AddOpportunityDetails("MCT");
        perpage.opportunityType("Warm Prospect");
        perpage.MCTId();
        ScreenShotUtils.captureScreenshotToWord("MCTOpportunity.docx","Step3:Entering Opportunity details");
        perpage.startEndDate();
        ScreenShotUtils.captureScreenshotToWord("MCTOpportunity.docx","Step4:Enter Start and end date");
        perpage.nonFlexProductname("PEACE_OF_MIND");
        perpage.addressIndicator();
        String storeOppID = perpage.captureOppID();
        perpage.addOppIDToWord("MCTOpportunity.docx", "Step5:Opportunity created in Identified status");

        String query = String.format(DBQueries.OppCreation,storeOppID);
        DBUtils.executeSelectQuery(query);
        ExcelUtils.setCellData(2,4, storeOppID);
    }

    @Test(dependsOnMethods = "opportunityCreation")
    public void OppFileCreation(){
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String FstCsvFilePath = ExcelUtils.getCellData(3,4);
        String SecCsvFilePath = ExcelUtils.getCellData(4,4);
        String ThrCsvFilePath = ExcelUtils.getCellData(5,4);
        FileRenameUtils.replaceDate(FstCsvFilePath);
        FileRenameUtils.replaceDate(SecCsvFilePath);
        FileRenameUtils.replaceDate(ThrCsvFilePath);
    }
    @Test(dependsOnMethods = "OppFileCreation")
    public void prospectFileUpoading() throws SQLException {
        String localPath1 = ExcelUtils.getCellData(3,4);
        System.out.println(localPath1);
        String localPath2 = ExcelUtils.getCellData(4,4);
        System.out.println(localPath2);
        String localPath3 = ExcelUtils.getCellData(5,4);
        System.out.println(localPath3);
        String remotePath = "/ccbfsx/Common/prospect_upload/";
        WinScpServerUtils.uploadFile(localPath1,remotePath);
        WinScpServerUtils.uploadFile(localPath2,remotePath);
        WinScpServerUtils.uploadFile(localPath3,remotePath);

        String file1 = FileRenameUtils.getFileNameFromPath(localPath1);
        System.out.println(file1);
        String file2 = FileRenameUtils.getFileNameFromPath(localPath2);
        System.out.println(file2);
        String file3 = FileRenameUtils.getFileNameFromPath(localPath3);
        System.out.println(file3);

        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMSPSU2Batch("CM-SPSU2", file1, "MCTS19-RT-2026");
        batchP.BatchPage();
        batchP.CMSPSU2Batch("CM-SPSU2", file2, "MCTS19-RT-2026");
        batchP.BatchPage();
        batchP.CMSPSU2Batch("CM-SPSU2", file3, "MCTS19-RT-2026");
    }

    @Test(dependsOnMethods = "prospectFileUpoading")
    public void siteFileUpoading() throws IOException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        PersonPage perpage = new PersonPage();
        perpage.SearchOpp("MCTS19-RT-2026");
        String oppId1 = ExcelUtils.getCellData(2,4);
        String oppId2 = perpage.OpportunityId2();
        System.out.println(oppId2);
        String oppId3 = perpage.OpportunityId3();
        System.out.println(oppId3);
        String oppId4 = perpage.OpportunityId4();
        System.out.println(oppId4);
        String cust2 = perpage.Customer2();
        System.out.println(cust2);
        String cust3 = perpage.Customer3();
        System.out.println(cust3);
        String cust4 = perpage.Customer4();
        System.out.println(cust4);
        String txt1 = ExcelUtils.getCellData(6,4);
        String txt2 = ExcelUtils.getCellData(7,4);
        String txt3 = ExcelUtils.getCellData(8,4);
        String txt4 = ExcelUtils.getCellData(9,4);
        String remotePathSite = "/ccbfsx/Common/site_upload/";
        String file1 = FileRenameUtils.getFileNameFromPath(txt1);
        String file2 = FileRenameUtils.getFileNameFromPath(txt2);
        String file3 = FileRenameUtils.getFileNameFromPath(txt3);
        String file4 = FileRenameUtils.getFileNameFromPath(txt4);
        FileRenameUtils.replaceCustID(Paths.get(txt2), cust2);
        FileRenameUtils.replaceCustID(Paths.get(txt3), cust3);
        FileRenameUtils.replaceCustID(Paths.get(txt4), cust4);
        WinScpServerUtils.uploadFile(txt1,remotePathSite);
        WinScpServerUtils.uploadFile(txt2,remotePathSite);
        WinScpServerUtils.uploadFile(txt3,remotePathSite);
        WinScpServerUtils.uploadFile(txt4,remotePathSite);
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMSPSU1ABatch("CMSPSU1A", file1, oppId1);
        batchP.BatchPage();
        batchP.CMSPSU1ABatch("CMSPSU1A", file2, oppId2);
        batchP.BatchPage();
        batchP.CMSPSU1ABatch("CMSPSU1A", file3, oppId3);
        batchP.BatchPage();
        batchP.CMSPSU1ABatch("CMSPSU1A", file4, oppId4);
        perpage.goback();
    }
    @Test(dependsOnMethods = "siteFileUpoading")
    public void CCTermSet() {
        OppForPerson oppPer = new OppForPerson();
        Runnable[] customers = {oppPer::CustomerhyperLink1, oppPer::CustomerhyperLink2, oppPer::CustomerhyperLink3, oppPer::CustomerhyperLink4};
        for (Runnable customer : customers){
            customer.run();
            oppPer.CreditCheck();
            oppPer.TermSet();
            oppPer.QualifyingOpportunity();
        }
    }
    @Test(dependsOnMethods = "CCTermSet")
    public void BatchRun(){
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("CM-ECCHK");
    }
    @Test(dependsOnMethods = "BatchRun")
    public void QuoteGeneration() throws IOException {
        OppForPerson oppPer = new OppForPerson();
        oppPer.qualifiedQuoteInProgress();
        ScreenShotUtils.captureScreenshotToWord("MCTOpportunity.docx","Quote Generated for 4th Opportunity");
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String Caseid4 = oppPer.quote();
        System.out.println("Quote ID-4: " + Caseid4);
        oppPer.goBack();
        oppPer.holdPenNavigation3();
        oppPer.holdingPenOverride();
        oppPer.qualifiedQuoteInProgress();
        ScreenShotUtils.captureScreenshotToWord("MCTOpportunity.docx","Quote Generated for 3rd Opportunity");
        String Caseid3 = oppPer.quote();
        System.out.println("Quote ID-3: " + Caseid3);
        oppPer.goBack();
        oppPer.holdPenNavigation2();
        oppPer.qualifiedQuoteInProgress();
        ScreenShotUtils.captureScreenshotToWord("MCTOpportunity.docx","Quote Generated for 2nd Opportunity");
        String Caseid2 = oppPer.quote();
        System.out.println("Quote ID-2: " + Caseid2);
        oppPer.goBack();
        oppPer.holdPenNavigation1();
        oppPer.holdingPenOverride();
        oppPer.qualifiedQuoteInProgress();
        ScreenShotUtils.captureScreenshotToWord( "MCTOpportunity.docx","Quote Generated for1st Opportunity");
        String Caseid1 = oppPer.quote();
        System.out.println("Quote ID-1: " + Caseid1);
        //Take all 4 Quote ID's and mail to QM team to check P-C relationship
        //Ended.....
    }
}
