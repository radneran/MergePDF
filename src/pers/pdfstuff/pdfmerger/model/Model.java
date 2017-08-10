package pers.pdfstuff.pdfmerger.model;

import java.io.File;

import javafx.collections.ObservableList;

public interface Model {
    //Returns the list of documents
    ObservableList<File> getDocumentListCopy();
    //Removes the document specified by the index
    void removeDocument(int index);
    //Moves the document to the given index
    void moveDocument(File file, int index);
    //Adds document to list
    void addDocument(File file);
    //Sets the document list to the given one
    void setDocumentList(ObservableList<File> docList);
}
