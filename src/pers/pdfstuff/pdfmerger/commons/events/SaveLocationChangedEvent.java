package pers.pdfstuff.pdfmerger.commons.events;

public class SaveLocationChangedEvent extends BaseEvent {
    
    private String location;
    public SaveLocationChangedEvent(String newLocation) {
        location = newLocation;
    }
    @Override
    public String toString() {
        return location;
    }

}
