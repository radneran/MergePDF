package pers.pdfstuff.pdfmerger.commons.events;

import pers.pdfstuff.pdfmerger.logic.commands.Command;

public class NewCommandEvent extends BaseEvent {

    public final Command command;
    public NewCommandEvent(Command command) {
        this.command = command;
    }
    @Override
    public String toString() {
        return "New command: " + command.getClass().toString();
    }

}
