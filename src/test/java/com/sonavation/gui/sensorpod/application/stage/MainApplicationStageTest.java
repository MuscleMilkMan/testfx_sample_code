package com.sonavation.gui.sensorpod.application.stage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.sonavation.gui.sensorpod.application.model.Session;
import com.sonavation.gui.sensorpod.application.ui.control.Label;
import com.sonavation.gui.sensorpod.application.ui.model.InitialFileNamePrefix;
import com.sonavation.gui.sensorpod.application.ui.model.StatusText;
import com.sonavation.sdk.sensorpod.utility.FileUtility;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.List;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

/**
 * Main Stage Test.
 */
public class MainApplicationStageTest extends StageTestBase {

    /**
     * Tests enroll functionality. 
     */
    @Test
    @Ignore
    public void testEnroll() throws Exception {
        
        this.commandTest("Enroll", StatusText.ENROLL_STARTED, StatusText.ENROLL_SUCCESS);
    }
    
    /**
     * Test verify functionality.
     */
    @Test
    @Ignore
    public void testVerify() throws Exception {
        
        this.commandTest("Verify", StatusText.VERIFY_STARTED, StatusText.VERIFY_SUCCESS);
    }
    
    /**
     * Test get image functionality.
     */
    @Test
    @Ignore
    public void testGetImage() throws Exception {
        
        this.commandTest("Get Image", StatusText.GET_IMAGE_STARTED, StatusText.GET_IMAGE_SUCCESS);
    }
    
    /**
     * Command test setup.
     * 
     * @param buttonName
     * @param statusTextStarted
     * @param statusTextSuccess
     * @throws Exception if image comparison fails
     */
    private void commandTest(String buttonName, StatusText statusTextStarted,
            StatusText statusTextSuccess) throws Exception{
        
        // Checks for initial update window and selects cancel if present.
        runOnceOnStartup();
        
        waitForUIUnlock();

        // Click a button
        click(buttonName, MouseButton.PRIMARY);
        
        // Verify UI is locked.
        assertTrue(Session.isUiLocked());
        
        // Verify status has changed to started.
        testStatusLabel(statusTextStarted.getStatus());
        
        // Waits for the finger and loops.
        waitForFinger(statusTextSuccess);
        
        // Wait for success.
        waitForUIUnlock();
        
        // Verify UI is unlocked after completion. 
        assertFalse(Session.isUiLocked());
        
        // Test status and green image success.
        testGreenSuccessStatus();

        // Verify status has changed to success.
        testStatusLabel(statusTextSuccess.getStatus());
        
        // Verify image matches whale sensor.
        testImageResolution();
    }
    
    /**
     * Reinitialize test.
     */
    @Test
    @Ignore
    public void reinitializeTest() throws Exception {
        
        // Checks for initial update window and selects cancel if present.
        runOnceOnStartup();
        
        waitForUIUnlock();
        
        // Select Reinitialize from Device menu. 
        selectMenuItem("_Device", "Reinitialize");
        
        // Verify UI is locked.
        assertTrue(Session.isUiLocked());
        
        // Verify status has changed to success.
        testStatusLabel(StatusText.REINITIALIZE_STARTED.getStatus());
        
        waitForUIUnlock();
        
        // Verify UI is unlocked after completion. 
        assertFalse(Session.isUiLocked());
        
        // Verify status and image has changed to success.
        testStatusLabel(StatusText.REINITIALIZE_SUCCESS.getStatus());
        testGreenSuccessStatus();

        // Check to verify Active Profile is set to Unknown.
        selectMenuItem("Advanced", "Info");
        verifyDialogue("Unknown");
    }
    
    /**
     * Factory reset test.
     */
    @Test
    @Ignore
    public void factoryResetTest() throws Exception {
        
        // Checks for initial update window and selects cancel if present.
        runOnceOnStartup();
        
        sleep(5, SECONDS);
        
        // Select Factory Reset from Device menu.
        selectMenuItem("_Device", "Perform Factory Reset" + ELLIPSIS);

        // Verify confirm factory reset window populates.
        verifyDialogue("Are you sure you want to perform a Factory Reset?");
        click("OK", MouseButton.PRIMARY);
        
        // Status changed to reset started
        testStatusLabel(StatusText.FACTORY_RESET_STARTED.getStatus());
        
        // Verify UI is locked.
        assertTrue(Session.isUiLocked());
        
        sleep(8, SECONDS);
                
        // Verify UI is unlocked after completion. 
        assertFalse(Session.isUiLocked());
        
        // Status changed to reset successful
        testStatusLabel(StatusText.FACTORY_RESET_SUCCESS.getStatus());
        testGreenSuccessStatus(); 
    }
    
    /**
     * Drag and Drop test.
     */
    @Test
    @Ignore
    public void dragAndDropTest() throws Exception {
        
        // Checks for initial update window and selects cancel if present.
        runOnceOnStartup();
        
        // Runs the Get Image test in its entirety. 
        this.commandTest("Get Image", StatusText.GET_IMAGE_STARTED, StatusText.GET_IMAGE_SUCCESS);
        
        // Drags and drops fingerprint image using geometric vector length(x) and direction(y).     
        drag("#fingerprintImageView").via(600, 90).drop();
        
        sleep(1, SECONDS);  
        
        String desktop = System.getProperty("user.home") + "/Desktop";
        
        Label serialNumberLabel = (Label) find("#fingerprintImageView");
        
        // Create character sequence that match in finding files
        String charSequence = 
                InitialFileNamePrefix.IMAGE_FILE.get() + serialNumberLabel.getTextFromState();
        
        // Find the droids we're looking for
        List<File> files = FileUtility.findFiles(desktop, charSequence);
        
        // Ensures fingerprint files are there.
        assertFalse(files.isEmpty());
        
        // Iterate over matching files and attempt to delete them
        for (File f : files){
        // Attempt to delete files (ignores delete failures)
        f.delete();
        }
    }
     
    /**
     * Toolbar help test.
     */
    @Test
    @Ignore
    public void toolbarHelpTest() throws Exception{
        
        // Checks for initial update window and selects cancel if present.
        runOnceOnStartup();

        // Select Check for Updates in the Help menu.
        selectMenuItem("_Help", "Check for Updates" + ELLIPSIS);
     
        // Verify application check success status message.
        testStatusLabel(StatusText.APPLICATION_CHECK_SUCCESS.getStatus());
        testGreenSuccessStatus(); 
     
        // Select cancel from update menu.
        click("Cancel", MouseButton.PRIMARY);
     
        // Verify update status message.
        testStatusLabel(StatusText.UPDATE_SUCCESS.getStatus());
        testGreenSuccessStatus(); 
     
        // Select Report Bug in the Help menu.
        selectMenuItem("_Help", "Report Bug" + ELLIPSIS);
     
        // Wait and escape out of email selection window
        sleep(3, SECONDS);
        push(KeyCode.ESCAPE);
     
        // Select legal in the Help menu and exit.
        selectMenuItem("_Help", "Legal" + ELLIPSIS);
     
        // Reload the legal page from a right click
        click(MouseButton.SECONDARY);
        sleep(1, SECONDS);
        click("Reload page", MouseButton.PRIMARY);
        closeCurrentWindow();

        // Select About in the Help menu and exit.
        selectMenuItem("_Help", "About" + ELLIPSIS);
        sleep(2, SECONDS);
        verifyDialogue("SonicTouch Evaluation Application");
        closeCurrentWindow();
    } 
    
    /**
     * Toolbar view tests.
     */
    @Test
    @Ignore
    public void toolbarViewTests(){
        
        // Checks for initial update window and selects cancel if present.
        runOnceOnStartup();
     
        // Select log in the View menu.
        selectMenuItem("_View", "Log" + ELLIPSIS);
     
        // Verify Save and Clear buttons work. 
        click("Save");
        sleep(2, SECONDS);
        push(KeyCode.ESCAPE);
        sleep(2, SECONDS);
        click("Clear");
        closeCurrentWindow();
    }
    
    /**
     * Info tab tests.
     */
    @Test
    @Ignore
    public void infoTabTests(){
      
        // Checks for initial update window and selects cancel if present.
        runOnceOnStartup();
        
        // Select the advanced tab
        click("Advanced");   
        sleep(1, SECONDS);
        click("OK");
        sleep(1, SECONDS);
     
        // Tests value for labels.
        testLabel("firmwareVersionValueLabel", "1.0.125");
        testLabel("sensorSizeValueLabel", "120x120");
        testLabel("serialNumberValueLabel", "FREERICKSANCHEZ");
        testLabel("enrollmentCountValueLabel", "0");
        testLabel("activeProfileValueLabel", "Unknown");
        testLabel("isCalibratedValueLabel", "No");
    }
    
    /**
     * Options tab tests.
     * 
     * @throws Exception if green status image doesn't match.
     */
    @Test
    @Ignore
    public void optionsTabTests() throws Exception{
        
        // Checks for initial update window and selects cancel if present.
        runOnceOnStartup();  
        
        // Select options tab under advanced.
        click("Advanced");   
        sleep(1, SECONDS);
        click("OK");
        click("Options");
        
        // Apply a mask.
        click("#optionsMaskCheckbox");
        click("Apply");
        testStatusLabel(StatusText.OPTIONS_STARTED.getStatus());
        waitForUIUnlock();
        testStatusLabel(StatusText.OPTIONS_SUCCESS.getStatus());
        testGreenSuccessStatus(); 

        // Apply contrast enhance.
        click("#optionsContrastEnhanceCheckbox");
        click("Apply");
        testStatusLabel(StatusText.OPTIONS_STARTED.getStatus());
        waitForUIUnlock();
        testStatusLabel(StatusText.OPTIONS_SUCCESS.getStatus());
        testGreenSuccessStatus(); 
    }
   
    /**
     * Developer menu tests.
     * 
     * @throws Exception if green status image doesn't match.
     */
    @Test
    @Ignore
    public void developerMenuTests() throws Exception{
        
        // Checks for initial update window and selects cancel if present.
        runOnceOnStartup(); 
        
        // Install Firmware from file.
        selectMenuItem("Developer", "Install Firmware from File" + ELLIPSIS);
        testStatusLabel(StatusText.FIRMWARE_CHECK_DRIVER_STARTED.getStatus());
        sleep(10, SECONDS);
        // TestFX bugs out with the use of a Colon, found alternative method. 
        type("C").press(KeyCode.SHIFT).press(KeyCode.SEMICOLON).
            release(KeyCode.SEMICOLON).release(KeyCode.SHIFT);
        type("\\Develop\\sonictouch-firmware-1.0.125.encdfu");
        push(KeyCode.ENTER);
        click("OK");
        testStatusLabel(StatusText.FIRMWARE_UPDATE_STARTED.getStatus());
        sleep(60, SECONDS);
        testStatusLabel(StatusText.FIRMWARE_UPDATE_SUCCESS.getStatus());
        click("OK");
        
        // Install default profile.
        selectMenuItem("Developer", "Install Default Profile" + ELLIPSIS);
        click("#profileTextArea");
        type("MCU00005,16200000,4,121,121,121,121,121,121,121,121,121,121,121,121,"
            + "121,121,121,121,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,15,0");
        click("Install");
        testStatusLabel(StatusText.SET_PROFILE_STARTED.getStatus());
        waitForUIUnlock();
        testStatusLabel(StatusText.SET_PROFILE_SUCCESS.getStatus());
        testGreenSuccessStatus();
        
        // Verify default profile installed.
        click("Advanced");   
        sleep(1, SECONDS);
        click("OK");
        testLabel("activeProfileValueLabel", "MCU00005");
    }
    
}
