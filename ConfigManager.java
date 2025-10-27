package com.automation.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Manager for handling application properties
 * Supports multiple environments and runtime property overrides
 */
public class ConfigManager {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config/config.properties";
    
    static {
        loadProperties();
    }
    
    /**
     * Load properties from configuration file
     */
    private static void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration properties: " + e.getMessage());
        }
    }
    
    /**
     * Get property value with system property override support
     * @param key Property key
     * @return Property value
     */
    public static String getProperty(String key) {
        // Check system properties first (for runtime overrides)
        String systemProperty = System.getProperty(key);
        if (systemProperty != null) {
            return systemProperty;
        }
        
        // Fall back to config file properties
        return properties.getProperty(key);
    }
    
    /**
     * Get property value with default fallback
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value or default
     */
    public static String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return value != null ? value : defaultValue;
    }
    
    /**
     * Get browser configuration
     * @return Browser name
     */
    public static String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    /**
     * Get application URL based on environment
     * @return Application URL
     */
    public static String getApplicationUrl() {
        String environment = getProperty("environment", "dev");
        return getProperty("app.url." + environment);
    }
    
    /**
     * Get implicit wait timeout
     * @return Implicit wait in seconds
     */
    public static int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }
    
    /**
     * Get explicit wait timeout
     * @return Explicit wait in seconds
     */
    public static int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "20"));
    }
    
    /**
     * Check if headless mode is enabled
     * @return true if headless mode is enabled
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }
    
    /**
     * Get database URL
     * @return Database connection URL
     */
    public static String getDatabaseUrl() {
        return getProperty("db.url");
    }
    
    /**
     * Get database username
     * @return Database username
     */
    public static String getDatabaseUsername() {
        return getProperty("db.username");
    }
    
    /**
     * Get database password
     * @return Database password
     */
    public static String getDatabasePassword() {
        return getProperty("db.password");
    }
    
    /**
     * Get reports directory path
     * @return Reports directory path
     */
    public static String getReportsPath() {
        return getProperty("reports.path", "./reports");
    }
    
    /**
     * Get screenshots directory path
     * @return Screenshots directory path
     */
    public static String getScreenshotsPath() {
        return getProperty("screenshots.path", "./screenshots");
    }
    
    /**
     * Check if screenshots are enabled
     * @return true if screenshots are enabled
     */
    public static boolean isScreenshotsEnabled() {
        return Boolean.parseBoolean(getProperty("enable.screenshots", "true"));
    }
}
