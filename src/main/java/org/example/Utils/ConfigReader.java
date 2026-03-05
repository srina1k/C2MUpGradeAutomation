package org.example.Utils;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.io.InputStream;
public class ConfigReader {
    //private static final Logger logger= LogManager.getLogger(ConfigReader.class);
    private static final Properties properties = new Properties();
    private static String activeEnv;
    static Properties winproperties = new Properties();
    static Properties DbProperties = new Properties();
    static {
        System.out.println("All loaded config keys:" + properties.keySet());
        System.out.println("base.url from ConfigReader:" + get("base.url"));
        System.out.println("ConfigReader:Starting initialization ===");
        try {
            loadProperties("config/common.properties");
            activeEnv = System.getProperty("env");
            if (activeEnv == null || activeEnv.trim().isEmpty()) {
                activeEnv = "dev";
                System.out.println("No environment provided defaulting to dev");
            } else {
                System.out.println("Environment detected via testng:" + activeEnv);
            }
            System.out.println("Loading common properties..");
            String envFile = "config/" + activeEnv + ".properties";
            System.out.println("Loading environment file:" + envFile);
            loadProperties(envFile);
            System.out.println("Configuration loaded successfully for env:" + activeEnv);
            System.out.println("browser=" + get("browser"));
            System.out.println("base.url=" + get("base.url"));
            System.out.println("username=" + get("username"));
            loadWinscpProperties("config/winscp.properties");
            //loadDBProperties("config/db.properties");
        } catch (Exception e) {
            System.err.println("!!! Config loading failed" + e.getMessage());
            throw new RuntimeException("Failed to Initialize ConfigReader", e);
        }
    }
    private static void loadProperties(String path) throws Exception {
        InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new RuntimeException("Properties file not found:" + path);
        }
        Properties temp = new Properties();
        temp.load(is);
        properties.putAll(temp);
        System.out.println("Successfully loaded:" + path);
    }
    private static void loadWinscpProperties(String path) throws Exception{
        InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(path);
        if(is==null){
            System.out.println("winscp properties not found in classpath");
            return;
        }
        winproperties.load(is);
        is.close();
    }
//    private static void loadDBProperties(String path) throws Exception{
//        InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(path);
//        if(is==null){
//            System.out.println("DB Properties not found in classpath");
//            return;
//        }
//        DbProperties.load(is);
//        is.close();
//    }
    public static String get(String key) {
        String envKey = "TEST_" + key.toUpperCase();
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.trim().isEmpty()) {
            System.out.println("Using" + key + "from environment variable:" + envKey);
            return envValue.trim();
        }
        String value = properties.getProperty(key);
        if (value == null) {
            System.out.println("Config key missing" + key);
        }
        return value != null ? value.trim() : null;
    }
    public static String getWinScpProperty(String key){
        return winproperties.getProperty(key);
    }
    public static String getEnv() {
        return activeEnv;
    }
//    public static String getDBproperty(String key){
//        return DbProperties.getProperty(key);
//    }
    public static void overrideEnvFromTestNG(String envFromSuite) {
        if (envFromSuite == null || envFromSuite.trim().isEmpty()) {
            return;
        }
        String oldEnv = activeEnv;
        activeEnv = envFromSuite.trim();
        System.out.println("ConfigReader override from testng.xml:" + oldEnv + "->" + activeEnv);
        try {
            String envFile = "config/" + activeEnv + ".properties";
            System.out.println("Reloading after override:" + envFile);
            loadProperties(envFile);
        } catch (Exception e) {
            System.err.println("Reload after override failed:" + e.getMessage());
        }
    }
}