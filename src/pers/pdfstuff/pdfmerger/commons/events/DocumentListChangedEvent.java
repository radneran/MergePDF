package pers.pdfstuff.pdfmerger.commons.events;

public class DocumentListChangedEvent extends BaseEvent {

    public final String message;

    public DocumentListChangedEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
