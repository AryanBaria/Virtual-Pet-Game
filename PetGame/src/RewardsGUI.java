import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * RewardsGUI class represents a graphical user interface that displays a reward 
 * obtained during the gameplay. It shows the image of the reward, a message, and 
 * a "Back" button to close the window.
 * <p>
 * The reward is passed as a parameter, and the corresponding image is displayed 
 * dynamically.
 *
 * <b>Example Use:</b>
 * <pre>
 * {@code
 * RewardsGUI rewardWindow = new RewardsGUI("Chicken");
 * }
 * </pre>
 *
 * <b>Example Output:</b>
 * A window with the image of a chicken, title "On Your Way Back You Got A...", 
 * "Chicken!", and a "Back" button.
 *
 * @version 1.0.0
 * @author Aryan Baria
 */
public class RewardsGUI extends JFrame{
	//Instance variables
	private String reward;

	/**
     * Constructor that creates a new rewards screen with the given reward item. 
     *
     * @param petReward the name of the reward 
     */
	public RewardsGUI(String petReward) {
		//Get the reward
		this.reward = petReward;
		
        //Create the screen
        setTitle("Rewards");
        setSize(470, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        //Set the background color
        getContentPane().setBackground(new Color(204, 255, 255));

        //Create the icons
        JLabel rewardIcon = new JLabel(new ImageIcon("img/" + reward + ".png"));

        //Create the labels
        JLabel title = new JLabel("On Your Way Back You Got A...");
        JLabel text = new JLabel(reward + "!");
        
        //Create the buttons
        JButton backButton = new JButton("Back");

        //Position the icons
        rewardIcon.setBounds(135, 95, 200, 200);
        
        //Position the labels and change fonts
        title.setFont(new Font("SansSerif", Font.BOLD, 25));
        title.setBounds(40, 35, 400, 50);
        
        text.setFont(new Font("SansSerif", Font.BOLD, 30));
        Dimension textSize = text.getPreferredSize();
        int x = (getWidth() - textSize.width) / 2;
        text.setBounds(x, 305, 200, textSize.height);
        
        //Position the button and change the font
        backButton.setBounds(350, 10, 100, 30);
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        backButton.setBorder(new LineBorder(Color.BLACK, 2));
        
        //Add a hover effect to the button
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            	backButton.setBackground(Color.BLACK);
            	backButton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	backButton.setBackground(Color.WHITE);
                backButton.setForeground(Color.BLACK);
            }
        });
        
        //Add functionality to the button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Close the system
                dispose();
            }
        });
        
        //Add the icon
        add(rewardIcon);
        
        //Add the labels
        add(title);
        add(text);
        
        //Add the button
        add(backButton);

        //Set visibility
        setVisible(true);
    }
}