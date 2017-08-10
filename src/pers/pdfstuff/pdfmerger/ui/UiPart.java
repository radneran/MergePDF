package pers.pdfstuff.pdfmerger.ui;

import java.io.IOException;
import java.net.URL;

import org.apache.pdfbox.tools.PDFMerger;

import javafx.fxml.FXMLLoader;
import pers.pdfstuff.pdfmerger.commons.core.EventsCenter;
import pers.pdfstuff.pdfmerger.commons.events.BaseEvent;

public abstract class UiPart<T> {

    private static final String FXML_FILE_FOLDER = "/view/";
    
    private FXMLLoader fxmlLoader;
    public UiPart(URL FXMLfileUrl) {
        fxmlLoader = new FXMLLoader(FXMLfileUrl);
        fxmlLoader.setController(this);
        try{
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public UiPart(String fxmlFileName) {
        this(fxmlFileName != null ? PDFMerger.class.getResource(FXML_FILE_FOLDER + fxmlFileName) : null);
    }
    
    public void registerAsEventHandler() {
        EventsCenter.getInstance().registerEventHandler(this);
    }
    
    public void raise(BaseEvent event) {
        EventsCenter.getInstance().post(event);
    }
    
    public T getRoot() {
        return fxmlLoader.getRoot();
    }

}
