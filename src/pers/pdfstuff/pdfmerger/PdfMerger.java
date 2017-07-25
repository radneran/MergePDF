package pers.pdfstuff.pdfmerger;

import java.io.File;
import java.util.logging.Logger;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import pers.pdfstuff.pdfmerger.commons.core.LogsCenter;
import pers.pdfstuff.pdfmerger.ui.MainWindow;

public class PdfMerger {

    public static void main(String args[]) {
        final Logger logger = LogsCenter.getLogger(PdfMerger.class);
        LogsCenter.addFileHandler(logger);
        logger.info("INITIALISING");
        MainWindow mw = new MainWindow();
        mw.logTest();
    }

    public void combine() {
        try {
            PDFMergerUtility mergePdf = new PDFMergerUtility();
            String folder = "C:/Users/Naren/Desktop/pdfbox";
            File _folder = new File(folder);
            File[] filesInFolder;
            filesInFolder = _folder.listFiles();
            for (File string : filesInFolder) {
                mergePdf.addSource(string);
            }
            mergePdf.setDestinationFileName("C:/Users/Naren/Desktop/pdfbox/Combined.pdf");
            mergePdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        } catch (Exception e) {

        }
    }

    public void createNew() {
        PDDocument document = null;
        try {
            String filename = "test.pdf";
            document = new PDDocument();
            PDPage blankPage = new PDPage();
            document.addPage(blankPage);
            document.save(filename);
        } catch (Exception e) {

        }
    }

}
