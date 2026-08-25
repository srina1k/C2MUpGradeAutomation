package org.example.Tests;


import org.example.Base.BaseClass;
import org.example.Pages.BatchJobSubmissionPage;
import org.example.Pages.LoginPage;
import org.example.Pages.OppForPerson;
import org.example.Utils.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.io.File;
import java.sql.SQLException;

public class pdvProcess extends BaseClass {
    String caseCount;
    String pdvCaseCount;
    String wrtCaseCount;
    String remoteFilePath;
    String remoteFilePath1;
    String filePreFix;
    String filePreFix1;

    @Test
    public void testLogin() {

//        String fileName = "PDV Process.docx";
//        File file = new File(fileName);
//        if (file.exists()) {
//            file.delete();
//            System.out.println("Old word file deleted: " + fileName);
//        }
        LoginPage LoginPage = new LoginPage(DriverManager.getDriver());
        LoginPage.Logincredentials();
    }
    @Test(dependsOnMethods = "testLogin")
    public void datacheck() throws SQLException {
        String caseCountQuery = String.format(DBQueries.pdvCaseCount);
        caseCount= DBUtils.getSingleDate(caseCountQuery, "COUNT(*)");
        System.out.println("PDV Case Count: " + caseCount);
        String pdvCaseCountQuery = String.format(DBQueries.pdvlgcount);
        pdvCaseCount= DBUtils.getSingleDate(pdvCaseCountQuery, "RECORD_COUNT");
        System.out.println("PDV Log Count: " + pdvCaseCount);
        String wrtCaseCountQuery = String.format(DBQueries.wrtlgcount);
        wrtCaseCount= DBUtils.getSingleDate(wrtCaseCountQuery, "RECORD_COUNT");
        System.out.println("Warrant Log Count: " + wrtCaseCount);
    }
    @Test(dependsOnMethods = "datacheck")
    public void batchRun() throws Exception {

        String query = String.format(DBQueries.pdvCase);
        String caseID = DBUtils.getSingleDate(query, "CASE_ID");
        System.out.println("Case ID: " + caseID);

        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.batchCode("CM-CNLPD");
        batchP.Threads("70");
//        batchP.enterBatchCode("CM-CNLPD"); //--60threads
        batchP.clickRefeshwithDataValidation();
        ScreenShotUtils.captureScreenshotToWord("PDV Process.docx","Running PDV case cancellation batch ");
        String batchcaseCount=batchP.batchRunValue();
        System.out.println("Batch Case Count: " + batchcaseCount);
        Assert.assertEquals(batchcaseCount,caseCount,"Processed Records count is not matching with expected count");
        OppForPerson oppPer=new OppForPerson();
        oppPer.goBack();
        batchP.BatchPage();
        batchP.batchCode("CM-PDVLG");
        batchP.save();
        batchP.clickRefeshwithDataValidation();
        ScreenShotUtils.captureScreenshotToWord("PDV Process.docx","Running PDV log extract batch ");
        String batchcaseCount1=batchP.batchRunValue();
        System.out.println("Batch Case Count: " + batchcaseCount1);
        Assert.assertEquals(batchcaseCount1,pdvCaseCount,"Processed Records count is not matching with expected count");
        oppPer.goBack();
        remoteFilePath = "/ccbfsx/Download-RO/pdv_download_pdvlg/";
        filePreFix = "PDV_LOG_EXTRACT";
        String pdvlgFile = WinScpServerUtils.verifyFileGenerated(remoteFilePath, filePreFix);
        System.out.println("File generated: " + pdvlgFile);

        batchP.BatchPage();
        batchP.batchCode("CM-WRTLG");
        batchP.save();
        batchP.clickRefeshwithDataValidation();
        ScreenShotUtils.captureScreenshotToWord("PDV Process.docx","Running Warrant log batch ");
        String batchcaseCount2=batchP.batchRunValue();
        System.out.println("Batch Case Count: " + batchcaseCount2);
        Assert.assertEquals(batchcaseCount2,wrtCaseCount,"Processed Records count is not matching with expected count");
        remoteFilePath1 = "/ccbfsx/Download-RO/warrant_log/";
        filePreFix1 = "CM-WARRANT_LOG_EXTRACT";
        String wrtlgFile = WinScpServerUtils.verifyFileGenerated(remoteFilePath1, filePreFix1);
        System.out.println("File generated: " + wrtlgFile);
    }
    @Test(dependsOnMethods = "batchRun")
    public void ValidateDataInExcel(){
        String localFilePath="C:\\AutomationDocuments\\pdvProcess";
        String pdvlgfile=WinScpServerUtils.downloadFile(remoteFilePath,filePreFix,localFilePath);
        int pdvlgrowcount=ExcelUtils.getRowCount(pdvlgfile);
        System.out.println("PDVLGcount+="+pdvlgrowcount);
        String wrtlgfile=WinScpServerUtils.downloadFile(remoteFilePath1,filePreFix1,localFilePath);
        int wrtlgcount=ExcelUtils.getRowCount(wrtlgfile);
        System.out.println("wrtlgvount+="+wrtlgcount);
    }
    // check the counts of data in the excel generated through batches.
}
