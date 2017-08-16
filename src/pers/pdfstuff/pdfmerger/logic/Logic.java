package pers.pdfstuff.pdfmerger.logic;

import java.io.File;

import javafx.collections.ObservableList;
import pers.pdfstuff.pdfmerger.logic.commands.Command;

public interface Logic {

    ObservableList<File> getSortedDocList();

    void execute(Command toExecute);
}
