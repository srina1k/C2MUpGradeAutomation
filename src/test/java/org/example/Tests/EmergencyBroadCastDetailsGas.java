package org.example.Tests;

import org.example.Base.BaseClass;
import org.example.Pages.LoginPage;
import org.example.Pages.servicePointPage;
import org.example.Utils.*;
import org.testng.annotations.Test;

import java.io.File;
import java.sql.SQLException;

public class EmergencyBroadCastDetailsGas extends BaseClass {
    String mprn1;
    String mprn2;

    @Test
    public void testLogin(){
//        String fileName  = "EmergencyBroadCastDetailsGas.docx";
//        File file = new File(fileName);
//        if(file.exists()){
//            file.delete();
//            System.out.println("Old word file deleted: " + fileName);
//        }
        LoginPage lp = new LoginPage(DriverManager.getDriver());
        lp.Logincredentials();
    }
    @Test (dependsOnMethods = "testLogin")
        public void AnnualCapacity1() throws SQLException {
            ExcelUtils.loadExcel("C:\\Users\\srina1k\\IdeaProjects\\C2MUpGradeAutomation\\src\\main\\java\\Resources\\RTScenarioTestDataReport.xlsx","EmergencyBroadCastDetails");
            mprn1=ExcelUtils.getCellData(1,1);
            mprn2=ExcelUtils.getCellData(2,1);
            String ServicePoint1=String.format(DBQueries.GasServicePoint,mprn1);
            String servicepoint= DBUtils.getSingleDate(ServicePoint1,"SP_ID");
            System.out.println("ServicePoint"+"="+servicepoint);
            servicePointPage spPage=new servicePointPage();
            spPage.navigateToCCBServicePoint();
            spPage.enterServicePoint(servicepoint);
            ScreenShotUtils.captureScreenshotToWord("EmergencyBroadCastDetailsGas.docx","Service point1:"+servicepoint);
            spPage.characteristicsTab();
            ScreenShotUtils.captureScreenshotToWord("EmergencyBroadCastDetailsGas.docx","Characteristics Tab");
            String AnnualQuantityQuery=String.format(DBQueries.GasSupplyQuantity,servicepoint);
            String AnnualQuantity=DBUtils.getSingleDate(AnnualQuantityQuery,"ADHOC_CHAR_VAL");
            System.out.println("Annual Quantity"+"="+AnnualQuantity);
            String SupplyIndicator=String.format(DBQueries.supplypoint,servicepoint);
            String SupplyPoint=DBUtils.getSingleDate(SupplyIndicator,"CHAR_VAL").trim();
            System.out.println("SupplyPoint"+"="+SupplyPoint);
            spPage.ValidateCharacteristics();
            spPage.NavigateToEmergencyBroadcastDetails(AnnualQuantity,SupplyPoint);
            ScreenShotUtils.captureScreenshotToWord("EmergencyBroadCastDetailsGas.docx","Emergency Details Window");
            spPage.AcceptAlertAndCancel();
            spPage.validateMdm();
            ScreenShotUtils.captureScreenshotToWord("EmergencyBroadCastDetailsGas.docx","Logs without entering any data");
            String QuantityDelete=String.format(DBQueries.DeleteANUQuantity,servicepoint);
            DBUtils.UpdateQuery(QuantityDelete);
            String SupplyIndicatorDelete=String.format(DBQueries.DeleteSupplyIndicator,servicepoint);
            DBUtils.UpdateQuery(SupplyIndicatorDelete);
            spPage.goback();
            spPage.NavigateToEmergencyBroadcastDetails(AnnualQuantity,SupplyPoint);
            spPage.fillcontactDetails();
            ScreenShotUtils.captureScreenshotToWord("EmergencyBroadCastDetailsGas.docx","Contact Details filled after deleting characteristics");
            spPage.AcceptAlertAndCancel();
            spPage.validateMdm();
            ScreenShotUtils.captureScreenshotToWord("EmergencyBroadCastDetailsGas.docx","Loga validation after filling the contact");
        }
        @Test (dependsOnMethods = "AnnualCapacity1")
        public void AnnualCapacity2() throws SQLException {
            String ServicePoint2=String.format(DBQueries.GasServicePoint,mprn2);
            String Servicepoint= DBUtils.getSingleDate(ServicePoint2,"SP_ID");
            System.out.println("ServicePoint"+"="+Servicepoint);
            servicePointPage spPage=new servicePointPage();
            spPage.navigateToCCBServicePoint();
            spPage.enterServicePoint(Servicepoint);
            ScreenShotUtils.captureScreenshotToWord("EmergencyBroadCastDetailsGas.docx","ServicePoint2:"+Servicepoint);
            spPage.characteristicsTab();
            ScreenShotUtils.captureScreenshotToWord("EmergencyBroadCastDetailsGas.docx","Validated characteristics");
            String AnnualQuantityQuery=String.format(DBQueries.GasSupplyQuantity,Servicepoint);
            String AnnualQuantity=DBUtils.getSingleDate(AnnualQuantityQuery,"ADHOC_CHAR_VAL");
            System.out.println("Annual Quantity"+"="+AnnualQuantity);
            String SupplyIndicator=String.format(DBQueries.supplypoint,Servicepoint);
            String SupplyPoint=DBUtils.getSingleDate(SupplyIndicator,"CHAR_VAL").trim();
            System.out.println("SupplyPoint"+"="+SupplyPoint);
            spPage.ValidateCharacteristics();
            spPage.NavigateToEmergencyBroadcastDetails(AnnualQuantity,SupplyPoint);
            ScreenShotUtils.captureScreenshotToWord("EmergencyBroadCastDetailsGas.docx","Emergency BroadCast details");
            spPage.AcceptAlertAndCancel();
            spPage.validateMdm();
            ScreenShotUtils.captureScreenshotToWord("EmergencyBroadCastDetailsGas.docx","Logs without entering data");
            String QuantityDelete=String.format(DBQueries.DeleteANUQuantity,Servicepoint);
            DBUtils.UpdateQuery(QuantityDelete);
            String SupplyIndicatorDelete=String.format(DBQueries.DeleteSupplyIndicator,Servicepoint);
            DBUtils.UpdateQuery(SupplyIndicatorDelete);
            spPage.goback();
            spPage.NavigateToEmergencyBroadcastDetails(AnnualQuantity,SupplyPoint);
            spPage.fillcontactDetails();
            spPage.AcceptAlertAndCancel();
            spPage.validateMdm();
            ScreenShotUtils.captureScreenshotToWord("EmergencyBroadCastDetailsGas.docx","Logs after contact details");
            spPage.goback();
            spPage.NavigateToEmergencyBroadcastDetails(AnnualQuantity,SupplyPoint);
            spPage.PriorityConsumerDetails();
            spPage.AcceptAlertAndCancel();
            spPage.validateMdm();
            ScreenShotUtils.captureScreenshotToWord("EmergencyBroadCastDetailsGas.docx","Logs After Consumer Details");
            spPage.goback();
            spPage.NavigateToEmergencyBroadcastDetails(AnnualQuantity,SupplyPoint);
            spPage.fillCustomerDetails();
            spPage.AcceptAlertAndCancel();
            spPage.validateMdm();
            ScreenShotUtils.captureScreenshotToWord("EmergencyBroadCastDetailsGas.docx","Logs After Customer Details");
        }

}
