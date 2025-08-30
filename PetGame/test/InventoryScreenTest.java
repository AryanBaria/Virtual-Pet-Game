import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * Unit tests for the {@link InventoryScreen} class.
 * <p>
 * This test class verifies core functionality of the inventory system used
 * in the pet game, including item addition, inventory conversion to string,
 * and random item generation. A mock {@link GameplayGUI} is used to isolate
 * InventoryScreen from UI/game logic dependencies.
 * </p>
 *
 * <p>Tested methods include:
 * <ul>
 *   <li>{@code customAddItems(String)}</li>
 *   <li>{@code inventoryToString()}</li>
 *   <li>{@code addFoodRandom()}</li>
 *   <li>{@code addGiftRandom()}</li>
 * </ul>
 * </p>
 * 
 * Author: Dilraj Deogan
 */
public class InventoryScreenTest {

    /** Instance of InventoryScreen being tested */
    private InventoryScreen inventory;

    /**
     * A mock subclass of {@code GameplayGUI} to satisfy dependencies when
     * constructing an {@code InventoryScreen}. The overridden methods allow
     * for controlled, isolated testing of inventory logic.
     */
    private static class MockGameplayGUI extends GameplayGUI {
        /**
         * Constructs the mock with safe default stats and valid inventory string
         * to avoid exceptions during {@code customAddItems} call.
         */
        public MockGameplayGUI() {
            super(0, 0, 100, 100, 100, 1, 
                  "Apple,food,Restores 10 hunger\nToy,gift,Increases happiness +10");
        }

        @Override public int getHunger() { return 50; }
        @Override public int getSleep() { return 60; }
        @Override public int getHapp() { return 70; }
        @Override public int getH() { return 80; }
        @Override public void updateStats(int h, int h2, int s, int hu) {}
        @Override public void checkStats() {}
    }

    /**
     * Initializes a new instance of InventoryScreen before each test.
     * Uses the {@code MockGameplayGUI} to safely instantiate the screen.
     */
    @BeforeEach
    public void setUp() {
        inventory = new InventoryScreen(new MockGameplayGUI());
    }

    /**
     * Verifies that custom items can be added using {@code customAddItems}
     * and that the output string from {@code inventoryToString} reflects
     * the correct content and formatting.
     */
    @Test
    public void testCustomAddItemsAndToString() {
        String input = "Apple,food,Restores 10 hunger\nToy,gift,Increases happiness +10";
        inventory.customAddItems(input);
        String output = inventory.inventoryToString();

        assertTrue(output.contains("Apple"));
        assertTrue(output.contains("Toy"));
        assertTrue(output.contains("Restores 10 hunger"));
        assertTrue(output.contains("Increases happiness +10"));
    }

    /**
     * Tests the {@code addFoodRandom()} method to ensure it either returns:
     * <ul>
     *   <li>An empty string if no food item was randomly added</li>
     *   <li>OR a valid name from the known list of food items</li>
     * </ul>
     */
    @Test
    public void testAddFoodRandomReturnsValidName() {
        String result = inventory.addFoodRandom();
        assertNotNull(result);

        // Acceptable outcomes: empty or valid food item name
        assertTrue(result.isEmpty() || result.matches("Apple|Carrot|Bone|Fish|Chicken|Steak"));
    }

    /**
     * Tests the {@code addGiftRandom()} method to ensure it either returns:
     * <ul>
     *   <li>An empty string if no gift item was randomly added</li>
     *   <li>OR a valid name from the known list of gift items</li>
     * </ul>
     */
    @Test
    public void testAddGiftRandomReturnsValidName() {
        String result = inventory.addGiftRandom();
        assertNotNull(result);

        // Acceptable outcomes: empty or valid gift item name
        assertTrue(result.isEmpty() || result.matches("Toy|Teddy Bear|Ball|Frisbee"));
    }
}
