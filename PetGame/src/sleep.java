import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;


/**
 * Sleep class represents the sleep screen in the game
 * 
 * <p> The screen allows user to interact with the game and increase the pet's sleep stat and 
 * by returning home the pet will receieve a gift at random times. 
 * 
 * <p>The screen displays an image of the chosen pet in a bedroom environment for immersive visuals.
 * 
 * <ul>
 *  <li><b>"go to bed button"</b> once triggered, increases the sleep bar and reduces other stats.
 *  <li><b>"return to main menu button"</b> meant to return user to main gameplay screen and at randmo times, they may receieve a gift that
 * would be added to the inventory.
 * </ul>
 * 
 * @author: Mohammed Bayoumi
 * 
 */
public class sleep extends JFrame {

  private JButton newBtn, backBtn;
  private JLabel messageLabel;
  private GameplayGUI getInfo;

  /**
    * Constructing the feed screen UI.
    * @param getInfo references GameplayGUI to get pet stats. 
    *
    */
  public sleep(GameplayGUI getInfo) {
// creating GUI to hold the action information with the image
    super("Sleep");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(600, 500);
    setLocationRelativeTo(null);
// retrieving the pet stats
    this.getInfo = getInfo;

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
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setOpaque(true);
    mainPanel.setBackground(new Color(204, 255, 255));

    JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    imagePanel.setBackground(new Color(204, 255, 255));

    ImageIcon icon = new ImageIcon("img/sleep" + getInfo.getPetNumber() + ".png");
    JLabel myImg = new JLabel(icon);

    imagePanel.add(myImg);
    mainPanel.add(imagePanel);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(204, 255, 255));
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

    newBtn = btnFx("Go to bed!");
    newBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

    backBtn = btnFx("Return to home");
    backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
  
  //adding action to the button to let it return user home when they trigger the button
    backBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
      dispose();
      String item = getInfo.getInventory().addFoodRandom();

  //allow users to get random gifts when they return home to give it realistic feeling
      if (item != "") {
      new RewardsGUI(item);
      }
      }
      });

    buttonPanel.add(newBtn);
    buttonPanel.add(backBtn);
    mainPanel.add(buttonPanel);

  //initiating the game logic to update pet stats accordingly whe sleep button is triggered
    newBtn.addActionListener(e -> {
      int newSleep = (getInfo.getSleep()+10>100)? 100:getInfo.getSleep()+10;
      int newHapp = (getInfo.getHapp()-5<1)?0:getInfo.getHapp()-5;
      int newh = (getInfo.getH()-8<0)?0:getInfo.getH()-8;
      int newHunger = (getInfo.getHunger()-10<10)?0:getInfo.getHunger()-10;

    //reducing other values when sleeing to give it a real life aspect
      if (newHunger <= 0|| newSleep <= 0||newHapp <= 0) {
        newh = (newh - 10 < 0) ? 0 : newh - 10;
      }

      // update game stats to reflect changes on main gameplay screen
      getInfo.updateStats(newHapp, newh, newSleep, newHunger);
      getInfo.addToScore(20);

      int cooldown = getInfo.getActionCooldown("goToSleep");
      startCooldownDialog(cooldown);
      getInfo.checkStats();
    });

    add(mainPanel);
    setVisible(true);
  }

  /**
    * creating a stylish button
    *
    * @param text the text to display on the button
    * @return the styled JButton
    */
  private JButton btnFx(String text) {
    //creating a stylish button that is eye pleasing
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
      final JDialog dialog = new JDialog(this, "Cooldown in Progress", true);
      dialog.setSize(350, 150);
      dialog.setLocationRelativeTo(this);
      dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
      dialog.setLayout(new BorderLayout());
    //creating cooldown notification message
      JLabel label = new JLabel("Sleeping... please wait " + cooldown + "s", JLabel.CENTER);
      label.setFont(new Font("SansSerif", Font.BOLD, 14));
      dialog.add(label, BorderLayout.CENTER);
    //adding timer to track countdown
      Timer timer = new Timer(1000, new AbstractAction() {
          int timeLeft = cooldown;
          @Override
          public void actionPerformed(ActionEvent e) {
              timeLeft--;
              label.setText("Sleeping... please wait " + timeLeft + "s");
              
              
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
