package sheep.core;

import org.junit.Test;
import static org.junit.Assert.*;

public class ViewElementTest {
    @Test
    public void content() {
        ViewElement element = new ViewElement("My getContent", "getBackground", "getForeground");
        assertEquals("My getContent", element.getContent());
    }

    @Test
    public void background() {
        ViewElement element = new ViewElement("getContent", "My getBackground", "getForeground");
        assertEquals("My getBackground", element.getBackground());
    }

    @Test
    public void foreground() {
        ViewElement element = new ViewElement("getContent", "getBackground", "My getForeground");
        assertEquals("My getForeground", element.getForeground());
    }
}
