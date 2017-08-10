package pers.pdfstuff.pdfmerger.ui;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import pers.pdfstuff.pdfmerger.commons.core.EventsCenter;
import pers.pdfstuff.pdfmerger.commons.core.LogsCenter;
import pers.pdfstuff.pdfmerger.commons.events.DeleteEvent;
import pers.pdfstuff.pdfmerger.commons.events.FileDropEvent;

public class DocumentListPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(DocumentListPanel.class);
    private static final String FXML = "DocumentListPanel.fxml";

    public DocumentListPanel(ObservableList<File> documentList) {
        super(FXML);
        setConnections(documentList);
        setDragOverEventListener();
        setDragEnteredEventListener();
        setDragDropEventListener();
    }

    @FXML
    private ListView<File> documentListView;
    
    public ListView<File> getListView() {
        return documentListView;
    }

    private void setConnections(ObservableList<File> documentList) {
        logger.info("Setting connections with DocList size:" + documentList.size());
        documentListView.setItems(documentList);
        documentListView.setCellFactory(listView -> new DocumentListViewCell());
        setKeyPressedHandler();
    }

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
                    EventsCenter.getInstance().post(new FileDropEvent(newFiles));
                }
                event.setDropCompleted(true);
                event.consume();
            }
        });
    }
    
    
    public void setKeyPressedHandler() {
        documentListView.setOnKeyPressed(new EventHandler <KeyEvent>() {
            
            @Override
            public void handle(KeyEvent event) {
                logger.info("KeyPressed: " + event.getCode());
                if (event.getCode().equals(KeyCode.DELETE)) {
                    int index = getListView().getSelectionModel().getSelectedIndex();
                    logger.info("Delete key pressed on file number " + index);
                    EventsCenter.getInstance().post(new DeleteEvent(index));
                }
                event.consume();
            }
        });
        
    }

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
