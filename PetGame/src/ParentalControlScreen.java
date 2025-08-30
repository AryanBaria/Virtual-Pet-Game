import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.geom.Rectangle2D; 

/**
 * Represents the Parental Control interface of the pet simulation game.
 * Provides functionality to restrict playtime, monitor average usage,
 * and protect access using a password.
 * 
 * <p>This screen reads parental settings from a JSON file and applies UI
 * restrictions based on those values. It includes features for:
 * <ul>
 *   <li>Setting time restrictions for gameplay</li>
 *   <li>Viewing average playtime statistics</li>
 *   <li>Password protection for parental controls</li>
 * </ul>
 * 
 * Author: Dilraj Deogan
 */
public class ParentalControlScreen extends JFrame {
    /** Button to access time restriction settings */
    private JButton setTimeRestrictionsButton;
    
    /** Button to view average playtime statistics */
    private JButton averagePlaytimeButton;
    
    /** Button to return to the main screen */
    private JButton backButton;
    
    /** Label displaying the pet's current status */
    private JLabel petStatusLabel;
    
    /** Label displaying the pet's sprite/image */
    private JLabel petSpriteLabel;
    
    /** Panel containing pet-related UI elements */
    private JPanel petPanel;

    /** Label for displaying system status messages */
    private JLabel statusLabel;
    
    /** Label for displaying playtime statistics */
    private JLabel playtimeStatsLabel;
    
    /** Daily playtime limit in minutes */
    private int dailyPlaytimeLimit = 0;
    
    /** Total accumulated playtime in minutes */
    private int totalPlaytime = 0;
    
    /** Average playtime in minutes */
    private int avgPlaytime = 0;
    
    /** Parental control password */
    private String parentPassword;
    
    /** Time at which playtime resets (in minutes since midnight) */
    private int resetPlayTime = 0;         
    
    /** Flag indicating if pet revival is enabled */
    private boolean petRevival = false;    

    /** Overlay panel for password protection */
    private JPanel overlayPanel;
    
    /** Buttons on overlay to trigger password input */
    private JButton passwordOverlayButton;

    private JButton overlayBackButton;

    /**
     * Constructs the Parental Control screen and initializes all UI components.
     * Sets up the main window layout, loads parental settings from JSON,
     * and creates the password protection overlay.
     */
    public ParentalControlScreen() {
        // Set up the frame
        setTitle("Parental Controls");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 255, 240)); // Background color
        setLocationRelativeTo(null); // Center the window
        
        // Set the layout and font
        setLayout(new BorderLayout(10, 10));
        Font font = new Font("SansSerif", Font.BOLD, 16);
        
        // Create the top half for pet display
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(new Color(255, 255, 200)); // Yellow Background
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Pet display area
        petPanel = new JPanel();
        petPanel.setLayout(new BorderLayout());
        petPanel.setBackground(new Color(255, 255, 200));
        petPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 100), 2, true));
        
        // Placeholder pet sprite
        petSpriteLabel = new JLabel(new ImageIcon("img/park4.png"), JLabel.CENTER);
        petPanel.add(petSpriteLabel, BorderLayout.CENTER);
        
        petStatusLabel = new JLabel("Pet Status: Happy!", JLabel.CENTER);
        petStatusLabel.setFont(font);
        petStatusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        petPanel.add(petStatusLabel, BorderLayout.SOUTH);
        
        topPanel.add(petPanel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel for status messages
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(240, 255, 240));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setFont(font);
        centerPanel.add(statusLabel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        
        // Bottom panel for buttons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 1, 10, 15));
        buttonsPanel.setBackground(new Color(240, 255, 240));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));
        
        // Styled buttons
        setTimeRestrictionsButton = createButton("Set Time Restrictions");
        averagePlaytimeButton = createButton("Average Playtime Allowed");
        backButton = createButton("Back");
        
        buttonsPanel.add(setTimeRestrictionsButton);
        buttonsPanel.add(averagePlaytimeButton);
        buttonsPanel.add(backButton);

        loadParentalSettings(); 
        
        add(buttonsPanel, BorderLayout.SOUTH);
        
        // Create password protection overlay
        createOverlayPanel();
        setGlassPane(overlayPanel);
        overlayPanel.setVisible(true); 

        setVisible(true);
    }

    /**
     * Loads parental settings from the JSON configuration file.
     * <p>Extracts the following values from the JSON file:
     * <ul>
     *   <li>Parent password</li>
     *   <li>Total playtime</li>
     *   <li>Average playtime</li>
     *   <li>Playtime reset time</li>
     *   <li>Pet revival setting</li>
     * </ul>
     * Updates the status label with the loaded password information.
     */
    private void loadParentalSettings() {
        String json = readFile("json/jsonFile.json");  
        if (json != null) {
            parentPassword = extractValue(json, "parentPassword");
            String totalPT = extractValue(json, "totalPlayTime");
            String avgPT = extractValue(json, "averagePlayTime");
            String resetPT = extractValue(json, "resetPlayTime");
            String petRevivalStr = extractValue(json, "petRevival");

            try {
                if (totalPT != null) {
                    totalPlaytime = Integer.parseInt(totalPT);
                }
                
                if (avgPT != null) {
                    avgPlaytime = Integer.parseInt(avgPT);
                }

                if (resetPT != null) {
                    resetPlayTime = Integer.parseInt(resetPT);
                }
                
                if (petRevivalStr != null) {
                    petRevival = Boolean.parseBoolean(petRevivalStr);
                }
                
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            String displayedPass = (parentPassword != null) ? parentPassword : "N/A";
            statusLabel.setText("<html><div style='text-align: center; color: green;'>" + 
                                  "</div></html>");
        } else {
            statusLabel.setText("Failed to load parental settings.");
        }
    }
    
    /**
     * Reads the entire contents of a file into a String.
     *
     * @param filePath the path to the file to be read
     * @return the file content as a String, or null if an error occurs
     */
    private String readFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        return sb.toString();
    }
    
    /**
     * Extracts the value for a given key from a JSON string.
     *
     * @param json the JSON string to parse
     * @param key the key whose value should be extracted
     * @return the extracted value as a String, or null if the key is not found
     */
    private String extractValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int keyIndex = json.indexOf(searchKey);

        if (keyIndex == -1) {
            return null;
        }

        int firstQuote = json.indexOf("\"", keyIndex + searchKey.length());
        if (firstQuote == -1) {
            return null;
        }

        int secondQuote = json.indexOf("\"", firstQuote + 1);
        if (secondQuote == -1) {
            return null;
        }

        return json.substring(firstQuote + 1, secondQuote);
    }

    /**
     * Creates a styled button with rounded corners and hover effects.
     *
     * @param text the text to display on the button
     * @return the configured JButton instance
     */
    private JButton createButton(String text) {
        JButton button = new RoundedButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(144, 238, 144)); // Light green
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(152, 251, 152)); // Lighter green on hover
                button.setForeground(Color.BLACK);
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(144, 238, 144)); // Default green
                button.setForeground(Color.BLACK);
            }
            public void mousePressed(MouseEvent evt) {
                button.setBackground(new Color(0, 100, 0)); // Dark green when pressed
                button.setForeground(Color.WHITE);
            }
            public void mouseReleased(MouseEvent evt) {
                button.setBackground(new Color(152, 251, 152));
                button.setForeground(Color.BLACK);
            }
        });
        
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(e);
            }
        });
        
        return button;
    }

    /**
     * Custom JButton implementation.
     * Provides visual feedback for different button states.
     */
    class RoundedButton extends JButton {
        private static final int ARC_WIDTH = 20;
        private static final int ARC_HEIGHT = 20;
        
        /**
         * Creates a new button with specified text.
         *
         * @param text the text to display on the button
         */
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
        }
        
        /**
         * Changes appearance based on button state (pressed, hover, normal).
         *
         * @param g the Graphics object to protect
         */
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
        
        /**
         *
         * @param g the Graphics object to protect
         */
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
     * Handles button click events for the main control buttons.
     *
     * @param e the ActionEvent containing information about the button click
     */
    private void handleButtonClick(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source == setTimeRestrictionsButton) {
            openTimeRestrictionsWindow();
        } else if (source == averagePlaytimeButton) {
            openAveragePlaytimeWindow();
        } else if (source == backButton) {
            new startingScreen();
            dispose();
        }
    }

    /**
     * Opens the time restrictions configuration window.
     */
    public void openTimeRestrictionsWindow() {
        new TimeRestrictionWindow(this).setVisible(true);
    }

    /**
     * Opens the average playtime statistics window.
     * Because of complications, this window was taken out, although can be implemented afterwards.
     */
    public void openAveragePlaytimeWindow() {
        new AveragePlaytimeWindow(this).setVisible(true);
    }

    /**
     * Displays a success message in the status label.
     *
     * @param message the message to display
     */
    public void showSavedMessage(String message) {
        statusLabel.setText("<html><div style='text-align: center; color: green;'>" + message + "</div></html>");
    }
    
    /**
     * Updates the playtime statistics with new playtime data.
     *
     * @param playtimeInMinutes the amount of playtime to add (in minutes)
     */
    public void updatePlaytimeStats(int playtimeInMinutes) {
        totalPlaytime += playtimeInMinutes;
        avgPlaytime = totalPlaytime / 1;
    }

    /**
     * Creates the password protection overlay panel.
     * <p>The overlay:
     * <ul>
     *   <li>Covers the entire window with a semi-transparent layer</li>
     *   <li>Displays a large "PASSWORD" button in the center</li>
     *   <li>Shows a password input dialog when clicked</li>
     *   <li>Removes the overlay if correct password is entered</li>
     * </ul>
     */
    private void createOverlayPanel() {
        overlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 120)); // Semi-transparent black background (50% opacity)
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };

        // Making sure the overlay locks other buttons
        overlayPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume(); 
            }

        });

        overlayPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                e.consume();
            }
            
        });


        overlayPanel.setLayout(null); 
        overlayPanel.setOpaque(false);

        // Create and configure password button
        passwordOverlayButton = new JButton("PASSWORD");
        passwordOverlayButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        passwordOverlayButton.setForeground(Color.WHITE);
        passwordOverlayButton.setBackground(new Color(0, 100, 0));
        passwordOverlayButton.setFocusPainted(false);
        passwordOverlayButton.setBounds(450, 300, 300, 100);

        // Add password verification action
        passwordOverlayButton.addActionListener(e -> {
            String entered = JOptionPane.showInputDialog(
                this, 
                "Enter Password:", 
                "Parental Control", 
                JOptionPane.PLAIN_MESSAGE
            );

            if (entered != null && entered.equals(parentPassword)) {
                overlayPanel.setVisible(false);
            } else if (entered != null) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Wrong password!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // ---------------- Back button if the user does not want to continue in settings ----------------
        overlayBackButton = new JButton("BACK");
        overlayBackButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        overlayBackButton.setForeground(Color.WHITE);
        overlayBackButton.setBackground(new Color(128, 0, 0)); 
        overlayBackButton.setFocusPainted(false);
        overlayBackButton.setBounds(912, 10, 250, 80);

        overlayBackButton.addActionListener(e -> {
            // allowing to go back to startingScreen (no password needed)
            new startingScreen();
            dispose();
        });

        // Add both to the overlay
        overlayPanel.add(passwordOverlayButton);
        overlayPanel.add(overlayBackButton);
    }

    /**
     * Main method to launch the Parental Control screen.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ParentalControlScreen frame = new ParentalControlScreen();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null); // Center the window
        });
    }
}