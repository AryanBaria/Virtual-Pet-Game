import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * Vet class represents the health screen in the game
 * 
 * <p> The class allows user to interact with the game increase the pets health stat and
 * by returning home the pet will receieve a gift at random times.  
 * 
 * <p>The screen showcases an image of the chosen pet in a vet to give it good visuals
 * 
 * <ul>
 *  <li><b>"heal button"</b> meant to trigger health increase
 *  <li><b>"return to main menu button"</b> meant to return user to main gameplay screen
 * </ul>
 * 
 * @author Mohammed Bayoumi
 * 
 */
public class vet extends JFrame {
  private JButton newBtn, backBtn;
  private GameplayGUI getInfo;

 /**
    * Constructing the feed screen UI.
    * @param getInfo references GameplayGUI to get pet stats. 
    *
    */
  public vet(GameplayGUI getInfo) {

    super("Vet");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(600, 500);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(new LineBorder(Color.blue));
    mainPanel.setOpaque(true);
    mainPanel.setBackground(new Color(204, 255, 255));
// retrieving the pet stats
    this.getInfo = getInfo;

    mainPanel.setBorder(
      BorderFactory.createCompoundBorder(
        new LineBorder(Color.BLACK, 2),
        BorderFactory.createEmptyBorder(20, 20, 20, 20)
      )
    );

    JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    imagePanel.setBackground(new Color(204, 255, 255));

    ImageIcon icon = new ImageIcon("img/vet" + getInfo.getPetNumber() + ".png");
    JLabel myImg = new JLabel(icon);

    imagePanel.add(myImg);
    mainPanel.add(imagePanel);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(204, 255, 255));
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

    newBtn = btnFx("Heal!");
    newBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

    newBtn.addActionListener(e -> {
  //adding game logic for the vet, gt pet info and edit them accordingly
      int newSleep = (getInfo.getSleep() - 5 <= 0)? 0 : getInfo.getSleep() - 5;
      int newHapp = (getInfo.getHapp() - 3 <= 0)? 100 : getInfo.getHapp() - 3;
      int newh = (getInfo.getH() + 10 > 100)? 100 : getInfo.getH() + 10;
      int newHunger = (getInfo.getHunger() - 5 <= 0)? 0 : getInfo.getHunger() - 5;
  //change pet health according to each other stat 
      if (newHunger <= 0|| newSleep <= 0||newHapp <= 0) {
        newh = (newh - 10 < 0) ? 0 : newh - 10;
      }
  //update pet stats
      getInfo.updateStats(newHapp, newh, newSleep, newHunger);
      getInfo.addToScore(20);
      
      
      // starting cooldown countdown
      int cooldown = getInfo.getActionCooldown("takeToVet");
      startCooldownDialog(cooldown);
      getInfo.checkStats();
    });

    backBtn = btnFx("Return to home");
    backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
//back button to return user gameplay screen when triggered
    backBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
      dispose();
      String item = getInfo.getInventory().addGiftRandom();
      
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

  }

  /**
    * creating a stylish button
    *
    * @param text the text to display on the button
    * @return the styled JButton
    */
  private JButton btnFx(String text) {
    //creatng stylish button
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
    //creating cooldown notification
      final JDialog dialog = new JDialog(this, "Cooldown in Progress", true);
      dialog.setSize(350, 150);
      dialog.setLocationRelativeTo(this);
      dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
      dialog.setLayout(new BorderLayout());

      JLabel label = new JLabel("Healing... please wait " + cooldown + "s", JLabel.CENTER);
      label.setFont(new Font("SansSerif", Font.BOLD, 14));
      dialog.add(label, BorderLayout.CENTER);

      // using Swing Timer to count down each second
      Timer timer = new Timer(1000, new AbstractAction() {
          int timeLeft = cooldown;
          @Override
          public void actionPerformed(ActionEvent e) {
              timeLeft--;
              label.setText("Healing... please wait " + timeLeft + "s");
              
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
