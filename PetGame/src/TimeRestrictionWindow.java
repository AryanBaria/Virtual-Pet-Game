import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D;

public class TimeRestrictionWindow extends JFrame {
    private JComboBox<String> startTimeComboBox;
    private JComboBox<String> endTimeComboBox;
    private RoundedButton confirmButton;
    private ParentalControlScreen parentScreen;
    
    public TimeRestrictionWindow(ParentalControlScreen parent) {
        this.parentScreen = parent;
        
        // Set up the frame
        setTitle("Set Time Restrictions");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 255, 240)); // Light green background
        setLayout(new BorderLayout(10, 10));
        
        Font font = new Font("SansSerif", Font.BOLD, 14);
        
        // Title Label
        JLabel titleLabel = new JLabel("Select Playtime Restrictions", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        titleLabel.setForeground(new Color(0, 100, 0)); // Dark green text
        add(titleLabel, BorderLayout.NORTH);
        
        // Panel for dropdowns
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(2, 2, 15, 15));
        timePanel.setBackground(new Color(240, 255, 240));
        timePanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        
        JLabel startTimeLabel = new JLabel("Start Time: ", JLabel.RIGHT);
        startTimeLabel.setFont(font);
        timePanel.add(startTimeLabel);
        
        String[] times = {"9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", 
                         "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM", 
                         "5:00 PM", "6:00 PM"};
        startTimeComboBox = new JComboBox<>(times);
        styleComboBox(startTimeComboBox);
        timePanel.add(startTimeComboBox);
        
        JLabel endTimeLabel = new JLabel("End Time: ", JLabel.RIGHT);
        endTimeLabel.setFont(font);
        timePanel.add(endTimeLabel);
        
        endTimeComboBox = new JComboBox<>(times);
        styleComboBox(endTimeComboBox);
        timePanel.add(endTimeComboBox);
        
        add(timePanel, BorderLayout.CENTER);
        
        // Confirm button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 255, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        
        confirmButton = new RoundedButton("Confirm Restrictions");
        confirmButton.setFont(font);
        confirmButton.setPreferredSize(new Dimension(200, 40));
        confirmButton.addActionListener(e -> confirmTimeRestrictions());
        
        buttonPanel.add(confirmButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Center window on screen
        setLocationRelativeTo(parent);
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 100, 0), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    private void confirmTimeRestrictions() {
        String startTime = (String) startTimeComboBox.getSelectedItem();
        String endTime = (String) endTimeComboBox.getSelectedItem();
        
        parentScreen.showSavedMessage("Time Restrictions Saved: " + startTime + " - " + endTime);
        this.dispose();
        parentScreen.setEnabled(true);
    }

    // RoundedButton class matching ParentalControlScreen
    class RoundedButton extends JButton {
        private static final int ARC_WIDTH = 20;
        private static final int ARC_HEIGHT = 20;
        
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setForeground(Color.BLACK);
            setBackground(new Color(144, 238, 144)); // Light green
            setFocusPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    setBackground(new Color(152, 251, 152));
                }
                public void mouseExited(MouseEvent evt) {
                    setBackground(new Color(144, 238, 144));
                }
                public void mousePressed(MouseEvent evt) {
                    setBackground(new Color(0, 100, 0));
                    setForeground(Color.WHITE);
                }
                public void mouseReleased(MouseEvent evt) {
                    setBackground(new Color(152, 251, 152));
                    setForeground(Color.BLACK);
                }
            });
        }
        
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
        
        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0, 100, 0));
            g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, ARC_WIDTH, ARC_HEIGHT);
            g2.dispose();
        }
    }
}