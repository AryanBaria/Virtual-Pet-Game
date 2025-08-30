import java.io.*;

public class GameState {
    private int health;
    private int hunger;
    private int sleep;
    private int happiness;

    //getter n setter
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }

    public int getHunger() { return hunger; }
    public void setHunger(int hunger) { this.hunger = hunger; }

    public int getSleep() { return sleep; }
    public void setSleep(int sleep) { this.sleep = sleep; }

    public int getHappiness() { return happiness; }
    public void setHappiness(int happiness) { this.happiness = happiness; }

    //convert gamestate to json 
    public String toJson() {
        return "{"
            + "\"health\": " + health + ","
            + "\"hunger\": " + hunger + ","
            + "\"sleep\": " + sleep + ","
            + "\"happiness\": " + happiness + ","
            + "}";
    }
    
    // load game from json
    public static GameState fromJson(String json) {
        GameState gameState = new GameState();

        // extract jackson type values 
        String[] parts = json.replace("{", "").replace("}", "").split(",");
        for (String part : parts) {
            String[] keyValue = part.split(":");
            switch (keyValue[0].trim()) {
                case "\"health\"":
                    gameState.setHealth(Integer.parseInt(keyValue[1].trim()));
                    break;
                case "\"hunger\"":
                    gameState.setHunger(Integer.parseInt(keyValue[1].trim()));
                    break;
                case "\"sleep\"":
                    gameState.setSleep(Integer.parseInt(keyValue[1].trim()));
                    break;
                case "\"happiness\"":
                    gameState.setHappiness(Integer.parseInt(keyValue[1].trim()));
                    break;
            }
        }

        return gameState;
    }
}
