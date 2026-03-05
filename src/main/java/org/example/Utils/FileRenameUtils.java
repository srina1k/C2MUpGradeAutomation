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
        Path path = Paths.get(filePath).toAbsolutePath();
        System.out.println("Updating file: " + path);
        List<String> inputLines;
        try {
            inputLines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + path, e);
        }
        if (inputLines.isEmpty()) {
            System.out.println("No content in file, nothing to update.");
            return;
        }
        List<String> output = new ArrayList<>(inputLines.size());
        String header = inputLines.get(0);
        output.add(header); // keep header as-is
        boolean modified = false;
        for (int i = 1; i < inputLines.size(); i++) {
            String line = inputLines.get(i);
            if (line == null || line.trim().isEmpty()) {
                output.add(line);
                continue;
            }
            String[] cols = line.split(",", -1);
            if (cols.length >= 1) {
                String opp = cols[0].trim();
                if (opp.equals(oldOppID)) {
                    cols[0] = newOppID;
                    modified = true;
                }
                output.add(String.join(",", cols));
            } else {
                output.add(line);
            }
        }
        try {
            Files.write(path, output, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file: " + path, e);
        }
        if (modified) {
            System.out.println("OPP_ID updated from " + oldOppID + " to " + newOppID);
        } else {
            System.out.println("Old OPP_ID " + oldOppID + " not found. No changes made.");
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
