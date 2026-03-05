package org.example.Tests;
import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.*;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class MediumFixedP1 extends BaseClass {
    @Test
    public void testLogin(){
        String fileName = "Medium Fixed-Renewal.docx";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage LoginPage = new LoginPage(DriverManager.getDriver());
        LoginPage.Logincredentials();
    }
    @Test(dependsOnMethods = "testLogin")
    public void opportunityCreation() {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","sheet1");
        String personID1 = ExcelUtils.getCellData(1,7);
        UserPage userpage = new UserPage();
        userpage.NavigateToOpportunity(personID1);
        ScreenShotUtils.captureScreenshotToWord( "Medium Fixed-Renewal.docx","Step1:Entering Person ID");
        userpage.clickSearch();
        ScreenShotUtils.captureScreenshotToWord("Medium Fixed-Renewal.docx","Step2:Person Details");
        PersonPage perpage = new PersonPage();
        perpage.AddOpportunityDetails("Medium Fixed");
        perpage.opportunityType("Renewal");
        ScreenShotUtils.captureScreenshotToWord("Medium Fixed-Renewal.docx","Step3:Entering Opportunity details");
        perpage.startEndDate();
        ScreenShotUtils.captureScreenshotToWord("Medium Fixed-Renewal.docx","Step4:Enter Start and end date");
        perpage.nonFlexProductname("PEACE_OF_MIND");
        perpage.addressIndicator();
        perpage.captureOppID();
        perpage.addOppIDToWord("Medium Fixed-Renewal.docx", "Step5:Opportunity created in Identified status");
    }
    @Test(dependsOnMethods = "opportunityCreation")
    public void FileUpoading() throws SQLException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String FilePath = ExcelUtils.getCellData(2,7);
        PersonPage perpage = new PersonPage();
        String newOppID = perpage.captureOppID();
        String storeOppID =perpage.getSaveOppID();
        String oldOppID = ExcelUtils.getCellData(5,7);
        FileRenameUtils.replaceOppID(FilePath,oldOppID,storeOppID);
        String remotePath = "/ccbfsx/Central-Pricing/renewal_site_details_upload/";
        WinScpServerUtils.uploadFile(FilePath, remotePath);
        String query = String.format(DBQueries.OppCreation,storeOppID);
        DBUtils.executeSelectQuery(query);
    }
    @Test(dependsOnMethods = "FileUpoading")
    public void BatchRun() throws SQLException {
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String FilePath = ExcelUtils.getCellData(2,7);
        String Filename = new File(FilePath).getName();
        batchP.CMBLRNOPBatch("CMBLRNOP", Filename);
        String query = DBQueries.StagingData;
        String query1 = DBQueries.UpdateStagingData;
        DBUtils.UpdateQuery(query1);
        batchP.BatchPage();
        batchP.enterBatchCode("CMCROPSP");
        ScreenShotUtils.captureScreenshotToWord("Medium Fixed-Renewal.docx","Batch completed");
        DBUtils.executeSelectQuery(query);
        batchP.BatchPage();
        batchP.enterBatchCode("CMRPGEN");
        ScreenShotUtils.captureScreenshotToWord("Medium Fixed-Renewal.docx","Batch completed");
        DBUtils.executeSelectQuery(query);
    }
    @Test(dependsOnMethods = "BatchRun" )
    public void CCTermSet()  {
        PersonPage perpage = new PersonPage();
        perpage.SiteVerify();
        OppForPerson oppPer = new OppForPerson();
        oppPer.CreditCheck();
        ScreenShotUtils.captureScreenshotToWord("Medium Fixed-Renewal.docx","Credit Check Processed Succesfully");
        oppPer.TermSet();
        ScreenShotUtils.captureScreenshotToWord("Medium Fixed-Renewal.docx","Term Set added");
    }
    @Test(dependsOnMethods = "CCTermSet" )
    public void QuoteGeneration() throws IOException {
        OppForPerson oppPer = new OppForPerson();
        oppPer.QualifyingOpportunity();
        ScreenShotUtils.captureScreenshotToWord("Medium Fixed-Renewal.docx","Opportunity Qualified");
        oppPer.qualifiedQuoteInProgress();
        ScreenShotUtils.captureScreenshotToWord("Medium Fixed-Renewal.docx","Quote generated");
        String Caseid = oppPer.quote();
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        ExcelUtils.setCellData(4,7, Caseid);
    }
}
