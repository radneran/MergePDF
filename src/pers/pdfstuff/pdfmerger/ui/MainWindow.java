package pers.pdfstuff.pdfmerger.ui;

import java.util.logging.Logger;

import pers.pdfstuff.pdfmerger.commons.core.LogsCenter;

public class MainWindow {

    private static final Logger logger = LogsCenter.getLogger(MainWindow.class);;
    public MainWindow() {
        logger.info("INIT MAINWINDOW");
    }
    
    public void logTest() {
        logger.info("testing");
    }

}
