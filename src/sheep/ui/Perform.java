package sheep.ui;

import sheep.core.SheetUpdate;
import sheep.core.SheetView;

/**
 * An interface for performable actions on a sheet.
 * @provided
 */
public interface Perform {

    /**
     * An action to perform on a sheet.
     *
     * @param row     The row to impact.
     * @param column  The column to impact.
     * @param prompt  A Prompter for the current UI.
     * @param view    A view of the sheet.
     * @param updater An updater for the sheet.
     */
    void perform(int row, int column, Prompt prompt, SheetView view, SheetUpdate updater);
}
