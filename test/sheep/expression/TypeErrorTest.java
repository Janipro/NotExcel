package sheep.expression;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TypeErrorTest {
    @Test
    public void throwExceptionNoMessage() {
        String message = "";
        try {
            throw new TypeError();
        } catch (TypeError e) {
            message = e.getMessage();
        }
        assertNull("Message should be null when no message is given.", message);
    }

    @Test
    public void throwExceptionWithMessage() {
        String message = "";
        try {
            throw new TypeError("Unable to create an expression.");
        } catch (TypeError e) {
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
            throw new TypeError(expected);
        } catch (TypeError e) {
            actual = e.getCause();
        }
        assertEquals("Exception gave incorrect cause.", expected, actual);
    }
}
