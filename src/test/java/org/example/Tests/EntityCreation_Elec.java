package org.example.Tests;

import org.example.Base.BaseClass;
import org.example.Pages.*;
import org.example.Utils.DriverManager;
import org.example.Utils.ScreenShotUtils;
import org.testng.annotations.Test;
import org.example.Utils.DBQueries;
import org.example.Utils.DBUtils;
import java.io.File;


public class EntityCreation_Elec extends BaseClass {

    String perID;
    String premID;
    String mpan = "1200036784281";
    String accID;
    String spId;
    String saID;
    String AdjustmentID;
    String PaymentID;

    @Test
    public void testLogin(){
        String fileName  = "Entity Creation.docx";
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage lp = new LoginPage(DriverManager.getDriver());
        lp.Logincredentials();
    }

    @Test(dependsOnMethods = "testLogin")
    public void personCreation(){
        PersonPage perpage = new PersonPage();
        perpage.personNavigation();
        perpage.personDetails("happie,jack", "12345 678989");
        perpage.correspondenceTab();
        perpage.charTab();
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","Adding Person details");
        perID = perpage.personID();
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","Person Entity Created: " + perID);
        System.out.println("Generated Person ID: " + perID);
    }

    @Test(dependsOnMethods = "personCreation")
    public void premiseCreation(){
        AddPremisePage premise = new AddPremisePage();
        premise.premiseNavigation();
        premise.premiseDetails();
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","Adding Prmise details");
        premID = premise.premiseID();
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","Premise Entity Created:  " + premID);
        System.out.println("Generated Premise ID: " + premID);
    }

    @Test(dependsOnMethods = "premiseCreation")
    public void servicePointCreation(){
        servicePointPage spPage = new servicePointPage();
        spPage.navigateToCCBServicePoint();
        spPage.CCBServicePointDetails(premID, mpan);
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","ServicePoint Entity Details:  " + premID);
        spId = spPage.servicePointID();
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","ServicePoint Entity Created:  " + premID);
        System.out.println("Generated SP ID: " + spId);
    }
    @Test(dependsOnMethods = "servicePointCreation")
    public void accountCreation() {
        accountPage accpage = new accountPage();
        accpage.navigateToAccount();
        accpage.accountDtails(premID);
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","Premise entity added in account");
        accpage.acctpersonsTab(perID);
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","Person entity added in account");
        accpage.acctCharTab();
        accID = accpage.accountID();
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","Account Entity Created: " + accID);
        System.out.println("Generated Account ID: " + accID);
    }
    @Test(dependsOnMethods = "accountCreation")
    public void serviceAgreementCreation() {
        ServiceAgreementPage saPage = new ServiceAgreementPage();
        saPage.navigatToSAFromAccountDropdown();
        saPage.saMainTab(premID);
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","Premise entity added in SA");
        saPage.saSpTab(spId);
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","ServicePoint entity entity added in SA");
        saPage.chargesQtyTab();
        saID = saPage.SaID();
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","SA created: " + saID);
    }
    @Test(dependsOnMethods = "serviceAgreementCreation")
    public void adjustmentCreation() {
        ServiceAgreementPage saPage = new ServiceAgreementPage();
        saPage.navigateToAdjustment();
        adjustmentPage adjPage = new adjustmentPage();
        adjPage.adjustmentDetails();
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","Adding Adjustment details");

        adjPage.adjustmentCharTab();
        AdjustmentID = adjPage.adjustmentID();
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","Adjustment is created: " + saID);

        System.out.println("Generated Adjustment ID: " + AdjustmentID);
    }
    @Test(dependsOnMethods = "adjustmentCreation")
    public void PaymentCreation() {

        String query = String.format(DBQueries.paymentEvent);
        String paymenteventid = DBUtils.getSingleDate(query, "PAY_EVENT_ID");
        System.out.println("Payment Event ID: " + paymenteventid);

        PaymentEventPage payeventpage = new PaymentEventPage();
        payeventpage.navigation();
        payeventpage.searchPayEventID(paymenteventid);
        payeventpage.payclickSearch();

        payeventpage.payEventDetails(accID,"50");
        PaymentID = payeventpage.getPaymentID();
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","Payment Event details");

        System.out.println("Generated Payment ID: " + PaymentID);

        payeventpage.paymentSearchPage(PaymentID);
        payeventpage.paymentSearch();
        ScreenShotUtils.captureScreenshotToWord("Entity Creation.docx","Payment details");

    }
}
