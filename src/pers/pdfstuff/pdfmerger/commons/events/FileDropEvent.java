package pers.pdfstuff.pdfmerger.commons.events;

import java.io.File;
import java.util.List;

public class FileDropEvent extends BaseEvent {

    public final List<File> files;
    
    public FileDropEvent(List<File> files) {
        this.files = files;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        files.forEach(file -> builder.append("New file dropped: " + file.getName()));
        return builder.toString();
    }
}
