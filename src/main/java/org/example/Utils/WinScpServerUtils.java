package org.example.Utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File;
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
    public static String fetchFileName() {

        String fileNameFound = null;
        String remoteDir = "/c2m/HOCPayments/in/";
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

                if (fileName.contains("HOCX")) {
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
}
