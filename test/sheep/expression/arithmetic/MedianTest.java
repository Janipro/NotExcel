package sheep.expression.arithmetic;

import org.junit.Test;
import sheep.expression.Expression;
import sheep.expression.TypeError;
import sheep.expression.basic.Constant;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MedianTest {
    @Test
    public void testMedianIdentityValue() throws TypeError {
        var func = new Median(new Expression[]{new Constant(20)});
        var result = func.value(new HashMap<>());
        assertTrue("Result of evaluating median is not a constant.",
                result instanceof Constant);
        assertEquals("Result of median with a single Constant is incorrect.",
                20, ((Constant) result).getValue());
    }

    @Test
    public void testMedianIdentityPerform() throws TypeError {
        var func = new Median(new Expression[]{new Constant(20)});
        var result = func.perform(new long[]{20});
        assertEquals("Result of performing median with a single Constant is incorrect.", 20, result);
    }

    @Test
    public void testMedianTwoValue() throws TypeError {
        var func = new Median(new Expression[]{new Constant(20), new Constant(20)});
        var result = func.value(new HashMap<>());
        assertTrue("Result of evaluating median is not a constant.",
                result instanceof Constant);
        assertEquals("Result of median with two Constants is incorrect.",
                20, ((Constant) result).getValue());
    }

    @Test
    public void testMedianTwoPerform() throws TypeError {
        var func = new Median(new Expression[]{new Constant(20), new Constant(30)});
        var result = func.perform(new long[]{20, 30});
        assertEquals("Result of performing median with two Constants is incorrect.", 25, result);
    }

    @Test
    public void testMedianTwoEqual() throws TypeError {
        var func = new Median(new Expression[]{new Constant(20), new Constant(20)});
        var result = func.perform(new long[]{20, 20});
        assertEquals("Result of performing median with two Constants is incorrect.", 20, result);
    }

    @Test
    public void testMedianOddOrdered() throws TypeError {
        var func = new Mean(new Expression[]{new Constant(10), new Constant(20), new Constant(30)});
        var result = func.perform(new long[]{10, 20, 30});
        assertEquals("Result of performing mean with two Constants is incorrect.", 20, result);
    }

    @Test
    public void testMedianOddUnordered() throws TypeError {
        var func = new Mean(new Expression[]{new Constant(10), new Constant(30), new Constant(20)});
        var result = func.perform(new long[]{10, 30, 20});
        assertEquals("Result of performing mean with two Constants is incorrect.", 20, result);
    }

    @Test
    public void testMedianEvenOrdered() throws TypeError {
        var func = new Mean(new Expression[]{new Constant(10), new Constant(30)});
        var result = func.perform(new long[]{10, 30});
        assertEquals("Result of performing mean with two Constants is incorrect.", 20, result);
    }

    @Test
    public void testMedianEvenUnordered() throws TypeError {
        var func = new Mean(new Expression[]{new Constant(30), new Constant(10)});
        var result = func.perform(new long[]{30, 10});
        assertEquals("Result of performing mean with two Constants is incorrect.", 20, result);
    }

    @Test
    public void testMedianNValue() throws TypeError {
        var func = new Median(
                new Expression[]{new Constant(20), new Constant(15), new Constant(25), new Constant(10), new Constant(10)}
        );
        var result = func.value(new HashMap<>());
        assertTrue("Result of evaluating median is not a constant.",
                result instanceof Constant);
        assertEquals("Result of median with multiple Constants is incorrect.",
                15, ((Constant) result).getValue());
    }

    @Test
    public void testMedianNPerform() throws TypeError {
        var func = new Median(
                new Expression[]{new Constant(20), new Constant(15), new Constant(25), new Constant(10), new Constant(10)}
        );
        var result = func.perform(new long[]{20, 15, 25, 10, 10});
        assertEquals("Result of performing median with multiple Constants is incorrect",15, result);
    }
}
