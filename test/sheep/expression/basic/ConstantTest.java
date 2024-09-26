package sheep.expression.basic;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.*;

public class ConstantTest {
    private Constant base;
    private Constant other;
    private Constant same;

    @Before
    public void setUp() {
        base = new Constant(24);
        other = new Constant(20);
        same = new Constant(24);
    }

    @Test
    public void testDependencies() {
        assertEquals("Constant incorrectly has dependencies.", new HashSet<>(), base.dependencies());
    }

    @Test
    public void testEquals() {
        assertNotEquals("Constants with different values are evaluated as equal to each other.",
                other, base);
        assertEquals("Constants with the same values are not evaluated as equal to each other.",
                same, base);
        assertNotEquals("Constants with different values are evaluated as equal to each other.",
                other, same);
    }

    @Test
    public void testGetValue() {
        assertEquals("Constant did not return correct value.", 24, base.getValue());
        assertEquals("Constant did not return correct value.", 20, other.getValue());
        assertEquals("Constant did not return correct value.", 24, same.getValue());
    }

    @Test
    public void testHashCode() {
        assertEquals("Constants with the same values do not have the same hash code.",
                base.hashCode(), same.hashCode());
    }

    @Test
    public void testRender() {
        assertEquals("Constant did not render the correct value.", "24", base.render());
        assertEquals("Constant did not render the correct value.", "20", other.render());
        assertEquals("Constant did not render the correct value.", "24", same.render());
    }

    @Test
    public void testToString() {
        assertEquals("Constant.toString did not return correct representation of the value.",
                "CONSTANT(24)", base.toString());
        assertEquals("Constant.toString did not return correct representation of the value.",
                "CONSTANT(20)", other.toString());
        assertEquals("Constant.toString did not return correct representation of the value.",
                "CONSTANT(24)", same.toString());
    }

    @Test
    public void testValue() {
        assertEquals("Constant did not return correct value.", 24, base.value());
        assertEquals("Constant did not return correct value.", 20, other.value());
        assertEquals("Constant did not return correct value.", 24, same.value());
    }

    @Test
    public void testValueState() {
        assertEquals(base, base.value(new HashMap<>()));
        assertEquals(other, other.value(new HashMap<>()));
        assertEquals(same, same.value(new HashMap<>()));
        assertEquals(base, same.value(new HashMap<>()));
    }
}
