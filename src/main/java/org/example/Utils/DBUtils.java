package org.example.Utils;

import java.io.InputStream;
import java.sql.*;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtils {
    private static Properties props = new Properties();
    static{
        try (InputStream Is = DBUtils.class.getClassLoader().getResourceAsStream("db.properties")){
            props.load(Is);
        } catch (Exception e){
            throw new RuntimeException("Unable to load db.properties", e);
        }
    }

    public static Connection getConnection() {

        try {
            String url = "jdbc:oracle:thin:@//" + props.getProperty("db.host") + ":" + props.getProperty("db.port") + "/" + props.getProperty("db.serviceName");
            //System.out.println("JDBC URL = " + url);
            return DriverManager.getConnection(url, props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (Exception e) {
            throw new RuntimeException("DB connection failed", e);
        }
    }

    public static boolean UpdateQuery(String query) throws SQLException {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement()) {
             int rowsUpdated = stmt.executeUpdate(query);
             System.out.println("Rows updated: " + rowsUpdated);
             return rowsUpdated > 0;
        } catch (SQLException e){
            throw new RuntimeException("Query execution failed: " + e.getMessage(), e);
        }
    }


    public static String getSingleDate(String query, String colname){

        String data = null;
        try {
            ResultSet rs = executeSelectQueryRS(query);
            if (rs != null && rs.next()){
                data = rs.getString(colname);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }
    private static ResultSet executeSelectQueryRS(String query) throws SQLException {
        Statement stmt = getConnection().createStatement();
        return stmt.executeQuery(query);
    }



    public static boolean executeSelectQuery(String query) throws SQLException {
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)){

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++){
                System.out.printf("%-25s", metaData.getColumnName(i));
            }
            System.out.println();
            System.out.println("--------------------------");
            while (rs.next()){
                for (int i = 1; i <= columnCount; i++){
                    System.out.printf("%-25s", rs.getString(i));
                }
            }
            System.out.println();
            return rs.next();

        } catch (SQLException e){
            throw new RuntimeException("Query execution failed", e);
        }
    }
}
