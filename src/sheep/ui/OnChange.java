package sheep.ui;


/**
 * A callback used whenever spreadsheet cell is updated.
 * This can be useful for triggering global events such as automatic saving.
 * @provided
 */
public interface OnChange {

    /**
     * The action to perform on change.
     */
    void change();
}
