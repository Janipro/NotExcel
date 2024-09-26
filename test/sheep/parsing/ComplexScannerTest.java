package sheep.parsing;

import org.junit.Test;

import static org.junit.Assert.*;

// [6] Some methods have been inspired from SimpleParserTest provided by A1
public class ComplexScannerTest {

    /**
     * Assert that parsing the empty string returns the
     * result of `createEmpty()` from the parsers factory.
     * Note: This is not necessarily an instance of `Nothing`.
     */
    @Test
    public void testNothing() throws ParseException {
        var tokens = ComplexScanner.tokenize("");
        assertTrue("Parsing an empty string did not return an empty token list.", tokens.isEmpty());
    }

    @Test
    public void testNothingSpace() throws ParseException {
        var tokens = ComplexScanner.tokenize(" ");
        assertTrue("Parsing single whitespace did not return an empty token list.", tokens.isEmpty());
    }

    @Test
    public void testEmptyParenthesesName() throws ParseException {
        var tokens = ComplexScanner.tokenize("()");
        assertEquals("The parser does not give FUNC the correct name.",
                "", tokens.getFirst().name());
    }

    @Test
    public void testEmptyParenthesesContents() throws ParseException {
        var tokens = ComplexScanner.tokenize("()");
        assertEquals("The parser does not give FUNC the correct name.",
                "", tokens.getFirst().contents());
    }

    @Test
    public void testEmptyParenthesesType() throws ParseException {
        var tokens = ComplexScanner.tokenize("()");
        assertEquals("The parser does not give parse () to a FUNC",
                ComplexScanner.TokenType.FUNC, tokens.getFirst().type());
    }

    @Test
    public void testFunctionType() throws ParseException {
        var tokens = ComplexScanner.tokenize("FUNCTION()");
        assertEquals("The parser does not give parse string with parentheses to a FUNC",
                ComplexScanner.TokenType.FUNC, tokens.getFirst().type());
    }


    @Test
    public void testOperatorType() throws ParseException {
        var randomNumber = (int) Math.round(Math.random()*ComplexScanner.OPERATORS.size());
        var choice = ComplexScanner.OPERATORS.get(randomNumber).toString();
        var tokens = ComplexScanner.tokenize(choice);
        assertEquals("The parser does not parse " + choice + " to an OP",
                ComplexScanner.TokenType.OP, tokens.getFirst().type());
    }

    @Test
    public void testOperatorContents() throws ParseException {
        var randomNumber = (int) Math.round(Math.random()*ComplexScanner.OPERATORS.size());
        var choice = ComplexScanner.OPERATORS.get(randomNumber).toString();
        var tokens = ComplexScanner.tokenize(choice);
        assertNull("The parser does not parse " + choice + " to an OP with correct content",
                tokens.getFirst().contents());
    }

    @Test
    public void testOperatorName() throws ParseException {
        var randomNumber = (int) Math.round(Math.random()*ComplexScanner.OPERATORS.size());
        var choice = ComplexScanner.OPERATORS.get(randomNumber).toString();
        var tokens = ComplexScanner.tokenize(choice);
        assertEquals("The parser does not parse " + choice + " to an OP with correct name",
                choice, tokens.getFirst().name());
    }

    @Test
    public void testConstType() throws ParseException {
        var tokens = ComplexScanner.tokenize("0");
        assertEquals("The parser does not parse CONST correctly",
                ComplexScanner.TokenType.CONST, tokens.getFirst().type());
    }

    @Test
    public void testConstName() throws ParseException {
        var tokens = ComplexScanner.tokenize("0");
        assertEquals("The parser does give CONST the correct name",
                "0", tokens.getFirst().name());
    }

    @Test
    public void testConstContents() throws ParseException {
        var tokens = ComplexScanner.tokenize("0");
        assertNull("The parser does give CONST the correct contents", tokens.getFirst().contents());
    }

    @Test
    public void testReferenceType() throws ParseException {
        var tokens = ComplexScanner.tokenize("TEST");
        assertEquals("The parser does not parse reference to REFERENCE",
                ComplexScanner.TokenType.REFERENCE, tokens.getFirst().type());
    }

    @Test
    public void testReferenceName() throws ParseException {
        var tokens = ComplexScanner.tokenize("TEST");
        assertEquals("The parser does give REFERENCE the correct name",
                "TEST", tokens.getFirst().name());
    }

    @Test
    public void testReferenceContents() throws ParseException {
        var tokens = ComplexScanner.tokenize("TEST");
        assertNull("The parser does give REFERENCE the correct contents", tokens.getFirst().contents());
    }

    @Test
    public void testNoFunctionNames() throws ParseException {
        var function = "NAME(CONTENTS)";
        var tokens = ComplexScanner.tokenize(function);
        assertEquals("The parser does not give FUNC the correct name",
        "NAME", tokens.getFirst().name());
    }

    @Test
    public void testNoFunctionNamesContents() throws ParseException {
        var function = "NAME(CONTENTS)";
        var tokens = ComplexScanner.tokenize(function);
        assertEquals("The parser does not give FUNC the correct contents",
                "CONTENTS", tokens.getFirst().contents());
    }
}
