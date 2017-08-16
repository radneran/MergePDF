package pers.pdfstuff.pdfmerger.logic.commands;

import static pers.pdfstuff.pdfmerger.commons.utils.FileUtils.isValidFile;

import java.io.File;
import java.util.List;

public class AddCommand extends Command {
    
    private List<File> toAdd;
    public AddCommand(List<File> toAdd) {
        this.toAdd = toAdd;
    }

    @Override
    public void onExecute() {
        toAdd.forEach(file -> {
            if (isValidFile(file)) {
                model.addDocument(file);
            }
        });
        
    }

}
