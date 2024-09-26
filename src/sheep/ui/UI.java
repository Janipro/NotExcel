package sheep.ui;

import sheep.core.SheetUpdate;
import sheep.core.SheetView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A UI for the SheeP Program.
 * @provided
 */
public abstract class UI {
    // A view of the primary sheet.
    protected final SheetView view;

    // An updater for the primary sheet.
    protected final SheetUpdate updater;

    // The callbacks that are invoked when the sheet updates.
    protected final List<OnChange> changeCallbacks = new ArrayList<>();

    // A map representing the feature menu.
    protected final Map<String, Map<String, Feature>> features = new HashMap<>();

    /**
     * Construct a new UI for the Sheep program,
     * and initialise it with a sheet.
     *
     * @param view    A view of the sheet to display.
     * @param updater An updater for the sheet to display.
     */
    public UI(SheetView view, SheetUpdate updater) {
        this.view = view;
        this.updater = updater;
    }

    /**
     * Add a new callback to the UI that will be triggered whenever the sheet is updated.
     *
     * @param callback The action to perform.
     */
    public void onChange(OnChange callback) {
        changeCallbacks.add(callback);
    }

    /**
     * Add a new feature to the UI.
     *
     * @param menuName   The name of the menu to add it to.
     * @param identifier The feature's id.
     * @param name       The feature's name.
     * @param action     The action to perform when this feature is invoked.
     */
    public void addFeature(String menuName, String identifier, String name, Perform action) {
        Map<String, Feature> menu = features.getOrDefault(menuName, new HashMap<>());
        menu.put(identifier, new Feature(name, action));
        features.put(menuName, menu);
    }

    /**
     * Begin rendering the UI.
     *
     * @throws Exception If the Application can't run.
     */
    public abstract void render() throws Exception;

    /**
     * Create a new window with a new sheet, using this UI.
     *
     * @param view    A view of the new sheet.
     * @param updater An updater for the new sheet.
     * @throws Exception If the new Application can't run.
     */
    public abstract void openWindow(SheetView view,
                                    SheetUpdate updater) throws Exception;

    /**
     * A named feature of the UI
     *
     * @param name   The feature's name
     * @param action The action to perform when this feature is invoked.
     */
    public record Feature(String name, Perform action) {

    }
}
