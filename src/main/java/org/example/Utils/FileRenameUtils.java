package org.example.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileRenameUtils {

    public static void replaceOppID(String filePath, String oldOppID, String newOppID) {

        StringBuilder fileContent = new StringBuilder();
        boolean isModified = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(oldOppID)) {
                    line = line.replace(oldOppID, newOppID); // replace ID
                    isModified = true;
                }
                fileContent.append(line).append(System.lineSeparator());
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(fileContent.toString());
            }
            if (!isModified) {
                System.out.println("Failed to edit file: Old Opp ID not found");
            } else {
                System.out.println("File edited successfully");
            }

        } catch (IOException e) {
            throw new RuntimeException("CSV updation failed", e);
        }
    }
    public static void replaceDate(String filePath){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);

        String newStartDate = startDate.format(formatter);
        String newEndDate = endDate.format(formatter);

        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = reader.readLine()) != null){
                line = line.replaceAll("\\d{4}-\\d{2}-\\d{2},\\d{4}-\\d{2}-\\d{2}", newStartDate + "," + newEndDate);
                fileContent.append(line).append(System.lineSeparator());
            }
        } catch (IOException e){
            throw new RuntimeException("Failed to read CSV file",e);
        }
        try (BufferedWriter writer =new BufferedWriter(new FileWriter(filePath))){
            writer.write(fileContent.toString());
        } catch (IOException e){
            throw new RuntimeException("Failed to write CSV file", e);
        }
        System.out.println("CSV dates Updated");
    }

    public static String getFileNameFromPath(String filePath){
        return new File(filePath).getName();
    }

    public static void replaceCustID(Path filePath, String CustID) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        for (int i = 0; i < lines.size(); i++){
            String line = lines.get(i);
            if (line.startsWith("DTL")) {
                String updateLine = line.replaceFirst("\\b\\d{10}\\b", java.util.regex.Matcher.quoteReplacement(CustID));
                lines.set(i,updateLine);
                break;
            }
        }
        Files.write(filePath, lines);
    }
    public static String renameFile(String filePath, String newName){
        File oldFile = new File(filePath);
        if (!oldFile.exists()){
            System.out.println("File not found: " + filePath);
        }

        File newFile = new File(oldFile.getParent() + File.separator + newName);
        boolean isRenamed = oldFile.renameTo(newFile);
        if (!isRenamed){
            System.out.println("Failed to rename file");
        }
        return newFile.getAbsolutePath();
    }

    public static void replaceOPPID(String filePath, List<String> newOPPIDs) throws Exception {
        Iterator<String> idIterator = newOPPIDs.iterator();
        Map<String, String> map = new HashMap<>();
        List<String> updatedLines = new ArrayList<>();

        for (String line : Files.readAllLines(Paths.get(filePath))){
            if (line.startsWith("OPP_ID")){
                updatedLines.add(line);
                continue;
            }
            if (!line.trim().isEmpty()){
                String[] cols = line.split(",", -1);
                String oldID = cols[0].trim();

                if (!map.containsKey(oldID)){
                    if (!idIterator.hasNext()){
                        throw new RuntimeException("not enough new Opp IDs");
                    }
                    map.put(oldID, idIterator.next());
                }
                cols[0] = map.get(oldID);
                updatedLines.add(String.join(",", cols));
            }
        }
        Files.write(Paths.get(filePath), updatedLines);
    }
}
