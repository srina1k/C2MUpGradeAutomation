package org.example.Tests;
import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.*;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class liveBillingP1 extends BaseClass {

    @Test
    public void testLogin(){

        String fileName = "Live-Billing-P1.docx";
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
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
        String personID1 = ExcelUtils.getCellData(13, 4);
        String childPerID = ExcelUtils.getCellData(14, 4);

        UserPage userpage = new UserPage();
        userpage.NavigateToOpportunity(personID1);
        ScreenShotUtils.captureScreenshotToWord("Live-Billing-P1.docx", "Step1:Entering Person ID");
        userpage.clickSearch();
        ScreenShotUtils.captureScreenshotToWord("Live-Billing-P1.docx", "Step2:Person Details");

        PersonPage perpage = new PersonPage();
        //perpage.personTab(childPerID, "Key Business Contact");
        ScreenShotUtils.captureScreenshotToWord("Live-Billing-P1.docx","Adding Child Person as Key business contract");

        perpage.AddOpportunityDetails("Live_Billing_p1_RT");
        perpage.opportunityType("Cold Prospect");
        ScreenShotUtils.captureScreenshotToWord("Live-Billing-P1.docx","Step3:Entering Opportunity details");
        perpage.startEndDate();
        ScreenShotUtils.captureScreenshotToWord( "Live-Billing-P1.docx","Step4:Enter Start and end date");
        perpage.FlexProductname("FLEX_BASKET");
        perpage.addressIndicator();
        String storeOppID = perpage.captureOppID();
        perpage.addOppIDToWord( "Live-Billing-P1.docx", "Step5:Opportunity created in Identified status");
        ExcelUtils.setCellData(15,4, storeOppID);
        //ExcelUtils.saveAndClose();
    }

    @Test (dependsOnMethods = "opportunityCreation")
    public void premiseCreation() {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String NhhMpan = ExcelUtils.getCellData(16,4);   //need to change it
        String hhMpan = ExcelUtils.getCellData(17,4);
        AddPremisePage premise = new AddPremisePage();
        premise.customerHyperlink();
        premise.addSiteMpan(NhhMpan);
        ScreenShotUtils.captureScreenshotToWord("Live-Billing-P1.docx","Add site using NhhMPAN details");
        premise.siteDetailsForLiveBilling();
        premise.servicePointSelectLiveBilling();
        ScreenShotUtils.captureScreenshotToWord("Live-Billing-P1.docx","Details are added");
        premise.clickSave();
        premise.addSiteMpan(hhMpan);
        ScreenShotUtils.captureScreenshotToWord("Live-Billing-P1.docx","Add site using HHMPAN details");
        premise.siteDetailsForLiveBilling();
        premise.servicePointSelectLiveBilling();
        ScreenShotUtils.captureScreenshotToWord("Live-Billing-P1.docx","Details are added");
        premise.clickSave();
    }

    @Test(dependsOnMethods = "premiseCreation" )
    public void CCTermSet() {
        OppForPerson oppPer = new OppForPerson();
        oppPer.CreditCheck();
        ScreenShotUtils.captureScreenshotToWord("Live-Billing-P1.docx","Credit Check Processed Succesfully");
        oppPer.TermSet();
        oppPer.QualifyingOpportunity();
        ScreenShotUtils.captureScreenshotToWord("Live-Billing-P1.docx","Opportunity Qualified");
    }

    @Test(dependsOnMethods = "CCTermSet")
    public void BatchRun() throws SQLException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String oppId = ExcelUtils.getCellData(15,4);
        String query = String.format(DBQueries.IsolateOpportunity,oppId);
        System.out.println(query);
        DBUtils.UpdateQuery(query);
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("CM-ECCHK");
        ScreenShotUtils.captureScreenshotToWord("Live-Billing-P1.docx","Running ECCHK for ECOES validation");
        //batchP.clickRefresh();
        OppForPerson oppPer=new OppForPerson();
        oppPer.echoeStatuscheck4LiveBilling();
        ScreenShotUtils.captureScreenshotToWord("Live-Billing-P1.docx","Validating the batches for Ecoes Validation");
        oppPer.goBack();
        batchP.BatchPage();
        batchP.enterBatchCode("CMRCECOE ");
//        String query1 = String.format(DBQueries.DeisolateOpportunity,oppId);
//        DBUtils.UpdateQuery(query1);
//        System.out.println(query1);
    }
//
    @Test(dependsOnMethods = "BatchRun")
    public void QuoteGeneration() throws IOException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        OppForPerson oppPer = new OppForPerson();
        oppPer.qualifiedQuoteInProgress();
        ScreenShotUtils.captureScreenshotToWord( "Live-Billing-P1","Quote generated");

        String Caseid = oppPer.quote();
        System.out.println("QUote ID generated: " + Caseid);
    }
}
