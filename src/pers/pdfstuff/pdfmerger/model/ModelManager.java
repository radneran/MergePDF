package pers.pdfstuff.pdfmerger.model;

import static pers.pdfstuff.pdfmerger.commons.utils.FileUtils.isValidFile;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pers.pdfstuff.pdfmerger.commons.core.ComponentManager;
import pers.pdfstuff.pdfmerger.commons.core.EventsCenter;
import pers.pdfstuff.pdfmerger.commons.core.LogsCenter;
import pers.pdfstuff.pdfmerger.commons.events.DocumentListChangedEvent;

public class ModelManager extends ComponentManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private ObservableList<File> mainDocumentList = FXCollections.observableArrayList();;

    //Constructor initializes mainDocList with the initial file list if provided
    public ModelManager(List<String> filePaths) {
        logger.info("Initialising ModelManager");
        initializeInternalList(filePaths);
    }

    //Initializes docList with given file array
    private void initializeInternalList(List<String> filePaths) {
        if (filePaths != null) {
            File file;
            for (String filePath : filePaths) {
                if (isValidFile(file = new File(filePath))) {
                    logger.info("Added Filepath: " + filePath);
                    mainDocumentList.add(file);
                } else {
                    logger.info("Invalid Filepath: " + filePath);
                }
            }
        }
    }

    public ModelManager() {
        logger.info("Initialising ModelManager");
    }

    @Override
    public ObservableList<File> getDocumentListCopy() {
        ObservableList<File> mainDocumentListCopy = FXCollections.observableArrayList();
        mainDocumentListCopy.addAll(mainDocumentList);
        return mainDocumentListCopy;
    }

    @Override
    public synchronized void removeDocument(int index) {
        File removed = mainDocumentList.remove(index);
        EventsCenter.getInstance()
                .post(new DocumentListChangedEvent("File removed:" + removed.getName()));
    }

    @Override
    public synchronized void moveDocument(File file, int index) {
        // TODO Auto-generated method stub

    }

    @Override
    public synchronized void addDocument(File file) {
        mainDocumentList.add(file.getAbsoluteFile());
        EventsCenter.getInstance().post(new DocumentListChangedEvent("New file added:" + file.getName()));
    }

    @Override
    public void setDocumentList(ObservableList<File> docList) {
        // TODO Auto-generated method stub

    }

}
