package sheep.core;

import org.junit.Test;
import static org.junit.Assert.*;

public class UpdateResponseTest {
    @Test
    public void successful() {
        UpdateResponse response = UpdateResponse.success();
        assertTrue(response.isSuccess());
        assertNull(response.getMessage());
    }

    @Test
    public void failure() {
        UpdateResponse response = UpdateResponse.fail("My message");
        assertFalse(response.isSuccess());
        assertEquals("My message", response.getMessage());
    }
}
