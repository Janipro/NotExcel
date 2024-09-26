package sheep.expression.arithmetic;

import org.junit.Test;
import sheep.expression.Expression;
import sheep.expression.TypeError;
import sheep.expression.basic.Constant;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MeanTest {
    @Test
    public void testMeanIdentityValue() throws TypeError {
        var func = new Mean(new Expression[]{new Constant(20)});
        var result = func.value(new HashMap<>());
        assertTrue("Result of evaluating mean is not a constant.",
                result instanceof Constant);
        assertEquals("Result of mean with a single Constant is incorrect.",
                20, ((Constant) result).getValue());
    }

    @Test
    public void testMeanIdentityPerform() throws TypeError {
        var func = new Mean(new Expression[]{new Constant(20)});
        var result = func.perform(new long[]{20});
        assertEquals("Result of performing mean with a single Constant is incorrect.", 20, result);
    }

    @Test
    public void testMeanTwoValue() throws TypeError {
        var func = new Mean(new Expression[]{new Constant(20), new Constant(10)});
        var result = func.value(new HashMap<>());
        assertTrue("Result of evaluating mean is not a constant.",
                result instanceof Constant);
        assertEquals("Result of mean with two Constants is incorrect.",
                15, ((Constant) result).getValue());
    }

    @Test
    public void testMeanTwoPerform() throws TypeError {
        var func = new Mean(new Expression[]{new Constant(20), new Constant(10)});
        var result = func.perform(new long[]{20, 10});
        assertEquals("Result of performing mean with two Constants is incorrect.", 15, result);
    }

    @Test
    public void testMeanNValue() throws TypeError {
        var func = new Mean(
                new Expression[]{new Constant(20), new Constant(15), new Constant(15), new Constant(10)}
        );
        var result = func.value(new HashMap<>());
        assertTrue("Result of evaluating mean is not a constant.",
                result instanceof Constant);
        assertEquals("Result of mean with multiple Constants is incorrect.",
                15, ((Constant) result).getValue());
    }

    @Test
    public void testMeanNPerform() throws TypeError {
        var func = new Mean(
                new Expression[]{new Constant(20), new Constant(15), new Constant(15), new Constant(10)}
        );
        var result = func.perform(new long[]{20, 15, 15, 10});
        assertEquals("Result of performing mean with multiple Constants is incorrect",15, result);
    }
}
