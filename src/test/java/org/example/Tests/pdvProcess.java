package org.example.Tests;


import org.example.Base.BaseClass;
import org.example.Pages.BatchJobSubmissionPage;
import org.example.Pages.LoginPage;
import org.example.Utils.*;
import org.testng.annotations.Test;
import java.io.File;
import java.sql.SQLException;

public class pdvProcess extends BaseClass {

    @Test
    public void testLogin() {

        String fileName = "PDV Process.docx";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage LoginPage = new LoginPage(DriverManager.getDriver());
        LoginPage.Logincredentials();
    }
    @Test(dependsOnMethods = "testLogin")
    public void batchRun() throws SQLException{

        String query = String.format(DBQueries.pdvCase);
        String caseID = DBUtils.getSingleDate(query, "CASE_ID");
        System.out.println("Case ID: " + caseID);

        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.enterBatchCode("CM-CNLPD");
        ScreenShotUtils.captureScreenshotToWord("PDV Process.docx","Running PDV case cancellation batch ");
        //batchP.clickRefresh();

        batchP.BatchPage();
        batchP.enterBatchCode("CM-PDVLG");
        ScreenShotUtils.captureScreenshotToWord("PDV Process.docx","Running PDV log extract batch ");
        //batchP.clickRefresh();

        String remoteFilePath = "/ccbfsx/Download-RO/pdv_download_pdvlg/";
        String filePreFix = "PDV_LOG_EXTRACT";
        String pdvlgFile = WinScpServerUtils.verifyFileGenerated(remoteFilePath, filePreFix);
        System.out.println("File generated: " + pdvlgFile);

        batchP.BatchPage();
        batchP.enterBatchCode("CM-WRTLG");
        ScreenShotUtils.captureScreenshotToWord("PDV Process.docx","Running Warrant log batch ");
        //batchP.clickRefresh();

        String remoteFilePath1 = "/ccbfsx/Download-RO/warrant_log/";
        String filePreFix1 = "CM-WARRANT_LOG_EXTRACT";
        String wrtlgFile = WinScpServerUtils.verifyFileGenerated(remoteFilePath1, filePreFix1);
        System.out.println("File generated: " + wrtlgFile);
    }
}
