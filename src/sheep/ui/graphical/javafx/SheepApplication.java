package sheep.ui.graphical.javafx;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sheep.core.SheetUpdate;
import sheep.core.SheetView;
import sheep.core.ViewElement;
import sheep.sheets.SheetBuilder;
import sheep.ui.UI;
import sheep.ui.graphical.Configuration;

import javax.swing.text.View;
import java.io.File;
import java.util.Map;

/**
 * The SheeP JavaFX application.
 * @stage0
 */
public class SheepApplication extends Application {

    /**
     * A view of the primary sheet.
     */
    private SheetView view;

    /**
     * An updater for the primary sheet.
     */
    private SheetUpdate updater;

    /**
     * A mapping of all the menu bar features.
     */
    private final Map<String, Map<String, UI.Feature>> features;

    /**
     * String used to index columns in spreadsheet.
     */
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * A representation of the grid (cells).
     */
    private final ObservableList<ObservableList<ViewElement>> grid =
            FXCollections.observableArrayList();

    /**
     * Construct a new SheeP Application with a sheet preloaded.
     * The application has a menu bar with each feature available.
     *
     * @param view A view of the primary sheet.
     * @param updater An updater for the primary sheet.
     * @param features A mapping of all the menu bar features.
     */
    public SheepApplication(SheetView view, SheetUpdate updater, Map<String, Map<String,
            UI.Feature>> features) {
        this.view = view;
        this.updater = updater;
        this.features = features;
    }

    /**
     * Start the SheeP Application.
     * Creates a new window to display and modify the sheet.
     * The scene has a menu bar with all the features.
     * [1], [2] The practical in week 9, 10 and tic-tac-toe has been
     * used to set up the grid with the tableview columns.
     * @param stage the primary stage.
     * @throws Exception if the application fails to run.
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(Configuration.TITLE);
        var textField = new TextField();
        var gridSizeHeight = view.getRows();
        var gridSizeWidth = view.getColumns();

        for (int i = 0; i < gridSizeHeight; i++) {
            ObservableList<ViewElement> row = FXCollections.observableArrayList();
            for (int j = 0; j < gridSizeWidth; j++) {
                try {
                    row.add(view.valueAt(i, j));
                } catch (NullPointerException ignore) {
                    row.add(new ViewElement("EMPTY", "WHITE", "GREEN"));
                }
            }
            grid.add(row);
        }
        TableView<ObservableList<ViewElement>> table = new TableView<>(grid);
        table.setOnMouseClicked(event -> handleMouseClicked(event, table, textField));
        table.setOnKeyPressed(event -> handleArrowKeyClicked(event, table, textField));
        textField.setFocusTraversable(false);
        textField.setEditable(false);
        // [3] Inspired by post regarding single cell selection
        table.getSelectionModel().setCellSelectionEnabled(true);
        table.setEditable(true);
        TableColumn<ObservableList<ViewElement>, String> emptyColumn = new TableColumn<>();
        emptyColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(String.valueOf(cellData.getTableView()
                        .getItems()
                        .indexOf(cellData.getValue()))));
        emptyColumn.setPrefWidth(Configuration.HEADER_COLUMN_WIDTH);
        table.getColumns().add(emptyColumn);

        for (int i = 0; i < gridSizeWidth; i++) {
            TableColumn<ObservableList<ViewElement>, String> column =
                    new TableColumn<>(String.valueOf(ALPHABET.charAt(i)));
            int colIndex = i;
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            column.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().get(colIndex).getContent()));
            column.setPrefWidth(Configuration.COLUMN_WIDTH);
            column.setOnEditCommit(event -> {
                var row = getSelectedPosition(table)[0];
                var col = getSelectedPosition(table)[1] - 1;
                updater.update(row, col, event.getNewValue());
                textField.setText(view.formulaAt(row, col).getContent());
                grid.get(row).set(col, view.valueAt(row, col));
                // [9] Refresh inspired by comment found on updating table.
                table.refresh();
            });
            table.getColumns().add(column);
        }
        var menuBar = new MenuBar();
        /* [8] Hint from A2 and oracle docs provided inspiration as
         * to how to implement a simple FileChooser
         */
        var fileChooser = new FileChooser();
        fileChooser.setTitle("Open Spreadsheet");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("SheepApplication Files", "*.sheep*"));
        var fileSaver = new FileChooser();
        fileSaver.setTitle("Save Spreadsheet");
        fileSaver.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("SheepApplication Files", "*.sheep*"));

        for (var feature : features.entrySet()) {
            var menuItem = new Menu(feature.getKey());

            for (var subFeature : feature.getValue().values()) {
                var subMenuItem = new MenuItem(subFeature.name());
                if (subMenuItem.getText().contains("S")) {
                    subMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent e) {
                            fileSaver.showSaveDialog(stage);
                        }
                    });
                } else {
                    subMenuItem.setOnAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent e) {
                            fileChooser.showOpenDialog(stage);
                        }
                    });
                }
                menuItem.getItems().add(subMenuItem);
            }
            menuBar.getMenus().add(menuItem);
        }
        menuBar.setUseSystemMenuBar(true);
        var topPane = new BorderPane();
        topPane.setTop(menuBar);
        topPane.setCenter(textField);
        var pane = new BorderPane();
        pane.setTop(topPane);
        pane.setCenter(table);
        stage.setScene(new Scene(pane, 1010, 400));
        stage.show();
    }

    /**
     * Create a new window, with a new sheet attached.
     *
     * @param view a view of the new sheet.
     * @param updater an updater for the new sheet.
     */
    public void createWindow(SheetView view, SheetUpdate updater) {
        var window = new SheepApplication(view, updater, this.features);
        var stage = new Stage();
        try {
            window.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Is responsible for having a click register in a cell.
    private void handleMouseClicked(Event event, TableView<ObservableList<ViewElement>> table,
                                    TextField textField) {
        var position = getSelectedPosition(table);

        //Clear text field if clicking outside spreadsheet
        if (position[1] == 0) {
            textField.clear();
            return;
        }
        var cell = view.formulaAt(position[0], position[1] - 1);
        textField.setText(cell.getContent());
    }

    // [4] Is responsible for having the arrow keys navigate the spreadsheet.
    private void handleArrowKeyClicked(KeyEvent event, TableView<ObservableList<ViewElement>> table,
                                       TextField textField) {
        if (event.getCode().isArrowKey() && !table.getSelectionModel().isEmpty()) {
            handleMouseClicked(event, table, textField);
            return;
        }
        table.getSelectionModel().selectFirst();
    }

    // Gets the selected position in the tableview.
    private Integer[] getSelectedPosition(TableView<ObservableList<ViewElement>> table) {
        var selectedPosition = table.getSelectionModel().getSelectedCells().getFirst();
        var rowIndex = selectedPosition.getRow();
        var columnIndex = selectedPosition.getColumn();
        return new Integer[]{rowIndex, columnIndex};
    }
}