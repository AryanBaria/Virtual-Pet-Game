import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *  @author Maher Rammal
 * 
 * Displaying the first tutorial screen
 * <p>
 * showing welcome message and intro text with a Next button
 * transitioning to second screen
 */
public class firstTutorialScreen extends JFrame {

    /**
     * Constructing the tutorial screen GUI window
     * <p>
     * layout includes heading, tutorial text block, and a Next button
     */
    public firstTutorialScreen() {

        // Setting up window/frame, size, color, etc
        super("Tutorial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(600, 400);
        setLocationRelativeTo(null); 

        getContentPane().setBackground(new Color(204, 255, 255));

        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);
        JLabel welcomeLabel = new JLabel("WELCOME!");
        welcomeLabel.setFont(new Font("Barlow", Font.BOLD, 36));
        topPanel.add(welcomeLabel);
        add(topPanel, BorderLayout.NORTH);

      // Displaying message using texthtml format so that it is easier to format, and then centering it 
    
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        String textHtml = "<html>"
            + "<p align='center'>"
            + "Are you ready to take care of a pet? There are a lot of responsibilities,<br>"
            + "but you got this! We’ll walk you through everything step by step."
            + "</p>"
            + "<p align='center'>"
            + "First, let’s choose your new furry friend! Each pet has its own personality and needs,<br>"
            + "so pick the one that suits you best. Click the next button below so you can pick your pet"
            + "</p>"
            + "</html>";
        JLabel infoLabel = new JLabel(textHtml);
        infoLabel.setFont(new Font("Barlow", Font.PLAIN, 16));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(infoLabel);
        add(centerPanel, BorderLayout.CENTER);

        //Bottom panel for the buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);


        //back button to go back a window/file
        JButton backButton = createMainButton("< Back");
        backButton.addActionListener(e -> {
            new startingScreen();
            dispose();
        });


        //next button to go foward a window/file
        JButton nextButton = createMainButton("Next >");
        nextButton.addActionListener(e -> {
            new secondTutorialPickPet();
            dispose();
        });

        bottomPanel.add(backButton);
        bottomPanel.add(nextButton);

        // adding the buttons to the bottom
        add(bottomPanel, BorderLayout.SOUTH);

        //Once a button is pressed to connect to this file, it will open correcly/smoothly 
        setVisible(true);
    }

    /**
     * Creating a styled button with Barlow font and hover effect
     *
     * @param text button label text
     * @return styled JButton with hover interactivity
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

    /**
     * Launching the tutorial screen on the Event Dispatch Thread
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(firstTutorialScreen::new);
    }
}
