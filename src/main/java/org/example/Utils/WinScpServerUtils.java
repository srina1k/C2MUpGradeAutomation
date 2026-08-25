package org.example.Utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File;
import java.util.Comparator;
import java.util.Vector;


public class WinScpServerUtils {
    public static void uploadFile(String localFilePath, String remoteFilePath) {
        String Host = ConfigReader.getWinScpProperty("sftp.host");
        int port = Integer.parseInt(ConfigReader.getWinScpProperty("sftp.port").trim());
        String username = ConfigReader.getWinScpProperty("sftp.username");
        String password = ConfigReader.getWinScpProperty("sftp.password");
        Session session = null;
        ChannelSftp channel = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, Host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();

            channel.put(localFilePath, remoteFilePath);

        } catch (Exception e) {
            throw new RuntimeException("File upload Failed", e);
        } finally {
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
        }
    }
    public static String verifyFileGenerated(String remoteDir, String filePrefix){

        String fileNameFound = null;
        String Host = ConfigReader.getWinScpProperty("sftp.host");
        int port = Integer.parseInt(ConfigReader.getWinScpProperty("sftp.port").trim());
        String username = ConfigReader.getWinScpProperty("sftp.username");
        String password = ConfigReader.getWinScpProperty("sftp.password");

        Session session = null;
        ChannelSftp channel = null;
        try {
            String todayeDate = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
            JSch jsch = new JSch();
            session = jsch.getSession(username, Host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();

            ChannelSftp sftp = (ChannelSftp) channel;

            Vector<ChannelSftp.LsEntry> files = sftp.ls(remoteDir);
            for (ChannelSftp.LsEntry file : files) {
                String fileName = file.getFilename();

                if (fileName.contains(filePrefix) && fileName.contains(todayeDate)) {
                    fileNameFound = fileName;
                    break;
                }
            }
            sftp.disconnect();
            session.disconnect();
        }   catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
        }
        return fileNameFound;
    }
    public static String fetchFileName(String remoteDir,String check) {

        System.out.println("Remote Dir = " + remoteDir);
        System.out.println("Check      = " + check);
        String fileNameFound = null;
        //String remoteDir = "/c2m/HOCPayments/in/";
        String Host = ConfigReader.getWinScpProperty("sftp.host");
        int port = Integer.parseInt(ConfigReader.getWinScpProperty("sftp.port").trim());
        String username = ConfigReader.getWinScpProperty("sftp.username");
        String password = ConfigReader.getWinScpProperty("sftp.password");
        Session session = null;
        ChannelSftp channel = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, Host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftp = (ChannelSftp) channel;
            Vector<ChannelSftp.LsEntry> files = sftp.ls(remoteDir);
            for (ChannelSftp.LsEntry file : files) {
                String fileName = file.getFilename();
                if (fileName.contains(check.trim())) {
                    System.out.println("Match Found: " + fileName);
                    fileNameFound = fileName;
                    break;
                }
            }
            sftp.disconnect();
            session.disconnect();
        }   catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
        }
        return fileNameFound;
    }
    public static String waitForFile(String remoteDir, String check,
                                     int maxAttempts, int waitSeconds) {

        String fileName = null;

        for (int i = 0; i < maxAttempts; i++) {

            fileName = fetchFileName(remoteDir, check);

            if (fileName != null) {
                System.out.println("File found: " + fileName);
                return fileName;
            }

            System.out.println("File not found. Waiting...");

            try {
                Thread.sleep(waitSeconds * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return null;
    }
    /*public static String downloadFile(
            String remoteDir,
            String fileName,
            String localDir) {

        String localFilePath = localDir + File.separator + fileName;

        String host = ConfigReader.getWinScpProperty("sftp.host");
        int port = Integer.parseInt(
                ConfigReader.getWinScpProperty("sftp.port").trim());
        String username = ConfigReader.getWinScpProperty("sftp.username");
        String password = ConfigReader.getWinScpProperty("sftp.password");

        Session session = null;
        ChannelSftp channel = null;

        try {
            JSch jsch = new JSch();

            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();

            String remoteFilePath = remoteDir + "/" + fileName;

            channel.get(remoteFilePath, localFilePath);

            System.out.println("Downloaded Successfully: " + localFilePath);

            return localFilePath;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to download file: " + fileName, e);
        } finally {
            if (channel != null)
                channel.disconnect();

            if (session != null)
                session.disconnect();
        }
    }*/
    public static String downloadFile(
            String remoteDir,
            String filePrefix,
            String localDir) {

        String host = ConfigReader.getWinScpProperty("sftp.host");
        int port = Integer.parseInt(
                ConfigReader.getWinScpProperty("sftp.port").trim());
        String username = ConfigReader.getWinScpProperty("sftp.username");
        String password = ConfigReader.getWinScpProperty("sftp.password");

        Session session = null;
        ChannelSftp channel = null;

        try {
            JSch jsch = new JSch();

            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();

            @SuppressWarnings("unchecked")
            Vector<ChannelSftp.LsEntry> files = channel.ls(remoteDir);

            ChannelSftp.LsEntry latestFile = files.stream()
                    .filter(file -> !file.getAttrs().isDir())
                    .filter(file -> file.getFilename().startsWith(filePrefix))
                    .max(Comparator.comparingInt(
                            file -> file.getAttrs().getMTime()))
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "No matching files found in " + remoteDir));

            String fileName = latestFile.getFilename();

            String localFilePath =
                    localDir + File.separator + fileName;

            String remoteFilePath =
                    remoteDir + "/" + fileName;

            channel.get(remoteFilePath, localFilePath);

            System.out.println(
                    "Latest file downloaded: " + fileName);

            return localFilePath;
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to download latest file", e);
        } finally {
            if (channel != null) {
                channel.disconnect();
            }

            if (session != null) {
                session.disconnect();
            }
        }
    }
}
