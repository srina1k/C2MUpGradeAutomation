package org.example.Tests;

import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SuspectQueryCreationToRemoveQuote extends BaseClass {
    String personId;
    String OppId;
    String quote;
    String quoteReqfileName;

    @Test
    public void testLogin(){
        String fileName="SuspectQueryCreationToRemoveQuote.docx";
        File file=new File(fileName);
        if(file.exists()){
            file.delete();
            System.out.println("Old word File Deleted" + fileName);
        }
        LoginPage loginPage=new LoginPage(DriverManager.getDriver());
        loginPage.Logincredentials();
    }
    @Test(dependsOnMethods = "testLogin")
    public void personIdCreation(){
        suspectQueryPage sqPage=new suspectQueryPage();
        sqPage.navigateSuspectQueryPortal();
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Suspect Query Page");
        sqPage.suspectDetails();
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Suspect Details");
        personId=sqPage.personid();
        System.out.println("Person Id:"+personId);
        sqPage.Creditcheck();
        sqPage.salesDetails();
        sqPage.qualify();
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Person Qualified");
    }
    @Test(dependsOnMethods = "personIdCreation")
    public void OpportunityCreation(){
        UserPage userPage=new UserPage();
        userPage.NavigateToOpportunity(personId);
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","PersonId Created");
        userPage.clickSearch();
        PersonPage perpage=new PersonPage();
        perpage.AddOpportunityDetails("SuspectQueryToRemoveQuote","Mukherjee, Indranil","Medium","Seventy");
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Opportunity Details");
        perpage.opportunityType("Gas New Connection");
        perpage.startEndDate();
        perpage.nonFlexProductname("STDNDRD_FXD_GS");
        perpage.addressIndicator();
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Opportunity Details");
        OppId=perpage.captureOppID();
        System.out.println("Opportunity Id:"+OppId);
    }
    @Test(dependsOnMethods = "OpportunityCreation")
    public void CCTermSet(){
        AddPremisePage premisePage=new AddPremisePage();
        premisePage.customerHyperlink();
        OppForPerson oppPer=new OppForPerson();
        oppPer.CreditCheck();
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Credit Check Initiated");
        oppPer.TermSet();
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Term Set Added");
    }
    @Test(dependsOnMethods = "CCTermSet")
    public void PremiseCreation(){
        AddPremisePage premisePage=new AddPremisePage();
        premisePage.customerHyperlink();
        premisePage.addSiteMpan("9372141301","MPRN Identifier");
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Adding site1");
        premisePage.siteDetailsForLiveBilling();
        premisePage.gaspremise();
        premisePage.clickSave();
    }
    @Test(dependsOnMethods = "PremiseCreation")
    public void qualifyOpportunity() throws Exception {
        OppForPerson oppPer = new OppForPerson();
        oppPer.goBack();
        oppPer.QualifyingOpportunity();
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Qualifying the opportunity");
        String IsolateOpportunity = String.format(DBQueries.IsolateOpportunity, OppId);
        DBUtils.UpdateQuery(IsolateOpportunity);
        BatchJobSubmissionPage batchPage=new BatchJobSubmissionPage();
        batchPage.BatchPage();
        batchPage.enterBatchCode("CM-XOSRV");
        String deIsolateOpportunity=String.format(DBQueries.DeisolateOpportunity, OppId);
        DBUtils.UpdateQuery(deIsolateOpportunity);
        oppPer.qualifiedQuoteInProgress();
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Quote id Generation");
        quote=oppPer.quote();
        System.out.println("QuoteId:"+quote);
    }
    @Test(dependsOnMethods = "qualifyOpportunity")
    public void verifyFile(){
        String filePath="/gas/hobs/quoteReq/out/";
        quoteReqfileName= WinScpServerUtils.fetchFileName(filePath,quote);
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Verifying quote Acceptance Flow");
        System.out.println("Quote Request FileName"+quoteReqfileName);
        Assert.assertNotNull(quoteReqfileName,"No File found/Generated On Server");
    }
    @Test(dependsOnMethods = "verifyFile")
    public void AddSite2(){
        OppForPerson oppPer=new OppForPerson();
        oppPer.goBack();
        AddPremisePage premisePage=new AddPremisePage();
        premisePage.customerHyperlink();
        premisePage.addSiteMpan("9309558900","MPRN Identifier");
        premisePage.siteDetailsForLiveBilling();
        premisePage.gaspremise();
        premisePage.clickSave();
    }
    @Test(dependsOnMethods = "AddSite2")
    public void veriFyFile2(){
        String FilePath="/gas/hobs/quoteAmeAdd/out/";
        String quoteReqfileName1=WinScpServerUtils.fetchFileName(FilePath,quote);
        System.out.println("quoteAmeAdd FileName="+quoteReqfileName1);
        Assert.assertNotNull(quoteReqfileName1,"No File found/Generated On Server");
    }
    @Test(dependsOnMethods = "veriFyFile2")
    public void RemoveSite(){
        AddPremisePage premisePage=new AddPremisePage();
        premisePage.RemoveSite();
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Removing the site");
        String filePath="/gas/hobs/quoteAmeRem/out/";
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String quoteReqfileName2=WinScpServerUtils.fetchFileName(filePath,currentDate);
        System.out.println("Removed Site File Name="+quoteReqfileName2);
        Assert.assertNotNull(quoteReqfileName2,"No File found/Generated On Server");
    }
    @Test(dependsOnMethods = "RemoveSite")
    public void AmendContractManager(){
        OppForPerson oppPer=new OppForPerson();
        oppPer.goBack();
        oppPer.amendContractManager();
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Contract Manager Amendment");
        String FilePath="/gas/hobs/quoteAmeCM/out/";
        String quoteReqFileName3=WinScpServerUtils.fetchFileName(FilePath,quote);
        System.out.println("Manager Ammended file="+quoteReqFileName3);
        Assert.assertNotNull(quoteReqFileName3,"No File found/Generated on server");
        oppPer.quote();
    }
    @Test(dependsOnMethods = "AmendContractManager")
    public void cancelQuote(){
        CasePage casePage=new CasePage();
        casePage.RemovefromProcess();
        ScreenShotUtils.captureScreenshotToWord("SuspectQueryCreationToRemoveQuote.docx","Remove Quote from processing");
        String filePath="/gas/hobs/quoteCan/out";
        String quoteFileName4=WinScpServerUtils.fetchFileName(filePath,quote);
        System.out.println("Quote Cancelled File Name="+quoteFileName4);
        Assert.assertNotNull(quoteFileName4,"No File found/Generated on server");
    }


}
