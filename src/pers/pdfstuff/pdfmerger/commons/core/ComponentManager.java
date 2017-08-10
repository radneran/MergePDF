package pers.pdfstuff.pdfmerger.commons.core;

public abstract class ComponentManager {

    protected ComponentManager() {
        registerAsEventHandler();
    }

    private void registerAsEventHandler() {
        EventsCenter.getInstance().registerEventHandler(this);
    }
}
