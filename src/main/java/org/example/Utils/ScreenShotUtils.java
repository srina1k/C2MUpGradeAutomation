package org.example.Utils;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;

public class ScreenShotUtils {

    public static void captureScreenshotToWord(String filePath, String imageName){
        try{
            WebDriver driver = DriverManager.getDriver();
            //Take Screenshot
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            XWPFDocument document;
            File file =  new File(filePath);

            if(file.exists()){
                FileInputStream fis = new FileInputStream(file);
                document = new XWPFDocument(fis);
                fis.close();
            } else {
                document = new XWPFDocument();
            }

            //Add title to word
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(" " + imageName);
            run.setBold(true);
            run.addBreak();

            //Add image to word
            FileInputStream pic = new FileInputStream(srcFile);
            run.addPicture(pic, Document.PICTURE_TYPE_PNG, imageName, 580*9525, 260*9525);
            pic.close();

            FileOutputStream out = new FileOutputStream(filePath);
            document.write(out);
            out.close();
            document.close();

            System.out.println("Screenshot added to word with name: " + imageName);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}