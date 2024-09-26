package sheep.expression.arithmetic;

import org.junit.Test;
import sheep.expression.Expression;
import sheep.expression.TypeError;
import sheep.expression.basic.Constant;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MinusTest {
    @Test
    public void testIdentityValue() throws TypeError {
        Arithmetic arith = new Minus(new Expression[]{new Constant(20)});
        Expression result = arith.value(new HashMap<>());
        assertTrue("Result of evaluating minus is not a constant.",
                result instanceof Constant);
        assertEquals("Result of minus with a single Constant is incorrect.",
                20, ((Constant) result).getValue());
    }

    @Test
    public void testIdentityPerform() throws TypeError {
        Arithmetic arith = new Minus(new Expression[]{new Constant(20)});
        long result = arith.perform(new long[]{20});
        assertEquals("Result of performing minus with a single Constant is incorrect.", 20, result);
    }

    @Test
    public void testTwoValue() throws TypeError {
        Arithmetic arith = new Minus(new Expression[]{new Constant(20), new Constant(10)});
        Expression result = arith.value(new HashMap<>());
        assertTrue("Result of evaluating minus is not a constant.",
                result instanceof Constant);
        assertEquals("Result of minus with two Constants is incorrect.",
                10, ((Constant) result).getValue());
    }

    @Test
    public void testTwoPerform() throws TypeError {
        Arithmetic arith = new Minus(new Expression[]{new Constant(20), new Constant(10)});
        long result = arith.perform(new long[]{20, 10});
        assertEquals("Result of performing minus with two Constants is incorrect.", 10, result);
    }

    @Test
    public void testNValue() throws TypeError {
        Arithmetic arith = new Minus(
                new Expression[]{new Constant(20), new Constant(2), new Constant(2), new Constant(2)}
        );
        Expression result = arith.value(new HashMap<>());
        assertTrue("Result of evaluating minus is not a constant.",
                result instanceof Constant);
        assertEquals("Result of minus with multiple Constants is incorrect.",
                14, ((Constant) result).getValue());
    }

    @Test
    public void testNPerform() throws TypeError {
        Arithmetic arith = new Minus(
                new Expression[]{new Constant(20), new Constant(2), new Constant(2), new Constant(2)}
        );
        long result = arith.perform(new long[]{20, 2, 2, 2});
        assertEquals("Result of performing minus with multiple Constants is incorrect",14, result);
    }
}
