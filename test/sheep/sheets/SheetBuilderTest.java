package sheep.sheets;

import sheep.expression.Expression;
import sheep.expression.TypeError;
import sheep.expression.basic.Constant;
import sheep.parsing.ParseException;
import sheep.parsing.Parser;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

class DummyExpression extends Expression {
    @Override
    public String toString() {
        return "DummyExpression";
    }

    @Override
    public Set<String> dependencies() {
        return new HashSet<>();
    }

    @Override
    public long value() throws TypeError {
        return 0;
    }

    @Override
    public Expression value(Map<String, Expression> state) throws TypeError {
        return this;
    }

    @Override
    public String render() {
        return this.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        DummyExpression exp = (DummyExpression) object;
        return "DummyExpression".equals(exp.toString());
    }
}

class DummyParser implements Parser {
    @Override
    public Expression parse(String input) throws ParseException {
        return new DummyExpression();
    }
}


public class SheetBuilderTest {
    public static final int NUM_ROWS = 5;
    public static final int NUM_COLUMNS = 7;

    @Rule
    public Timeout timeout = new Timeout(60000);

    private SheetBuilder builder;
    private Sheet sheet;

    @Before
    public void setUp() throws Exception {
        Parser parser = new EchoParser();
        DummyExpression dummyExpression = new DummyExpression();
        builder = new SheetBuilder(parser, dummyExpression);
        sheet = builder.empty(NUM_ROWS, NUM_COLUMNS);
    }

    @Test
    public void testEmpty() {
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int column = 0; column < NUM_COLUMNS; column++) {
                assertEquals("Newly created empty sheet did not contain the dummy expression in all cells.",
                        new DummyExpression(), sheet.formulaAt(new CellLocation(row, column)));
            }
        }
    }

    @Test
    public void testAddBuiltin() throws TypeError {
        builder = builder.includeBuiltIn("dood", new FormulaExpr("3490524077"));
        Sheet sheet = builder.empty(NUM_ROWS, NUM_COLUMNS);

        sheet.update(new CellLocation(1, 1), new RefExpr("dood"));
        assertEquals("Formula retrieved from cell is not the built-in inserted into the cell.",
                "Ref(dood)", sheet.formulaAt(1, 1).getContent());
        assertEquals("Value retrieved from cell is not the value of the built-in inserted into the cell.",
                "Value(3490524077)", sheet.valueAt(1, 1).getContent());
    }

    @Test
    public void testBuiltSheetDimensions() {
        assertEquals("SheetBuilder created sheet with incorrect number of rows.",
                NUM_ROWS, sheet.getRows());
        assertEquals("SheetBuilder created sheet with incorrect number of columns.",
                NUM_COLUMNS, sheet.getColumns());
    }

    @Test
    public void testBuiltSheetInitialValues() {
        for (int row = 0; row < sheet.getRows(); row++) {
            for (int column = 0; column < sheet.getColumns(); column++) {
                assertEquals("SheetBuilder created sheet with incorrect initial value at ("
                             + row + ", " + column + ").",
                             new DummyExpression().render(), sheet.valueAt(row, column).getContent());
            }
        }
    }
}