package sheep.expression;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

class StubExpr extends Expression {
    @Override
    public Set<String> dependencies() {
        return null;
    }

    @Override
    public Expression value(Map<String, Expression> state) throws TypeError {
        return null;
    }

    @Override
    public long value() throws TypeError {
        return 0;
    }

    @Override
    public String render() {
        return null;
    }
}

public class ExpressionTest {
    Class<?> expressionClass;

    @Before
    public void setUp() throws ClassNotFoundException {
        expressionClass = Class.forName("sheep.expression.Expression");
    }

    @Test
    public void testExpressionAbstract() {
        int modifiers = expressionClass.getModifiers();
        assertTrue("Expression class is not abstract.", Modifier.isAbstract(modifiers));
    }

    @Test
    public void testIsReferenceDefault() {
        Expression expr = new StubExpr();
        assertFalse("Expression base class does not return false for isReference.",
                expr.isReference());
    }
}
