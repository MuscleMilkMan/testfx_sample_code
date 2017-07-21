package com.sonavation.gui.sensorpod.application.stage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import com.sonavation.gui.sensorpod.application.MainApplicationTestBase;
import com.sonavation.gui.sensorpod.application.model.Session;
import com.sonavation.gui.sensorpod.application.ui.model.StatusText;
import com.sonavation.gui.sensorpod.utility.ImageUtility;
import com.sonavation.sdk.sensorpod.protocol.model.SensorType;

import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

/**
 * Stage test base.
 */
public class StageTestBase  extends MainApplicationTestBase {
    
    /**
     * Unicode Ellipsis.
     */
    protected static final String ELLIPSIS = "\u2026";
            
    /**
     * hasRunOnce false until cancel is selected on startup.
     */
    private static boolean hasRunOnce = false;
    
    /**
     * Run Once on Startup command to check for initial update screen.
     */
    protected void runOnceOnStartup(){
        if (!hasRunOnce){
            hasRunOnce = true;
            sleep(2, SECONDS);
            click("Cancel", MouseButton.PRIMARY);
            //click("Cancel", MouseButton.PRIMARY);
        }
    }
   
    /**
     * Tests status and successful image match.
     * 
     * @throws Exception if image doesn't match
     */
    public void testGreenSuccessStatus() throws Exception{
        
        // Get the status image and the green success image.
        ImageView imageViewStatus = (ImageView) find("#imageViewStatus");
        Image greenImage = ImageUtility.loadImageFromPath("/images/status-green.png");
        assertNotNull(imageViewStatus);
        assertNotNull(greenImage);
        
        // Test that status image and green success image match.
        testImagesMatch(imageViewStatus.getImage(), greenImage);
    }
    
    /**
     * Test image resolution.
     * 
     * @throws Exception If sensor not supported.
     */
    public void testImageResolution() throws Exception{
        // Sensor pod type and size. 
        int height = SensorType.getHeight(Session.getSensorType());
        int width = SensorType.getWidth(Session.getSensorType());
        
        // Grabs the fingerprintImage and verifies its existence. 
        ImageView imageViewFingerprint = (ImageView) find("#imageViewFingerprint");
        assertNotNull(imageViewFingerprint);
        
        // Verify's the dimensions match the sensor profiles.
        assertEquals(height, (int) imageViewFingerprint.getImage().getHeight());  
        assertEquals(width, (int) imageViewFingerprint.getImage().getWidth());
    }
    
    /**
     * Tests a label.
     * 
     * @param labelName Label name.
     * @param expectedValue Expected value.
     */
    public void testLabel(String labelName, String expectedValue) {
        
        if (!labelName.startsWith("#")){
            labelName = "#" + labelName;
        }
        Label label = (Label) super.find(labelName);
        verifyThat(label, hasText(expectedValue));
    }
    
    
    /**
     * Tests status and failure image match.
     * 
     * @throws Exception if image doesn't match
     */
    public void testRedFailureStatus() throws Exception{
        
        // Get the status image and the red failure image.
        ImageView imageViewStatus = (ImageView) find("#statusImage");
        Image redImage = ImageUtility.loadImageFromPath("/images/status-red.png");
        assertNotNull(imageViewStatus);
        assertNotNull(redImage);
        
        // Test that status image and red failure image match.
        testImagesMatch(imageViewStatus.getImage(), redImage);
    }

    /**
     * Test that two images match.
     * 
     * @param a Image A.
     * @param b Image B.
     * @throws Exception If image comparison fails.
     */
    public void testImagesMatch(Image a, Image b) throws Exception{
        byte[] aBytes = ImageUtility.getImageBytes(a);
        byte[] bBytes = ImageUtility.getImageBytes(b);
        assertTrue(Arrays.equals(aBytes, bBytes));
    }    
    
    /**
     * Tests statusLabel.
     * 
     * @param statusText
     */
    public void testStatusLabel(String statusText){
        testLabel("statusLabel", statusText);
    }
   
    /**
     * Toolbar menu select.
     * 
     * @param buttonName1
     * @param buttonName2
     */
    public void selectMenuItem(String click1, String click2) {
        
        click(click1, MouseButton.PRIMARY);
        click(click2, MouseButton.PRIMARY);
    }
    
    /**
     * Verify dialogue text.
     * 
     * @param dialogue
     */
        public void verifyDialogue(String dialogue) {
            find(dialogue);
            assertNotNull(dialogue);
        }
        
    /**
     * Waits for a finger detect by testing if status text changes.
     * <p>
     * Timeout occurs after 120 seconds.
     * 
     * @param statusText StatusText to test against. This should be the status text that you expect
     * the StatusLabel to display while you wait for finger detection.
     * @throws TimeoutException If wait for finger detection exceeds the specified timeout.
     */
    protected void waitForFinger(StatusText statusText) throws TimeoutException{
        // Get current system time in milliseconds
        long currentTime = System.currentTimeMillis();
        
        // Get end time as current time plus 120 seconds (120,000 milliseconds)
        long timeLimit = currentTime + 120000;
        
        // Find status label
        Label label = (Label) super.find("#statusLabel");
        
        // Loop while status label equals status text
        // This will cause the application to loop until the status text changes or a timeout
        // throws an exception
        while (!label.getText().equals(statusText.getStatus())){
            
            // Test for timeout
            if (System.currentTimeMillis() > timeLimit){
                throw new TimeoutException("Timeout waiting for finger");
            }
            
            // Sleep half a second
            sleep(500, MILLISECONDS);
        }
    }
    
    /**
     * Application waits while UI loads. 
     */
    public void waitForUIUnlock() {
        
        // Wait 5 seconds for unlock.
        sleep(5, SECONDS);
    } 
}
