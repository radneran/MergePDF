package pers.pdfstuff.pdfmerger.logic.commands;

import static pers.pdfstuff.pdfmerger.ui.DocumentListPanel.DESTINATION;
import static pers.pdfstuff.pdfmerger.ui.DocumentListPanel.START;

import java.io.File;

import javafx.scene.input.ClipboardContent;

public class MoveCommand extends Command {
    public final File toMove;
    public final int start;
    public final int destination;

    public MoveCommand(ClipboardContent content) {
        toMove = content.getFiles().get(0);
        start = (int) content.get(START);
        destination = (int) content.get(DESTINATION);
    }

    @Override
    public void onExecute() {
        model.moveDocument(start, destination);
    }

}
