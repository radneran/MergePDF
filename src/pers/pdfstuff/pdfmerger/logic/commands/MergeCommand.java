package pers.pdfstuff.pdfmerger.logic.commands;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.collections.ObservableList;
import pers.pdfstuff.pdfmerger.commons.utils.FileUtils;

public class MergeCommand extends Command {

    @Override
    public void onExecute() {
        ObservableList<File> documentListCopy = model.getDocumentListCopy();
        if (documentListCopy.isEmpty()) {
            logger.info("Nothing to merge");
            return;
        }
        File mergedFile = FileUtils.mergeFiles(documentListCopy);
        
        try {
            Desktop.getDesktop().open(mergedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
