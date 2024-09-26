package sheep.core;

/**
 * Handles rendering of cells within a sheet.
 * @provided
 */
public interface SheetView {
    /**
     * The number of rows in the spreadsheet.
     *
     * @return The number of rows in the spreadsheet.
     */
    int getRows();

    /**
     * The number of columns in the spreadsheet.
     *
     * @return The number of columns in the spreadsheet.
     */
    int getColumns();

    /**
     * Determine the value to render at the cell position.
     *
     * @param row    The row index of the cell.
     * @param column The column index of the cell.
     * @return A {@link ViewElement} that details how to render the cell's value.
     * @require 0 &leq; row &lt; {@link SheetView#getRows()}
     * @require 0 &leq; column &lt; {@link SheetView#getColumns()}
     */
    ViewElement valueAt(int row, int column);

    /**
     * Determine the formula to render at the cell position.
     *
     * @param row    The row index of the cell.
     * @param column The column index of the cell.
     * @return A {@link ViewElement} that details how to render the cell's formula.
     * @require 0 &leq; row &lt; {@link SheetView#getRows()}
     * @require 0 &leq; column &lt; {@link SheetView#getColumns()}
     */
    ViewElement formulaAt(int row, int column);

    /**
     * Create an interpretable string representation of the current spreadsheet.
     *
     * @return The string representation of the spreadsheet.
     */
    String encode();
}
