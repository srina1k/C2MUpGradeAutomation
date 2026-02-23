package org.example.Utils;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;

public class ScreenShotUtils {

    public static void captureScreenshotToWord(String filePath, String imageName) {
        FileOutputStream fos = null;
        FileInputStream fis = null;

        try {
            WebDriver driver = DriverManager.getDriver();
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            XWPFDocument document;
            File file = new File(filePath);

            if (file.exists()) {
                document = new XWPFDocument(OPCPackage.open(file));
                System.out.println("Appending to existing Word document...");
            } else {
                document = new XWPFDocument();
                System.out.println("Creating new Word document...");
            }

            XWPFParagraph title = document.createParagraph();
            title.setSpacingBefore(200);
            XWPFRun titleRun = title.createRun();
            titleRun.setText("Screenshot: " + imageName);
            titleRun.setBold(true);
            titleRun.addBreak();

            fis = new FileInputStream(srcFile);
            XWPFRun imageRun = document.createParagraph().createRun();
            imageRun.addPicture(
                    fis,
                    Document.PICTURE_TYPE_PNG,
                    imageName,
                    500 * 9525,
                    300 * 9525
            );
            imageRun.addBreak();

            fos = new FileOutputStream(filePath);
            document.write(fos);

            System.out.println("Screenshot added to Word doc: " + imageName);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (fis != null) fis.close(); } catch (IOException ignored) {}
            try { if (fos != null) fos.close(); } catch (IOException ignored) {}
        }
    }
}