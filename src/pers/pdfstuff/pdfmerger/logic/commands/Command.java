package pers.pdfstuff.pdfmerger.logic.commands;

import java.util.logging.Logger;

import pers.pdfstuff.pdfmerger.commons.core.LogsCenter;
import pers.pdfstuff.pdfmerger.model.Model;

public abstract class Command {
    
    protected static final Logger logger = LogsCenter.getLogger(Command.class);
    protected Model model;
    
    public void setModel(Model model) {
        this.model = model;
    }
    
    abstract public void onExecute();
}
