package pers.pdfstuff.pdfmerger.logic.commands;

public class DeleteCommand extends Command {
    public final int toDel;
    public final int[] delAll;

    public DeleteCommand(int toDel) {
        this.toDel = toDel;
        delAll = null;
    }

    public DeleteCommand(int[] delAll) {
        this.delAll = delAll;
        toDel = -1;
    }

    @Override
    public void onExecute() {
        if (toDel != -1) {
            model.removeDocument(toDel);
        } else {
            for(int i: delAll) {
                model.removeDocument(i);
            }
        }
    }

}
