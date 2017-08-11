package pers.pdfstuff.pdfmerger.logic;

import static pers.pdfstuff.pdfmerger.commons.utils.FileUtils.isValidFile;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import pers.pdfstuff.pdfmerger.commons.core.ComponentManager;
import pers.pdfstuff.pdfmerger.commons.core.LogsCenter;
import pers.pdfstuff.pdfmerger.commons.events.DeleteEvent;
import pers.pdfstuff.pdfmerger.commons.events.DuplicateInstanceEvent;
import pers.pdfstuff.pdfmerger.commons.events.FileDropEvent;
import pers.pdfstuff.pdfmerger.commons.events.MergeEvent;
import pers.pdfstuff.pdfmerger.commons.events.MoveEvent;
import pers.pdfstuff.pdfmerger.commons.utils.FileUtils;
import pers.pdfstuff.pdfmerger.model.Model;

public class LogicManager extends ComponentManager implements Logic {
    
    private static final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private Model model;

    public LogicManager(Model model) {
        this.model = model;
    }

    @Override
    public ObservableList<File> getSortedDocList() {
        return model.getDocumentListCopy();
    }

    @Override
    public void executeAddCommand(File fileToAdd) {
        if (isValidFile(fileToAdd)) {
            model.addDocument(fileToAdd);
        }
    }

    @Override
    public void executeMergeCommand() {
        File mergedFile = FileUtils.mergeFiles(model.getDocumentListCopy());
        try {
            Desktop.getDesktop().open(mergedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void executeDeleteCommand(int index) {
        model.removeDocument(index);
    }

    public void executeMoveCommand(int start, int destination) {
        model.moveDocument(start, destination);
    }

    /**
     * ============= EVENT HANDLERS =================
     */

    @Subscribe
    public void handleFileDropEvent(FileDropEvent event) {
        if (event.files != null) {
            event.files.forEach(file -> executeAddCommand(file));
        }
    }

    @Subscribe
    public void handleDeleteEvent(DeleteEvent event) {
        executeDeleteCommand(event.index);
    }

    @Subscribe
    public void handleMoveEvent(MoveEvent event) {
        executeMoveCommand(event.start, event.destination);
    }

    @Subscribe
    public void handleMergeEvent(MergeEvent event) {
        executeMergeCommand();
    }

    @Subscribe
    public void handleDuplicateInstanceEvent(DuplicateInstanceEvent event) {
        logger.info("[Event Handled]==== " + event.getClass().getName() + " Message: " + event.message);
        executeAddCommand(new File(event.message));
    }

}
