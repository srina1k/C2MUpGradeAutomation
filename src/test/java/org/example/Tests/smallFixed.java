package org.example.Tests;


import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class smallFixed extends BaseClass {
    @Test
    public void Logintest(){
        LoginPage loginPage=new LoginPage(DriverManager.getDriver());
        loginPage.Logincredentials();
    }
    @Test(dependsOnMethods = "Logintest")
    public void uploadFile(){
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String CsvFilePath = ExcelUtils.getCellData(1,9);
        FileRenameUtils.replaceDate(CsvFilePath);
        String remotePath = "/ccbfsx/Common/prospect_upload/";
        WinScpServerUtils.uploadFile(CsvFilePath,remotePath);
        String csvFilename = FileRenameUtils.getFileNameFromPath(CsvFilePath);
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMSPSU2Batch("CM-SPSU2", csvFilename," ");
        ScreenShotUtils.captureScreenshotToWord("SmallFixed.docx","Batch completed");
    }
    @Test(dependsOnMethods = "uploadFile")
    public void oppSearch()  {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\OneDrive - EDF\\Documents\\GitHub\\AM-Automation-Framework\\src\\main\\resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        String CustName = ExcelUtils.getCellData(2,9);
        PersonPage perpage = new PersonPage();
        perpage.SearchOppByname(CustName);
        ScreenShotUtils.captureScreenshotToWord("SmallFixed.docx","Opportunity Created");

        AddPremisePage premise = new AddPremisePage();
        premise.siteDetails();
        ScreenShotUtils.captureScreenshotToWord("SmallFixed.docx","Ecoes Validation - Yes.");
    }

    @Test(dependsOnMethods = "oppSearch")
    public void CCTermSet() throws IOException {
        OppForPerson oppPer = new OppForPerson();
        oppPer.CreditCheck();
        ScreenShotUtils.captureScreenshotToWord("SmallFixed.docx","Credit Check Processed");
        oppPer.TermSet();
        ScreenShotUtils.captureScreenshotToWord("SmallFixed.docx","Term set added.");
        oppPer.QualifyingOpportunity();
        ScreenShotUtils.captureScreenshotToWord("SmallFixed.docx","Opportunity Qualified");
        oppPer.qualifiedQuoteInProgress();
        ScreenShotUtils.captureScreenshotToWord("SmallFixed.docx","Quote generated");

        String Caseid = oppPer.quote();
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\OneDrive - EDF\\Documents\\GitHub\\AM-Automation-Framework\\src\\main\\resources\\RTScenarioTestDataReport.xlsx","Sheet1");
        ExcelUtils.setCellData(4,9, Caseid);
    }
}
