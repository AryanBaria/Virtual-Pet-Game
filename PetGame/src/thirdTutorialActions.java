import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 *  @author Maher Rammal
 * 
 * Extending JFrame for third tutorial screen
 *
 * Configuring layout with top, center, and bottom panels
 * Integrating action and description grid with navigation buttons
 * transitioning to fourth screen
 */
public class thirdTutorialActions extends JFrame {

    //Variable added by Aryan
	private int petNumber;

    /**
     * Initializing tutorial screen 3 with layout configuration and navigation
     *
     * Setting frame title, close operation, size, extended state, and center location
     * Configuring BorderLayout with panels for heading, actions and descriptions, and navigation buttons
     * Adding headers and grid of action and description labels
     * Displaying frame
     * 
     * @param petNumber
     * 
     */
    public thirdTutorialActions(int petNumber) {
        super("Tutorial");
        
        this.petNumber = petNumber;
        
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(204, 255, 255));

        setLayout(new BorderLayout(10, 10));


        //Centering top tile, overall format
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);
        JLabel welcomeLabel = new JLabel("Action Buttons");
        welcomeLabel.setFont(new Font("Barlow", Font.BOLD, 36));
        topPanel.add(welcomeLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel actionsHeader = new JLabel("ACTIONS", SwingConstants.CENTER);
        actionsHeader.setFont(new Font("Barlow", Font.BOLD, 18));
        JLabel descriptionHeader = new JLabel("DESCRIPTION", SwingConstants.CENTER);
        descriptionHeader.setFont(new Font("Barlow", Font.BOLD, 18));

        centerPanel.add(actionsHeader);
        centerPanel.add(descriptionHeader);


         // actions but these will be attached to jlabels as fake buttons
        String[] actions = {
            "Score",
            "Go to Sleep",
            "Take to Vet",
            "Feed",
            "Play Games",
       
            
        };


        // descrition of each jlabel/fake button
        String[] descriptions = {
            "This box keeps track of your score",
            "This action lets your pet go to sleep",
            "This action takes your pet to the vet",
            "This action lets you feed your pet",
            "This action lets you play games with your pet",
            
            
        };


        // Creating the fake jlabel as mentioned above, as this will basically be a button with no functionality (to not confuse the user)
        for (int i = 0; i < actions.length; i++) {
            JLabel actionLabel = createFakeButton(actions[i]);
            centerPanel.add(actionLabel);

            JLabel descLabel = new JLabel(descriptions[i], SwingConstants.CENTER);
            descLabel.setFont(new Font("Barlow", Font.BOLD, 18));
            centerPanel.add(descLabel);
        }

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);


        // Button to go back to the previous file/window 
        JButton backButton = createMainButton("Back");
        backButton.addActionListener(e -> {
            new secondTutorialPickPet();
            dispose();
        });


        // next button to go to the foward file/window
        JButton nextButton = createMainButton("Next >");
        nextButton.addActionListener(e -> {
            new fourthTutorialActions(petNumber);
            dispose();
        });

        bottomPanel.add(backButton);
        bottomPanel.add(nextButton);
        add(bottomPanel, BorderLayout.SOUTH);


        //Once a button is pressed to connect to this file, it will open correcly/smoothly 
        setVisible(true);
    }

    /**
     * Creating fake button label styled to mimic a button (without functionality) with uppercase text, Barlow font, border, and hover effect
     *
     * Setting fixed dimensions, background, foreground, and opacity
     * Attaching mouse listener for hover effect changing background and foreground colors
     *
     * @param text label text to style
     * @return styled label component
     */
    private JLabel createFakeButton(String text) {
        JLabel label = new JLabel(text.toUpperCase(), SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(120, 40));
        label.setFont(new Font("Barlow", Font.BOLD, 14));
        label.setBorder(new LineBorder(Color.BLACK, 2));
        label.setBackground(Color.WHITE);
        label.setForeground(Color.BLACK);
        label.setOpaque(true);

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                label.setBackground(Color.BLACK);
                label.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }
        });

        return label;
    }

    /**
     * Creating main button with uppercase text, fixed dimensions, Barlow font, and hover effect
     *
     * Setting preferred size, background, foreground, font, border, and cursor style
     * Attaching mouse listener for hover effect changing background and foreground colors
     *
     * @param text button text
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


    //IGNORE THIS PART
    /**
     * 
     * Invoking GUI on event dispatch thread using SwingUtilities
     *
     * @param args command-line arguments (unused)
     */
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(thirdTutorialActions::new);
//    }
}
