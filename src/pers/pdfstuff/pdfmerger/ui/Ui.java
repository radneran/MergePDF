package pers.pdfstuff.pdfmerger.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI */
    void start(Stage primaryStage);

    /** Stops the UI. */
    void stop();

}