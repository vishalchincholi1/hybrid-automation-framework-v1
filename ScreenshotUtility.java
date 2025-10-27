package com.automation.utilities;

import com.automation.config.ConfigManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Screenshot Utility for capturing and managing screenshots
 * Supports automatic screenshot capture on test failures
 */
public class ScreenshotUtility {
    private static final String SCREENSHOT_FORMAT = "png";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
    
    /**
     * Capture screenshot and save to file
     * @param driver WebDriver instance
     * @param testName Test name for file naming
     * @return Screenshot file path
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        if (!ConfigManager.isScreenshotsEnabled()) {
            return null;
        }
        
        try {
            // Create screenshots directory if it doesn't exist
            String screenshotsPath = ConfigManager.getScreenshotsPath();
            File screenshotsDir = new File(screenshotsPath);
            if (!screenshotsDir.exists()) {
                screenshotsDir.mkdirs();
            }
            
            // Generate unique filename
            String timestamp = LocalDateTime.now().format(DATE_FORMAT);
            String fileName = String.format("%s_%s.%s", testName, timestamp, SCREENSHOT_FORMAT);
            String filePath = screenshotsPath + File.separator + fileName;
            
            // Capture screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(filePath);
            
            // Copy screenshot to destination
            FileUtils.copyFile(sourceFile, destFile);
            
            System.out.println("Screenshot captured: " + filePath);
            return filePath;
            
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Capture screenshot with default naming
     * @param driver WebDriver instance
     * @return Screenshot file path
     */
    public static String captureScreenshot(WebDriver driver) {
        return captureScreenshot(driver, "screenshot");
    }
    
    /**
     * Capture screenshot for failed test
     * @param driver WebDriver instance
     * @param testMethodName Test method name
     * @return Screenshot file path
     */
    public static String captureFailureScreenshot(WebDriver driver, String testMethodName) {
        return captureScreenshot(driver, "FAILED_" + testMethodName);
    }
    
    /**
     * Clean up old screenshots (older than specified days)
     * @param daysToKeep Number of days to keep screenshots
     */
    public static void cleanupOldScreenshots(int daysToKeep) {
        try {
            String screenshotsPath = ConfigManager.getScreenshotsPath();
            File screenshotsDir = new File(screenshotsPath);
            
            if (!screenshotsDir.exists()) {
                return;
            }
            
            File[] files = screenshotsDir.listFiles();
            if (files == null) {
                return;
            }
            
            long cutoffTime = System.currentTimeMillis() - (daysToKeep * 24 * 60 * 60 * 1000L);
            
            for (File file : files) {
                if (file.isFile() && file.lastModified() < cutoffTime) {
                    if (file.delete()) {
                        System.out.println("Deleted old screenshot: " + file.getName());
                    }
                }
            }
            
        } catch (Exception e) {
            System.err.println("Failed to cleanup old screenshots: " + e.getMessage());
        }
    }
    
    /**
     * Get screenshots directory size in MB
     * @return Directory size in MB
     */
    public static double getScreenshotsDirSize() {
        try {
            String screenshotsPath = ConfigManager.getScreenshotsPath();
            File screenshotsDir = new File(screenshotsPath);
            
            if (!screenshotsDir.exists()) {
                return 0.0;
            }
            
            long sizeInBytes = FileUtils.sizeOfDirectory(screenshotsDir);
            return sizeInBytes / (1024.0 * 1024.0); // Convert to MB
            
        } catch (Exception e) {
            System.err.println("Failed to calculate screenshots directory size: " + e.getMessage());
            return 0.0;
        }
    }
    
    /**
     * Create screenshot subdirectory for test suite
     * @param suiteName Test suite name
     * @return Subdirectory path
     */
    public static String createSuiteScreenshotDir(String suiteName) {
        try {
            String screenshotsPath = ConfigManager.getScreenshotsPath();
            String suiteDir = screenshotsPath + File.separator + suiteName;
            
            File dir = new File(suiteDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            
            return suiteDir;
            
        } catch (Exception e) {
            System.err.println("Failed to create suite screenshot directory: " + e.getMessage());
            return ConfigManager.getScreenshotsPath();
        }
    }
}
