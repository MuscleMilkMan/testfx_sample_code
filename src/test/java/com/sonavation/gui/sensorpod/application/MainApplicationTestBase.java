package com.sonavation.gui.sensorpod.application;

import com.sonavation.gui.sensorpod.application.stage.model.StageDimension;

import org.loadui.testfx.GuiTest;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Main application test base.
 */
public class MainApplicationTestBase extends GuiTest {
    
    /**
     * Application parent.
     */
    protected Parent parent;

    /**
     * Get root node.
     * <p>
     * Gets root node from MainApplication stage.
     */    
    @Override
    protected Parent getRootNode() {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(MainApplicationTestBase.class.getResource(
                    FxmlPath.MAIN_APPLICATION.getFxmlPath()));
        } catch (IOException ex) { }
        
        stage.setWidth(StageDimension.MAIN_APPLICATION_WIDTH.getDimension());
        stage.setHeight(StageDimension.MAIN_APPLICATION_HEIGHT.getDimension());
        stage.centerOnScreen();
        
        return parent;
    }

}
