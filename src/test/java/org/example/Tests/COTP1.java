package org.example.Tests;

import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.DriverManager;
import org.example.Utils.ExcelUtils;
import org.example.Utils.ScreenShotUtils;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class COTP1 extends BaseClass {
    @Test
    public void testLogin(){

        String fileName = "COTP1.docx";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage LoginPage = new LoginPage(DriverManager.getDriver());
        LoginPage.Logincredentials();
    }
    @Test(dependsOnMethods = "testLogin")
    public void opportunityCreation() throws IOException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String personID1 = ExcelUtils.getCellData(7,9);
        UserPage userpage = new UserPage();
        userpage.NavigateToOpportunity(personID1);
        //userpage.personSearch(personID1);
        ScreenShotUtils.captureScreenshotToWord( "COTP1.docx","Entering Person ID");
        userpage.clickSearch();
        //userpage.personTab("Key Business Contact");
        PersonPage perpage = new PersonPage();
        perpage.AddOpportunityDetails("COT");
        perpage.opportunityType("Change of Tenancy");
        ScreenShotUtils.captureScreenshotToWord("COTP1.docx","Entering Opportunity details");
        perpage.startEndDate();
        ScreenShotUtils.captureScreenshotToWord( "COTP1.docx","Enter Start and end date");
        perpage.FlexProductname("FLEX");
        perpage.addressIndicator();
        String storeOppID = perpage.captureOppID();
        perpage.addOppIDToWord( "COTP1.docx", "Opportunity created in Identified status");
        ExcelUtils.setCellData(8,9, storeOppID);
    }
    @Test (dependsOnMethods = "opportunityCreation")
    public void premiseCreation() throws InterruptedException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
        String premiseID = ExcelUtils.getCellData(9, 9);
        AddPremisePage premise = new AddPremisePage();
        premise.NavigateToPremise(premiseID);
        ScreenShotUtils.captureScreenshotToWord("COTP1.docx", "Search Premise ID to add site");
        premise.clickSearch();
        premise.servicePointSelect();
        ScreenShotUtils.captureScreenshotToWord("COTP1.docx","Select MPAN");
        premise.clickSave();
        ScreenShotUtils.captureScreenshotToWord("COTP1.docx","Site added succesfully.");
    }
    @Test(dependsOnMethods = "premiseCreation" )
    public void CCTermSet() {
        OppForPerson oppPer = new OppForPerson();
        oppPer.CreditCheck();
        ScreenShotUtils.captureScreenshotToWord("COTP1.docx","Credit Check Processed Succesfully");
        oppPer.TermSet();
        oppPer.QualifyingOpportunity();
        ScreenShotUtils.captureScreenshotToWord("COTP1.docx","Opportunity Qualified");
    }
    @Test(dependsOnMethods = "CCTermSet")
    public void BatchRun() throws InterruptedException, SQLException {
        //ExcelUtils.loadExcel("D:\\Users\\sunag1a\\IdeaProjects\\AM_Tasks\\src\\main\\resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        //String oppId = ExcelUtils.getCellData(6,1);
        //String query = String.format(DBQueries.IsolateOpportunity,oppId);
        //DBUtils.UpdateQuery(query);
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("CM-ECCHK");
    }
    @Test(dependsOnMethods = "BatchRun")
    public void QuoteGeneration() throws IOException {
        OppForPerson oppPer = new OppForPerson();
        //oppPer.holdingPenOverride();
        oppPer.qualifiedQuoteInProgress();
        ScreenShotUtils.captureScreenshotToWord( "COTP1","Quote generated");
        String Caseid = oppPer.quote();
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        ExcelUtils.setCellData(10,9, Caseid);
    }
}
