package sheep.parsing;

import org.junit.Before;
import org.junit.Test;
import sheep.expression.Expression;

import static org.junit.Assert.assertEquals;

public class SimpleParserTest {
    private Parser parser;

    @Before
    public void setUp() {
        parser = new SimpleParser(new EchoFactory());
    }

    /**
     * Assert that parsing the empty string returns the
     * result of `createEmpty()` from the parsers factory.
     * Note: This is not necessarily an instance of `Nothing`.
     */
    @Test
    public void testNothing() throws ParseException {
        Expression result = parser.parse("");
        assertEquals("Parsing an empty string did not return result of createEmpty().",
                "(Empty)", result.toString());
    }

    @Test
    public void testNothingSpace() throws ParseException {
        Expression result = parser.parse(" ");
        assertEquals("Parsing single whitespace did not return result of createEmpty().",
                "(Empty)", result.toString());
    }

    @Test
    public void testNothingWhiteSpace() throws ParseException {
        Expression result = parser.parse("\t    ");
        assertEquals("Parsing multiple whitespaces did not return result of createEmpty().",
                "(Empty)", result.toString());
    }

    @Test
    public void testConstant() throws ParseException {
        Expression expression = parser.parse("42");
        assertEquals("Parsing a constant value did not return result of createConstant(long).",
                "(Constant: 42)", expression.toString());
    }

    @Test
    public void testConstantWhitespace() throws ParseException {
        Expression expression = parser.parse("  42\t");
        assertEquals("Parsing a constant surrounded by whitespace did not return result of createConstant(long).",
                "(Constant: 42)", expression.toString());
    }

    /**
     * Assert that parsing the string "42.0" results in a `ParseException`.
     */
    @Test(expected = ParseException.class)
    public void testConstantFloat() throws ParseException {
        Expression expression = parser.parse("42.0");
    }

    @Test
    public void testConstantNegative() throws ParseException {
        Expression expression = parser.parse("-42");
        assertEquals("Parsing a negative constant did not return result of createConstant(long).",
                "(Constant: -42)", expression.toString());
    }

    @Test
    public void testConstantStripZeros() throws ParseException {
        Expression expression = parser.parse("00000");
        assertEquals("Parsing multiple zeros did not return result of createConstant(long).",
                "(Constant: 0)", expression.toString());
    }

    @Test
    public void testConstantNegativeSpace() throws ParseException {
        Expression expression = parser.parse("   -42");
        assertEquals("Parsing a negative constant with whitespace did not return result of createConstant(long).",
                "(Constant: -42)", expression.toString());
    }

    @Test
    public void testArithmeticPlus() throws ParseException {
        Expression expression = parser.parse("3 + 20 + 12 + 100");
        assertEquals("Parsing addition expression with whitespace did not return result of creatOperator.",
                "(Operator: + [(Constant: 3), (Constant: 20), (Constant: 12), (Constant: 100)])",
                expression.toString());
    }

    @Test
    public void testArithmeticPlusNoSpace() throws ParseException {
        Expression expression = parser.parse("3+20+12+100");
        assertEquals("Parsing addition expression did not return result of creatOperator.",
                "(Operator: + [(Constant: 3), (Constant: 20), (Constant: 12), (Constant: 100)])",
                expression.toString());
    }

    @Test
    public void testArithmeticPlusMixedSpace() throws ParseException {
        Expression expression = parser.parse("3+ 20 +12+ 100");
        assertEquals("Parsing addition expression with whitespace did not return result of creatOperator.",
                "(Operator: + [(Constant: 3), (Constant: 20), (Constant: 12), (Constant: 100)])",
                expression.toString());
    }

    @Test
    public void testArithmeticTimesMixedSpace() throws ParseException {
        Expression expression = parser.parse("3* 20 *12* 100");
        assertEquals("Parsing multiplication expression with whitespace did not return result of creatOperator.",
                "(Operator: * [(Constant: 3), (Constant: 20), (Constant: 12), (Constant: 100)])",
                expression.toString());
    }

    @Test
    public void testArithmeticNested() throws ParseException {
        Expression expression = parser.parse("3* 2 * 20 - 2/15 +12* 100");
        assertEquals("Parsing mixed expression with whitespace did not return result of creatOperator.",
                "(Operator: + [(Operator: - [(Operator: * [(Constant: 3), (Constant: 2), (Constant: 20)]), (Operator: / [(Constant: 2), (Constant: 15)])]), (Operator: * [(Constant: 12), (Constant: 100)])])",
                expression.toString());
    }

    @Test
    public void testReference() throws ParseException {
        Expression expression = parser.parse("A0");
        assertEquals("Parsing a reference string did not return result of createReference.",
                "(Reference: A0)", expression.toString());
    }

    @Test
    public void testOddReference() throws ParseException {
        Expression expression = parser.parse("sd45678fghjk");
        assertEquals("Parsing a reference string did not return result of createReference.",
                "(Reference: sd45678fghjk)", expression.toString());
    }

    @Test
    public void testReferenceSpaces() throws ParseException {
        Expression expression = parser.parse("   OO  ");
        assertEquals("Parsing a reference string with whitespace did not return result of createReference.",
                "(Reference: OO)", expression.toString());
    }

    /**
     * Assert that parsing "   O_O  " throws a ParseException.
     */
    @Test(expected = ParseException.class)
    public void testInvalidChars() throws ParseException {
        Expression expression = parser.parse("   O_O  ");
    }

    /**
     * Assert that parsing "_ =_" throws a ParseException.
     */
    @Test(expected = ParseException.class)
    public void testInvalidChars1() throws ParseException {
        Expression expression = parser.parse("_ =_");
    }
}
