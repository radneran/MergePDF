package pers.pdfstuff.pdfmerger.logic;

import java.io.File;
import java.util.Collections;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import pers.pdfstuff.pdfmerger.commons.core.ComponentManager;
import pers.pdfstuff.pdfmerger.commons.core.LogsCenter;
import pers.pdfstuff.pdfmerger.commons.events.DuplicateInstanceEvent;
import pers.pdfstuff.pdfmerger.commons.events.NewCommandEvent;
import pers.pdfstuff.pdfmerger.logic.commands.AddCommand;
import pers.pdfstuff.pdfmerger.logic.commands.Command;
import pers.pdfstuff.pdfmerger.model.Model;

public class LogicManager extends ComponentManager implements Logic {
    
    private static final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private Model model;

    public LogicManager(Model model) {
        this.model = model;
    }
    
    @Override
    public void execute(Command toExecute) {
        toExecute.setModel(model);
        toExecute.onExecute();
    }


    @Override
    public ObservableList<File> getSortedDocList() {
        return model.getDocumentListCopy();
    }

   
    /**
     * ============= EVENT HANDLERS =================
     */

    @Subscribe
    public void handleDuplicateInstanceEvent(DuplicateInstanceEvent event) {
        logger.info("[Event Handled]==== " + event.getClass().getName() + " Message: " + event.message);
        execute(new AddCommand(Collections.singletonList(new File(event.message))));
    }
    
    @Subscribe
    public void handleNewCommandEvent(NewCommandEvent event) {
        execute(event.command);
    }

}
