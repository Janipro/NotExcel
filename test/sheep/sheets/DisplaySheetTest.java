package sheep.sheets;

import org.junit.Before;
import org.junit.Test;
import sheep.core.UpdateResponse;
import sheep.core.ViewElement;
import sheep.expression.*;
import sheep.parsing.ParseException;
import sheep.parsing.Parser;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

class BaseExpr extends Expression {
    private final String id;

    public BaseExpr(String id) {
        this.id = id;
    }

    @Override
    public Set<String> dependencies() {
        return new HashSet<>();
    }

    @Override
    public Expression value(Map<String, Expression> state) throws TypeError {
        throw new RuntimeException("Display sheet must not try to evaluate expressions.");
    }

    @Override
    public long value() throws TypeError {
        throw new RuntimeException("Display sheet must not try to evaluate expressions.");
    }

    @Override
    public String render() {
        return "EXPR(" + id + ")";
    }
}

public class DisplaySheetTest {
    private Parser parser;
    private Expression defaultE;

    @Before
    public void setup() {
        this.parser = input -> {
            if (input.equals("E")) {
                throw new ParseException();
            }
            return new BaseExpr(input);
        };
        this.defaultE = new BaseExpr("DEFAULT");
    }

    /**
     * Creates three DisplaySheet instances with columns=2 and rows=2,10,24.
     * Then assert that getRows returns the correct number 2, 10, or 24.
     */
    @Test
    public void rows() {
        DisplaySheet sheet = new DisplaySheet(parser, defaultE, 2, 2);
        assertEquals("Sheet created with 2 rows did not return 2 from getRows().",
                2, sheet.getRows());

        sheet = new DisplaySheet(parser, defaultE, 10, 2);
        assertEquals("Sheet created with 10 rows did not return 10 from getRows().",
                10, sheet.getRows());

        sheet = new DisplaySheet(parser, defaultE, 24, 2);
        assertEquals("Sheet created with 24 rows did not return 24 from getRows().",
                24, sheet.getRows());
    }

    /**
     * Creates three DisplaySheet instances with rows=2 and columns=2,10,24.
     * Then assert that getColumns returns the correct number 2, 10, or 24.
     */
    @Test
    public void columns() {
        DisplaySheet sheet = new DisplaySheet(parser, defaultE, 2, 2);
        assertEquals("Sheet created with 2 columns did not return 2 from getColumns().",
                2, sheet.getColumns());

        sheet = new DisplaySheet(parser, defaultE, 2, 10);
        assertEquals("Sheet created with 10 columns did not return 10 from getColumns().",
                10, sheet.getColumns());

        sheet = new DisplaySheet(parser, defaultE, 2, 24);
        assertEquals("Sheet created with 24 columns did not return 24 from getColumns().",
                24, sheet.getColumns());
    }

    /**
     * Construct a new DisplaySheet with a default expression that renders as
     * "EXPR(DEFAULT)".
     * Go through every cell location and assert that the ViewElement returned
     * by `valueAt` has "EXPR(DEFAULT)" as the content.
     */
    @Test
    public void populatedWithDefaultValues() {
        DisplaySheet sheet = new DisplaySheet(parser, defaultE, 2, 2);

        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 2; column++) {
                ViewElement element = sheet.valueAt(row, column);
                assertEquals("Cell in newly created DisplaySheet did not contain the default expression.",
                        "EXPR(DEFAULT)", element.getContent());
            }
        }
    }

    /**
     * Construct a new DisplaySheet with a default expression that renders as
     * "EXPR(DEFAULT)".
     * Go through every cell location and assert that the ViewElement returned
     * by `formulaAt` has "EXPR(DEFAULT)" as the content.
     */
    @Test
    public void populatedWithDefaultFormulae() {
        DisplaySheet sheet = new DisplaySheet(parser, defaultE, 2, 2);

        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 2; column++) {
                ViewElement element = sheet.formulaAt(row, column);
                assertEquals("Cell in newly created DisplaySheet did not render the default expression.",
                        "EXPR(DEFAULT)", element.getContent());
            }
        }
    }

    /**
     * Construct a new DisplaySheet with a custom parser that
     * parses any string into an expression that renders as EXPR(x) where
     * x is the input string.
     * Insert "NEW" at row=1, column=1 - check response is success.
     * Assert that valueAt content contains "EXPR(NEW)".
     * <p>
     * Perform similar tests for NEW2 at row=0, column=1
     * and NEW3 at row=1, column=1.
     */
    @Test
    public void updateValue() {
        DisplaySheet sheet = new DisplaySheet(parser, defaultE, 2, 2);
        UpdateResponse response = sheet.update(1, 1, "NEW");
        assertTrue("Updating DisplaySheet failed.", response.isSuccess());
        assertEquals("Cell in DisplaySheet did not contain expression inserted into cell.",
                "EXPR(NEW)", sheet.valueAt(1, 1).getContent());

        response = sheet.update(0, 1, "NEW2");
        assertTrue("Updating DisplaySheet failed.", response.isSuccess());
        assertEquals("Cell in DisplaySheet did not contain expression inserted into cell.",
                "EXPR(NEW2)", sheet.valueAt(0, 1).getContent());

        response = sheet.update(1, 1, "NEW3");
        assertTrue("Updating DisplaySheet failed.", response.isSuccess());
        assertEquals("Updated cell in DisplaySheet did not contain expression inserted into cell.",
                "EXPR(NEW3)", sheet.valueAt(1, 1).getContent());
    }

    /**
     * Construct a new DisplaySheet with a custom parser that
     * parses any string into an expression that renders as EXPR(x) where
     * x is the input string.
     * Insert "NEW2" at row=1, column=1 - check response is success.
     * Assert that formulaAt content contains "EXPR(NEW2)".
     * <p>
     * Perform similar tests for NEW at row=0, column=1
     * and NEW3 at row=1, column=1.
     */
    @Test
    public void updateFormula() {
        DisplaySheet sheet = new DisplaySheet(parser, defaultE, 2, 2);
        UpdateResponse response = sheet.update(1, 1, "NEW2");
        assertTrue("Updating DisplaySheet failed.", response.isSuccess());
        assertEquals("Cell in DisplaySheet did not render expression inserted into cell.",
                "EXPR(NEW2)", sheet.formulaAt(1, 1).getContent());

        response = sheet.update(0, 1, "NEW");
        assertTrue("Updating DisplaySheet failed.", response.isSuccess());
        assertEquals("Cell in DisplaySheet did not render expression inserted into cell.",
                "EXPR(NEW)", sheet.formulaAt(0, 1).getContent());

        response = sheet.update(1, 1, "NEW3");
        assertTrue("Updating DisplaySheet failed.", response.isSuccess());
        assertEquals("Updated cell in DisplaySheet did not render expression inserted into cell.",
                "EXPR(NEW3)", sheet.formulaAt(1, 1).getContent());
    }

    /**
     * Construct a new DisplaySheet with a custom parser that
     * throws a ParseException when the input is "E".
     * <p>
     * Insert "E" at row=1, column=2 and assert that the update is not successful
     * and the message is "Unable to parse: E".
     */
    @Test
    public void cannotParse() {
        DisplaySheet sheet = new DisplaySheet(parser, defaultE, 2, 2);
        UpdateResponse response = sheet.update(1, 1, "E");
        assertFalse("Allowed invalid input to be parsed successfully.", response.isSuccess());
        assertEquals("UpdateResponse object did not contain correct error message.",
                "Unable to parse: E", response.getMessage());
    }
}
