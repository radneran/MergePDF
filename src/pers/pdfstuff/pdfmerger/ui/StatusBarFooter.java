package pers.pdfstuff.pdfmerger.ui;

import org.controlsfx.control.StatusBar;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import pers.pdfstuff.pdfmerger.commons.core.Config;

public class StatusBarFooter extends UiPart<Region> {
    
    public static final String FXML = "StatusBarFooter.fxml";
    
    @FXML
    private StatusBar saveLocationStatus;
    
    public StatusBarFooter() {
        super(FXML);
        updateSaveLocation(Config.getSaveLocation());
    }
    
    public void updateSaveLocation(String newSaveLocation) {
        saveLocationStatus.setText(newSaveLocation);
    }

}
