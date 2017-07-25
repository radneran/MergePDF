package pers.pdfstuff.pdfmerger.commons.core;

public abstract class ComponentManager {

    ComponentManager() {
        registerAsEventHandler();
    }

    private void registerAsEventHandler() {
        EventsCenter.getInstance().registerEventHandler(this);
    }
}
