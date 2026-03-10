package org.example.Pages;
import org.example.Utils.ScreenShotUtils;
import org.example.Utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.example.Utils.DriverManager;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BatchJobSubmissionPage {
    private WebDriver driver;

    public BatchJobSubmissionPage(){
        this.driver = DriverManager.getDriver();
    }
        public void BatchPage()  {
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver,"main",10);
            String currentFrame = (String) ((JavascriptExecutor) driver).executeScript("return window.frameElement? window.frameElement.name : 'main';");
            System.out.println("Current frame switched to: " + currentFrame);
            By[] menuSequence2 = {By.id("IM_menuButton"), By.xpath("//li[@id='mainMenu']/child::div"), By.id("CI_MAINMENU_topMenuItem0x32"), By.xpath("(//span[contains(text(),'Add')])[1]")};
            for (By menuItem2 : menuSequence2){
                WaitUtils.waitAndClick(driver, menuItem2, 20);
            }
            WaitUtils.sleep(3000);
        }
        public void batchControl(String BatchCode){
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver,"main",5);

            By[] menuSequence2 = {By.id("IM_adminButton"), By.id("CI_ADMINMENU_topMenuItem0x1"), By.id("CI_SG_ADMIN_B_subMenuItem1x1"), By.xpath("//span[text()='Search']")};
            for (By menuItem2 : menuSequence2){
                WaitUtils.waitAndClick(driver, menuItem2, 3);
            }
            WaitUtils.sleep(3000);

            new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > 1);
            for (String window : driver.getWindowHandles()){
                driver.switchTo().window(window);
                if (driver.getTitle().contains("Batch Control Search")){
                    break;
                }
            }
            System.out.println("Switched to new window: " + driver.getTitle());
            WebElement batchCodeField = WaitUtils.waitForPVisible(driver, By.id("BATCH_CD"), 20);
            batchCodeField.sendKeys(BatchCode);
        }

        public Map<String, String> readLastBatchRunDate(){
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver,"main",5);
            WaitUtils.waitForFrameAndSwitch(driver,"tabPage",5);

            WebElement LastBatchDate = WaitUtils.waitForPVisible(driver, By.id("LAST_UPDATE_DTTM"), 10);
            String uiDate = LastBatchDate.getText().trim();
            uiDate = uiDate.replaceAll("[^0-9 :]", "-").trim();
            DateTimeFormatter uiFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            LocalDateTime dateTime = LocalDateTime.parse(uiDate,uiFormatter);
            LocalDate baseDate  = dateTime.toLocalDate();
            LocalDate businessDateobj = baseDate.plusDays(1);
            LocalDate updatedDate = baseDate.plusDays(3);
            DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH);

            DateTimeFormatter businessDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            Map<String, String> dates = new HashMap<>();
            dates.put("SQL_DATE", updatedDate.format(sqlFormatter).toUpperCase());
            dates.put("BUSINESS_DATE",businessDateobj.format(businessDate));
            return dates;

        }
        public void clickSearch() {
            WaitUtils.waitAndClick(driver, By.id("BU_criteria_batchSrch"), 2);
            String mainHandle = driver.getWindowHandles().iterator().next();
            driver.switchTo().window(mainHandle);
            WaitUtils.sleep(5000);
        }
        public void enterBatchCode(String batchcode) {
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver,"main",5);
            WaitUtils.waitForFrameAndSwitch(driver,"uiMap",5);
            //driver.findElement(By.xpath("//input[@id='batchControlInp']")).sendKeys(batchcode);
            WaitUtils.getWait(driver,15);
            WaitUtils.ElementToBeClickable(driver,By.xpath("//span/input[@id='batchControlInp']"),10);
            WebElement batch_control=driver.findElement(By.xpath("//span/input[@id='batchControlInp']"));
            batch_control.click();
            new Actions(driver).doubleClick(batch_control).sendKeys(Keys.BACK_SPACE).sendKeys(batchcode).sendKeys(Keys.ENTER).sendKeys(Keys.ENTER).perform();
            //batch_control.sendKeys(batchcode).sendKeys(Keys.ENTER);
            //WaitUtils.waitForPVisible(driver,By.xpath("//input[@id='batchControlInp']"),10);
            WaitUtils.waitForVisible(driver,By.xpath("//span/input[@id='boGroup_user']"));
            WebElement double_click = driver.findElement(By.xpath("//span/input[@id='boGroup_user']"));
            new Actions(driver).doubleClick(double_click).sendKeys(Keys.BACK_SPACE).sendKeys("BATCHUSR").sendKeys(Keys.ENTER).sendKeys(Keys.ENTER).perform();
            // Actions act = new Actions(driver);
//            act.doubleClick(double_click).build().perform();
//            act.sendKeys(Keys.BACK_SPACE);
//            act.sendKeys("BATCHUSR").sendKeys(Keys.ENTER).sendKeys(Keys.ENTER);
            WaitUtils.sleep(1000);
           // driver.findElement(By.xpath("//input[@id='boGroup_user']")).sendKeys("BATCHUSR");
           // WaitUtils.sleep(3000);
            ScreenShotUtils.captureScreenshotToWord("COMC15P1.docx","Step12:Running Ecoes batch");
           // driver.switchTo().defaultContent();
           // WaitUtils.waitForFrameAndSwitch(driver,"main",5);
            WebElement saveButton=driver.findElement(By.xpath("//div[@class='oraSectionFull']/div/input[@id='SAVE_BTN_MP']"));
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",saveButton);
            WaitUtils.sleep(2000);
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", saveButton);
           // WaitUtils.ElementToBeClickable(driver,By.xpath("//input[@id='SAVE_BTN_MP']"),30);
            //WaitUtils.waitAndClick(driver, By.xpath("//div[@class='oraSectionFull']/div/input[@id='SAVE_BTN_MP']"),10);
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver,"main",10);
            WaitUtils.sleep(3000);
            WebElement refresh = driver.findElement(By.xpath("//div[@class='pageTitleArea clearFloat']/div/input[@id='IM_REFRESH']"));
            while(true) {
                WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 10);
                WaitUtils.waitForFrameAndSwitch(driver,"zoneMapFrame_1",10);
                String status = driver.findElement(By.xpath("//span[@id='batchJobStatus']")).getText().trim();
                System.out.println("Current Batch status: " + status);
                if ("Ended".equalsIgnoreCase(status)) {
                    System.out.println("Batch completed successfully");
                    break;
                }
                driver.switchTo().defaultContent();
                WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
                refresh.click();
                WaitUtils.sleep(8000);
            }
            ScreenShotUtils.captureScreenshotToWord("COMC15P1.docx","Step12:Running Ecoes batch");
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
            WaitUtils.waitAndClick(driver, By.xpath("//ou-button[@id='IM_GOBACK']"),5);
        }
        public void CMBLRNOPBatch(String batchcode,String filename){
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver,"main",5);
            WaitUtils.waitForFrameAndSwitch(driver,"uiMap",5);
            WebElement CM_BLR_Batch = driver.findElement(By.cssSelector("input[id='batchControlInp']"));
            CM_BLR_Batch.click();
            new Actions(driver).doubleClick(CM_BLR_Batch).sendKeys(Keys.BACK_SPACE).sendKeys(batchcode).sendKeys(Keys.ENTER).sendKeys(Keys.ENTER).perform();;
            //driver.findElement(By.cssSelector("input[id='batchControlInp']")).sendKeys(batchcode);
//            WebElement double_click = driver.findElement(By.xpath("//input[@id='USER_ID']"));
//            Actions act = new Actions(driver);
//            act.doubleClick(double_click).build().perform();
//            act.sendKeys(Keys.BACK_SPACE);
//            WaitUtils.sleep(1000);
//            driver.findElement(By.xpath("//input[@id='USER_ID']")).sendKeys("BATCHUSR");
            WaitUtils.waitForVisible(driver,By.xpath("//span/input[@id='boGroup_user']"));
            WebElement double_click = driver.findElement(By.xpath("//span/input[@id='boGroup_user']"));
            new Actions(driver).doubleClick(double_click).sendKeys(Keys.BACK_SPACE).sendKeys("BATCHUSR").sendKeys(Keys.ENTER).sendKeys(Keys.ENTER).perform();
            WaitUtils.sleep(3000);
            //driver.switchTo().frame("BJP");
            driver.findElement(By.id("batchParameterValue_2")).sendKeys(filename);
//            driver.switchTo().defaultContent();
//            WaitUtils.waitForFrameAndSwitch(driver,"main",5);
            WaitUtils.waitAndClick(driver, By.cssSelector("input[id='SAVE_BTN_MP']"),5);
            WaitUtils.sleep(3000);
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver,"main",10);
            WebElement refresh = driver.findElement(By.cssSelector("input[id='IM_REFRESH']"));
            while(true) {
                WaitUtils.waitForFrameAndSwitch(driver,"tabPage",15);
                WaitUtils.waitForFrameAndSwitch(driver, "zoneMapFrame_1", 15);
                String status = driver.findElement(By.xpath("//span[@id='batchJobStatus']")).getText().trim();
                System.out.println("Current Batch status: " + status);
                if ("Ended".equalsIgnoreCase(status)) {
                    System.out.println("Batch completed successfully");
                    break;
                }
                driver.switchTo().defaultContent();
                WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
                refresh.click();
                WaitUtils.sleep(8000);
            }
            ScreenShotUtils.captureScreenshotToWord("COMC15P1.docx","Step12:Running Ecoes batch");
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
            WaitUtils.waitAndClick(driver, By.xpath("//ou-button[@id='IM_GOBACK']"),5);
        }
    public void CMSPSU2Batch(String batchcode,String filename, String MCTId){
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"uiMap",5);
        WaitUtils.ElementToBeClickable(driver,By.xpath("//span/input[@id='batchControlInp']"),10);
        WebElement batch_control=driver.findElement(By.xpath("//span/input[@id='batchControlInp']"));
        batch_control.click();;
        new Actions(driver).doubleClick(batch_control).sendKeys(Keys.BACK_SPACE).sendKeys(batchcode).sendKeys(Keys.ENTER).sendKeys(Keys.ENTER).perform();
        //driver.findElement(By.xpath("//span/input[@id='batchControlInp']")).sendKeys(batchcode);
        WaitUtils.waitForVisible(driver,By.xpath("//span/input[@id='boGroup_user']"));
        WebElement double_click = driver.findElement(By.xpath("//span/input[@id='boGroup_user']"));
        new Actions(driver).doubleClick(double_click).sendKeys(Keys.BACK_SPACE).sendKeys("BATCHUSR").sendKeys(Keys.ENTER).sendKeys(Keys.ENTER).perform();
//        Actions act = new Actions(driver);
//        act.doubleClick(double_click).build().perform();
//        act.sendKeys(Keys.BACK_SPACE);
//        WaitUtils.sleep(1000);
//        driver.findElement(By.xpath("//input[@id='USER_ID']")).sendKeys("BATCHUSR");
        WaitUtils.sleep(3000);
        //driver.switchTo().frame("BJP");
        driver.findElement(By.id("batchParameterValue_2")).sendKeys(filename);
        WaitUtils.sleep(3000);
        //driver.findElement(By.id("BJP:5$BATCH_PARM_VAL")).sendKeys(MCTId);
        driver.findElement(By.id("batchParameterValue_5")).sendKeys(MCTId);
        driver.findElement(By.id("batchParameterValue_6")).sendKeys("N");
        driver.findElement(By.id("batchParameterValue_9")).sendKeys("I");
        //driver.switchTo().defaultContent();
        //WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        //WaitUtils.waitAndClick(driver, By.cssSelector("input[id='IM_SAVE']"),5);
        WebElement saveButton=driver.findElement(By.xpath("//div[@class='oraSectionFull']/div/input[@id='SAVE_BTN_MP']"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",saveButton);
        WaitUtils.sleep(2000);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", saveButton);
        WaitUtils.sleep(3000);
        //WebElement refresh = driver.findElement(By.cssSelector("input[id='IM_REFRESH']"));
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",10);
        WaitUtils.sleep(3000);
        WebElement refresh = driver.findElement(By.xpath("//input[@id='IM_REFRESH']"));
        while(true) {
            WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
            WaitUtils.waitForFrameAndSwitch(driver,"zoneMapFrame_1",5);
            String status = driver.findElement(By.xpath("//span[@id='batchJobStatus']")).getText().trim();
            System.out.println("Current Batch status: " + status);
            if ("Ended".equalsIgnoreCase(status)) {
                System.out.println("Batch completed successfully");
                break;
            }
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
            refresh.click();
            WaitUtils.sleep(8000);
        }
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitAndClick(driver, By.xpath("//ou-button[@id='IM_GOBACK']"),15);
    }
    public void CMSPSU1ABatch(String batchcode,String filename, String OppID){

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitForFrameAndSwitch(driver,"uiMap",5);
        WaitUtils.ElementToBeClickable(driver,By.xpath("//span/input[@id='batchControlInp']"),10);
        WebElement batch_control= driver.findElement(By.xpath("//span/input[@id='batchControlInp']"));
        batch_control.click();
        new Actions(driver).doubleClick(batch_control).sendKeys(Keys.BACK_SPACE).sendKeys(batchcode).sendKeys(Keys.ENTER).sendKeys(Keys.ENTER).perform();
        WaitUtils.waitForVisible(driver,By.xpath("//span/input[@id='boGroup_user']"));
        WebElement double_click= driver.findElement(By.xpath("//span/input[@id='boGroup_user']"));
        new Actions(driver).doubleClick(double_click).sendKeys(Keys.BACK_SPACE).sendKeys("BATCHUSR").sendKeys(Keys.ENTER).sendKeys(Keys.ENTER).perform();
        WaitUtils.sleep(3000);
//        driver.findElement(By.cssSelector("input[id='BATCH_CD']")).sendKeys(batchcode);
//        WebElement double_click = driver.findElement(By.xpath("//input[@id='USER_ID']"));
//        Actions act = new Actions(driver);
//        act.doubleClick(double_click).build().perform();
//        act.sendKeys(Keys.BACK_SPACE).sendKeys("BATCHUSR").sendKeys(Keys.ENTER).sendKeys(Keys.ENTER);
//        WaitUtils.sleep(1000);
        //driver.findElement(By.xpath("//input[@id='USER_ID']")).sendKeys("BATCHUSR");
        //WaitUtils.sleep(3000);
        //driver.switchTo().frame("BJP");
        driver.findElement(By.id("batchParameterValue_2")).sendKeys(filename);
        WaitUtils.sleep(3000);
        driver.findElement(By.id("batchParameterValue_4")).sendKeys(OppID);
        driver.findElement(By.id("batchParameterValue_6")).sendKeys("N");
        driver.findElement(By.id("batchParameterValue_9")).sendKeys("I");
//        WaitUtils.waitAndClick(driver, By.cssSelector("input[id='IM_SAVE']"),5);
//        WaitUtils.sleep(3000);
        WebElement saveButton=driver.findElement(By.xpath("//input[@id='SAVE_BTN_MP']"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);",saveButton);
        WaitUtils.sleep(2000);
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", saveButton);
        WaitUtils.sleep(3000);
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",10);
        WaitUtils.sleep(3000);
        WebElement refresh = driver.findElement(By.xpath("//input[@id='IM_REFRESH']"));
        while(true) {
            WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
            WaitUtils.waitForFrameAndSwitch(driver,"zoneMapFrame_1",5);
            String status = driver.findElement(By.xpath("//span[@id='batchJobStatus']")).getText().trim();
            System.out.println("Current Batch status: " + status);
            if ("Ended".equalsIgnoreCase(status)) {
                System.out.println("Batch completed successfully");
                break;
            }
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
            refresh.click();
            WaitUtils.sleep(8000);
        }
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitAndClick(driver, By.xpath("//ou-button[@id='IM_GOBACK']"),10);
        WaitUtils.sleep(2000);
    }

    public void CMLTMPTBatch (String batchcode, String businessDate) {

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
        driver.findElement(By.cssSelector("input[id='BATCH_CD']")).sendKeys(batchcode);
        WebElement double_click1 = driver.findElement(By.id("BATCH_THREAD_CNT"));
        Actions act1 = new Actions(driver);
        act1.doubleClick(double_click1).build().perform();
        act1.sendKeys(Keys.BACK_SPACE);
        WaitUtils.sleep(1000);
        double_click1.sendKeys("50");
        driver.findElement(By.id("PROCESS_DT")).sendKeys(businessDate);

        WebElement double_click2 = driver.findElement(By.xpath("//input[@id='USER_ID']"));
        Actions act2 = new Actions(driver);
        act2.doubleClick(double_click2).build().perform();
        act2.sendKeys(Keys.BACK_SPACE);
        WaitUtils.sleep(1000);
        driver.findElement(By.xpath("//input[@id='USER_ID']")).sendKeys("BATCHUSR");

        /*driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitAndClick(driver, By.cssSelector("input[id='IM_SAVE']"),5);
        WaitUtils.sleep(3000);
        WebElement refresh = driver.findElement(By.cssSelector("input[id='IM_REFRESH']"));

        while(true) {
            WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
            String status = driver.findElement(By.xpath("//span[@id='BATCH_JOB_STAT_FLG']")).getText().trim();
            System.out.println("Current Batch status: " + status);
            if ("Ended".equalsIgnoreCase(status)) {
                System.out.println("Batch completed successfully");
                break;
            }
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
            //refresh.click();
            WaitUtils.sleep(8000);
        }*/
        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[@id='IM_GOBACK']"),10);
        WaitUtils.sleep(2000);
    }

    public void CMLPAFCTBatch (String batchcode) {

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
        driver.findElement(By.cssSelector("input[id='BATCH_CD']")).sendKeys(batchcode);

        WebElement double_click2 = driver.findElement(By.xpath("//input[@id='USER_ID']"));
        Actions act2 = new Actions(driver);
        act2.doubleClick(double_click2).build().perform();
        act2.sendKeys(Keys.BACK_SPACE);
        WaitUtils.sleep(1000);
        driver.findElement(By.xpath("//input[@id='USER_ID']")).sendKeys("BATCHUSR");

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitAndClick(driver, By.cssSelector("input[id='IM_SAVE']"),5);
        WaitUtils.sleep(3000);
        WebElement refresh = driver.findElement(By.cssSelector("input[id='IM_REFRESH']"));

        while(true) {
            WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
            String status = driver.findElement(By.xpath("//span[@id='BATCH_JOB_STAT_FLG']")).getText().trim();
            System.out.println("Current Batch status: " + status);
            if ("Ended".equalsIgnoreCase(status)) {
                System.out.println("Batch completed successfully");
                break;
            }
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
            //refresh.click();
            WaitUtils.sleep(8000);
        }

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[@id='IM_GOBACK']"),10);
        WaitUtils.sleep(2000);
    }

    public void CMCUUB2Batch (String batchcode, String filename) {

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
        driver.findElement(By.cssSelector("input[id='BATCH_CD']")).sendKeys(batchcode);

        WebElement double_click2 = driver.findElement(By.xpath("//input[@id='USER_ID']"));
        Actions act2 = new Actions(driver);
        act2.doubleClick(double_click2).build().perform();
        act2.sendKeys(Keys.BACK_SPACE);
        WaitUtils.sleep(1000);
        driver.findElement(By.xpath("//input[@id='USER_ID']")).sendKeys("BATCHUSR");

        driver.switchTo().frame("BJP");
        driver.findElement(By.id("BJP:2$BATCH_PARM_VAL")).sendKeys(filename);
        WaitUtils.sleep(3000);

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitAndClick(driver, By.cssSelector("input[id='IM_SAVE']"),5);
        WaitUtils.sleep(3000);
        WebElement refresh = driver.findElement(By.cssSelector("input[id='IM_REFRESH']"));

        while(true) {
            WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
            String status = driver.findElement(By.xpath("//span[@id='BATCH_JOB_STAT_FLG']")).getText().trim();
            System.out.println("Current Batch status: " + status);
            if ("Ended".equalsIgnoreCase(status)) {
                System.out.println("Batch completed successfully");
                break;
            }
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
            refresh.click();
            WaitUtils.sleep(8000);
        }

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[@id='IM_GOBACK']"),10);
        WaitUtils.sleep(2000);
    }

    public void CMMONOPBatch (String batchcode) {

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
        driver.findElement(By.cssSelector("input[id='BATCH_CD']")).sendKeys(batchcode);

        WebElement double_click2 = driver.findElement(By.xpath("//input[@id='USER_ID']"));
        Actions act2 = new Actions(driver);
        act2.doubleClick(double_click2).build().perform();
        act2.sendKeys(Keys.BACK_SPACE);
        WaitUtils.sleep(1000);
        driver.findElement(By.xpath("//input[@id='USER_ID']")).sendKeys("BATCHUSR");
        WaitUtils.sleep(1000);

        driver.switchTo().frame("BJP");
        driver.findElement(By.id("BJP:3$BATCH_PARM_VAL")).sendKeys("CM-Opportunity");
        driver.findElement(By.id("BJP:4$BATCH_PARM_VAL")).sendKeys("QUOTE");

        /*driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver,"main",5);
        WaitUtils.waitAndClick(driver, By.cssSelector("input[id='IM_SAVE']"),5);
        WaitUtils.sleep(3000);
        WebElement refresh = driver.findElement(By.cssSelector("input[id='IM_REFRESH']"));

        while(true) {
            WaitUtils.waitForFrameAndSwitch(driver, "tabPage", 5);
            String status = driver.findElement(By.xpath("//span[@id='BATCH_JOB_STAT_FLG']")).getText().trim();
            System.out.println("Current Batch status: " + status);
            if ("Ended".equalsIgnoreCase(status)) {
                System.out.println("Batch completed successfully");
                break;
            }
            driver.switchTo().defaultContent();
            WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
            //refresh.click();
            WaitUtils.sleep(8000);
        }

        driver.switchTo().defaultContent();
        WaitUtils.waitForFrameAndSwitch(driver, "main", 5);
        WaitUtils.waitAndClick(driver, By.xpath("//span[@id='IM_GOBACK']"),10);
        WaitUtils.sleep(2000);*/
    }

}
