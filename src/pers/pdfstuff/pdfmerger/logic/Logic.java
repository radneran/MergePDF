package pers.pdfstuff.pdfmerger.logic;

import java.io.File;

import javafx.collections.ObservableList;

public interface Logic {
    ObservableList<File> getSortedDocList();

    void executeMergeCommand();
    
    void executeAddCommand(File fileToAdd);
}
