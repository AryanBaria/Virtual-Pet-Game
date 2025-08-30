import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * GUI for gameplay class used for creating the main game screen.
 * <br><br>
 * Players can perform actions such as feeding, playing, sleeping, and visiting the vet.
 * The interface also tracks the pet's stats, including hunger, happiness, health, 
 * sleep, and score. Game progress can be saved to a file, and inventory items 
 * can be used within the GUI.<br><br>
 *
 * <b>Example Use:</b>
 * <pre>
 * {@code
 * 		//Creates a new gameplay GUI with all stats set 100, score to 0, pet number 1 and default items. 
 * 		GameplayGUI gui = new GameplayGUI(0, 100, 100, 100, 100, 1, "");
 * }
 * </pre>
 *
 * <b>Example Output:</b> <code>//Main game screen initialized with default stats.</code><br>
 *
 * @version 1.0.0
 * @author Aryan Baria
 */
public class GameplayGUI extends JFrame{
	//Create Instance Varibales
	private int score, health, sleep, hunger, happiness, petNumber;
	private String inventoryItems;
	private InventoryScreen inventory;
	private JLabel happinessValueLabel, sleepValueLabel, healthValueLabel, hungerValueLabel, scoreLabel;

	    // ------------------- FIELD FOR COOLDOWNS -------------------
    private Map<String, Integer> actionCooldowns = new HashMap<>();
    // ---------------------------------------------------------------

	/**
	 * GameplayGUI constructor. Creates a new main game screen.
	 * 
	 * @param inputScore the player's starting score
 	 * @param inputHappiness the pet's initial happiness level 
 	 * @param inputHealth the pet's initial health level 
 	 * @param inputSleep the pet's initial sleep level 
 	 * @param inputHunger the pet's initial hunger level
 	 * @param petNumber the pet's identifier for selecting its image
 	 * @param inventoryItems a string representing the player's inventory ("" for default inventory)
	 */
	public GameplayGUI(int inputScore, int inputHappiness, int inputHealth, int inputSleep, int inputHunger, int petNumber, String inventoryItems) {
		//Get the values
		this.sleep = inputSleep;
		this.happiness = inputHappiness;
		this.hunger = inputHunger;
		this.health = inputHealth;
		this.score = inputScore;
		this.petNumber = petNumber;
		this.inventoryItems = inventoryItems;




		// -------- Loading action cooldowns from the JSON on startup --------
		loadActionCooldownsFromJson();
		// ---------------------------------------------------------------------



		//Fill the inventory
		inventory = new InventoryScreen(GameplayGUI.this);
		
		if(inventoryItems != null) {
			inventory.customAddItems(inventoryItems);
		}

		//Create the screen
		setTitle("Home");
		setSize(1200, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);  
		setLayout(null);

		//Set the background color
		getContentPane().setBackground(new Color(204, 255, 255));

		//Create the icons
		JLabel catIcon = new JLabel(new ImageIcon("img/pet" + String.valueOf(petNumber) + ".png"));

		//Create the labels
		scoreLabel = new JLabel("Score: " + String.valueOf(score));
		JLabel happinessTextLabel = new JLabel("Happiness:");
		JLabel sleepTextLabel = new JLabel("Sleep:");
		JLabel healthTextLabel = new JLabel("Health:");
		JLabel hungerTextLabel = new JLabel("Hunger:");

		happinessValueLabel = new JLabel(String.valueOf(happiness) + "/100");
		sleepValueLabel = new JLabel(String.valueOf(sleep) + "/100");
		healthValueLabel = new JLabel(String.valueOf(health) + "/100");
		hungerValueLabel = new JLabel(String.valueOf(hunger) + "/100");

		//Create the buttons
		JButton exitButton = createButton("Exit", 980, 10, 200, 60);
		JButton saveButton = createButton("Save", 20, 10, 200, 60);

		JButton sleepButton = createButton("Go To Sleep", 150, 200, 200, 60);
		JButton feedButton = createButton("Feed", 150, 300, 200, 60);

		JButton playButton = createButton("Play Games", 850, 200, 200, 60);
		JButton vetButton = createButton("Take To Vet", 850, 300, 200, 60);

		JButton inventoryButton = createButton("Inventory", 500, 500, 200, 60);

		//Position the icon
		catIcon.setBounds(475, 80, 250, 381);

		//Position the labels and change fonts

		//Score label
		scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		scoreLabel.setBounds(500, 20, 200, 60);

		//Create box around the text and center
		scoreLabel.setOpaque(true);
		scoreLabel.setBackground(Color.WHITE);
		scoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

		//Text labels
		happinessTextLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		happinessTextLabel.setBounds(50, 580, 200, 60);

		sleepTextLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		sleepTextLabel.setBounds(650, 580, 200, 60);

		healthTextLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		healthTextLabel.setBounds(50, 650, 200, 60);

		hungerTextLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
		hungerTextLabel.setBounds(650, 650, 200, 60);

		//Value labels
		happinessValueLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		happinessValueLabel.setBounds(460, 580, 200, 60);

		sleepValueLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		sleepValueLabel.setBounds(1060, 580, 200, 60);

		healthValueLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		healthValueLabel.setBounds(460, 650, 200, 60);

		hungerValueLabel.setFont(new Font("SansSerif", Font.BOLD, 25));
		hungerValueLabel.setBounds(1060, 650, 200, 60);


		//Add the icon
		add(catIcon);

		//Add the labels
		add(scoreLabel);
		add(happinessTextLabel);
		add(sleepTextLabel);
		add(healthTextLabel);
		add(hungerTextLabel);
		add(happinessValueLabel);
		add(sleepValueLabel);
		add(healthValueLabel);
		add(hungerValueLabel);

		//Add the buttons
		add(exitButton);
		add(playButton);
		add(vetButton);
		add(inventoryButton);
		add(saveButton);
		add(sleepButton);
		add(feedButton);

		//Create Action Listeners

		//Exit button
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Confirm Exit
				int choice = JOptionPane.showConfirmDialog(
						null, 
						"Are You Sure You Want To Exit?\nAny Unsaved Progress Will Be Lost.", 
						"Exit Confirmation", 
						JOptionPane.YES_NO_OPTION
						);

				//Exit to home screen if yes
				if (choice == JOptionPane.YES_OPTION) {
					new startingScreen();
					dispose();
				}
			}
		});

		//Save button
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Prompt for a file to save to
				String[] options = {"Save File 1", "Save File 2", "Save File 3", "Back"};

				int choice = JOptionPane.showOptionDialog(
						null, 
						"Choose A File To Save To:", 
						"Save To File", 
						JOptionPane.DEFAULT_OPTION, 
						JOptionPane.INFORMATION_MESSAGE, 
						null, 
						options, 
						options[3]
						);

				//Get the choise and save to file
				if (choice == 0) {
					saveToFile("save1.txt");
				} else if (choice == 1) {
					saveToFile("save2.txt");
				} else if (choice == 2) {
					saveToFile("save3.txt");
				}
			}
		});

		//Sleep Button
		sleepButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//New sleep screen
				new sleep(GameplayGUI.this);

			}
		});

		//Play Button
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {            	
				//New mini game screen
				new miniGame(GameplayGUI.this);
			}
		});

		//Vet Button
		vetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {  
				//New vet screen          	
				new vet(GameplayGUI.this);
			}
		});

		//Feed Button
		feedButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {            	
				//New feed screen
				new feed(GameplayGUI.this);
			}
		});

		//Inventory Button
		inventoryButton.addActionListener(e -> {
			//Set the inventory to visible
			inventory.setVisible(true);
		});

		//Set visibility
		setVisible(true);
	}

	/**
 	 * Updates the pet's stats and refreshes the display.
 	 *
 	 * @param happiness the updated happiness level (0-100)
 	 * @param health the updated health level (0-100)
 	 * @param sleep the updated sleep level (0-100)
 	 * @param hunger the updated hunger level (0-100)
 	 */
	public void updateStats(int happiness, int health, int sleep, int hunger) {
		//Change the stats
		this.happiness = happiness;
		this.health = health;
		this.sleep = sleep;
		this.hunger = hunger;

		//Update the text labels for each stat
		happinessValueLabel.setText(happiness + "/100");
		healthValueLabel.setText(health + "/100");
		sleepValueLabel.setText(sleep + "/100");
		hungerValueLabel.setText(hunger + "/100");

		repaint();
	}

	/**
	 * Getter for the pet's current sleep level.
 	 *
	 *@return the pet's sleep level (0-100)
 	 */
	public int getSleep(){
		return sleep;
	}

	/**
	 * Getter for the pet's current happiness level.
 	 *
	 *@return the pet's happiness level (0-100)
 	 */	
	public int getHapp(){
		return happiness;
	}

	/**
	 * Getter for the pet's current health level.
 	 *
	 *@return the pet's health level (0-100)
 	 */
	public int getH(){
		return health;
	}

	/**
	 * Getter for the players current score.
 	 *
	 *@return the players score
 	 */	
	public int getScore(){
		return score;
	}

	/**
	 * Getter for the pet's current hunger level.
 	 *
	 *@return the pet's hunger level (0-100)
 	 */	
	public int getHunger(){
		return hunger;
	}

	/**
	 * Getter for players inventory.
 	 *
	 *@return the players inventory
 	 */		 
	public InventoryScreen getInventory() {
		return inventory;
	}

	/**
 	 * Adds an amount to the current score and updates the display.
 	 *
 	 * @param scoreToAdd amount to add
 	 */
	public void addToScore(int scoreToAdd) {
		//Add to the score
		this.score = this.score + scoreToAdd;
		scoreLabel.setText("Score: " + score);

		repaint();
	}

	/**
 	 * Checks if the pet has died.
 	 */
	public void checkStats() {
		//Check if pet has died
		if (health == 0){
			JOptionPane.showMessageDialog(null, "Your Pet Has Died\nYou may continue with this pet from your last save.");
			dispose();
			new startingScreen();
		}
	}

	/**
 	 * Saves the game data to the specified file. If the file already contains a saved game,
 	 * the user is prompted to confirm whether they want to overwrite it.
	 *
 	 * @param file name of file to save to
 	 */
	private void saveToFile(String file) {
		File f = new File(file);

	    //Check if the file exists and is not empty
	    if (f.exists() && f.length() > 0) {
			//Confirm overwrite message
	        int choice = JOptionPane.showConfirmDialog(
	            null,
	            "The file has a game saved on it.\nDo you want to overwrite it?",
	            "Confirm Save",
	            JOptionPane.YES_NO_OPTION
	        );

	        if (choice != JOptionPane.YES_OPTION) {
	            JOptionPane.showMessageDialog(null, "Game not Saved.");
	            return;
	        }
	    }
		
		try (FileWriter writer = new FileWriter(file)) {
			//Write all the stats to the file
			writer.write(score + "\n");
			writer.write(happiness + "\n");
			writer.write(health + "\n");
			writer.write(sleep + "\n");
			writer.write(hunger + "\n");
			writer.write(petNumber + "\n");
			writer.write(inventory.inventoryToString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Print confirmation message
		if (file.equals("save1.txt")) {
			JOptionPane.showMessageDialog(null, "Game Saved to Save File 1");
		} else if (file.equals("save2.txt")) {
			JOptionPane.showMessageDialog(null, "Game Saved to Save File 2");
		} else if (file.equals("save3.txt")) {
			JOptionPane.showMessageDialog(null, "Game Saved to Save File 3");
		}
	}

	/**
 	 * Creates a custom JButton with the specified inputs. 
 	 *
 	 * @param text the text to be displayed on the button
 	 * @param x the x-coordinate of the button
 	 * @param y the y-coordinate of the button
 	 * @param width the width of the button
 	 * @param heigth the height of the button
 	 * @return the created JButton
 	 */
	private JButton createButton(String text, int x, int y, int width, int heigth) {
		//Position the button and change the font
		JButton button = new JButton(text);
		button.setBounds(x, y, width, heigth);
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setFocusPainted(false);
		button.setFont(new Font("SansSerif", Font.BOLD, 26));
		button.setBorder(new LineBorder(Color.BLACK, 2));

		//Add a hover effect to the button
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(Color.BLACK);
				button.setForeground(Color.WHITE);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(Color.WHITE);
				button.setForeground(Color.BLACK);
			}
		});

		return button;
	}

	/**
	 * Getter for pets icon number.
 	 *
	 *@return the pets icon number
 	 */		
	public int getPetNumber() {
		return petNumber;
	}

	/**
 	 * Paints the bars for the stats with specific colors for each stats as well as outlines and backgrounds. 
	 * 
 	 * @param g the Graphics object to paint with
 	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		//Draw Background black bars filled with white bars to show the total with an outline
		g.setColor(Color.BLACK);
		g.fillRect(248, 623, 204, 44);

		g.setColor(Color.WHITE);
		g.fillRect(250, 625, 200, 40);

		g.setColor(Color.BLACK);
		g.fillRect(248, 688, 204, 44);

		g.setColor(Color.WHITE);
		g.fillRect(250, 690, 200, 40);

		g.setColor(Color.BLACK);
		g.fillRect(848, 623, 204, 44);

		g.setColor(Color.WHITE);
		g.fillRect(850, 625, 200, 40);

		g.setColor(Color.BLACK);
		g.fillRect(848, 688, 204, 44);

		g.setColor(Color.WHITE);
		g.fillRect(850, 690, 200, 40);

		//Draw bars for the stats
		g.setColor(Color.YELLOW);
		g.fillRect(250, 625, happiness * 2, 40);

		g.setColor(Color.GREEN);
		g.fillRect(250, 690, health * 2, 40);

		g.setColor(Color.BLUE);
		g.fillRect(850, 625, sleep * 2, 40);

		g.setColor(Color.RED);
		g.fillRect(850, 690, hunger * 2, 40);
	}


    //-----------------------------Json part--------------------------------------
    /**
     * Reading the json/jsonFile.json to find availableActions block
     * and storing each action's cooldown in a map for quick access
     */
    private void loadActionCooldownsFromJson() {
        String json = readFile("json/jsonFile.json");  
        if (json == null) {
            // If file not found or error, fallback to 10 for everything
            actionCooldowns.put("feed", 5);
            actionCooldowns.put("goToSleep", 5);
            actionCooldowns.put("play", 5);
            actionCooldowns.put("takeToVet", 5);
            return;
        }

        // We'll parse out the "availableActions": { ... }
        String searchKey = "\"availableActions\":";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1) {
            // if missing, fallback
            actionCooldowns.put("feed", 5);
            actionCooldowns.put("goToSleep", 5);
            actionCooldowns.put("play", 5);
            actionCooldowns.put("takeToVet", 5);
            return;
        }

        // We'll find the next '{' and the matching '}'
        int braceOpen = json.indexOf("{", startIndex);
        int braceClose = findMatchingBrace(json, braceOpen);
        if (braceOpen == -1 || braceClose == -1) {
            // in this case, again fallback
            actionCooldowns.put("feed", 5);
            actionCooldowns.put("goToSleep", 5);
            actionCooldowns.put("play", 5);
            actionCooldowns.put("takeToVet", 5);
            return;
        }
        
        String actionsBlock = json.substring(braceOpen, braceClose + 1);

        // We will look for each known action and parse cooldown
        // feed
        int feedCd = parseCooldown(actionsBlock, "feed");
        actionCooldowns.put("feed", feedCd);

        // goToSleep
        int sleepCd = parseCooldown(actionsBlock, "goToSleep");
        actionCooldowns.put("goToSleep", sleepCd);

        // play
        int playCd = parseCooldown(actionsBlock, "play");
        actionCooldowns.put("play", playCd);

        // takeToVet
        int vetCd = parseCooldown(actionsBlock, "takeToVet");
        actionCooldowns.put("takeToVet", vetCd);
    }

    /**
     * Returning then the cooldown in seconds for a given action name, or 5 if not found
     */
    public int getActionCooldown(String actionName) {
        // If the map doesn't have it, default to 5
        return actionCooldowns.getOrDefault(actionName, 5);
    }

    /**
     * Reading the entire contents of a file into a String
     */
    private String readFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = br.readLine()) != null) {
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
     * Finding the matching closing brace for an opening '{' in a JSON substring
     */
    private int findMatchingBrace(String json, int openIndex) {
        if (openIndex == -1 || openIndex >= json.length()) return -1;
        int balance = 0;
        
        for (int i = openIndex; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '{') balance++;
            if (c == '}') balance--;

            if (balance == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Parsing the cooldown value for a given action from a block of JSON
     * Returning 5 if not found or if there's an error
     */
    private int parseCooldown(String block, String actionKey) {
        String key = "\"" + actionKey + "\":";
        int index = block.indexOf(key);

        if (index == -1) return 5;
        
        // finding the cooldown part
        String cdKey = "\"cooldown\":";
        int cdIndex = block.indexOf(cdKey, index);

        if (cdIndex == -1) return 5;
        
        // Now we can read the number after cdKey
        int numberStart = cdIndex + cdKey.length();

        // We'll then parse until we hit a comma or bracket
        int comma = block.indexOf(",", numberStart);
        int brace = block.indexOf("}", numberStart);
        
        if (comma == -1) comma = Integer.MAX_VALUE;
        if (brace == -1) brace = Integer.MAX_VALUE;
        
        int endPos = Math.min(comma, brace);
        if (endPos == Integer.MAX_VALUE) return 5;
        
        String valStr = block.substring(numberStart, endPos).trim();
        
        try {
            return Integer.parseInt(valStr);
        } 
        
        catch (NumberFormatException e) {
            return 5;
        }
    }
    // -------------------------------------------------------------------------


}
