package org.example.Tests;




import org.example.Base.BaseClass;
import org.example.Pages.LoginPage;
import org.example.Utils.DriverManager;
import org.testng.annotations.Test;

import java.io.File;

public class liveBillingP2 extends BaseClass {

    @Test
    public void testLogin() {
        String fileName = "LiveBilling-P2.docx";
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
            System.out.println("Old word file deleted: " + fileName);
        }
        LoginPage lp = new LoginPage(DriverManager.getDriver());
        lp.Logincredentials();
    }
}
