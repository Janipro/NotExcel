package sheep.ui.graphical.swing;

import sheep.core.SheetUpdate;
import sheep.core.SheetView;
import sheep.core.ViewElement;
import sheep.ui.OnChange;
import sheep.ui.Prompt;
import sheep.ui.UI;
import sheep.ui.graphical.Configuration;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Map;
import java.util.Optional;

/**
 * Graphical interface for the spreadsheet program.
 * built in swing.
 * <p>
 * Requires a {@link SheetView} and {@link SheetUpdate} to determine
 * what to render and how to update the sheet respectively.
 * @provided
 */
public class GUI extends UI {
    /**
     * Construct a new graphical interface.
     *
     * @param view    The viewer that determines the size of the
     *                spreadsheet and the formula and values to display.
     * @param updater A model used to update the spreadsheet whenever
     *                the user makes a change.
     */
    public GUI(SheetView view, SheetUpdate updater) {
        super(view, updater);
    }

    /**
     * The render method will trigger the user interface to be displayed.
     * The interface uses Java Swing and will open a new window.
     */
    public void render() {
        // Use the native file menu on MacOS
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        // frame is the whole window that is displayed.
        JFrame frame = new JFrame();
        frame.setTitle(Configuration.TITLE);

        // JTable operates on a model which tells it what to render
        // and what to do when a cell is updated.
        // We use a small hack of storing Location instances
        // in the table model so that both the value is rendered
        // but the formula is retrieved when editing.
        SheetModel model = new SheetModel(frame, view, updater);
        JTable table = new JTable(model);
        style(table);

        // When the model is updated, re-render the table and
        // call any change callbacks (notably, saving).
        model.addTableModelListener((e -> table.updateUI()));
        for (OnChange callback : changeCallbacks) {
            model.addTableModelListener(e -> callback.change());
        }
        setupMenu(frame, table);

        // Configure the custom cell renderer and editor for each column.
        for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++) {
            TableColumn column = table.getColumnModel().getColumn(columnIndex);

            column.setCellRenderer(new CellRenderer(view));
            column.setCellEditor(new CellEditor(view, new JTextField()));
        }

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(formulaView(table));
        panel.add(table);

        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void openWindow(SheetView view, SheetUpdate updater) throws Exception {
        throw new RuntimeException("new windows not supported in swing");
    }

    /**
     * Create an item in the menu to trigger each feature.
     */
    private void setupMenu(JFrame frame, JTable table) {
        JMenuBar menuBar = new JMenuBar();
        Prompt prompt = new MessagePrompt();

        for (Map.Entry<String, Map<String, Feature>> menuEntry : features.entrySet()) {
            JMenu menu = new JMenu(menuEntry.getKey());
            for (Map.Entry<String, Feature> itemEntry : menuEntry.getValue().entrySet()) {
                JMenuItem item = new JMenuItem(itemEntry.getValue().name());
                item.addActionListener(e -> {
                    itemEntry.getValue().action().perform(table.getSelectedRow() - 1, table.getSelectedColumn() - 1, prompt, view, updater);
                    table.updateUI();
                });
                menu.add(item);
            }
            menuBar.add(menu);
        }
        frame.setJMenuBar(menuBar);
    }

    /**
     * Style the table as per the {@link Configuration} class.
     */
    private void style(JTable table) {
        table.setGridColor(Configuration.LINE_COLOR);
        table.setRowHeight(Configuration.ROW_HEIGHT);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(Configuration.HEADER_COLUMN_WIDTH);
        for (int column = 1; column < view.getColumns(); column++) {
            columnModel.getColumn(column).setPreferredWidth(Configuration.COLUMN_WIDTH);
        }
    }

    /**
     * Create a new component that is linked to the value of the currently highlighted cell.
     * When the highlighted cell changes, the formula is updated.
     */
    private Component formulaView(JTable table) {
        TextField formulaView = new TextField();
        formulaView.setEnabled(false);

        // Callback for whenever the highlighted cell changes.
        ListSelectionListener updateFormula = e -> {
            // Clear the view when selecting headers.
            if (table.getSelectedColumn() <= 0 || table.getSelectedRow() <= 0) {
                formulaView.setText("");
                return;
            }

            ViewElement element = view.formulaAt(
                    table.getSelectedRow() - 1,
                    table.getSelectedColumn() - 1
            );

            formulaView.setText(element.getContent());
            formulaView.setForeground(CellRenderer.getColorByName(element.getForeground()));
            formulaView.setBackground(CellRenderer.getColorByName(element.getBackground()));
        };

        // Add the callback when both the column changes and the row changes.
        table.getSelectionModel().addListSelectionListener(updateFormula);
        table.getColumnModel().getSelectionModel().addListSelectionListener(updateFormula);

        return formulaView;
    }

    private static class MessagePrompt implements Prompt {

        @Override
        public Optional<String> ask(String prompt) {
            Optional<String[]> answer = askMany(new String[]{prompt});
            return answer.map(strings -> strings[0]);
        }

        @Override
        public Optional<String[]> askMany(String[] prompts) {
            JPanel panel = new JPanel(new GridLayout(0, 1));

            JTextField[] promptFields = new JTextField[prompts.length];
            for (int i = 0; i < prompts.length; i++) {
                promptFields[i] = new JTextField(15);
                panel.add(new JLabel(prompts[i]));
                panel.add(promptFields[i]);
                panel.add(Box.createHorizontalStrut(15));
            }

            int result = JOptionPane.showConfirmDialog(null, panel,
                    "Prompt", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) {
                return Optional.empty();
            }

            String[] answers = new String[prompts.length];
            for (int i = 0; i < prompts.length; i++) {
                answers[i] = promptFields[i].getText();
            }
            return Optional.of(answers);
        }

        @Override
        public boolean askYesNo(String prompt) {
            int result = JOptionPane.showConfirmDialog(null, prompt,
                    "Prompt", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            return result == JOptionPane.OK_OPTION;
        }

        @Override
        public void message(String prompt) {
            JOptionPane.showMessageDialog(null, prompt,
                    "Prompt", JOptionPane.PLAIN_MESSAGE);
        }

        @Override
        public String openFile() {
            throw new RuntimeException("File chooser not implemented in swing");
        }

        @Override
        public String saveFile() {
            throw new RuntimeException("File chooser not implemented in swing");
        }
    }
}
