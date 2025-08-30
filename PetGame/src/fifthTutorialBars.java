import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *  @author Maher Rammal
 * 
 * Extending JFrame for fifth tutorial screen
 *
 * Configuring layout with center panel for pet status overview and bottom panel for navigation
 * transitioning to last screen
 */
public class fifthTutorialBars extends JFrame {
     //Variable added by Aryan
	private int petNumber;
    /**
     * Initializing tutorial screen 5 with layout configuration and navigation
     *
     * Setting frame title, default close operation, size, extended state, and center location
     * Configuring BorderLayout with center panel for displaying pet status images and descriptions
     * Adding bottom panel with back and next buttons for navigation
     * Displaying frame
     * 
     * @param petNumber
     * 
     */
    public fifthTutorialBars(int petNumber) {
        super("Pet Status Overview");
        
        this.petNumber = petNumber;
        
        // centering top title, and formatting actions and descriptions using grid layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(204, 255, 255));

        setLayout(new BorderLayout(10, 10));
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);
        JLabel welcomeLabel = new JLabel("Pet's Status");
        welcomeLabel.setFont(new Font("Barlow", Font.BOLD, 36));
        topPanel.add(welcomeLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        centerPanel.setBackground(new Color(204, 255, 255)); 

        
        // storing images to be formatted 
        String[] barImages = {
            "img/happyBar.jpg",
            "img/healthBar.jpg",
            "img/sleepBar.jpg",
            "img/hungerBar.jpg"
        };

        // storing descriptions to match the images 
        String[] descriptions = {
            "Shows how happy your pet is",
            "Indicates your pet’s current health level",
            "Represents how well your pet has rested",
            "Displays how hungry your pet is"
        };

        // adjusting the image by going through the array and adjusting the size 
        for (int i = 0; i < barImages.length; i++) {
            ImageIcon icon = new ImageIcon(barImages[i]);
            Image scaled = icon.getImage().getScaledInstance(320, 60, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(scaled));
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
            centerPanel.add(imgLabel);

            JLabel descLabel = new JLabel(descriptions[i], SwingConstants.LEFT);
            descLabel.setFont(new Font("Barlow", Font.BOLD, 18));
            centerPanel.add(descLabel);
        }

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);


        // Button to go back to the previous file/window
        JButton backButton = createMainButton("Back");
        backButton.addActionListener(e -> {
            new fourthTutorialActions(petNumber);
            dispose();
        });


        // next button to go to the foward file/window
        JButton nextButton = createMainButton("Next >");
        nextButton.addActionListener(e -> {
            new lastTutorialScreen(petNumber);
            dispose();
        });

        bottomPanel.add(backButton);
        bottomPanel.add(nextButton);

        add(bottomPanel, BorderLayout.SOUTH);


        // Once a button is pressed to connect to this file, it will open correcly/smoothly 
        setVisible(true);
    }

    /**
     * Creating main button with uppercase text, fixed dimensions, Barlow font, and hover effect
     *
     * Setting preferred size, background, foreground, font, border, and cursor style
     * Attaching mouse listener for hover effect switching background and foreground colors
     *
     * @param text button text to style
     * @return styled main button component
     */
    private JButton createMainButton(String text) {
        JButton button = new JButton(text.toUpperCase());
        button.setPreferredSize(new Dimension(180, 65));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Barlow", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.BLACK, 2));
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

    // IGNORE THIS PART 
    /**
     *
     * Invoking GUI on event dispatch thread using SwingUtilities
     *
     * @param args command-line arguments (in this case, unused)
     */
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(fifthTutorialBars::new);
//    }
}
