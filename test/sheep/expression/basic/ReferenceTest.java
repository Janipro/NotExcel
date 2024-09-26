package sheep.expression.basic;

import org.junit.Before;
import org.junit.Test;
import sheep.expression.Expression;
import sheep.expression.TypeError;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.*;

public class ReferenceTest {
    private Reference base;
    private Reference other;
    private Reference same;

    @Before
    public void setUp() {
        base = new Reference("A0");
        other = new Reference("A1");
        same = new Reference("A0");
    }

    @Test
    public void testDependencies() {
        assertEquals("Reference has incorrect dependencies: " + base.dependencies() + ".",
                new HashSet<>(Collections.singleton("A0")), base.dependencies());
        assertEquals("Reference has incorrect dependencies: " + base.dependencies() + ".",
                new HashSet<>(Collections.singleton("A1")), other.dependencies());
        assertEquals("Reference has incorrect dependencies: " + base.dependencies() + ".",
                new HashSet<>(Collections.singleton("A0")), same.dependencies());
    }

    @Test
    public void testEquals() {
        assertNotEquals("References with different identifiers are evaluated as equal to each other.",
                other, base);
        assertEquals("References with the same identifiers are not evaluated as equal to each other.",
                same, base);
        assertNotEquals("References with different identifiers are evaluated as equal to each other.",
                other, same);
    }

    @Test
    public void testGetIdentifier() {
        assertEquals("Reference did not return correct identifier.", "A0", base.getIdentifier());
        assertEquals("Reference did not return correct identifier.", "A1", other.getIdentifier());
        assertEquals("Reference did not return correct identifier.", "A0", same.getIdentifier());
    }

    @Test
    public void testHashCode() {
        assertEquals("References with the same identifier do not have the same hash code.",
                base.hashCode(), same.hashCode());
    }

    @Test
    public void testIsReference() {
        assertTrue("Does not correctly identify that it is a Reference.", base.isReference());
        assertTrue("Does not correctly identify that it is a Reference.", other.isReference());
        assertTrue("Does not correctly identify that it is a Reference.", same.isReference());
    }

    @Test
    public void testRender() {
        assertEquals("Reference did not render the correct identifier.", "A0", base.render());
        assertEquals("Reference did not render the correct identifier.", "A1", other.render());
        assertEquals("Reference did not render the correct identifier.", "A0", same.render());
    }

    @Test
    public void testToString() {
        assertEquals("Reference.toString did not return correct representation of the identifier.",
                "REFERENCE(A0)", base.toString());
        assertEquals("Reference.toString did not return correct representation of the identifier.",
                "REFERENCE(A1)", other.toString());
        assertEquals("Reference.toString did not return correct representation of the identifier.",
                "REFERENCE(A0)", same.toString());
    }

    @Test(expected = TypeError.class)
    public void testValue() throws TypeError {
        base.value();
    }

    @Test(expected = TypeError.class)
    public void testValue2() throws TypeError {
        other.value();
    }

    @Test
    public void testValueStateNoValue() throws TypeError {
        assertEquals(base, base.value(new HashMap<>()));
        assertEquals(other, other.value(new HashMap<>()));
        assertEquals(same, same.value(new HashMap<>()));
        assertEquals(base, same.value(new HashMap<>()));
    }

    @Test
    public void testValueState() throws TypeError {
        Map<String, Expression> state = new HashMap<>();
        Nothing nothing = new Nothing();
        state.put("A0", new Reference("A2"));
        state.put("A1", nothing);
        state.put("A2", nothing);
        assertEquals(nothing, base.value(state));
        assertEquals(nothing, other.value(state));
        assertEquals(nothing, same.value(state));
        assertEquals(nothing, same.value(state));
    }
}
