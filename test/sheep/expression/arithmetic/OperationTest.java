package sheep.expression.arithmetic;

import org.junit.Before;
import org.junit.Test;
import sheep.expression.Expression;
import sheep.expression.TypeError;
import sheep.expression.basic.Constant;
import sheep.expression.basic.Reference;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;


class ArithmeticExpression extends Arithmetic {
    public ArithmeticExpression(Expression[] arguments) {
        super("^", arguments);
    }

    @Override
    protected long perform(long[] arguments) {
        return 0;
    }
}


public class OperationTest {
    private Operation base;

    @Before
    public void setUp() {
        base = new ArithmeticExpression(new Expression[]{new Constant(1)});
    }

    @Test
    public void testDependencies() {
        assertEquals("Expression only containing a constant incorrectly has dependencies.",
                new HashSet<>(), base.dependencies());
    }

    @Test
    public void testOneDependency() {
        base = new ArithmeticExpression(new Expression[]{new Constant(1), new Reference("Hi")});
        assertEquals("Did not identify the reference dependency.",
                new HashSet<>(Collections.singleton("Hi")), base.dependencies());
    }

    @Test
    public void testMultipleDependencies() {
        base = new ArithmeticExpression(new Expression[]{new Reference("Hello"), new Reference("Hi")});
        assertEquals("Did not identify both reference dependencies.",
                new HashSet<>(List.of("Hello", "Hi")), base.dependencies());
    }

    @Test
    public void testNestedDependencies() {
        base = new ArithmeticExpression(new Expression[]{new ArithmeticExpression(new Expression[]{new Reference("Hello")}), new Reference("Hi")});
        assertEquals("Did not identify both reference dependencies in a more complex expression.",
                new HashSet<>(List.of("Hello", "Hi")), base.dependencies());
    }

    @Test
    public void testDivide() {
        base = Arithmetic.divide(new Expression[]{new Constant(1), new Reference("A0")});
        assertTrue("Arithmetic.divide does not produce a Divide instance",
                base instanceof Divide);
    }

    @Test
    public void testEqual() {
        base = Arithmetic.equal(new Expression[]{new Constant(1), new Reference("A0")});
        assertTrue("Arithmetic.equal does not produce a Equal instance",
                base instanceof Equal);
    }

    @Test
    public void testLess() {
        base = Arithmetic.less(new Expression[]{new Constant(1), new Reference("A0")});
        assertTrue("Arithmetic.less does not produce a Less instance",
                base instanceof Less);
    }

    @Test
    public void testMinus() {
        base = Arithmetic.minus(new Expression[]{new Constant(1), new Reference("A0")});
        assertTrue("Arithmetic.minus does not produce a Minus instance",
                base instanceof Minus);
    }

    @Test
    public void testPlus() {
        base = Arithmetic.plus(new Expression[]{new Constant(1), new Reference("A0")});
        assertTrue("Arithmetic.plus does not produce a Plus instance",
                base instanceof Plus);
    }

    @Test
    public void testTimes() {
        base = Arithmetic.times(new Expression[]{new Constant(1), new Reference("A0")});
        assertTrue("Arithmetic.times does not produce a Times instance",
                base instanceof Times);
    }

    @Test
    public void testCallPerform() throws TypeError {
        final boolean[] calledRight = {false};
        class Exp extends Arithmetic {
            public Exp(Expression[] arguments) {
                super("^", arguments);
            }

            @Override
            protected long perform(long[] arguments) {
                if (arguments.length == 2) {
                    if (arguments[0] == 4 && arguments[1] == 6) {
                        calledRight[0] = true;
                    }
                }
                return 0;
            }
        }

        Arithmetic exp = new Exp(new Expression[]{new Constant(4), new Constant(6)});
        Expression result = exp.value(new HashMap<>());

        assertTrue("The perform abstract method is not correctly called by Arithmetic",
                calledRight[0]);
        assertTrue("Result of value(Map<String, Expression>) is not a constant", result instanceof Constant);
        assertEquals("Result of value(Map<String, Expression>) is not zero", 0, ((Constant) result).getValue());
    }

    @Test
    public void testRenderOne() {
        assertEquals("Expression only containing a constant did not render it correctly.",
                "1", base.render());
    }

    @Test
    public void testRenderTwo() {
        Expression exp = new ArithmeticExpression(new Expression[]{new Constant(2), new Constant(3)});
        assertEquals("Expression with one operator did not render correctly.",
                "2 ^ 3", exp.render());
    }

    @Test
    public void testRenderMultiple() {
        Expression exp = new ArithmeticExpression(new Expression[]{new Constant(2), new Constant(3), new Constant(4), new Constant(5)});
        assertEquals("Expression with multiple operators did not render correctly.",
                "2 ^ 3 ^ 4 ^ 5", exp.render());
    }

    @Test
    public void testToStringOne() {
        assertEquals("Arithmetic.toString did not return correct representation of the constant.",
                "1", base.toString());
    }

    @Test
    public void testToStringTwo() {
        Expression exp = new ArithmeticExpression(new Expression[]{new Constant(2), new Constant(3)});
        assertEquals("Arithmetic.toString did not return correct representation of the expression.",
                "2 ^ 3", exp.toString());
    }

    @Test
    public void testToStringMultiple() {
        Expression exp = new ArithmeticExpression(new Expression[]{new Constant(2), new Constant(3), new Constant(4), new Constant(5)});
        assertEquals("Arithmetic.toString did not return correct representation of the complex expression.",
                "2 ^ 3 ^ 4 ^ 5", exp.toString());
    }

    @Test(expected = TypeError.class)
    public void testValue() throws TypeError {
        base.value();
    }
}
