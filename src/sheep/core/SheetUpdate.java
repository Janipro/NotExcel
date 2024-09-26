package sheep.core;

/**
 * Handles replacing the value of a cell with a given input.
 * @provided
 */
public interface SheetUpdate {
    /**
     * To be called whenever one wishes to replace a cell's value.
     *
     * @param row    The row index to update.
     * @param column The column index to update.
     * @param input  The value as a string to replace within the sheet.
     * @return An {@link UpdateResponse} indicating success or failure with a message.
     * @requires input != null
     */
    UpdateResponse update(int row, int column, String input);
}
