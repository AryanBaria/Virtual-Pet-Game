import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *  @author Maher Rammal
 * 
 * Extending JFrame for second tutorial screen 
 *
 * Configuring layout with top, center, and bottom panels
 * Integrating pet selection panels and back button for navigation
 * transitioning to tutorialScreen3GUI
 */
public class secondTutorialPickPet extends JFrame {

    // Adding in fields to store pet names extracted from setupFile.json
    private String petName1;
    private String petName2;
    private String petName3;

    //also storing pet descriptions
    private String petDesc1;
    private String petDesc2;
    private String petDesc3;

    /**
     * Initializing tutorial screen 2 with allowing the user to first choose 
     *
     * Setting frame title, default close operation, size, extended state, and center location
     * Configuring border layout with gaps, adding top panel with heading, center panel with pet panels, and bottom panel with back button
     * Displaying frame
     */
    public secondTutorialPickPet() {
        super("Virtual Pet Game Tutorial");


          //----------------------------Json part---------------------------

        // Loading JSON file manually 
        String json = readFile("json/jsonFile.json");
        if (json != null) {

            // Extracting pet names and descriptions based on their occurrence
            petName1 = extractPetValue(json, "petName", 0);
            petDesc1 = extractPetValue(json, "petDescription", 0);

            petName2 = extractPetValue(json, "petName", 1);
            petDesc2 = extractPetValue(json, "petDescription", 1);

            petName3 = extractPetValue(json, "petName", 2);
            petDesc3 = extractPetValue(json, "petDescription", 2);
        }
        else {
            // In this case we can have fallback values if JSON fails to load
            petName1 = "Luka";
            petName2 = "Anthony";
            petName3 = "Sam";

            petDesc1 = "Default description for Luka";
            petDesc2 = "Default description for Anthony";
            petDesc3 = "Default description for Sam";
        }

        // Setting up frame, size, color, opening in maximized form 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(204, 255, 255));

        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setOpaque(false);
        JLabel headingLabel = new JLabel("CHOOSE YOUR PET");
        headingLabel.setFont(new Font("Barlow", Font.BOLD, 24));
        topPanel.add(headingLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        centerPanel.setOpaque(false);

        // Updated to show the correct pet name and description from the json
        JPanel petPanel1 = createPetPanel("img/pet1.png", "Name: " + petName1 + "\n" + petDesc1, "Choose!", 1);
        JPanel petPanel2 = createPetPanel("img/pet2.png", "Name: " + petName2 + "\n" + petDesc2, "Choose!", 2);
        JPanel petPanel3 = createPetPanel("img/pet3.png", "Name: " + petName3 + "\n" + petDesc3, "Choose!", 3);

        centerPanel.add(petPanel1);
        centerPanel.add(petPanel2);
        centerPanel.add(petPanel3);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);


        // back button to go back to a file/window 
        JButton backButton = createMainButton("< Back");

        backButton.addActionListener(e -> {
            new firstTutorialScreen();
            dispose();
        });

        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);


        //Once a button is pressed to connect to this file, it will open correcly/smoothly 
        setVisible(true);
    }

    /**
     * Creating pet panel with image, description, and button
     *
     * Using border layout with gaps, setting opaque false and line border
     * Adding image label, formatted description label, and main button
     * Attaching action listener for navigating to tutorial screen 3 GUI
     *
     * @param imagePath path to pet image
     * @param description pet description text with line breaks
     * @param buttonText text for the button
     * @return pet panel component
     */
    private JPanel createPetPanel(String imagePath, String description, String buttonText, int petNumber) {
        JPanel petPanel = new JPanel(new BorderLayout(5, 5));
        petPanel.setOpaque(false);
        petPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel imageLabel = new JLabel(new ImageIcon(imagePath));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        petPanel.add(imageLabel, BorderLayout.NORTH);

        // Formatting in a box type of style
        JLabel descLabel = new JLabel("<html><center>" + description.replaceAll("\n", "<br>") + "</center></html>");
        descLabel.setFont(new Font("Barlow", Font.PLAIN, 14));
        descLabel.setHorizontalAlignment(JLabel.CENTER);
        petPanel.add(descLabel, BorderLayout.CENTER);

        JButton chooseButton = createMainButton(buttonText);

        chooseButton.addActionListener(e -> {
            new thirdTutorialActions(petNumber);
            dispose();
        });

        petPanel.add(chooseButton, BorderLayout.SOUTH);
        return petPanel;
    }

    /**
     * Creating main button with fixed style and hover effect
     *
     * Converting text to uppercase, setting preferred size, background, foreground, font, border, and cursor
     * Attaching mouse listener for hover effect changing background and foreground
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


      //----------------------------More Json part---------------------------

    /**
     * Reading the entire contents of a file into a String
     *
     * @param filePath the path to the JSON file
     * @return the file content as a String, or null if an error occurs
     */
    private String readFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        
        // reading in the file, and throwing excpetion
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } 
       
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return sb.toString();
    }

    /**
     * Extracting a specific pet-related value for a given occurrence from the JSON string
     * <p>
     * Searching for the given key (petName or petDescription) 
     * within the "pets" array and returning the value for the specified occurrence (0-based)
     *
     * @param json the JSON string
     * @param key the key to look for (petName, petDescription)
     * @param occurrence the 0-based index of which pet in the array
     * @return the value if found, or "Unknown" if not found
     */
    private String extractPetValue(String json, String key, int occurrence) {
        String searchKey = "\"" + key + "\":";
        int arrayIndex = 0;
        int fromIndex = 0;

        // finding each occurrence within the pets array
        // counting up until we've reached the desired index
        while (true) {
            int keyIndex = json.indexOf(searchKey, fromIndex);
            if (keyIndex == -1) {
                return "Unknown";
            }
            
            
            if (arrayIndex == occurrence) {
                // Found the correct occurrence
                int firstQuote = json.indexOf("\"", keyIndex + searchKey.length());
                
                //Checking other occurences 
                if (firstQuote == -1) {
                    return "Unknown";
                }
                int secondQuote = json.indexOf("\"", firstQuote + 1);
                
                if (secondQuote == -1) {
                    return "Unknown";
                }
                return json.substring(firstQuote + 1, secondQuote);
            }
            fromIndex = keyIndex + searchKey.length();
            arrayIndex++;
        }
    }


    /**
     *
     * Invoking GUI on event dispatch thread using SwingUtilities
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(secondTutorialPickPet::new);
    }
}
