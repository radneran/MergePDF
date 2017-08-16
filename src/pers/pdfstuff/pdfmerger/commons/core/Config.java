package pers.pdfstuff.pdfmerger.commons.core;

import pers.pdfstuff.pdfmerger.commons.events.SaveLocationChangedEvent;

public class Config {
    public static String appName = "MergePDF";
    public static final String ICON = "/images/combinepdf.png";
    public static final String FILE_NAME = "Combined.pdf";
    public static boolean saveLocationSet = false;
    
    private static String saveLocation = ".\\" + FILE_NAME;
    
    public static void updateSaveLocation(String newSaveLocation) {
        if(saveLocationSet) {
            return;
        }
        saveLocation = newSaveLocation + "\\" + FILE_NAME;
        EventsCenter.getInstance().post(new SaveLocationChangedEvent(saveLocation));
    }

    public static String getSaveLocation() {
        return saveLocation;
    }

    public static void updateExactSaveLocation(String absolutePath) {
        saveLocation = absolutePath;
        EventsCenter.getInstance().post(new SaveLocationChangedEvent(saveLocation));
        saveLocationSet = true;
    }
}
