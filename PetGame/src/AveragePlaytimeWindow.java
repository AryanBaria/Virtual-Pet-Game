import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Rectangle2D;

public class AveragePlaytimeWindow extends JFrame {
    private JComboBox<String> playtimeComboBox;
    private RoundedButton confirmButton;
    private ParentalControlScreen parentScreen;

    public AveragePlaytimeWindow(ParentalControlScreen parent) {
        this.parentScreen = parent;
        
        // Set up the frame
        setTitle("Set Average Playtime");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(240, 255, 240)); // Light green background
        setLayout(new BorderLayout(10, 10));
        
        Font font = new Font("SansSerif", Font.BOLD, 14);

        // Title Label
        JLabel titleLabel = new JLabel("Select Average Playtime Allowed", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        titleLabel.setForeground(new Color(0, 100, 0)); // Dark green text
        add(titleLabel, BorderLayout.NORTH);

        // Main content panel
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(240, 255, 240));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // ComboBox for playtime selection
        String[] playtimeOptions = {
            "1 hour", "1.5 hours", "2 hours", "2.5 hours", "3 hours", 
            "3.5 hours", "4 hours", "4.5 hours", "5 hours"
        };
        playtimeComboBox = new JComboBox<>(playtimeOptions);
        styleComboBox(playtimeComboBox);
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(playtimeComboBox, gbc);
        add(contentPanel, BorderLayout.CENTER);

        // Confirm button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 255, 240));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        
        confirmButton = new RoundedButton("Confirm Playtime");
        confirmButton.setFont(font);
        confirmButton.setPreferredSize(new Dimension(200, 40));
        confirmButton.addActionListener(e -> confirmPlaytimeLimit());
        
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

    private void confirmPlaytimeLimit() {
        String selectedPlaytime = (String) playtimeComboBox.getSelectedItem();
        
        parentScreen.showSavedMessage("Average Playtime Saved: " + selectedPlaytime);
        this.dispose();
        parentScreen.setEnabled(true);
    }

    // RoundedButton class matching the style
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