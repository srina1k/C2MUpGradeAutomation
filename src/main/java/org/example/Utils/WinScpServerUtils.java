package org.example.Utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File;


public class WinScpServerUtils {
    public static void uploadFile(String localFilePath, String remoteFilePath) {
        String host = ConfigReader.getWinScpProperty("sftp.host");
        int port = Integer.parseInt(ConfigReader.getWinScpProperty("sftp.port").trim());
        String username = ConfigReader.getWinScpProperty("sftp.username");
        String password = ConfigReader.getWinScpProperty("sftp.password");

        Session session = null;
        ChannelSftp channel = null;

        try {
            System.out.println("Uploading file:");
            System.out.println("Local : " + localFilePath);
            System.out.println("Remote: " + remoteFilePath);

            // verify local file
            File file = new File(localFilePath);
            System.out.println("Local file exists? " + file.exists());

            JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            String remoteDir = remoteFilePath.substring(0, remoteFilePath.lastIndexOf('/'));
            channel.cd(remoteDir);
            channel.put(localFilePath, remoteFilePath, ChannelSftp.OVERWRITE);

            System.out.println("File upload successful.");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("File upload failed: " + e.getMessage(), e);

        } finally {
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
        }
    }
}
