package pers.pdfstuff.pdfmerger;

import java.io.File;
import java.util.logging.Logger;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javafx.application.Application;
import javafx.stage.Stage;
import pers.pdfstuff.pdfmerger.commons.core.Config;
import pers.pdfstuff.pdfmerger.commons.core.LogsCenter;
import pers.pdfstuff.pdfmerger.logic.LogicManager;
import pers.pdfstuff.pdfmerger.model.ModelManager;
import pers.pdfstuff.pdfmerger.ui.UiManager;

public class PdfMerger extends Application {
    private final Logger logger = LogsCenter.getLogger(PdfMerger.class);
    
    protected Config config;
    protected LogicManager logic;
    protected ModelManager model;
    protected UiManager ui;
    
    public void init() throws Exception {
        super.init();
        logger.info("=============== Initializing Application ===============");
        config = new Config();
        
        model = new ModelManager(getParameters().getRaw());
        
        logic = new LogicManager(model);
        
        ui = new UiManager(logic, config);
    }
    
    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting application...");
        ui.start(primaryStage);
    }
    public static void main(String args[]) {
        launch(args);
    }

    public void createNew() {
        PDDocument document = null;
        try {
            String filename = "test.pdf";
            document = new PDDocument();
            PDPage blankPage = new PDPage();
            document.addPage(blankPage);
            document.save(filename);
        } catch (Exception e) {

        }
    }
}
