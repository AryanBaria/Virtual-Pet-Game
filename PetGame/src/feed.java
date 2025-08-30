import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;


/**
 * Feed class represents the feed screen in the game
 * 
 * <p> The class allows user to interact with the game and decrease the pet's hunger stat and 
 * by returning home the pet will receieve a gift at random times.
 * 
 * <p>The screen showcases an image of the chosen pet in a dining room to give it good visuals and has a cooldown after user had fed the pet
 * 
 * <ul>
 *  <li>"feed button" meant to trigger hunger decrease and a cooldown pops up that does not allow user to exit out if it until timer is up
 *  <li>"return to main menu button" meant to return user to main gameplay screen
 * </ul>
 * 
 * @author Mohammed Bayoumi
 * 
 */

public class feed extends JFrame {

  private JButton newBtn, backBtn;
  private GameplayGUI getInfo;

  /**
    * Constructing the feed screen UI.
    * @param getInfo references GameplayGUI to get pet stats. 
    *
    */
  public feed(GameplayGUI getInfo) {
//creating the GUI for the action buttons to hold the images and the buttons itself
    super("Feed");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 500);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(new LineBorder(Color.blue));
    mainPanel.setBackground(new Color(204, 255, 255));
    mainPanel.setBorder(
      BorderFactory.createCompoundBorder(
        new LineBorder(Color.BLACK, 2),
        BorderFactory.createEmptyBorder(20, 20, 20, 20)
      )
    );

    JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    imagePanel.setBackground(new Color(204, 255, 255));

    ImageIcon icon = new ImageIcon("img/feed" + getInfo.getPetNumber() + ".png");
    JLabel myImg = new JLabel(icon);

    imagePanel.add(myImg);
    mainPanel.add(imagePanel);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(204, 255, 255));
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

    newBtn = btnFx("Feed!");
    newBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

    backBtn = btnFx("Return to home");
    backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
  //adding the action for the button toreturn user to main screen when triggered
    backBtn.addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent e) {
     dispose();
     String item = getInfo.getInventory().addFoodRandom();
   // implement the feature to give user randon gifts on the way home
     if (item != "") {
     new RewardsGUI(item);
     }
     }
     });

    buttonPanel.add(newBtn);
    buttonPanel.add(backBtn);
    mainPanel.add(buttonPanel);

    add(mainPanel);
    setVisible(true);
  // adding the action and logic to increase or decrease pet stats accrodingly  
    newBtn.addActionListener(e -> {
      int newSleep = (getInfo.getSleep()-1<=0)?0:getInfo.getSleep()-1;
      int newHapp = (getInfo.getHapp()+2>100)?100:getInfo.getHapp()+2;
      int newh = (getInfo.getH()+8>100)?100:getInfo.getH()+8;
      int newHunger = (getInfo.getHunger()+10>100)?100:getInfo.getHunger()+10;
    // change pet health if sleep/hunger/happiness is at 0 to give it a realistic feeling and update score
      if (newHunger <= 0|| newSleep <= 0||newHapp <= 0) {
        newh = (newh - 10 < 0) ? 0 : newh - 10;
      }

      getInfo.updateStats(newHapp, newh, newSleep, newHunger);
      getInfo.addToScore(20);
      

      int cooldown = getInfo.getActionCooldown("feed");
      startCooldownDialog(cooldown);
      getInfo.checkStats();

    });
  }

  /**
    * creating a stylish button
    *
    * @param text the text to display on the button
    * @return the styled JButton
    */
  private JButton btnFx(String text) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(200, 50));
    button.setMaximumSize(new Dimension(200, 50));
    button.setBackground(Color.WHITE);
    button.setForeground(Color.BLACK);
    button.setFocusPainted(false);
    button.setFont(new Font("SansSerif", Font.BOLD, 16));
    button.setBorder(new LineBorder(Color.BLACK, 1));

    button.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        button.setBackground(Color.black);
        button.setForeground(Color.WHITE);
      }
    //creating hover feature
      @Override
      public void mouseExited(MouseEvent e) {
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
      }
    });

    return button;
  }



  /**
 * This private method creates a cooldown window that prevents the user from interacting with the game
 * until the specified cooldown time has been done.
 * 
 * @param cooldown the number of seconds the cooldown has left until user can go back to interacting with the game.
 * 
 */ 
  private void startCooldownDialog(int cooldown) {
    //creating cooldown notification message
      final JDialog dialog = new JDialog(this, "Cooldown in Progress", true);
      dialog.setSize(350, 150);
      dialog.setLocationRelativeTo(this);
      dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
      dialog.setLayout(new BorderLayout());

      JLabel label = new JLabel("Feeding... please wait " + cooldown + "s", JLabel.CENTER);
      label.setFont(new Font("SansSerif", Font.BOLD, 14));
      dialog.add(label, BorderLayout.CENTER);
    //adding timer to track countdown
      Timer timer = new Timer(1000, new AbstractAction() {
          int timeLeft = cooldown;
          @Override
          public void actionPerformed(ActionEvent e) {
              timeLeft--;
              label.setText("Feeding... please wait " + timeLeft + "s");
              
              if (timeLeft <= 0) {
                  ((Timer)e.getSource()).stop();
                  dialog.dispose();
              }
          }
      });

      timer.setRepeats(true);
      timer.start();

      dialog.setVisible(true);
  }
}
