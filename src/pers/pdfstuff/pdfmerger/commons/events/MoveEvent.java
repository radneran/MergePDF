package pers.pdfstuff.pdfmerger.commons.events;

import static pers.pdfstuff.pdfmerger.ui.DocumentListPanel.DESTINATION;
import static pers.pdfstuff.pdfmerger.ui.DocumentListPanel.START;

import java.io.File;

import javafx.scene.input.ClipboardContent;

public class MoveEvent extends BaseEvent {
    
    public final File toMove;
    public final int start;
    public final int destination;
    /**
     * Contains the info for transferring files from one index to another
     * 
     */
    public MoveEvent(ClipboardContent content) {
        toMove = content.getFiles().get(0);
        start = (int) content.get(START);
        destination = (int) content.get(DESTINATION);
    }
    @Override
    public String toString() {
        return "Moving " + toMove.getName() + " from " + start + " to " + destination;
    }

}
