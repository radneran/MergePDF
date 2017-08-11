package pers.pdfstuff.pdfmerger.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pers.pdfstuff.pdfmerger.PdfMerger;
import pers.pdfstuff.pdfmerger.commons.core.ComponentManager;
import pers.pdfstuff.pdfmerger.commons.core.Config;
import pers.pdfstuff.pdfmerger.commons.core.LogsCenter;
import pers.pdfstuff.pdfmerger.commons.events.DocumentListChangedEvent;
import pers.pdfstuff.pdfmerger.logic.Logic;
import pers.pdfstuff.pdfmerger.model.ModelManager;

public class UiManager extends ComponentManager implements Ui {

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);

    private Logic logic;
    private MainWindow mainWindow;

    public UiManager(Logic logic) {
        super();
        this.logic = logic;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.getIcons().add(new Image(PdfMerger.class.getResourceAsStream(Config.ICON)));
        try {
            mainWindow = new MainWindow(primaryStage, logic);
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
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                mainWindow.syncWithMasterDocumentList();
                if (event.scrollToIndex == ModelManager.SCROLL_TO_LAST) {
                    mainWindow.getDocListPanel()
                            .scrollTo(mainWindow.getDocListPanel().getListView().getItems().size() - 1);
                } else {
                    mainWindow.getDocListPanel().scrollTo(event.scrollToIndex);
                }
            }

        });
    }
}
