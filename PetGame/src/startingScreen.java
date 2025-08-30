import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.LineBorder;


/**
 *  @author Maher Rammal
 * 
 * Class represents the main landing screen for the game
 * <p>
 * Displaying the game title, a list of available actions (as styled buttons),
 * a welcome image, and developer credits at the bottom of the screen (Us)
 * Screen serves as the gateway to the rest of the game flow
 * Connects to tutorial, loading game, settings, or exits 
 */
public class startingScreen extends JFrame {
	
	private int score, health, sleep, hunger, happiness, petNumber;
	private String inventoryString; 

    /**
     * Constructing the home screen GUI window
     * <p>
     * Seting up the main layout with a heading, action buttons, a central image,
     * and a developer credit box (Us)
     */
    public startingScreen() {

        //Setting up main window/frame with the size, color
        super("Home Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null); 

        getContentPane().setBackground(new Color(204, 255, 255));

        setLayout(new BorderLayout(10, 10));

     
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);
        JLabel welcomeLabel = new JLabel("WELCOME!");
        welcomeLabel.setFont(new Font("Barlow", Font.BOLD, 36));
        topPanel.add(welcomeLabel);
        add(topPanel, BorderLayout.NORTH);

       
        // Setting up grid layout so that we have 2 columns 
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        JPanel actionsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        actionsPanel.setOpaque(false);


        // Starting actions to start game, settings
        String[] actions = {
            "Start New Game",
            "Load Game",
            "Parental Screen",
            "Exit"
        };


        // Cases, so that when a button is pressed go to the respected file
        for (String action : actions) {
            JButton button = createStyledButton(action);
      
            switch (action.toLowerCase()) {
                
                case "start new game":
                    button.addActionListener(e -> {
                        new firstTutorialScreen();
                        dispose();
                    });

                    break;
                
                //-------------------- Aryan Added this--------------------
                case "load game":
                    button.addActionListener(e -> {
                    	String[] options = {"Save File 1", "Save File 2", "Save File 3", "Cancel"};

        				int choice = JOptionPane.showOptionDialog(
        						null, 
        						"Choose A File To Load From:", 
        						"Save To File", 
        						JOptionPane.DEFAULT_OPTION, 
        						JOptionPane.INFORMATION_MESSAGE, 
        						null, 
        						options, 
        						options[3]
        						);

        				// Handle the user's choice
        				if (choice == 0) {
        					loadFromFile("save1.txt");
        				} else if (choice == 1) {
        					loadFromFile("save2.txt");
        				} else if (choice == 2) {
        					loadFromFile("save3.txt");
        				}
                    });

                    break;

                     //-----------------------------------------------------
                
                case "parental screen":
                    button.addActionListener(e -> {
                        new ParentalControlScreen();
                        dispose();
                    });

                    break;
                
                case "exit":
                    button.addActionListener(e -> System.exit(0));
                    break;
                
                    default:
                    button.addActionListener(e -> {
                        JOptionPane.showMessageDialog(this, action + " clicked!");
                    });
            }
            

            actionsPanel.add(button);
        }

        centerPanel.add(actionsPanel);

       //Adding in the image as a label so that it can be moved easily 
        JLabel imageLabel = new JLabel(new ImageIcon("img/cat.png"));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(imageLabel);

        add(centerPanel, BorderLayout.CENTER);

       // Bottom panel to display names
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        bottomPanel.setPreferredSize(new Dimension(600, 60));

        JLabel devsLabel = new JLabel("Developers: Aryan Baria, Dilraj Deogan, Maher Rammal, Mohammed Bayoumi, Rayan Amir");
        devsLabel.setFont(new Font("Barlow", Font.PLAIN, 14));
        bottomPanel.add(devsLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    
     //------------------ Aryan Added this ----------------------------------------------
    private void loadFromFile(String fileName) {  
        	 File file = new File(fileName);
        	
            // Checking if the file exists and is empty
            if (!file.exists() || file.length() == 0) {
                JOptionPane.showMessageDialog(null, "ERROR! No Game Saved To This File.", "Load Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        	
            int score = Integer.parseInt(reader.readLine());
            int happiness = Integer.parseInt(reader.readLine());
            int health = Integer.parseInt(reader.readLine());
            int sleep = Integer.parseInt(reader.readLine());
            int hunger = Integer.parseInt(reader.readLine());
            int petNumber = Integer.parseInt(reader.readLine());

            // Reading all inventory lines and concatenate them into a single string
            StringBuilder inventoryData = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                inventoryData.append(line).append("\n");
            }

            JOptionPane.showMessageDialog(null, "Game Loaded Successfully!");
            dispose();
            
            new GameplayGUI(score, happiness, health, sleep, hunger, petNumber, inventoryData.toString());
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error loading file!", "Load Error", JOptionPane.ERROR_MESSAGE);
        }
    }

  //--------------------------------------------------------------------------------------------------------------


    /**
     * Creating a styled button with consistent appearance for use on the home screen
     * <p>
     * In this case, buttons are uppercased, bordered, use the Barlow font, and include a hover effect
     *
     * @param text the text label for the button
     * @return a fully styled {@code JButton} with mouse hover effects
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text.toUpperCase());
        button.setFont(new Font("Barlow", Font.BOLD, 14));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.BLACK, 2));
        button.setPreferredSize(new Dimension(180, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    
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
     * Launching the on the Event Dispatch Thread
     *
     * @param args command-line arguments (In this case, not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(startingScreen::new);
    }
}
