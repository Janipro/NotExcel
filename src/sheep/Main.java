package sheep;

import sheep.expression.CoreFactory;
import sheep.expression.ExpressionFactory;
import sheep.fun.Fibonacci;
import sheep.fun.FunException;
import sheep.fun.Pascal;
import sheep.parsing.ComplexParser;
import sheep.parsing.Parser;
import sheep.sheets.Sheet;
import sheep.sheets.SheetBuilder;
import sheep.ui.UI;
import sheep.ui.graphical.javafx.JFXUI;
import sheep.ui.graphical.swing.GUI;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Execute the SheeP spreadsheet program.
 * This file is for you to execute your program.
 * It will not be marked.
 * @provided
 */
public class Main {
    private static Sheet defaultSheet(SheetBuilder builder) {
        try {
            Sheet sheet = builder.empty(20, 10);
            new Fibonacci(20).draw(sheet);
            new Pascal(4, 2).draw(sheet);
            return sheet;
        } catch (FunException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Start the spreadsheet program.
     *
     * @param uiType the type of UI to use: between TEXT, LEGACY, or default
     */
    private static void sheep(String uiType) {
        ExpressionFactory factory = new CoreFactory();
        Parser parser = new ComplexParser(factory);

        SheetBuilder builder = new SheetBuilder(parser, factory.createEmpty());
        builder.includeBuiltIn("life", factory.createConstant(42));

        run(uiType, builder, defaultSheet(builder));
    }

    private static UI selectUI(String uiType, Sheet sheet) {
        return switch (uiType.toUpperCase()) {
            case "LEGACY", "OLD", "SWIFT" -> new GUI(sheet, sheet);
            default -> new JFXUI(sheet, sheet);
        };
    }

    private static void run(String uiType, SheetBuilder builder, Sheet sheet) {
        UI ui = selectUI(uiType, sheet);

        ui.addFeature("File", "save", "Save As...", (row, column, prompt, view, updater) -> {
            String filepath = prompt.saveFile();
            if (filepath == null) {
                return;
            }
            try (FileWriter writer = new FileWriter(filepath)) {
                writer.write(view.encode());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ui.addFeature("File", "open", "Open...", (row, column, prompt, view, updater) -> {
            try {
                String filepath = prompt.openFile();
                if (filepath == null) {
                    return;
                }
                Sheet newSheet = builder.load(filepath);
                ui.openWindow(newSheet, newSheet);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        try {
            ui.render();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Run the SheeP program.
     *
     * @param argv Arguments to the program
     * @throws Exception If it can't run
     */
    public static void main(String[] argv) throws Exception {
        List<String> args = List.of(argv);
        sheep(args.isEmpty() ? "default" : args.get(0));
    }
}
