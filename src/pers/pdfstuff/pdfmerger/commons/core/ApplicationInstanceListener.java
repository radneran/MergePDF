package pers.pdfstuff.pdfmerger.commons.core;

public interface ApplicationInstanceListener {
    /**
     * Event handler for when multiple application instances are created
     * 
     * @param message
     *            Contains the arguments passed to the instance
     */
    public void newInstanceCreated(String message);
}
