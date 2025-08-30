import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * Unit tests for the {@link GameplayGUI} class.
 * 
 * <p>These tests verify core non-GUI logic including stat handling,
 * inventory integration, score tracking, and cooldown fallback behavior.
 * 
 * <p>Note: UI rendering and file save dialogs are not tested here.
 * 
 * Author: Dilraj Deogan
 */
public class GameplayGUITest {

    private GameplayGUI gui;

    /**
     * Initializes a new GameplayGUI instance before each test with controlled values.
     */
    @BeforeEach
    public void setUp() {
        gui = new GameplayGUI(0, 50, 60, 70, 80, 1, "Apple,food,Restores 10 hunger\nToy,gift,Increases happiness +10");
    }

    /**
     * Tests that the constructor correctly initializes the game state with given parameters.
     */
    @Test
    public void testInitialValues() {
        assertEquals(50, gui.getHapp(), "Happiness should be initialized to 50");
        assertEquals(60, gui.getH(), "Health should be initialized to 60");
        assertEquals(70, gui.getSleep(), "Sleep should be initialized to 70");
        assertEquals(80, gui.getHunger(), "Hunger should be initialized to 80");
        assertEquals(0, gui.getScore(), "Score should be initialized to 0");
        assertEquals(1, gui.getPetNumber(), "Pet number should be initialized to 1");
    }

    /**
     * Tests that the updateStats method correctly updates pet stats.
     */
    @Test
    public void testUpdateStats() {
        gui.updateStats(10, 20, 30, 40);
        assertEquals(10, gui.getHapp(), "Happiness should be updated to 10");
        assertEquals(20, gui.getH(), "Health should be updated to 20");
        assertEquals(30, gui.getSleep(), "Sleep should be updated to 30");
        assertEquals(40, gui.getHunger(), "Hunger should be updated to 40");
    }

    /**
     * Tests that the addToScore method correctly increments the score.
     */
    @Test
    public void testAddToScore() {
        gui.addToScore(15);
        assertEquals(15, gui.getScore(), "Score should be 15 after first addition");

        gui.addToScore(10);
        assertEquals(25, gui.getScore(), "Score should be 25 after second addition");
    }

    /**
     * Tests that action cooldowns are returned with safe fallback values
     * when no JSON configuration is present.
     */
    @Test
    public void testGetActionCooldownDefaults() {
        assertTrue(gui.getActionCooldown("feed") >= 0, "Feed cooldown should be non-negative");
        assertTrue(gui.getActionCooldown("play") >= 0, "Play cooldown should be non-negative");
    }

    /**
     * Tests that the inventory is properly initialized and not null.
     */
    @Test
    public void testGetInventoryIsNotNull() {
        assertNotNull(gui.getInventory(), "Inventory instance should not be null");
    }
}
