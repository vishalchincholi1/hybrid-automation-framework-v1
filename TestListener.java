package com.automation.listeners;

import com.automation.drivers.DriverManager;
import com.automation.utilities.ScreenshotUtility;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestNG Listener for handling test events
 * Provides logging and screenshot capture functionality
 */
public class TestListener implements ITestListener {
    
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Starting test: " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test passed: " + result.getMethod().getMethodName());
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test failed: " + result.getMethod().getMethodName());
        System.out.println("Failure reason: " + result.getThrowable().getMessage());
        
        // Capture screenshot on failure
        try {
            if (DriverManager.getDriver() != null) {
                String screenshotPath = ScreenshotUtility.captureFailureScreenshot(
                    DriverManager.getDriver(), 
                    result.getMethod().getMethodName()
                );
                
                if (screenshotPath != null) {
                    System.setProperty("screenshot.path", screenshotPath);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test skipped: " + result.getMethod().getMethodName());
        System.out.println("Skip reason: " + result.getThrowable().getMessage());
    }
}
