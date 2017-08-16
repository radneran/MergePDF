package pers.pdfstuff.pdfmerger.ui;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import pers.pdfstuff.pdfmerger.commons.core.EventsCenter;
import pers.pdfstuff.pdfmerger.commons.core.LogsCenter;
import pers.pdfstuff.pdfmerger.commons.events.NewCommandEvent;
import pers.pdfstuff.pdfmerger.logic.commands.AddCommand;
import pers.pdfstuff.pdfmerger.logic.commands.DeleteCommand;
import pers.pdfstuff.pdfmerger.logic.commands.MergeCommand;
import pers.pdfstuff.pdfmerger.logic.commands.MoveCommand;

public class DocumentListPanel extends UiPart<Region> {
    
    public static final DataFormat START = new DataFormat("start");
    public static final DataFormat DESTINATION = new DataFormat("destination");
    
    private static final Logger logger = LogsCenter.getLogger(DocumentListPanel.class);
    private static final String FXML = "DocumentListPanel.fxml";

    @FXML
    private ListView<File> documentListView;

    public DocumentListPanel(ObservableList<File> documentList) {
        super(FXML);
        setConnections(documentList);
        setDragOverEventListener();
        setDragEnteredEventListener();
        setDragDropEventListener();
        setKeyPressedHandler();
        setKeyReleasedHandler();
    }

    public ListView<File> getListView() {
        return documentListView;
    }

    private void setConnections(ObservableList<File> documentList) {
        logger.info("Setting connections with DocList size:" + documentList.size());
        documentListView.setItems(documentList);
        documentListView.setCellFactory(listView -> new DocumentListViewCell());
    }
    
    public void scrollTo(int index) {
        Platform.runLater(() -> {
            documentListView.scrollTo(index);
            documentListView.getSelectionModel().clearAndSelect(index);
        });
    }


/**
 * ====================== EVENT HANDLERS =====================================
 */
    //Clipboard for moving content about
    private ClipboardContent content;
    private void setDragEnteredEventListener() {
        documentListView.setOnDragEntered(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                logger.info("Drag entered event" + "\nSource: " + event.getSource() + "\nTarget: " + event.getTarget()
                        + "\nGestureSource: " + event.getGestureSource() + "\nGestureTarget: "
                        + event.getGestureTarget());
                event.consume();
            }
        });
    }

    private void setDragOverEventListener() {
        documentListView.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.COPY);
                event.consume();
            }

        });
    }

    private void setDragDropEventListener() {
        documentListView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    List<File> newFiles = db.getFiles();
                    logger.info("No. Files dropped: " + newFiles.size());
                    EventsCenter.getInstance().post(new NewCommandEvent(new AddCommand(newFiles)));
                }
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }

    public void setKeyPressedHandler() {
        documentListView.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                logger.info("Key Pressed: " + event.getCode());
                int selectedIndex = getListView().getSelectionModel().getSelectedIndex();
                
                switch (event.getCode()) {
                case DELETE:
                case D:
                    logger.info("Delete key pressed on file number " + selectedIndex);
                    EventsCenter.getInstance().post(new NewCommandEvent(new DeleteCommand(selectedIndex)));
                    break;
                case UP:
                    if (selectedIndex > 0) {
                        scrollTo(selectedIndex - 1);
                    }
                    break;
                case DOWN:
                    if (selectedIndex < documentListView.getItems().size() - 1) {
                        scrollTo(selectedIndex + 1);
                    }
                    break;
                case CONTROL:
                    content = new ClipboardContent();
                    content.putFiles(Collections.singletonList(documentListView.getItems().get(selectedIndex)));
                    content.put(START, selectedIndex);
                    logger.info("Clipboard contains: " + content.getFiles().get(0).getName());
                    break;
                case ENTER:
                    EventsCenter.getInstance().post(new NewCommandEvent(new MergeCommand()));
                    break;
                default:
                    break;
                }

                event.consume();
            }
        });
    }
    public void setKeyReleasedHandler() {
        documentListView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                logger.info("Key Released: " + event.getCode());
                int selectedIndex = getListView().getSelectionModel().getSelectedIndex();    
                switch(event.getCode()) {
                case CONTROL:
                    content.put(DESTINATION, selectedIndex);
                    EventsCenter.getInstance().post(new NewCommandEvent(new MoveCommand(content)));
                    break;
                default:
                    break;
                }
                
            }
        });
    }
    
/**
 * ======================= INNER CLASS ==============================    
 */
    /**
     * Custom listcell visuals and event handling
     * @author Naren
     */
    private class DocumentListViewCell extends ListCell<File> {

        @Override
        protected void updateItem(File file, boolean empty) {
            super.updateItem(file, empty);

            if (empty || file == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new DocumentCard(file, getIndex() + 1).getRoot());
            }
        }
    }
}



/*   private DocumentListViewCell() {
super();
DocumentListViewCell source = this;
setDragDetection(source);
source.setOnDragOver(new EventHandler<DragEvent>() {

    @Override
    public void handle(DragEvent event) {
        source.getGraphic().setStyle("-fx-border-width: 4");
        event.acceptTransferModes(TransferMode.LINK);
        event.consume();
    }
    
});
}
private void setDragDetection(DocumentListViewCell source) {
source.setOnDragDetected(new EventHandler<MouseEvent>() {

    @Override
    public void handle(MouseEvent event) {
        logger.info("Drag detected on cell no. " + getIndex());
        try {
            Dragboard dragboard = source.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putFiles(Collections.singletonList(documentListView.getItems().get(getIndex())));
            dragboard.setContent(content);
            logger.info("Dragboard contains: " + dragboard.getFiles().get(0).getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        event.consume();
    }
});
}*/