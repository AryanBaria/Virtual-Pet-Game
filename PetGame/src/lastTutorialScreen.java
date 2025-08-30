import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;


/**
 * @author Maher Rammal
 * 
 * Extending JFrame for last tutorial screen
 *
 * Configuring layout with top, center and bottom panels
 * Integrating instructional text and navigation buttons
 * transitioning to game play screen
 */
public class lastTutorialScreen extends JFrame {

    //Variable added by Aryan
	private int petNumber;
    /**
     * Initializing tutorial screen 6 with layout configuration and navigation
     *
     * Setting frame title, default close operation, size, extended state and center location
     * Configuring BorderLayout with panels for heading, tutorial text and navigation buttons
     * Adding instructional text and navigation buttons with placeholder action
     * Displaying frame
     * 
     * @param petNumber
     * 
     */
    public lastTutorialScreen(int petNumber) {
        super("Tutorial");
        
        this.petNumber = petNumber;
        
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(204, 255, 255));

        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);
        JLabel welcomeLabel = new JLabel("Have Fun!");
        welcomeLabel.setFont(new Font("Barlow", Font.BOLD, 36));
        topPanel.add(welcomeLabel);
        add(topPanel, BorderLayout.NORTH);


        // explaining the end of the tutorial, using textHTML format so that it can be formatted easily into a Jlabel
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        String textHtml = "<html>"
            + "<p align='center'>"
            + "Great job! You're all set to take care of your new pet<br>"
            + "Remember to feed them, play with them, and give them lots of love<br>"
            + "Keep an eye on their needs, and they'll grow into a happy and healthy companion"
            + "</p>"
            + "<p align='center'>"
            + "Now go have fun and enjoy your new adenture together!"
            + "</p>"
            + "</html>";
        JLabel infoLabel = new JLabel(textHtml);
        infoLabel.setFont(new Font("Barlow", Font.PLAIN, 16));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(infoLabel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);


        //back button
        JButton backButton = createMainButton("Back");
        backButton.addActionListener(e -> {
            new fifthTutorialBars(petNumber);
            dispose();
        });


        //next button, but when going to the next page, sending in values for the gameplay
        JButton nextButton = createMainButton("Next >");
        nextButton.addActionListener(e -> {
            new GameplayGUI(0, 70, 100, 70, 70, petNumber, null);
            dispose();
        });
        

        bottomPanel.add(backButton);
        bottomPanel.add(nextButton);
        add(bottomPanel, BorderLayout.SOUTH);


        // Once a button is pressed to connect to this file, it will open correcly/smoothly 
        setVisible(true);
    }

    /**
     * Creating main button with uppercase text, fixed dimensions, Barlow font and hover effect
     *
     * Setting preferred size, background, foreground, font, border and cursor style
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
     * @param args command-line arguments (unused)
     */
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(lastTutorialScreen::new);
//    }
}
