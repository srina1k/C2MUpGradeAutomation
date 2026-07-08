package org.example.Tests;

import org.apache.commons.math3.analysis.function.Add;
import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class OpportunityToMarketMessageCreationGas extends BaseClass {
    String storeOppId;
    String personId;
    String quote;
    String quoteReqFileName;
    String archiveFileName;
    String quoteId;
    String contractID;
    @Test
    public void testLogin() {

//        String fileName = "OpportunityToMarketMessageGas.docx";
//        File file = new File(fileName);
//        if (file.exists()) {
//            file.delete();
//            System.out.println("Old word file deleted: " + fileName);
//        }
        LoginPage lp = new LoginPage(DriverManager.getDriver());
        lp.Logincredentials();
    }

    @Test(dependsOnMethods = "testLogin")
    public void OpportunityCreation() throws IOException {
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx", "Sheet1");
        personId = ExcelUtils.getCellData(20, 4);
        System.out.println(personId);
        UserPage userPage = new UserPage();
        userPage.NavigateToOpportunity(personId);
        ScreenShotUtils.captureScreenshotToWord("OpportunityToMarketMessageGas.docx", "Entering PersonId");
        userPage.clickSearch();
        ScreenShotUtils.captureScreenshotToWord("OpportunityToMarketMessageGas.docx", "Person Details");
        PersonPage perPage = new PersonPage();
        perPage.AddOpportunityDetailsGas("OpportunityToMarketMessageGas");
        ScreenShotUtils.captureScreenshotToWord("OpportunityToMarketMessageGas.docx", "Opportunity Details Added");
        perPage.startEndDate();
        ScreenShotUtils.captureScreenshotToWord("OpportunityToMarketMessageGas.docx", "Dates Added");
        perPage.nonFlexProductname("STDNDRD_FXD_GS");
        ScreenShotUtils.captureScreenshotToWord("OpportunityToMarketMessageGas.docx", "Product Selected");
        perPage.addressIndicator();
        storeOppId = perPage.captureOppID();
        ScreenShotUtils.captureScreenshotToWord("OpportunityToMarketMessageGas.docx", "Opportunity Created");
        ExcelUtils.setCellData(21, 4, storeOppId);
    }

    @Test(dependsOnMethods = "OpportunityCreation")
    public void InitiateCreditCheck() {
        AddPremisePage premisePage = new AddPremisePage();
        premisePage.customerHyperlink();
        ScreenShotUtils.captureScreenshotToWord("OpportunityToMarketMessageGas.docx", "Navigate to Customer");
        OppForPerson oppForPerson = new OppForPerson();
        oppForPerson.CreditCheck();
        ScreenShotUtils.captureScreenshotToWord("OpportunityToMarketMessageGas.docx", "Credit Check done");
        oppForPerson.TermSet();
        ScreenShotUtils.captureScreenshotToWord("OpportunityToMarketMessageGas.docx", "Term Set added");

    }

    @Test(dependsOnMethods = "InitiateCreditCheck")
    public void siteAddition() throws SQLException {
//        String mprn1=ExcelUtils.getCellData(22,4);
//        String mprn2=ExcelUtils.getCellData(23,4);
//        String mprn3=ExcelUtils.getCellData(24,4);
//        String mprn4=ExcelUtils.getCellData(25,4);
        AddPremisePage premisePage = new AddPremisePage();
        premisePage.customerHyperlink();
        for (int i = 0; i < 4; i++) {
            String mprn = ExcelUtils.getCellData(22 + i, 4);
            if (mprn != null && !mprn.trim().isEmpty()) {
                WaitUtils.getWait(driver,20);
                premisePage.addSiteMpan(mprn, "MPRN Identifier");
                premisePage.siteDetailsForLiveBilling();
                premisePage.gaspremise();
                premisePage.clickSave();
            }
        }
        OppForPerson oppPer = new OppForPerson();
        oppPer.goBack();
        oppPer.QualifyingOpportunity();
        String IsolateOpportunity = String.format(DBQueries.IsolateOpportunity, storeOppId);
        DBUtils.UpdateQuery(IsolateOpportunity);
    }

    @Test(dependsOnMethods = "siteAddition")
    public void validateSites() throws SQLException, IOException {
        BatchJobSubmissionPage batchPage = new BatchJobSubmissionPage();
        batchPage.BatchPage();
        batchPage.enterBatchCode("CM-XOSRV");
        String DeIsolateOpportunity = String.format(DBQueries.DeisolateOpportunity, storeOppId);
        DBUtils.UpdateQuery(DeIsolateOpportunity);
        AddPremisePage premisePage = new AddPremisePage();
        OppForPerson oppPer = new OppForPerson();
        oppPer.echoeStatuscheck4LiveBilling();
        premisePage.customerHyperlink();
        batchPage.BatchPage();
        batchPage.enterBatchCode("CMRCECOE");
//        premisePage.customerHyperlink();
        oppPer.goBack();
        oppPer.holdingPenOverride();
        oppPer.qualifiedQuoteInProgress();
        String quote=oppPer.quote();
        System.out.println("Quote="+quote);
    }
    @Test(dependsOnMethods = "validateSites")
    public void RequalifyingOpportunity() throws SQLException{
//        String mprn3=ExcelUtils.getCellData(24,4);
//        String mprn4=ExcelUtils.getCellData(25,4);
//        String ServicePoint3=String.format(DBQueries.GasServicePoint,mprn3);
//        String sp3= DBUtils.getSingleDate(ServicePoint3,"SP_ID");
//        String sp3fyofqty=String.format(DBQueries.InsertQTY,sp3,"CM-FYOFQ","50");
//        DBUtils.UpdateQuery(sp3fyofqty);
//        String ServicePoint4=String.format(DBQueries.GasServicePoint,mprn4);
//        String sp4=DBUtils.getSingleDate(ServicePoint4,"SP_ID");
//        String sp4fyofq=String.format(DBQueries.InsertQTY,sp4,"CM-FYOFQ","800");
//        DBUtils.UpdateQuery(sp4fyofq);
//        String sp4Anuqty=String.format(DBQueries.InsertQTY,sp4,"CMANUQTY",96500);
//        DBUtils.UpdateQuery(sp4Anuqty);
//        String sp4class=String.format(DBQueries.InsertClass,sp4,"CM-CLASS","3");
//        DBUtils.UpdateQuery(sp4class);
//        CasePage casePage=new CasePage();
//        casePage.RemovedfromProcess();
//        OppForPerson oppPer=new OppForPerson();
//        oppPer.goBack();
//        oppPer.clickqualified();
//        oppPer.QualifyingOpportunity();
//        quote=oppPer.quote1();
//        System.out.println(quote);
    }
    @Test(dependsOnMethods = "RequalifyingOpportunity")
    public void verifyFile(){
    String filePath="/gas/hobs/quoteReq/out/";
    quoteReqFileName=WinScpServerUtils.fetchFileName(filePath,quote);
    System.out.println("FileName: "+quoteReqFileName);
    Assert.assertNotNull(quoteReqFileName,"No File Found/Generated On Server");
    String Localpath="C:\\AutomationDocuments\\OpportunityToMarketMessageCreationGas";
    WinScpServerUtils.downloadFile(filePath,quoteReqFileName,Localpath);
    }
    @Test(dependsOnMethods = "verifyFile")
    public void xmlfileUpload(){
        String uploadPath="/gas/hobs/quoteAcc/in/";
        String localPath = Paths.get("C:\\AutomationDocuments\\OpportunityToMarketMessageCreationGas", quoteReqFileName).toString();
        System.out.println(localPath);
        String uploadFilePath="C://AutomationDocuments//OpportunityToMarketMessageCreationGas//4228472141.xml";
        XmlUtil.copyTagValues(localPath,uploadFilePath,"id","quoteRequestId");
        XmlUtil.updateTagValue(uploadFilePath,"ccbPersonId",personId);
        //XmlUtil.copyTagValues(localPath,uploadFilePath,"ccbPersonId","ccbPersonId");
        Random random=new Random();
        int number=random.nextInt(10)+1;
        quoteId=number+"RTAUGGAS";
        System.out.println(quoteId);
        XmlUtil.updateTagValue(uploadFilePath,"quoteId",quoteId);
        XmlUtil.copyTagValues(localPath,uploadFilePath,"startDate","startDate");
        XmlUtil.copyTagValues(localPath,uploadFilePath,"endDate","endDate");
        XmlUtil.copyTagValues(localPath,uploadFilePath,"ccbPremiseId","ccbPremiseId");
        XmlUtil.copyTagValues(localPath,uploadFilePath,"mpanId","mpanId");
        XmlUtil.copyTagValues(localPath,uploadFilePath,"mprnId","mprnId");
        String RenamedFile=FileRenameUtils.renameFile(uploadFilePath,quote+".xml");
        System.out.println(RenamedFile);
        WinScpServerUtils.uploadFile(RenamedFile,uploadPath);
    }
    @Test(dependsOnMethods = "xmlfileUpload")
    public void generateSyncRequests(){
        BatchJobSubmissionPage batchPage=new BatchJobSubmissionPage();
        batchPage.BatchPage();
        batchPage.enterBatchCode("CM-QRGAS");
        String archiveFilePath="/gas/hobs/quoteAcc/archive/";
        archiveFileName=WinScpServerUtils.fetchFileName(archiveFilePath,quote);
        System.out.println("ArchivedFileName:"+archiveFileName);
        Assert.assertNotNull(archiveFileName,"No File Found/Generated on server");
    }
    @Test(dependsOnMethods = "generateSyncRequests")
    public void generateContract() throws SQLException{
        String query = String.format(DBQueries.syncReq, storeOppId);
        String syncRequestID = DBUtils.getSingleDate(query, "F1_SYNC_REQ_ID");
        System.out.println("F1_SYNC_REQ_ID: " + syncRequestID);
        syncRequestPage syncReq = new syncRequestPage();
        syncReq.NavigateTosyncRequestQuery();
        syncReq.dropdownSyncRequestID(syncRequestID);
        syncReq.validation();
        String quoteQuery1 = String.format(DBQueries.IsolateQuote, storeOppId);
        DBUtils.UpdateQuery(quoteQuery1);
        BatchJobSubmissionPage batchPage=new BatchJobSubmissionPage();
        batchPage.BatchPage();
        batchPage.enterBatchCode("CMQRSYN2");
        String quoteQuery2 = String.format(DBQueries.DeIsolateQuote, storeOppId);
        DBUtils.UpdateQuery(quoteQuery2);
//        CasePage casePage=new CasePage();
//        casePage.navigateToCase();   ---Need to Update regarding the case page
        batchPage.BatchPage();
        batchPage.CMMONOPBatch("CM-MONOP");
        batchPage.clickRefresh();
        String OpportunityStatus=String.format(DBQueries.OppCreation,storeOppId);
        String status=DBUtils.getSingleDate(OpportunityStatus,"BO_STATUS_CD");
        System.out.println("Opportunity Status="+status);
        Assert.assertEquals(status,"WON-PA");
    }
    @Test(dependsOnMethods = "generateContract")
    public void contractValidation() throws SQLException{
        String contractQuery = String.format(DBQueries.fetchContract, storeOppId);
        contractID = DBUtils.getSingleDate(contractQuery, "CM_CONTRACT_ID");
        System.out.println("Contract_ID: " + contractID);
        contractSearch contract = new contractSearch();
        contract.navigateToContract(contractID);
        contract.ContractValidation();
        String IsolateContract=String.format(DBQueries.IsolateContract,contractID);
        DBUtils.UpdateQuery(IsolateContract);
        BatchJobSubmissionPage batchPage=new BatchJobSubmissionPage();
        batchPage.BatchPage();
        batchPage.enterBatchCode("CM-MCONI");
        contract.clickrefresh();
        contract.contractStatus();
    }

    //Now you need to verify three accounts are created from the database
    // select * from ci_acct where acct_id in (select acct_id from ci_acct_per where per_id in ('2160000000')) order by SETUP_DT desc;
    // check the market message created by the script from database it should have three market message id's
//    SELECT * FROM F1_MKTMSG_OUT WHERE MKTMSG_ID IN (
//            SELECT MKTMSG_ID FROM F1_MKTMSG_OUT_REL_OBJ WHERE PK_VALUE1 in (provide_contract_id)) order by CRE_DTTM desc;

}
