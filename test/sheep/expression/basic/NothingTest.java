package sheep.expression.basic;

import org.junit.Before;
import org.junit.Test;
import sheep.expression.TypeError;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

public class NothingTest {
    private Nothing base;

    @Before
    public void setUp() {
        base = new Nothing();
    }

    @Test
    public void testDependencies() {
        assertEquals("Nothing incorrectly has dependencies.", new HashSet<>(), base.dependencies());
    }

    @Test
    public void testRender() {
        assertEquals("Nothing did not render as an empty string.", "", base.render());
    }

    @Test
    public void testToString() {
        assertEquals("Nothing.toString did not return correct representation.",
                "NOTHING", base.toString());
    }

    @Test(expected = TypeError.class)
    public void testValue() throws TypeError {
        base.value();
    }

    @Test
    public void testValueState() throws TypeError {
        assertEquals(base, base.value(new HashMap<>()));
    }
}
