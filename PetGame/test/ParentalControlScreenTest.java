import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import javax.swing.*;
import java.lang.reflect.*;

/**
 * Unit tests for the {@link ParentalControlScreen} class.
 * This test suite verifies all major public methods including GUI updates
 * and method accessibility, as required.
 * 
 * Author: Dilraj Deogan
 */
public class ParentalControlScreenTest {

    private ParentalControlScreen screen;

    /**
     * Sets up a new instance of ParentalControlScreen before each test.
     */
    @BeforeEach
    public void setUp() {
        screen = new ParentalControlScreen();
    }

    /**
     * Tests that the showSavedMessage method updates the status label correctly.
     *
     * @throws Exception if reflection fails
     */
    @Test
    public void testShowSavedMessageDisplaysCorrectText() throws Exception {
        String msg = "Settings saved!";
        screen.showSavedMessage(msg);

        JLabel statusLabel = (JLabel) getPrivateField(screen, "statusLabel");
        assertNotNull(statusLabel.getText(), "Status label text should not be null.");
        assertTrue(statusLabel.getText().contains(msg), "Status label should contain the saved message.");
    }

    /**
     * Tests that openTimeRestrictionsWindow() executes without throwing an exception.
     */
    @Test
    public void testOpenTimeRestrictionsWindowIsCallable() {
        assertDoesNotThrow(() -> screen.openTimeRestrictionsWindow(),
                "Opening time restriction window should not throw an exception.");
    }

    /**
     * Tests that openAveragePlaytimeWindow() executes without throwing an exception.
     */
    @Test
    public void testOpenAveragePlaytimeWindowIsCallable() {
        assertDoesNotThrow(() -> screen.openAveragePlaytimeWindow(),
                "Opening average playtime window should not throw an exception.");
    }

    /**
     * Utility method to access a private field using reflection.
     *
     * @param obj       the object containing the field
     * @param fieldName the name of the field to access
     * @return the field's value
     * @throws Exception if reflection fails
     */
    private Object getPrivateField(Object obj, String fieldName) throws Exception {
        Field f = obj.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        return f.get(obj);
    }
}
