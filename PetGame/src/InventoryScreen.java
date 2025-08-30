import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;

/**
 * The InventoryScreen class represents the inventory management interface for a pet simulation game.
 * It provides functionality to view, use, and manage food and gift items for the virtual pet.
 * 
 * <p>Key features include:
 * <ul>
 *   <li>Tabbed interface for food and gift items</li>
 *   <li>Ability to use items which affect pet stats</li>
 *   <li>Visual display of item quantities and effects</li>
 *   <li>Support for loading inventory from JSON file</li>
 *   <li>Random item generation functionality</li>
 * </ul>
 * 
 * <p>The class maintains two separate inventories:
 * <ul>
 *   <li>foodItems - List of available food items</li>
 *   <li>giftItems - List of available gift items</li>
 * </ul>
 * 
 * Author: Dilraj Deogan
 * 
 */

public class InventoryScreen extends JFrame {

	/** List of currently available food items */
	private ArrayList<Item> foodItems = new ArrayList<>();

	 /** List of currently available gift items */
	private ArrayList<Item> giftItems = new ArrayList<>();

	 /** Complete set of all possible food items */
	private Set<Item> foodItemsTotal = new HashSet<>();

	/** Complete set of all possible gift items */
	private Set<Item> giftItemsTotal = new HashSet<>();

	 /** Panel for displaying food items */
	private JPanel foodPanel;
	/** Panel for displaying gift items */
	private JPanel giftPanel;
	/** Label for food items section */
	private JLabel foodLabel;
	/** Label for gift items section */
	private JLabel giftLabel;

	 /** Button to use food items */
	private RoundedButton useFoodButton;
	/** Button to use gift items */
	private RoundedButton useGiftButton;
	 /** Button to close the inventory */
	private RoundedButton closeButton;
	/** Reference to the main game logic */
	private GameplayGUI logic;

	/**
     * Constructs the InventoryScreen with a reference to the main game logic.
     * Initializes the UI components and loads inventory data.
     *
     * @param logic The main gameplay logic controller
     */
	public InventoryScreen(GameplayGUI logic) {
		// Set up the frame
		setTitle("Pet Game - Inventory");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(new Color(240, 255, 240));
		setLayout(new BorderLayout(10, 10));
		this.logic = logic;


			foodItemsTotal.add(new Item("Apple", "food", "Restores 10 hunger"));
			foodItemsTotal.add(new Item("Carrot", "food", "Restores 5 hunger"));
			foodItemsTotal.add(new Item("Bone", "food", "Restores 15 hunger"));
			foodItemsTotal.add(new Item("Fish", "food", "Restores 12 hunger"));
			foodItemsTotal.add(new Item("Chicken", "food", "Restores 13 hunger"));
			foodItemsTotal.add(new Item("Steak", "food", "Restores 20 hunger"));

			giftItemsTotal.add(new Item("Toy", "gift", "Increases happiness +10"));
			giftItemsTotal.add(new Item("Teddy Bear", "gift", "Increases happiness +15"));
			giftItemsTotal.add(new Item("Ball", "gift", "Increases happiness +9"));
			giftItemsTotal.add(new Item("Frisbee", "gift", "Increases happiness +12"));





		// ------------- NEW: Load from JSON or fallback -------------
		boolean loaded = loadInventoryFromJson();
		if(!loaded) {
			// If JSON load fails, do the original populate
			populateInventory();
		}
		// -----------------------------------------------------------





		// Title label
		JLabel titleLabel = new JLabel("Inventory", JLabel.CENTER);
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		titleLabel.setForeground(new Color(0, 100, 0));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
		add(titleLabel, BorderLayout.NORTH);

		// Main content panel with tabs
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("SansSerif", Font.BOLD, 14));
		tabbedPane.setBackground(new Color(240, 255, 240));

		// Food panel
		foodPanel = new JPanel();
		foodPanel.setLayout(new BoxLayout(foodPanel, BoxLayout.Y_AXIS));
		foodPanel.setBackground(new Color(240, 255, 240));
		foodPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		foodLabel = new JLabel("Food Items", JLabel.CENTER);
		foodLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		foodLabel.setForeground(new Color(0, 100, 0));

		JPanel foodContainer = new JPanel(new BorderLayout());
		foodContainer.setBackground(new Color(240, 255, 240));
		foodContainer.add(foodLabel, BorderLayout.NORTH);

		JScrollPane foodScrollPane = new JScrollPane(foodPanel);
		foodScrollPane.setBorder(BorderFactory.createEmptyBorder());
		foodScrollPane.getViewport().setBackground(new Color(255, 255, 255, 200));
		foodContainer.add(foodScrollPane, BorderLayout.CENTER);

		tabbedPane.addTab("Food", foodContainer);

		// Gift panel
		giftPanel = new JPanel();
		giftPanel.setLayout(new BoxLayout(giftPanel, BoxLayout.Y_AXIS));
		giftPanel.setBackground(new Color(240, 255, 240));
		giftPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		giftLabel = new JLabel("Gift Items", JLabel.CENTER);
		giftLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		giftLabel.setForeground(new Color(0, 100, 0));

		JPanel giftContainer = new JPanel(new BorderLayout());
		giftContainer.setBackground(new Color(240, 255, 240));
		giftContainer.add(giftLabel, BorderLayout.NORTH);

		JScrollPane giftScrollPane = new JScrollPane(giftPanel);
		giftScrollPane.setBorder(BorderFactory.createEmptyBorder());
		giftScrollPane.getViewport().setBackground(new Color(255, 255, 255, 200));
		giftContainer.add(giftScrollPane, BorderLayout.CENTER);

		tabbedPane.addTab("Gifts", giftContainer);

		add(tabbedPane, BorderLayout.CENTER);

		// Button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(240, 255, 240));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));

		useFoodButton = new RoundedButton("Use Food");
		useFoodButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		useFoodButton.setPreferredSize(new Dimension(150, 40));
		useFoodButton.addActionListener(e -> useFood());

		

		useGiftButton = new RoundedButton("Use Gift");
		useGiftButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		useGiftButton.setPreferredSize(new Dimension(150, 40));
		useGiftButton.addActionListener(e -> useGift());

		closeButton = new RoundedButton("Close");
		closeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		closeButton.setPreferredSize(new Dimension(150, 40));
		closeButton.addActionListener(e -> {
			setVisible(false);
		});

		buttonPanel.add(useFoodButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		buttonPanel.add(useGiftButton);
		buttonPanel.add(closeButton);

		add(buttonPanel, BorderLayout.SOUTH);

		// Since, we already tried loading from JSON above, then if that failed we do populateInventory()
		updateInventoryUI();
	}

	/**
     * Populates the inventory with default items as a fallback when JSON loading fails.
     * Creates default food and gift items with initial quantities.
     */
	private void populateInventory() {
		foodItems.clear();
		giftItems.clear();

		foodItems.add(new Item("Apple", "food", "Restores 10 hunger", 1));
		foodItems.add(new Item("Carrot", "food", "Restores 5 hunger", 1));
		foodItems.add(new Item("Bone", "food", "Restores 15 hunger", 1));
		foodItems.add(new Item("Fish", "food", "Restores 12 hunger", 1));
		foodItems.add(new Item("Chicken", "food", "Restores 13 hunger", 1));
		foodItems.add(new Item("Steak", "food", "Restores 20 hunger", 1));

		giftItems.add(new Item("Toy", "gift", "Increases happiness +10", 1));
		giftItems.add(new Item("Teddy Bear", "gift", "Increases happiness +15", 1));
		giftItems.add(new Item("Ball", "gift", "Increases happiness +9", 1));
		giftItems.add(new Item("Frisbee", "gift", "Increases happiness +12", 1));
	}

	 /**
     * Attempts to load inventory data from a JSON file.
     * 
     * @return true if loading was successful, false otherwise
     */
	private boolean loadInventoryFromJson() {
		String json = readJsonFile("json/jsonFile.json"); 
		if (json == null) {
			return false;
		}
		String inventoryKey = "\"inventory\":";
		int invIndex = json.indexOf(inventoryKey);
		if (invIndex == -1) {
			return false;
		}
		// find the '['
		int startArray = json.indexOf("[", invIndex);
		int endArray = json.indexOf("]", startArray);
		if (startArray == -1 || endArray == -1) {
			return false;
		}
		String inventoryContent = json.substring(startArray+1, endArray);

		foodItems.clear();
		giftItems.clear();

		int fromIndex = 0;
		while (true) {
			String searchKey = "\"itemId\":";
			int keyPos = inventoryContent.indexOf(searchKey, fromIndex);
			if (keyPos == -1) {
				break; 
			}

			String itemId = extractValue(inventoryContent, "itemId", keyPos);
			String itemName = extractValue(inventoryContent, "itemName", keyPos);
			String qtyStr   = extractValue(inventoryContent, "quantity", keyPos);

			int quantity = 1;
			try {
				quantity = (qtyStr != null) ? Integer.parseInt(qtyStr) : 1;
			} catch (NumberFormatException e) {
				quantity = 1;
			}

			
			String effect = "Some effect";
			if (itemName != null) {
				if (itemName.equalsIgnoreCase("Apple"))       effect = "Restores 10 hunger";
				else if (itemName.equalsIgnoreCase("Carrot")) effect = "Restores 5 hunger";
				else if (itemName.equalsIgnoreCase("Bone"))   effect = "Restores 15 hunger";
				else if (itemName.equalsIgnoreCase("Fish"))   effect = "Restores 12 hunger";
				else if (itemName.equalsIgnoreCase("Chicken"))effect = "Restores 13 hunger";
				else if (itemName.equalsIgnoreCase("Steak"))  effect = "Restores 20 hunger";
				else if (itemName.equalsIgnoreCase("Toy"))    effect = "Increases happiness +10";
				else if (itemName.equalsIgnoreCase("Teddy Bear")) effect = "Increases happiness +15";
				else if (itemName.equalsIgnoreCase("Ball"))   effect = "Increases happiness +9";
				else if (itemName.equalsIgnoreCase("Frisbee"))effect = "Increases happiness +12";
			}

			String type = "food"; 
			if (itemId != null && itemId.contains("TOY")) {
				type = "gift";
			}

			Item newItem = new Item(
				(itemName != null)? itemName : "Unknown",
				type,
				effect,
				quantity
			);

			if ("food".equals(type)) {
				foodItems.add(newItem);
			} 
			
			else {
				giftItems.add(newItem);
			}

			fromIndex = keyPos + searchKey.length();
		}

		return true;
	}

	/**
     * Extracts a value from JSON string given a key and starting position.
     *
     * @param json The JSON string to search
     * @param key The key to look for
     * @param pos The starting position for search
     * @return The extracted value or null if not found
     */
	private String extractValue(String json, String key, int pos) {
		String searchKey = "\"" + key + "\":";
		int keyIndex = json.indexOf(searchKey, pos);
		if (keyIndex == -1) return null;

		int colonIndex = keyIndex + searchKey.length();
		int quoteIndex = json.indexOf("\"", colonIndex);
		if (quoteIndex == -1) {
			int comma = json.indexOf(",", colonIndex);
			int brace = json.indexOf("}", colonIndex);
			
			if (comma == -1) comma = Integer.MAX_VALUE;
			
			if (brace == -1) brace = Integer.MAX_VALUE;
			int endPos = Math.min(comma, brace);
			
			if (endPos == Integer.MAX_VALUE) return null;
			String val = json.substring(colonIndex, endPos).trim();
			return val;
		}

		int secondQuote = json.indexOf("\"", quoteIndex + 1);
		if (secondQuote == -1) return null;

		return json.substring(quoteIndex+1, secondQuote);
	}

	/**
     * Reads the contents of a JSON file into a string.
     *
     * @param filePath Path to the JSON file
     * @return The file contents as a string, or null if reading fails
     */
	private String readJsonFile(String filePath) {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} 
		
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}

	/**
     * Updates the inventory UI to reflect current item states.
     * Refreshes both food and gift panels with current items.
     */
	private void updateInventoryUI() {
		foodPanel.removeAll();
		giftPanel.removeAll();

		for (Item food : foodItems) {
			JPanel itemPanel = createItemPanel(food);
			foodPanel.add(itemPanel);
			foodPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		}

		for (Item gift : giftItems) {
			JPanel itemPanel = createItemPanel(gift);
			giftPanel.add(itemPanel);
			giftPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		}

		foodPanel.revalidate();
		giftPanel.revalidate();
		foodPanel.repaint();
		giftPanel.repaint();
	}

	 /**
     * Creates a panel to display an individual inventory item.
     *
     * @param item The item to display
     * @return A configured JPanel displaying the item
     */
	private JPanel createItemPanel(Item item) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(255, 255, 255, 200));
		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(new Color(200, 230, 200), 1),
				BorderFactory.createEmptyBorder(8, 10, 8, 10)
		));

		// Display "Apple (x2)" if quantity=2
		String displayName = item.getName() + " (x" + item.getQuantity() + ")";

		JLabel nameLabel = new JLabel(displayName);
		nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

		JLabel effectLabel = new JLabel(item.getEffect());
		effectLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
		effectLabel.setForeground(Color.GRAY);

		JPanel textPanel = new JPanel(new GridLayout(2, 1));
		textPanel.setOpaque(false);
		textPanel.add(nameLabel);
		textPanel.add(effectLabel);

		panel.add(textPanel, BorderLayout.CENTER);

		return panel;
	}

	/**
     * Handles the use of food items.
     * Displays a dialog allowing selection of food items to use,
     * updates pet stats accordingly, and refreshes the inventory.
     */
	private void useFood() {
		if (foodItems.isEmpty()) {
			showStyledMessageDialog("No food items available!", "Inventory Empty", JOptionPane.WARNING_MESSAGE);
			return;
		}

		JDialog dialog = new JDialog(this, "Select Food to Use", true);
		dialog.setSize(400, 350);
		dialog.setLocationRelativeTo(this);
		dialog.getContentPane().setBackground(new Color(240, 255, 240));
		dialog.setLayout(new BorderLayout(10, 10));

		JLabel header = new JLabel("Select Food to Use", JLabel.CENTER);
		header.setFont(new Font("SansSerif", Font.BOLD, 18));
		header.setForeground(new Color(0, 100, 0));
		header.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
		dialog.add(header, BorderLayout.NORTH);

		JPanel itemsPanel = new JPanel();
		itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
		itemsPanel.setBackground(new Color(240, 255, 240));

		for (Item food : new ArrayList<>(foodItems)) { 
			RoundedPanel itemPanel = new RoundedPanel(15, new Color(255, 255, 255, 220));
			itemPanel.setLayout(new BorderLayout(10, 10));
			itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

			String displayName = food.getName() + " (x" + food.getQuantity() + ")";
			JLabel name = new JLabel(displayName);
			name.setFont(new Font("SansSerif", Font.BOLD, 14));

			JLabel effect = new JLabel(food.getEffect());
			effect.setFont(new Font("SansSerif", Font.PLAIN, 12));
			effect.setForeground(new Color(80, 80, 80));

			JPanel infoPanel = new JPanel(new GridLayout(2, 1));
			infoPanel.setOpaque(false);
			infoPanel.add(name);
			infoPanel.add(effect);

			RoundedButton useBtn = new RoundedButton("Use");
			useBtn.setPreferredSize(new Dimension(80, 30));

			int hungerVal = 0;
			switch (food.getName()) {
				case "Apple":
					hungerVal = 10;
					break;
				case "Carrot":
					hungerVal = 5;
					break;
				case "Bone":
					hungerVal = 15;
					break;
				case "Fish":
					hungerVal = 12;
					break;
				case "Chicken":
					hungerVal = 13;
					break;
				case "Steak":
					hungerVal = 20;
					break;
				default:
					hungerVal = 0;
			}
			int totalHungerVal = hungerVal; 

			useBtn.addActionListener(e -> {
				// Decrement quantity
				food.setQuantity(food.getQuantity() - 1);

				if (food.getQuantity() <= 0) {
					foodItems.remove(food);
				}
				updateInventoryUI();
				dialog.dispose();
				showStyledMessageDialog("Used " + food.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);

				int newHunger = (logic.getHunger() + totalHungerVal > 100) ? 100 : logic.getHunger() + totalHungerVal;
				int newSleep = logic.getSleep();
				int newHapp = logic.getHapp();
				int newh = logic.getH();

				logic.updateStats(newHapp, newh, newSleep, newHunger);
				logic.checkStats();
			});

			itemPanel.add(infoPanel, BorderLayout.CENTER);
			itemPanel.add(useBtn, BorderLayout.EAST);

			itemsPanel.add(itemPanel);
			itemsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		JScrollPane scrollPane = new JScrollPane(itemsPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setBackground(new Color(240, 255, 240));
		dialog.add(scrollPane, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(240, 255, 240));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 15, 50));

		RoundedButton cancelBtn = new RoundedButton("Cancel");
		cancelBtn.setPreferredSize(new Dimension(120, 35));
		cancelBtn.addActionListener(e -> dialog.dispose());

		bottomPanel.add(cancelBtn);
		dialog.add(bottomPanel, BorderLayout.SOUTH);

		dialog.setVisible(true);
	}

	/**
     * Handles the use of gift items.
     * Displays a dialog allowing selection of gifts to give,
     * updates pet stats accordingly, and refreshes the inventory.
     */
	private void useGift() {
		if (giftItems.isEmpty()) {
			showStyledMessageDialog("No gift items available!", "Inventory Empty", JOptionPane.WARNING_MESSAGE);
			return;
		}

		JDialog dialog = new JDialog(this, "Select Gift to Give", true);
		dialog.setSize(400, 350);
		dialog.setLocationRelativeTo(this);
		dialog.getContentPane().setBackground(new Color(240, 255, 240));
		dialog.setLayout(new BorderLayout(10, 10));

		JLabel header = new JLabel("Select Gift to Give", JLabel.CENTER);
		header.setFont(new Font("SansSerif", Font.BOLD, 18));
		header.setForeground(new Color(0, 100, 0));
		header.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
		dialog.add(header, BorderLayout.NORTH);

		JPanel itemsPanel = new JPanel();
		itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
		itemsPanel.setBackground(new Color(240, 255, 240));

		for (Item gift : new ArrayList<>(giftItems)) {
			RoundedPanel itemPanel = new RoundedPanel(15, new Color(255, 255, 255, 220));
			itemPanel.setLayout(new BorderLayout(10, 10));
			itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

			String displayName = gift.getName() + " (x" + gift.getQuantity() + ")";
			JLabel name = new JLabel(displayName);
			name.setFont(new Font("SansSerif", Font.BOLD, 14));

			JLabel effect = new JLabel(gift.getEffect());
			effect.setFont(new Font("SansSerif", Font.PLAIN, 12));
			effect.setForeground(new Color(80, 80, 80));

			JPanel infoPanel = new JPanel(new GridLayout(2, 1));
			infoPanel.setOpaque(false);
			infoPanel.add(name);
			infoPanel.add(effect);

			RoundedButton giveBtn = new RoundedButton("Give");
			giveBtn.setPreferredSize(new Dimension(80, 30));
			int happyVal = 0;
			switch (gift.getName()) {
				case "Toy":
					happyVal = 10;
					break;
				case "Teddy Bear":
					happyVal = 15;
					break;
				case "Ball":
					happyVal = 9;
					break;
				case "Frisbee":
					happyVal = 12;
					break;
				default:
					happyVal = 0;
			}

			int totalHappyVal = happyVal; 

			giveBtn.addActionListener(e -> {
				gift.setQuantity(gift.getQuantity() - 1);
				if (gift.getQuantity() <= 0) {
					giftItems.remove(gift);
				}
				updateInventoryUI();
				dialog.dispose();
				showStyledMessageDialog("Gave " + gift.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);

				int newHunger = logic.getHunger();
				int newSleep = logic.getSleep();
				int newHapp = (logic.getHapp() + totalHappyVal > 100) ? 100 : totalHappyVal + logic.getHapp();
				int newh = logic.getH();

				logic.updateStats(newHapp, newh, newSleep, newHunger);
				logic.checkStats();
			});

			itemPanel.add(infoPanel, BorderLayout.CENTER);
			itemPanel.add(giveBtn, BorderLayout.EAST);

			itemsPanel.add(itemPanel);
			itemsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}

		JScrollPane scrollPane = new JScrollPane(itemsPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getViewport().setBackground(new Color(240, 255, 240));
		dialog.add(scrollPane, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(new Color(240, 255, 240));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 15, 50));

		RoundedButton cancelBtn = new RoundedButton("Cancel");
		cancelBtn.setPreferredSize(new Dimension(120, 35));
		cancelBtn.addActionListener(e -> dialog.dispose());

		bottomPanel.add(cancelBtn);
		dialog.add(bottomPanel, BorderLayout.SOUTH);

		dialog.setVisible(true);
	}

	/**
     * Displays a styled message dialog with custom appearance.
     *
     * @param message The message to display
     * @param title The dialog title
     * @param messageType The JOptionPane message type constant
     */
	private void showStyledMessageDialog(String message, String title, int messageType) {
		JOptionPane pane = new JOptionPane(message, messageType);
		JDialog dialog = pane.createDialog(this, title);

		dialog.getContentPane().setBackground(new Color(240, 255, 240));

		for (Component comp : dialog.getContentPane().getComponents()) {
			if (comp instanceof JButton) {
				JButton btn = (JButton) comp;
				btn.setBackground(new Color(144, 238, 144));
				btn.setForeground(Color.BLACK);
				btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
				btn.setFocusPainted(false);
			}
		}

		dialog.setVisible(true);
	}

	/**
     * Converts the current inventory to a string representation.
     *
     * @return String containing all inventory items
     */
	public String inventoryToString() {
		StringBuilder sb = new StringBuilder();

		for (Item item : foodItems) {
			sb.append(item.getName()).append(",");
			sb.append(item.getType()).append(",");
			sb.append(item.getEffect()).append("\n");
		}

		for (Item item : giftItems) {
			sb.append(item.getName()).append(",");
			sb.append(item.getType()).append(",");
			sb.append(item.getEffect()).append("\n");
		}
		return sb.toString();
	}

	  /**
     * Adds items to inventory from a custom string representation.
     *
     * @param itemList String containing items in format "name,type,effect\n"
     */
	public void customAddItems(String itemList) {
		foodItems.clear();
		giftItems.clear();

		String[] items = itemList.split("\n");

		for (String item : items) {
			String[] indivisualItems = item.split(",");
			if (indivisualItems[1].equals("food")) {
				foodItems.add(new Item(indivisualItems[0], indivisualItems[1], indivisualItems[2]));
			}
			else {
				giftItems.add(new Item(indivisualItems[0], indivisualItems[1], indivisualItems[2]));
			}
		}

		updateInventoryUI();
	}

 	/**
     * Randomly adds a food item to the inventory.
     *
     * @return Name of the added item, or empty string if none was added
     */
	public String addFoodRandom() {
		boolean found = false;

		for (Item item : foodItemsTotal) {
			for (Item food : foodItems) {
				if (item.isSame(food)) {
					found = true;
				}
			}
			if (!found && Math.random() < 0.5) {
				foodItems.add(item);
				updateInventoryUI();
				return item.getName();
			}
			found = false;
		}
		return "";
	}
		
	/**
     * Randomly adds a gift item to the inventory.
     *
     * @return Name of the added item, or empty string if none was added
     */
	public String addGiftRandom() {

	boolean found = false;
	
	for (Item item : giftItemsTotal) {
		for (Item gift : giftItems) {
	if (item.isSame(gift)) {
		found = true;
		}
		}
	if (!found && Math.random() < 0.5) {
		giftItems.add(item);
		updateInventoryUI();
		return item.getName();
		}
		found = false;
	}
		return "";
	}

	/**
     * Custom JButton implementation with rounded corners and hover effects.
     */
	class RoundedButton extends JButton {
		private static final int ARC_WIDTH = 20;
		private static final int ARC_HEIGHT = 20;

		public RoundedButton(String text) {
			super(text);
			setContentAreaFilled(false);
			setForeground(Color.BLACK);
			setBackground(new Color(144, 238, 144));
			setFocusPainted(false);
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent evt) {
					setBackground(new Color(152, 251, 152));
				}
				public void mouseExited(MouseEvent evt) {
					setBackground(new Color(144, 238, 144));
				}
				public void mousePressed(MouseEvent evt) {
					setBackground(new Color(0, 100, 0));
					setForeground(Color.WHITE);
				}
				public void mouseReleased(MouseEvent evt) {
					setBackground(new Color(152, 251, 152));
					setForeground(Color.BLACK);
				}
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			if (getModel().isPressed()) {
				g2.setColor(new Color(0, 100, 0));
			} else if (getModel().isRollover()) {
				g2.setColor(new Color(152, 251, 152));
			} else {
				g2.setColor(getBackground());
			}

			g2.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);
			g2.setColor(getForeground());
			g2.setFont(getFont());

			FontMetrics fm = g2.getFontMetrics();
			Rectangle2D r = fm.getStringBounds(getText(), g2);
			int x = (getWidth() - (int) r.getWidth()) / 2;
			int y = (getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();

			g2.drawString(getText(), x, y);
			g2.dispose();
		}

		@Override
		protected void paintBorder(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(new Color(0, 100, 0));
			g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, ARC_WIDTH, ARC_HEIGHT);
			g2.dispose();
		}
	}

	/**
     * Custom JPanel implementation with rounded corners.
     */
	class RoundedPanel extends JPanel {
		private int radius;
		private Color bgColor;

		public RoundedPanel(int radius, Color bgColor) {
			this.radius = radius;
			this.bgColor = bgColor;
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(bgColor);
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
			g2.dispose();
		}

		@Override
		protected void paintBorder(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(new Color(200, 230, 200));
			g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, radius, radius);
			g2.dispose();
		}
	}

	 /**
     * Represents an inventory item with name, type, effect and quantity.
     */
	public class Item {
		private String name;
		private String type;
		private String effect;
		private int quantity; 

		public Item(String name, String type, String effect) {
			this(name, type, effect, 1);
		}

		public Item(String name, String type, String effect, int quantity) {
			this.name = name;
			this.type = type;
			this.effect = effect;
			this.quantity = quantity;
		}

		public String getName() { return name; }
		public String getType() { return type; }
		public String getEffect() { return effect; }



		public boolean isSame(Item item) {
			return (this.getName().equals(item.getName()) && this.getType().equals(item.getType()) && this.getEffect().equals(item.getEffect()));
		}
			

		public int getQuantity() { return quantity; }
		public void setQuantity(int q) { this.quantity = q; }
	}
}
