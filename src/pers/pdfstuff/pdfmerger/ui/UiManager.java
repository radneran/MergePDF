package pers.pdfstuff.pdfmerger.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pers.pdfstuff.pdfmerger.commons.core.ComponentManager;
import pers.pdfstuff.pdfmerger.commons.core.Config;
import pers.pdfstuff.pdfmerger.commons.core.EventsCenter;
import pers.pdfstuff.pdfmerger.commons.core.LogsCenter;
import pers.pdfstuff.pdfmerger.commons.events.DeleteEvent;
import pers.pdfstuff.pdfmerger.commons.events.DocumentListChangedEvent;
import pers.pdfstuff.pdfmerger.logic.Logic;

public class UiManager extends ComponentManager implements Ui {

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private Logic logic;
    private Config config;
    private MainWindow mainWindow;

    public UiManager(Logic logic, Config config) {
        super();
        this.logic = logic;
        this.config = config;
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            mainWindow = new MainWindow(primaryStage, logic, config);
            mainWindow.show();
            mainWindow.fillInnerParts();
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Fatal error when initialising");
        }
    }

    @Override
    public void stop() {
        mainWindow.hide();
    }

    @Subscribe
    public void handleDocumentListChangedEvent(DocumentListChangedEvent event) {
        mainWindow.syncWithMasterDocumentList();
    }
}
