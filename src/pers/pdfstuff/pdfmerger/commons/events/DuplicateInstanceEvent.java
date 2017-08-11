package pers.pdfstuff.pdfmerger.commons.events;

public class DuplicateInstanceEvent extends BaseEvent {

    public final String message;
    
    public DuplicateInstanceEvent(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "Duplicate Instance detected. Received message: " + message;
    }

}
