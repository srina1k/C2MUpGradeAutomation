package org.example.Tests;

import org.example.Base.BaseClass;
import org.example.Pages.BatchJobSubmissionPage;
import org.example.Pages.LoginPage;
import org.example.Utils.*;
import org.testng.annotations.Test;
import java.io.File;
import java.sql.SQLException;

/*Pre-Condition Check
.
.
.
Before executing this sceanrio, check Bill After Date is present
for Model temaplate account, if present make it null and proceed*/

public class billStop extends BaseClass {

    String newFilename;
    String templateAccountID;// = "3540684679"; //Need to check the charactersitic is not there on the account
    String accountID;//= "7481000000"; //To check the account with bill stop
    @Test
    public void testLogin(){
        String fileName = "BillStop.docx";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage LoginPage = new LoginPage(DriverManager.getDriver());
        LoginPage.Logincredentials();
    }
    @Test(dependsOnMethods = "testLogin")
    public void fileUpload(){
        ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","BillStop");
        templateAccountID = ExcelUtils.getCellData(1,1);
        System.out.println("Template Account ID: " + templateAccountID);
        accountID= ExcelUtils.getCellData(2,1);
        System.out.println("Account ID: " + accountID);
        String renameFilePath = "C:\\AutomationDocuments\\BillStop\\CMACUPL_FRI_AT.csv";
        newFilename  = "CMACUPL_MAR_AT7.csv";
        String newFilePath  = FileRenameUtils.renameFile(renameFilePath, newFilename);
        System.out.println("Updated File Path: " + newFilePath);
        String remotePath = "/ccbfsx/Common/account_cascade_upload/";
        WinScpServerUtils.uploadFile(newFilePath,remotePath);
    }
    @Test(dependsOnMethods = "fileUpload")
    public void batchRun() throws SQLException {
        String query1 = String.format(DBQueries.billStop1, accountID);
        DBUtils.executeSelectQuery(query1);
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.CMACUPL4Batch("CMACUPL4",newFilename, templateAccountID);
        ScreenShotUtils.captureScreenshotToWord("BillStop.docx","Running Batch CMACUPL4");
        batchP.clickRefresh();
        String query = String.format(DBQueries.billStop, accountID);
        DBUtils.executeSelectQuery(query);
        String query2 = String.format(DBQueries.billStop1, accountID);
        DBUtils.executeSelectQuery(query2);
    }
}
