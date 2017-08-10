package pers.pdfstuff.pdfmerger.commons.events;

public class DeleteEvent extends BaseEvent {

    public final int index;
    
    public DeleteEvent(int index) {
        this.index = index;
    }
    
    @Override
    public String toString() {
        return "Deleted index " + index;
    }

}
