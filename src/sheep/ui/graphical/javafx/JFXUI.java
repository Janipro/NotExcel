package sheep.ui.graphical.javafx;

import javafx.application.Platform;
import javafx.stage.Stage;
import sheep.core.SheetUpdate;
import sheep.core.SheetView;
import sheep.ui.UI;


/**
 * Graphical interface for the spreadsheet program, built in JavaFX.
 * <p>
 * Requires a {@link SheetView} and {@link SheetUpdate} to determine
 * what to render and how to update the sheet respectively.
 * @provided
 */
public class JFXUI extends UI {
    // The main JavaFX application.
    private SheepApplication application;

    /**
     * Construct a new JavaFX UI for the Sheep program,
     * and initialise it with a sheet.
     *
     * @param view    A view of the sheet to display.
     * @param updater An updater for the sheet to display.
     */
    public JFXUI(SheetView view, SheetUpdate updater) {
        super(view, updater);
    }

    /**
     * Render the JavaFX UI by creating a {@link SheepApplication} and starting it.
     * @throws RuntimeException If the JavaFX application fails to start.
     */
    @Override
    public void render() throws RuntimeException {
        // This is not standard practice and should not be used in
        // native JavaFX applications, but in order to make JavaFX operate
        // similarly to Swing's invokeLater() behaviour this is necessary.
        // This is why updating the sheet after calling render() but before
        // returning from main() is still reflected in the UI.
        Platform.setImplicitExit(true);
        Platform.startup(() -> {
            application = new SheepApplication(this.view, this.updater, this.features);
            try {
                application.init();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Platform.runLater(() -> {
                try {
                    application.start(new Stage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    /**
     * Create the application's window with a new sheet attached.
     *
     * @param view    A view of the new sheet.
     * @param updater An updater for the new sheet.
     * @throws Exception
     */
    @Override
    public void openWindow(SheetView view, SheetUpdate updater) throws Exception {
        application.createWindow(view, updater);
    }
}
