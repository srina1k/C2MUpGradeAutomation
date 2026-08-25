package org.example.Tests;
import org.example.Listener.RetryAnalyzer;
import org.example.Utils.*;
import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
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
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
        String personID1 = ExcelUtils.getCellData(1,5);
        UserPage userpage = new UserPage();
        userpage.NavigateToOpportunity(personID1);
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step1:Entering Person ID");
        userpage.clickSearch();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step2:Person Details");
        PersonPage perpage = new PersonPage();
        perpage.AddOpportunityDetails("New Connection","Burnett, Richard","Medium","Eighty");
        perpage.opportunityType("New Connection");
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step3:Entering Opportunity details");
        perpage.startEndDate();
        ScreenShotUtils.captureScreenshotToWord("New_Conne ction.docx","Step4:Enter Start and end date");
        perpage.nonFlexProductname("DEEMED");
        perpage.addressIndicator();
        String storeOppID = perpage.captureOppID();
        perpage.addOppIDToWord("New_Connection.docx", "Step5:Opportunity created in Identified status");
        ExcelUtils.setCellData(3,5, storeOppID);
    }
    @Test (dependsOnMethods = "opportunityCreation1")
    public void premiseCreation1() throws InterruptedException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
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
//    @Test(dependsOnMethods = "CCTermSet1")
//    public void OpportunityCreation2() throws InterruptedException, IOException {
//        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
//        String personID2 = ExcelUtils.getCellData(1,6);
//        UserPage userpage = new UserPage();
//        userpage.NavigateToOpportunity(personID2);
//        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step1:Entering Person ID");
//        userpage.clickSearch();
//        WaitUtils.sleep(3000);
//        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step2:Person Details");
//        PersonPage perpage = new PersonPage();
//        perpage.AddOpportunityDetails("New Connection","Burnett, Richard","Medium","Eighty");
//        perpage.opportunityType("New Connection");
//        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step3:Entering Opportunity details");
//        perpage.startEndDate();
//        ScreenShotUtils.captureScreenshotToWord( "New_Connection.docx","Step4:Enter Start and end date");
//        perpage.nonFlexProductname("DEEMED");
//        perpage.addressIndicator();
//        String storeOppID = perpage.captureOppID();
//        perpage.addOppIDToWord( "New_Connection.docx", "Step5:Opportunity created in Identified status");
//        ExcelUtils.setCellData(3,6, storeOppID);
//    }
//
//    @Test (dependsOnMethods = "OpportunityCreation2")
//    public void premiseCreation2() throws InterruptedException {
//        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
//        String premiseID = ExcelUtils.getCellData(2, 6);
//        AddPremisePage premise = new AddPremisePage();
//        premise.NavigateToPremise(premiseID);
//        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Step6:Search Premise ID to add site");
//        premise.clickSearch();
//        premise.servicePointSelect();
//        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Select MPAN");
//        premise.DataColNewConnection2();
//        premise.DataAggNewConnection2();
//        premise.MopNewConnection2();
//        premise.addressPostal();
//        premise.clickSave();
//        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Site added succesfully.");
//    }
//    @Test(dependsOnMethods = "premiseCreation2")
//    public void CCTermSet2() throws InterruptedException{
//        OppForPerson oppPer = new OppForPerson();
//        oppPer.CreditCheck();
//        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Credit Check Processed Succesfully");
//        oppPer.TermSet();
//        oppPer.QualifyingOpportunity();
//        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Opportunity Qualified");
//    }
    @Test(dependsOnMethods="CCTermSet1")
    public void BatchRun() throws Exception {
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("CM-ECCHK");
    }
    @Test(dependsOnMethods = "BatchRun")
    public void won(){
//        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
//        String oppID1= ExcelUtils.getCellData(3,5);
//        System.out.println(oppID1);
        OppForPerson oppPer = new OppForPerson();
        oppPer.DeemedWon();
        //oppPer.wonclick();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Opportunity Moved to Won Status");
//        oppPer.NavigateToOpportunity(oppID1);
//        oppPer.DeemedWon();
//        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Opportunity1 Moved to Won Status");
    }
    //Only one opportunity check registration market message and check the account change the test data.
    @Test(dependsOnMethods = "won")
    public void MarketMessageCheck() throws Exception {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
        String oppID1= ExcelUtils.getCellData(3,5);
        System.out.println(oppID1);
        String query = String.format(DBQueries.MarketMessageIdFromOpportunity,oppID1);
        String marketMessageId = DBUtils.getSingleDate(query, "MESSAGE_PARM");
        System.out.println("Market Message ID: " + marketMessageId);
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Market Message ID for the Opportunity is: "+marketMessageId);
        marketMessageSearch mktMsg=new marketMessageSearch();
        mktMsg.navigateToMarketMessage();
        mktMsg.OdropdownoMarketMessageId(marketMessageId);
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Market Message Details");
        mktMsg.NewConnectionIndicator();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","New Connection Indicator is set to Yes");
        String date= WaitUtils.sysdate1();
        String Uidate=mktMsg.settlementDate();
        ScreenShotUtils.captureScreenshotToWord("New_Connection.docx","Effective from settlement Date: " + date);
        Assert.assertEquals(date,Uidate,"Settlement date is not matching with expected date");
    }


}
