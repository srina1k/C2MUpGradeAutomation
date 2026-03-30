package org.example.Tests;

import Base.BaseTest;
import Listener.MyListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.BatchJobSubmissionPage;
import pages.LoginPage;
import pages.OverdueProcessPage;
import utils.DBQueries;
import utils.DBUtils;
import utils.ScreenShotUtils;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Listeners(MyListener.class)
public class OverdueProcess extends BaseTest {

    @Test
    public void testLogin(){
        String fileName  = "Overdue Process.docx";
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
            LoginPage lp = new LoginPage();
            lp.loginFromConfig();
    }

    //Running C1-ADMOV batch for New log creation of OverDue Processes
    @Test(dependsOnMethods = "testLogin")
    public void batchRun() throws SQLException {
        BatchJobSubmissionPage batchP = new BatchJobSubmissionPage();
        batchP.BatchPage();
        batchP.C1AdmovBatch("C1-ADMOV");
        ScreenShotUtils.captureScreenshotToWord("Overdue Process.docx","C1-ADMOV batch is running");
        batchP.clickRefresh();

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH);
        String formattedDate = today.format(formatter).toUpperCase();

        String query = String.format(DBQueries.overDue, formattedDate);
        String overDueID = DBUtils.getSingleDate(query, "OD_PROC_ID");
        System.out.println("Overdue ID: " + overDueID);

        OverdueProcessPage overdueProcess = new OverdueProcessPage();
        overdueProcess.navigateOverdueProcess(overDueID);
        ScreenShotUtils.captureScreenshotToWord("Overdue Process.docx","Take any one overdue ID from DB and seacrh.");
        overdueProcess.eventsTab(30);
        ScreenShotUtils.captureScreenshotToWord("Overdue Process.docx","Event Status is Pending");

        batchP.BatchPage();
        batchP.C1ODETBatch("C1-ODET");
        ScreenShotUtils.captureScreenshotToWord("Overdue Process.docx","C1-ODET batch is Running");
        batchP.clickRefresh();

        overdueProcess.eventsTab(30);
        ScreenShotUtils.captureScreenshotToWord("Overdue Process.docx","Event Status is now Completed after batch run");

        batchP.BatchPage();
        batchP.CMLTPEXBatch("CM-LTPEX");
        ScreenShotUtils.captureScreenshotToWord("Overdue Process.docx","C1-LTPEX batch is Running");
        batchP.clickRefresh();
    }


}
