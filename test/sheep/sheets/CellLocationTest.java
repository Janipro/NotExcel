package sheep.sheets;

import java.util.Optional;

import org.junit.Test;

import static org.junit.Assert.*;

public class CellLocationTest {
    /**
     * Attempt to construct a CellLocation with row=0 and column=0.
     * Assert appropriate getRow and getColumn values.
     */
    @Test
    public void testNumeric0_0Construction() {
        CellLocation location = new CellLocation(0, 0);
        assertEquals("CellLocation contains the incorrect row value.", 0, location.getRow());
        assertEquals("CellLocation contains the incorrect column value.", 0, location.getColumn());
    }

    /**
     * Attempt to construct a CellLocation with row=10 and column=0.
     * Assert appropriate getRow and getColumn values.
     */
    @Test
    public void testNumeric10_0Construction() {
        CellLocation location = new CellLocation(10, 0);
        assertEquals("CellLocation contains the incorrect row value.", 10, location.getRow());
        assertEquals("CellLocation contains the incorrect column value.", 0, location.getColumn());
    }

    /**
     * Attempt to construct a CellLocation with row=10 and column=24.
     * Assert appropriate getRow and getColumn values.
     */
    @Test
    public void testNumeric10_24Construction() {
        CellLocation location = new CellLocation(10, 24);
        assertEquals("CellLocation contains the incorrect row value.", 10, location.getRow());
        assertEquals("CellLocation contains the incorrect column value.", 24, location.getColumn());
    }

    /**
     * Attempt to construct a CellLocation with row=0 and column='A'.
     * Assert appropriate getRow and getColumn values (0, 0).
     */
    @Test
    public void testChar0_0Construction() {
        CellLocation location = new CellLocation(0, 'A');
        assertEquals("CellLocation contains the incorrect row value.", 0, location.getRow());
        assertEquals("CellLocation contains the incorrect column value.", 0, location.getColumn());
    }

    /**
     * Attempt to construct a CellLocation with row=10 and column='A'.
     * Assert appropriate getRow and getColumn values (10, 0).
     */
    @Test
    public void testChar10_0Construction() {
        CellLocation location = new CellLocation(10, 'A');
        assertEquals("CellLocation contains the incorrect row value.", 10, location.getRow());
        assertEquals("CellLocation contains the incorrect column value.", 0, location.getColumn());
    }

    /**
     * Attempt to construct a CellLocation with row=10 and column='Y'.
     * Assert appropriate getRow and getColumn values (10, 24).
     */
    @Test
    public void testChar10_24Construction() {
        CellLocation location = new CellLocation(10, 'Y');
        assertEquals("CellLocation contains the incorrect row value.", 10, location.getRow());
        assertEquals("CellLocation contains the incorrect column value.", 24, location.getColumn());
    }

    /**
     * Attempt to construct a CellLocation using maybeReference of "A0".
     * Assert that a CellLocation is created and has appropriate getRow() and
     * getColumn() values.
     */
    @Test
    public void testMaybeReferenceA0() {
        Optional<CellLocation> location = CellLocation.maybeReference("A0");
        assertTrue("CellLocation for reference is created.", location.isPresent());
        assertEquals("CellLocation contains the incorrect row value.", 0, location.get().getRow());
        assertEquals("CellLocation contains the incorrect column value.", 0, location.get().getColumn());
    }

    /**
     * Attempt to construct a CellLocation using maybeReference of "A11".
     * Assert that a CellLocation is created and has appropriate getRow() and
     * getColumn() values.
     */
    @Test
    public void testMaybeReferenceA11() {
        Optional<CellLocation> location = CellLocation.maybeReference("A11");
        assertTrue("CellLocation for reference is created.", location.isPresent());
        assertEquals("CellLocation contains the incorrect row value.", 11, location.get().getRow());
        assertEquals("CellLocation contains the incorrect column value.", 0, location.get().getColumn());
    }

    /**
     * Attempt to construct a CellLocation using maybeReference of "F12".
     * Assert that a CellLocation is created and has appropriate getRow() and
     * getColumn() values (12, 5).
     */
    @Test
    public void testMaybeReferenceF12() {
        Optional<CellLocation> location = CellLocation.maybeReference("F12");
        assertTrue("CellLocation for reference is created.", location.isPresent());
        assertEquals("CellLocation contains the incorrect row value.", 12, location.get().getRow());
        assertEquals("CellLocation contains the incorrect column value.", 5, location.get().getColumn());
    }

    /**
     * Attempt to construct a CellLocation using maybeReference of "12F".
     * Assert that a CellLocation cannot be created.
     */
    @Test
    public void testMaybeReference12F() {
        Optional<CellLocation> location = CellLocation.maybeReference("12F");
        assertFalse("Incorrectly created location for an invalid reference.", location.isPresent());
    }

    /**
     * Attempt to construct a CellLocation using maybeReference of "B".
     * Assert that a CellLocation cannot be created.
     */
    @Test
    public void testMaybeReferenceB() {
        Optional<CellLocation> location = CellLocation.maybeReference("B");
        assertFalse("Incorrectly created location for an invalid reference.", location.isPresent());
    }

    /**
     * Attempt to construct a CellLocation using maybeReference of "14".
     * Assert that a CellLocation cannot be created.
     */
    @Test
    public void testMaybeReference14() {
        Optional<CellLocation> location = CellLocation.maybeReference("14");
        assertFalse("Incorrectly created location for an invalid reference.", location.isPresent());
    }

    /**
     * Attempt to construct a CellLocation using maybeReference of "".
     * Assert that a CellLocation cannot be created.
     */
    @Test
    public void testMaybeReferenceEmpty() {
        Optional<CellLocation> location = CellLocation.maybeReference("");
        assertFalse("Incorrectly created location for an invalid reference.", location.isPresent());
    }

    /**
     * Attempt to construct a CellLocation using maybeReference of " ".
     * Assert that a CellLocation cannot be created.
     */
    @Test
    public void testMaybeReferenceSpace() {
        Optional<CellLocation> location = CellLocation.maybeReference(" ");
        assertFalse("Incorrectly created location for an invalid reference.", location.isPresent());
    }

    /**
     * Attempt to construct a CellLocation using maybeReference of "MM".
     * Assert that a CellLocation cannot be created.
     */
    @Test
    public void testMaybeReferenceMM() {
        Optional<CellLocation> location = CellLocation.maybeReference("MM");
        assertFalse("Incorrectly created location for an invalid reference.", location.isPresent());
    }

    /**
     * Attempt to construct a CellLocation using maybeReference of "B-12".
     * Assert that a CellLocation cannot be created.
     */
    @Test
    public void testMaybeReferenceBdash12() {
        Optional<CellLocation> location = CellLocation.maybeReference("B-12");
        assertFalse("Incorrectly created location for an invalid reference.", location.isPresent());
    }

    /**
     * Attempt to construct a CellLocation using maybeReference of "????".
     * Assert that a CellLocation cannot be created.
     */
    @Test
    public void testMaybeReferenceQMarks() {
        Optional<CellLocation> location = CellLocation.maybeReference("????");
        assertFalse("Incorrectly created location for an invalid reference.", location.isPresent());
    }

    /**
     * Assert that two cells at 'A0' are equal.
     */
    @Test
    public void testEqualsA0() {
        CellLocation first = new CellLocation(0, 'A');
        CellLocation second = new CellLocation(0, 'A');
        assertEquals("Two CellLocations with same position are not equal.", first, second);
    }

    /**
     * Assert that two cells at 'B12' are equal.
     * One constructed with column=1, one constructed with column='B'.
     */
    @Test
    public void testEqualsB12() {
        CellLocation first = new CellLocation(12, 'B');
        CellLocation second = new CellLocation(12, 1);
        assertEquals("Two CellLocations with same position are not equal.", first, second);
    }

    /**
     * Assert that two cells at 'Z12' are equal.
     * One constructed with column=25, one constructed with column='Z'.
     */
    @Test
    public void testEqualsZ12() {
        CellLocation first = new CellLocation(12, 'Z');
        CellLocation second = new CellLocation(12, 25);
        assertEquals("Two CellLocations with same position are not equal.", first, second);
    }

    /**
     * Assert that two 'A0' cells have an equal hashCode.
     */
    @Test
    public void testHashCodeA0() {
        CellLocation first = new CellLocation(0, 'A');
        CellLocation second = new CellLocation(0, 'A');
        assertEquals("Two cells at the same location do not have the same hash code.",
                first.hashCode(), second.hashCode());
    }

    /**
     * Assert that two 'B12' cells have an equal hashCode.
     * One constructed with column=1, one constructed with column='B'.
     */
    @Test
    public void testHashCodeB12() {
        CellLocation first = new CellLocation(12, 'B');
        CellLocation second = new CellLocation(12, 1);
        assertEquals("Two cells at the same location do not have the same hash code.",
                first.hashCode(), second.hashCode());
    }

    /**
     * Assert that two 'Z25' cells have an equal hashCode.
     * One constructed with column=25, one constructed with column='Z'.
     */
    @Test
    public void testHashCodeZ12() {
        CellLocation first = new CellLocation(12, 'Z');
        CellLocation second = new CellLocation(12, 25);
        assertEquals("Two cells at the same location do not have the same hash code.",
                first.hashCode(), second.hashCode());
    }

    /**
     * Assert that the toString of an A0 cell equals "A0".
     */
    @Test
    public void testToStringA0() {
        CellLocation cell = new CellLocation(0, 'A');
        assertEquals("toString did not return the correct representation of the cell location.",
                "A0", cell.toString());
    }

    /**
     * Assert that the toString of an Z100 cell equals "Z100".
     */
    @Test
    public void testToStringZ100() {
        CellLocation cell = new CellLocation(100, 'Z');
        assertEquals("toString did not return the correct representation of the cell location.",
                "Z100", cell.toString());
    }

    /**
     * Assert that the toString of an F10 cell equals "F10".
     */
    @Test
    public void testToStringF10() {
        CellLocation cell = new CellLocation(10, 5);
        assertEquals("toString did not return the correct representation of the cell location.",
                "F10", cell.toString());
    }

}
