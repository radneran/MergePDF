package pers.pdfstuff.pdfmerger.ui;

import java.io.File;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pers.pdfstuff.pdfmerger.commons.core.Config;
import pers.pdfstuff.pdfmerger.commons.core.EventsCenter;
import pers.pdfstuff.pdfmerger.commons.core.LogsCenter;
import pers.pdfstuff.pdfmerger.commons.events.NewCommandEvent;
import pers.pdfstuff.pdfmerger.logic.Logic;
import pers.pdfstuff.pdfmerger.logic.commands.MergeCommand;

public class MainWindow extends UiPart<Region> {

    private static final String FXML = "MainWindow.fxml";
    private static final double MIN_WIDTH = 340;
    private static final double MIN_HEIGHT = 340;
    private static final Logger logger = LogsCenter.getLogger(MainWindow.class);;

    private Stage primaryStage;
    private Logic logic;
    private DocumentListPanel docListPanel;
    private StatusBarFooter statusBarFooter;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane docListPanelPlaceholder;

    @FXML
    private Button mergeButton;
    
    @FXML
    private Button saveButton;
        
    @FXML
    private StackPane statusbarPlaceholder;
    
    @FXML
    private RadioButton setCompress;

    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML);
        logger.info("initialising main window");

        this.primaryStage = primaryStage;
        this.logic = logic;

        setTitle(Config.appName);
        setWindowMinSize();

        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
    }

    private void setTitle(String appName) {
        primaryStage.setTitle(appName);
    }

    public void fillInnerParts() {
        logger.info("Document List Size: " + logic.getSortedDocList().size());
        
        docListPanel = new DocumentListPanel(logic.getSortedDocList());
        docListPanelPlaceholder.getChildren().add(docListPanel.getRoot());
        
        statusBarFooter = new StatusBarFooter();
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
        
        setCompress = new RadioButton();
        
        setButtonPressedEventHandler();
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    public void show() {
        primaryStage.show();
    }

    public void hide() {
        primaryStage.hide();
    }

    public void syncWithMasterDocumentList() {
        docListPanel = new DocumentListPanel(logic.getSortedDocList());
        docListPanelPlaceholder.getChildren().set(0, docListPanel.getRoot());
    }

    private void setButtonPressedEventHandler() {
        mergeButton.setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                logger.info("Merge button pressed");
                EventsCenter.getInstance().post(new NewCommandEvent(new MergeCommand()));
                event.consume();
            }

        });
        
        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                logger.info("Save As button pressed");
                FileChooser chooser = new FileChooser();
                File saveFile = chooser.showSaveDialog(primaryStage);
                Config.updateExactSaveLocation(saveFile.getAbsolutePath());
                event.consume();
            }

        });
    }

    public DocumentListPanel getDocListPanel() {
        return docListPanel;
    }
    
    public void bringWindowToForeground() {
        logger.info("Bringing window to foreground.");
        primaryStage.toFront();
    }
    
    public StatusBarFooter getStatusBarFooter() {
        return statusBarFooter;
    }
   
}
