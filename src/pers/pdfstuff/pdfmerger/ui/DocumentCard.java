package pers.pdfstuff.pdfmerger.ui;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class DocumentCard extends UiPart<Region>{
    
    private static final String FXML = "DocumentCard.fxml";
    
    @FXML
    private Label filename;
    @FXML
    private Label id;
    
    public DocumentCard(File file, Integer index) {
        super(FXML);
        filename.setText(file.getName());
        id.setText(index.toString() + " ");
    }

}
