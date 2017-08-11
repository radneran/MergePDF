package pers.pdfstuff.pdfmerger.commons.utils;

import java.io.File;
import java.util.List;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class FileUtils {
    
    public static boolean isValidFile(File file) {
        return file.exists() && file.isFile() && isPdf(file);
    }
    
    private static boolean isPdf(File file) {
        return file.getAbsolutePath().toLowerCase().endsWith("pdf");
    }
    
    public static File mergeFiles(List<File> documentList) {
        try {
            PDFMergerUtility mergePdf = new PDFMergerUtility();
            for (File document : documentList) {
                mergePdf.addSource(document);
            }
            mergePdf.setDestinationFileName("Combined.pdf");
            mergePdf.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
            return new File("Combined.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
