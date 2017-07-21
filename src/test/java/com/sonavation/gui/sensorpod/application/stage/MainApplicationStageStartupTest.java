package com.sonavation.gui.sensorpod.application.stage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.sonavation.gui.sensorpod.application.model.Session;
import com.sonavation.gui.sensorpod.application.ui.model.StatusText;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Main stage test.
 */
@Ignore
public class MainApplicationStageStartupTest extends StageTestBase {

    /**
     * Tests application startup.
     * 
     * @throws Exception If testing application startup fails.
     */
    @Test
    @Ignore
    public void testStartup() throws Exception{
        
        // Wait a second for app to startup and test to ensure app is locked
        sleep(1, SECONDS);
        assertTrue(Session.isUiLocked());

        // Wait 6 seconds for application to be connected
        sleep(6, SECONDS);
        assertFalse(Session.isUiLocked());
        
        // Test that the label has the correct text
        testStatusLabel(StatusText.SETTINGS_GET_SUCCESS.getStatus());
        
        // Test status and green image success.
        testGreenSuccessStatus();
    }
}
