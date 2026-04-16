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


public class COMC15P1 extends BaseClass {

    @Test
    public void testLogin(){

        String fileName = "COMC15P1.docx";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage lp = new LoginPage(DriverManager.getDriver());
        lp.Logincredentials();
    }

    @Test(dependsOnMethods = "testLogin")
    public void opportunityCreation() throws IOException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String personID1 = ExcelUtils.getCellData(1,1);

        UserPage userpage = new UserPage();
        userpage.NavigateToOpportunity(personID1);
        ScreenShotUtils.captureScreenshotToWord( "COMC15P1.docx","Step1:Entering Person ID");
        userpage.clickSearch();
        ScreenShotUtils.captureScreenshotToWord("COMC15P1.docx","Step2:Person Details");

        PersonPage perpage = new PersonPage();
        perpage.AddOpportunityDetails("COMC15");
        perpage.opportunityType("Change of Measurement Class");
        ScreenShotUtils.captureScreenshotToWord("COMC15P1.docx","Step3:Entering Opportunity details");
        perpage.startEndDate();
        ScreenShotUtils.captureScreenshotToWord( "COMC15P1.docx","Step4:Enter Start and end date");
        perpage.nonFlexProductname("PEACE_OF_MIND");
        perpage.addressIndicator();
        String storeOppID = perpage.captureOppID();
        perpage.addOppIDToWord( "COMC15P1.docx", "Step5:Opportunity created in Identified status");

        ExcelUtils.setCellData(3,1, storeOppID);
    }

    @Test (dependsOnMethods = "opportunityCreation")
    public void premiseCreation() {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String premiseID = ExcelUtils.getCellData(2,1);

        AddPremisePage premise = new AddPremisePage();
        premise.NavigateToPremise(premiseID);
        ScreenShotUtils.captureScreenshotToWord("COMC15P1.docx","Step6:Search Premise ID to add site");
        premise.clickSearch();
        premise.servicePointSelect();
        ScreenShotUtils.captureScreenshotToWord("COMC15P1.docx","Select MPAN");
        premise.newMeasurementClassCOMC15();
        premise.newProfileClassCOMC15();
        premise.NewMTCCOMC15();
        premise.LLFCCOMC15();
        premise.SSCCOMC15();
        premise.retrevalAMRRequest();
        ScreenShotUtils.captureScreenshotToWord("COMC15P1.docx","Adding Site details");
        premise.DaDcMopCOMC15();
        premise.clickSave();
        ScreenShotUtils.captureScreenshotToWord("COMC15P1.docx","Site added succesfully.");
    }

    @Test(dependsOnMethods = "premiseCreation" )
    public void CCTermSet() {
        OppForPerson oppPer = new OppForPerson();
        oppPer.CreditCheck();
        ScreenShotUtils.captureScreenshotToWord("COMC15P1.docx","Credit Check Processed Succesfully");
        oppPer.TermSet();
        oppPer.QualifyingOpportunity();
        ScreenShotUtils.captureScreenshotToWord("COMC15P1.docx","Opportunity Qualified");

        /*BatchSync.readyCount.incrementAndGet();
        System.out.println("opp marked ready for batch");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(5));
        wait.until(d -> BatchSync.batchExecuted);*/
    }

    @Test(dependsOnMethods = "CCTermSet")
    public void BatchRun() throws SQLException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        //String oppId = ExcelUtils.getCellData(6,1);
        //String query = String.format(DBQueries.IsolateOpportunity,oppId);
        //DBUtils.UpdateQuery(query);
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("CM-ECCHK");
        //batchP.clickRefresh();

        //String query1 = String.format(DBQueries.DeisolateOpportunity,oppId);
        //DBUtils.UpdateQuery(query1);
    }

    @Test(dependsOnMethods = "CCTermSet")
    public void QuoteGeneration() throws IOException {
        OppForPerson oppPer = new OppForPerson();
        oppPer.qualifiedQuoteInProgress();
        ScreenShotUtils.captureScreenshotToWord( "COMC15P1","Quote generated");

        String Caseid = oppPer.quote();
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        ExcelUtils.setCellData(4,1, Caseid);
        ExcelUtils.saveAndClose();
    }


}
