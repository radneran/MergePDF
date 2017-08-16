package pers.pdfstuff.pdfmerger;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import javafx.application.Application;
import javafx.stage.Stage;
import pers.pdfstuff.pdfmerger.commons.core.ApplicationInstanceListener;
import pers.pdfstuff.pdfmerger.commons.core.ApplicationInstanceManager;
import pers.pdfstuff.pdfmerger.commons.core.Config;
import pers.pdfstuff.pdfmerger.commons.core.EventsCenter;
import pers.pdfstuff.pdfmerger.commons.core.LogsCenter;
import pers.pdfstuff.pdfmerger.commons.events.DuplicateInstanceEvent;
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
        List<String> rawArgs = getParameters().getRaw();
        
        model = new ModelManager(rawArgs);

        logic = new LogicManager(model);

        if (!ApplicationInstanceManager.registerInstance(rawArgs)) {
            System.out.println("Another instance of this application is already running.  Exiting.");
            System.exit(0);
        }
       
        ApplicationInstanceManager.setApplicationInstanceListener(new ApplicationInstanceListener() {
            @Override
            public void newInstanceCreated(String message) {
                logger.info("New Instance detected.");
                EventsCenter.getInstance().post(new DuplicateInstanceEvent(message));
            }
        });

        ui = new UiManager(logic);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting application...");
        ui.start(primaryStage);
    }
    
    @Override
    public void stop() {
        try {
            ApplicationInstanceManager.closeSocket();
        } catch (IOException e) {
            logger.severe("Error while closing socket");
            e.printStackTrace();
        }
        //cleanUp();
    }

   /* private void cleanUp() {
        if(Config.saveLocationSet) {
            File savedFile = new File(Config.getSaveLocation());
            if (savedFile.exists()) {
                
            }
        }
    }*/

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
