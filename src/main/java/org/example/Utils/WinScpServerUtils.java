package org.example.Utils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class WinScpServerUtils {
    public static void uploadFile(String localFilePath, String remoteFilePath){
        String Host = ConfigReader.getWinScpProperty("sftp.host");
        int port = Integer.parseInt(ConfigReader.getWinScpProperty("sftp.port").trim());
        String username = ConfigReader.getWinScpProperty("sftp.username");
        String password = ConfigReader.getWinScpProperty("sftp.password");
        Session session = null;
        ChannelSftp channel =null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, Host, port);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            channel.put(localFilePath,remoteFilePath);
            try {
                channel.put(localFilePath, remoteFilePath, ChannelSftp.OVERWRITE);
            } catch (com.jcraft.jsch.SftpException ex) {
                if (ex.id == ChannelSftp.SSH_FX_FAILURE || ex.id == ChannelSftp.SSH_FX_PERMISSION_DENIED) {
                    System.out.println("Upload succeeded but server refused to set permissions/timestamp. Ignoring...");
                } else {
                    throw ex;
                }
            }
        } catch (Exception e){
            throw new RuntimeException("File upload Failed", e);
        } finally {
            if (channel != null) channel.disconnect();
            if (session != null) session.disconnect();
        }
    }
}
