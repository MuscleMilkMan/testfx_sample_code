package com.sonavation.gui.sensorpod.application.stage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.sonavation.gui.sensorpod.application.model.Session;
import com.sonavation.gui.sensorpod.application.ui.model.StatusText;

import org.junit.Ignore;
import org.junit.Test;

import javafx.scene.input.MouseButton;

/**
 * Main stage error tests. 
 */
@Ignore
public class MainApplicationStageErrorTest extends StageTestBase {

    /**
     * Tests failed enroll.
     */
    @Test
    public void testFailedEnroll() throws Exception {
        
        // Checks for initial update window and selects cancel if present.
        runOnceOnStartup();
        
        waitForUIUnlock();

        // Click enroll button without finger.
        click("Enroll", MouseButton.PRIMARY);
        
        // Verify status has changed to enroll started.
        testStatusLabel(StatusText.ENROLL_STARTED.getStatus());
        
        // Verify UI is locked during enroll.
        assertTrue(Session.isUiLocked());
        
        // Sleep Jimmy it will all be over soon.
        sleep(5, SECONDS);
        
        // Verify UI is unlocked after completion. 
        assertFalse(Session.isUiLocked());
        
        // Verify error message matches error.
        verifyDialogue("Unable to create a fingerprint template");
        
        // Find and select error window's OK button. 
        click("OK", MouseButton.PRIMARY);

        // Verify status has changed to an enroll failure. 
        testStatusLabel(StatusText.ENROLL_FAILURE.getStatus());
        
        // Test status and red image failure.
        testRedFailureStatus();
    }
    
    /**
     * Test failed verify template mismatch.
     */
    @Test
    public void testVerifyMismatch() throws Exception {
        
        // Checks for initial update window and selects cancel if present.
        runOnceOnStartup();
        
        waitForUIUnlock();
        
        // Click enroll button with successful enroll.
        click("Enroll", MouseButton.PRIMARY);
        
        // GoToSleepClown.jpeg.
        sleep(1, SECONDS);
        
        // Verify status has changed to enroll started.
        testStatusLabel(StatusText.ENROLL_STARTED.getStatus());
        
        // Wait for successful enroll.
        sleep(10, SECONDS); 
        
        // Verify image matches whale sensor.
        testImageResolution();
        
        // Verify with different finger used from enroll.
        click("Verify", MouseButton.PRIMARY);
        
        // Status changed to Verify Started.
        testStatusLabel(StatusText.VERIFY_STARTED.getStatus());
        
        // Verify UI is locked during execution.
        assertTrue(Session.isUiLocked());
        
        // Wait for error message.
        sleep(6, SECONDS);
        
        // Verify UI is unlocked after completion. 
        assertFalse(Session.isUiLocked());
        
        // Find correct error message. 
        verifyDialogue("Fingerprint template mismatch, score is below threshold");
        
        // Click OK button to close message.
        click("OK", MouseButton.PRIMARY);
        
        // Verify mismatch image
        testImageResolution();
        
        // Status changed to an enroll failure. 
        testStatusLabel(StatusText.VERIFY_FAILURE.getStatus());
        
        // Test status and red image failure.
        testRedFailureStatus();
    }
    
    /**
     * Test failed verify create template.
     */
    @Test
    public void testVerifyCreateTemplate() throws Exception {
        
        // Checks for initial update window and selects cancel if present.
        runOnceOnStartup();
        
        waitForUIUnlock();
        
        // No finger during enroll process.
        click("Enroll", MouseButton.PRIMARY);
        
        // Let's wait.
        sleep(6, SECONDS);
        
        // Verify error message matches error.
        verifyDialogue("Unable to create a fingerprint template");
        
        // Find and select error window's OK button. 
        click("OK", MouseButton.PRIMARY);
        
        // Verify status has changed to an enroll failure. 
        testStatusLabel(StatusText.ENROLL_FAILURE.getStatus());
        
        // Click that Verify button.
        click("Verify", MouseButton.PRIMARY);
        
        // Status changed to Verify Started.
        testStatusLabel(StatusText.VERIFY_STARTED.getStatus());
        
        // Verify UI is locked during execution.
        assertTrue(Session.isUiLocked());
        
        // Sleep tight puppers.
        sleep(3, SECONDS);
        
        // Verify UI is unlocked after completion. 
        assertFalse(Session.isUiLocked());
        
        // Verify error message matches error.
        verifyDialogue("Unable to create a fingerprint template");
        
        // Find and select error window's OK button. 
        click("OK", MouseButton.PRIMARY);
        
        // Status changed to an enroll failure. 
        testStatusLabel(StatusText.VERIFY_FAILURE.getStatus());
        
        // Test status and red image failure.
        testRedFailureStatus();
    }
    
    /**
     * Test verify create template not found.
     */
    @Test
    public void testVerifyTemplateNotFound() throws Exception {
        
        // Checks for initial update window and selects cancel if present.
        runOnceOnStartup();
        
        waitForUIUnlock();
        
        // Click enroll button with successful enroll.
        click("Enroll", MouseButton.PRIMARY);
        
        // Go to sleep clown.
        sleep(1, SECONDS);
        
        // Verify status has changed to enroll started.
        testStatusLabel(StatusText.ENROLL_STARTED.getStatus());
        
        // Wait for successful enroll. 
        sleep(6, SECONDS);
        
        // Test that resolution.
        testImageResolution();
        
        // Perform factory reset.
        selectMenuItem("_Device", "Perform Factory Reset" + ELLIPSIS);
        
        // Just your everyday cat nap.
        sleep(1, SECONDS);
        
        // Confirm factory reset window populates. 
        verifyDialogue("Are you sure you want to perform a Factory Reset?");
        click("OK", MouseButton.PRIMARY);
        
        // Verify status has changed to Factory Reset Started.
        testStatusLabel(StatusText.FACTORY_RESET_STARTED.getStatus());
        
        // Hush little Gogo's don't say a word, Joshie gunna buy you those gains you nerd.
        sleep(5, SECONDS);
        
        // Verify status has changed to Factory Reset Success.
        testStatusLabel(StatusText.FACTORY_RESET_SUCCESS.getStatus());
        
        // Test status and green image success.
        testGreenSuccessStatus();
        
        // Click verify with with finger on sensor.
        click("Verify", MouseButton.PRIMARY);
        
        // Enough time to dream about muscle men.
        sleep(5, SECONDS);
        
        // Verify error message.
        verifyDialogue("The requested fingerprint template was not found");
        
        // Click OK button on new window to close.
        click("OK", MouseButton.PRIMARY);
        
        // Status changed to an enroll failure.
        testStatusLabel(StatusText.VERIFY_FAILURE.getStatus());
        
        // Test status and red image failure.
        testRedFailureStatus();
    }
}
