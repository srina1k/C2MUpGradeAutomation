package org.example.Tests;
import org.example.Listener.RetryAnalyzer;
import org.example.Utils.*;
import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class ConnectionNew extends BaseClass {
    @Test
     public void LoginTest(){

        String fileName = "New_Connection.docx";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage loginPage=new LoginPage(DriverManager.getDriver());
        loginPage.Logincredentials();
    }
    @Test(dependsOnMethods = "LoginTest")
    public void opportunityCreation1() throws InterruptedException, IOException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\OneDrive - EDF\\Documents\\GitHub\\AM-Automation-Framework\\src\\main\\resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
        String personID1 = ExcelUtils.getCellData(1,5);
        UserPage userpage = new UserPage();
        userpage.NavigateToOpportunity(personID1);
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step1:Entering Person ID");
        userpage.clickSearch();
        WaitUtils.sleep(3000);
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step2:Person Details");
        PersonPage perpage = new PersonPage();
        perpage.AddOpportunityDetails("New Connection");
        perpage.opportunityType("New Connection");
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step3:Entering Opportunity details");
        perpage.startEndDate();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step4:Enter Start and end date");
        perpage.nonFlexProductname("DEEMED");
        WaitUtils.sleep(2000);
        perpage.addressIndicator();
        String storeOppID = perpage.captureOppID();
        perpage.addOppIDToWord("New_Connection.docx", "Step5:Opportunity created in Identified status");
        ExcelUtils.setCellData(3,5, storeOppID);
    }
    @Test (dependsOnMethods = "opportunityCreation1")
    public void premiseCreation1() throws InterruptedException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\OneDrive - EDF\\Documents\\GitHub\\AM-Automation-Framework\\src\\main\\resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
        String premiseID = ExcelUtils.getCellData(2, 5);
        AddPremisePage premise = new AddPremisePage();
        premise.NavigateToPremise(premiseID);
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step6:Search Premise ID to add site");
        premise.clickSearch();
        premise.servicePointSelect();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Select MPAN");
        premise.DataColNewConnection1();
        premise.DataAggNewConnection1();
        premise.MopNewConnection1();
        premise.addressPostal();
        premise.clickSave();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Site added succesfully.");
    }
    @Test(dependsOnMethods = "premiseCreation1")
    public void CCTermSet1() throws InterruptedException{
        OppForPerson oppPer = new OppForPerson();
        oppPer.CreditCheck();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Credit Check Processed Succesfully");
        oppPer.TermSet();
        oppPer.QualifyingOpportunity();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Opportunity Qualified");
    }
    @Test(dependsOnMethods = "CCTermSet1")
    public void OpportunityCreation2() throws InterruptedException, IOException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\OneDrive - EDF\\Documents\\GitHub\\AM-Automation-Framework\\src\\main\\resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
        String personID2 = ExcelUtils.getCellData(1,6);
        UserPage userpage = new UserPage();
        userpage.NavigateToOpportunity(personID2);
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step1:Entering Person ID");
        userpage.clickSearch();
        WaitUtils.sleep(3000);
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step2:Person Details");
        PersonPage perpage = new PersonPage();
        perpage.AddOpportunityDetails("New Connection");
        perpage.opportunityType("New Connection");
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step3:Entering Opportunity details");
        perpage.startEndDate();
        ScreenShotUtils.captureScreenshotToWord( "New_Connection.docx","Step4:Enter Start and end date");
        perpage.nonFlexProductname("DEEMED");
        perpage.addressIndicator();
        String storeOppID = perpage.captureOppID();
        perpage.addOppIDToWord( "New_Connection.docx", "Step5:Opportunity created in Identified status");
        ExcelUtils.setCellData(3,6, storeOppID);
    }

    @Test (dependsOnMethods = "OpportunityCreation2")
    public void premiseCreation2() throws InterruptedException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\OneDrive - EDF\\Documents\\GitHub\\AM-Automation-Framework\\src\\main\\resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
        String premiseID = ExcelUtils.getCellData(2, 6);
        AddPremisePage premise = new AddPremisePage();
        premise.NavigateToPremise(premiseID);
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step6:Search Premise ID to add site");
        premise.clickSearch();
        premise.servicePointSelect();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Select MPAN");
        premise.DataColNewConnection2();
        premise.DataAggNewConnection2();
        premise.MopNewConnection2();
        premise.addressPostal();
        premise.clickSave();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Site added succesfully.");
    }
    @Test(dependsOnMethods = "premiseCreation2")
    public void CCTermSet2() throws InterruptedException{
        OppForPerson oppPer = new OppForPerson();
        oppPer.CreditCheck();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Credit Check Processed Succesfully");
        oppPer.TermSet();
        oppPer.QualifyingOpportunity();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Opportunity Qualified");
    }
    @Test(dependsOnMethods="CCTermSet2")
    public void BatchRun() {
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("CM-ECCHK");
    }
    @Test(dependsOnMethods = "BatchRun")
    public void won(){
        OppForPerson oppPer = new OppForPerson();
        oppPer.DeemedWon();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Opportunity Moved to Won Status");
    }
}
