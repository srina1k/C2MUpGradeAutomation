package org.example.Tests;


import org.example.Base.BaseClass;
import org.example.Listener.RetryAnalyzer;
import org.example.Pages.*;
import org.example.Utils.DriverManager;
import org.example.Utils.ExcelUtils;
import org.example.Utils.ScreenShotUtils;
import org.example.Utils.WaitUtils;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;


public class COMC16P1 extends BaseClass {

    @Test
    public void testLogin(){
        String fileName  = "COMC16P1.docx";
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage lp = new LoginPage(DriverManager.getDriver());
        lp.Logincredentials();
    }
    @Test(dependsOnMethods = "testLogin")
    public void opportunityCreation() throws IOException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String personID = ExcelUtils.getCellData(1,2);
        UserPage userpage = new UserPage();
        userpage.NavigateToOpportunity(personID);
        ScreenShotUtils.captureScreenshotToWord("COMC16P1.docx","Step1:Entering Person ID");

        userpage.clickSearch();
        WaitUtils.sleep(3000);
        ScreenShotUtils.captureScreenshotToWord("COMC16P1.docx","Step2:Person Details");

        PersonPage perpage = new PersonPage();
        perpage.AddOpportunityDetails("COMC16");
        perpage.opportunityType("Change of Measurement Class");
        ScreenShotUtils.captureScreenshotToWord("COMC16P1.docx","Step3:Entering Opportunity details");
        perpage.startEndDate();
        ScreenShotUtils.captureScreenshotToWord( "COMC16P1.docx","Step4:Enter Start and end date");
        perpage.nonFlexProductname("PEACE_OF_MIND");
        perpage.addressIndicator();
        String storeOppID = perpage.captureOppID();
        perpage.addOppIDToWord( "COMC16P1.docx", "Step5:Opportunity created in Identified status");
        ExcelUtils.setCellData(3,1, storeOppID);
    }
    @Test(dependsOnMethods = "opportunityCreation", retryAnalyzer = RetryAnalyzer.class)
    public void premiseCreation() {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String premiseID = ExcelUtils.getCellData(2,2);
        AddPremisePage premise = new AddPremisePage();
        premise.NavigateToPremise(premiseID);
        ScreenShotUtils.captureScreenshotToWord( "COMC16P1.docx","Step6:Search Premise ID to add site");
        premise.clickSearch();
        premise.servicePointSelect();
        ScreenShotUtils.captureScreenshotToWord("COMC16P1.docx","Select MPAN");
        premise.newMeasurementClassCOMC16();
        premise.newProfileClassCOMC16();
        premise.NewMTCCOMC16();
        premise.LLFCCOMC16();
        premise.SSCCOMC16();
        premise.retrevalAMRRequest();
        ScreenShotUtils.captureScreenshotToWord("COMC16P1.docx","Adding Site details");
        premise.DaDcMopCOMC16();
        premise.clickSave();
        ScreenShotUtils.captureScreenshotToWord( "COMC16P1.docx","Site added succesfully.");
    }
    @Test(dependsOnMethods = "premiseCreation" )
    public void CCTermSet() throws InterruptedException{
        OppForPerson oppPer = new OppForPerson();
        oppPer.CreditCheck();
        ScreenShotUtils.captureScreenshotToWord("COMC16P1.docx","Credit Check Processed Succesfully");
        oppPer.TermSet();
        oppPer.QualifyingOpportunity();
        ScreenShotUtils.captureScreenshotToWord( "COMC16P1.docx","Opportunity Qualified");

    }
    @Test(dependsOnMethods = "CCTermSet")
    public void BatchRun() throws InterruptedException {
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("CM-ECCHK");
        //batchP.clickRefresh();
    }

    @Test(dependsOnMethods = "CCTermSet")
    public void QuoteGeneration() throws IOException {
        OppForPerson oppPer = new OppForPerson();
        //oppPer.holdingPenOverride();
        oppPer.qualifiedQuoteInProgress();
        ScreenShotUtils.captureScreenshotToWord( "COMC16P1","Quote generated");

        String Caseid = oppPer.quote();
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        ExcelUtils.setCellData(4,2, Caseid);
    }
}
