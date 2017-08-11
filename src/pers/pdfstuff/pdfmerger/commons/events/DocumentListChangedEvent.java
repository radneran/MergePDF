package pers.pdfstuff.pdfmerger.commons.events;

public class DocumentListChangedEvent extends BaseEvent {
    //public static enum CommandType {ADD, REMOVE, MOVE};
    public final String message;
    public final int scrollToIndex;

    public DocumentListChangedEvent(String message, int lastSelectionIndex) {
        this.message = message;
        scrollToIndex = lastSelectionIndex;
    }

    @Override
    public String toString() {
        return message;
    }

}
