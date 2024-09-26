package sheep.expression;

import org.junit.Test;

import static org.junit.Assert.*;

public class InvalidExpressionTest {
    @Test
    public void throwExceptionNoMessage() {
        String message = "";
        try {
            throw new InvalidExpression();
        } catch (InvalidExpression e) {
            message = e.getMessage();
        }
        assertNull("Message should be null when no message is given.", message);
    }

    @Test
    public void throwExceptionWithMessage() {
        String message = "";
        try {
            throw new InvalidExpression("Unable to create an expression.");
        } catch (InvalidExpression e) {
            message = e.getMessage();
        }
        assertEquals("Exception gave the incorrect message.",
                "Unable to create an expression.",
                message);
    }

    @Test
    public void throwExceptionWithCause() {
        Throwable actual = null;
        Exception expected = new InvalidExpression();
        try {
            throw new InvalidExpression(expected);
        } catch (InvalidExpression e) {
            actual = e.getCause();
        }
        assertEquals("Exception gave incorrect cause.", expected, actual);
    }
}
