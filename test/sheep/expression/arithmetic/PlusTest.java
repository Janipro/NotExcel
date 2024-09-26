package sheep.expression.arithmetic;

import org.junit.Test;
import sheep.expression.Expression;
import sheep.expression.TypeError;
import sheep.expression.basic.Constant;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlusTest {
    @Test
    public void testIdentityValue() throws TypeError {
        Arithmetic arith = new Plus(new Expression[]{new Constant(20)});
        Expression result = arith.value(new HashMap<>());
        assertTrue("Result of evaluating plus is not a constant.",
                result instanceof Constant);
        assertEquals("Result of plus with a single Constant is incorrect.",
                20, ((Constant) result).getValue());
    }

    @Test
    public void testIdentityPerform() throws TypeError {
        Arithmetic arith = new Plus(new Expression[]{new Constant(20)});
        long result = arith.perform(new long[]{20});
        assertEquals("Result of performing plus with a single Constant is incorrect.",20, result);
    }

    @Test
    public void testTwoValue() throws TypeError {
        Arithmetic arith = new Plus(new Expression[]{new Constant(20), new Constant(10)});
        Expression result = arith.value(new HashMap<>());
        assertTrue("Result of evaluating plus is not a constant.",
                result instanceof Constant);
        assertEquals("Result of plus with two Constants is incorrect.",
                30, ((Constant) result).getValue());
    }

    @Test
    public void testTwoPerform() throws TypeError {
        Arithmetic arith = new Plus(new Expression[]{new Constant(20), new Constant(10)});
        long result = arith.perform(new long[]{20, 10});
        assertEquals("Result of performing plus with two Constants is incorrect.", 30, result);
    }

    @Test
    public void testNValue() throws TypeError {
        Arithmetic arith = new Plus(
                new Expression[]{new Constant(20), new Constant(2), new Constant(2), new Constant(2)}
        );
        Expression result = arith.value(new HashMap<>());
        assertTrue("Result of evaluating plus is not a constant.",
                result instanceof Constant);
        assertEquals("Result of plus with multiple Constants is incorrect.",
                26, ((Constant) result).getValue());
    }

    @Test
    public void testNPerform() throws TypeError {
        Arithmetic arith = new Plus(
                new Expression[]{new Constant(20), new Constant(2), new Constant(2), new Constant(2)}
        );
        long result = arith.perform(new long[]{20, 2, 2, 2});
        assertEquals("Result of performing plus with multiple Constants is incorrect.", 26, result);
    }
}
