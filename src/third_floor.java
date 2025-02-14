import java.util.*;
import java.sql.*;

public class third_floor {
    private static final String START_LOCATION = "Central Core Room";
    private static final Map<String, String> locations = new HashMap<>();
    private static final Map<String, Set<String>> transitions = new HashMap<>();

    static {
        locations.put("Central Core Room", "The heart of the facility. The air is heavy, and a strange hum echoes.");
        locations.put("Observation Deck", "Overlooking the reactor. You feel watched.");
        locations.put("Biological Testing Lab", "Cages broken open. The experiments are missing...");
        locations.put("Storage Chamber", "This place held the most dangerous items. The special item might be here.");
        locations.put("Emergency Escape Pod", "An escape route... if you have the right keycard.");

        transitions.put("Central Core Room", Set.of("Observation Deck", "Biological Testing Lab", "Storage Chamber", "Emergency Escape Pod"));
        transitions.put("Observation Deck", Set.of("Central Core Room"));
        transitions.put("Biological Testing Lab", Set.of("Central Core Room"));
        transitions.put("Storage Chamber", Set.of("Central Core Room"));
        transitions.put("Emergency Escape Pod", Set.of("Central Core Room"));
    }

    private static void getLocation(String location) {
        System.out.println("You entered the " + location + ".");
        System.out.println(locations.get(location));
    }

    private static boolean checkInventory(int playerId, String itemName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql:project_postgresql", "jasonwoodruff", "8608");
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT item_id FROM inv WHERE name = ? AND item_id = ANY(SELECT unnest(inv) FROM player WHERE id = ?)"
            );
            stmt.setString(1, itemName);
            stmt.setInt(2, playerId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            System.out.println("Error checking inventory: " + ex.getMessage());
        }
        return false;
    }

    private static void addItemToInventory(int playerId, String itemName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql:project_postgresql", "jasonwoodruff", "8608");

            // Get item ID
            PreparedStatement stmt = connection.prepareStatement("SELECT item_id FROM inv WHERE name = ?");
            stmt.setString(1, itemName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int itemId = rs.getInt("item_id");

                // Add item to player inventory
                PreparedStatement updateStmt = connection.prepareStatement(
                        "UPDATE player SET inv = array_append(inv, ?) WHERE id = ?"
                );
                updateStmt.setInt(1, itemId);
                updateStmt.setInt(2, Main.player_id);
                updateStmt.executeUpdate();

                System.out.println(itemName + " added to your inventory.");
            }
        } catch (SQLException ex) {
            System.out.println("Error adding item: " + ex.getMessage());
        }
    }

    public static void third_floor3() {
        Scanner scanner = new Scanner(System.in);
        String currentLocation = START_LOCATION;

        System.out.println("You reached the third floor. This is where everything ends... one way or another.");

        while (true) {
            getLocation(currentLocation);
            System.out.println("Where would you like to go next?");
            String nextRoom = scanner.nextLine();

            if (transitions.get(currentLocation).contains(nextRoom)) {
                currentLocation = nextRoom;

                if (currentLocation.equals("Storage Chamber")) {
                    if (!checkInventory(Main.player_id, "Special Item")) {
                        System.out.println("You find the Special Item. This could change everything...");
                        addItemToInventory(Main.player_id, "Special Item");
                    } else {
                        System.out.println("You've already taken the Special Item.");
                    }
                } else if (currentLocation.equals("Emergency Escape Pod")) {
                    if (checkInventory(Main.player_id, "blue key-card")) {
                        System.out.println("You used the keycard to access the escape pod.");
                        System.out.println("Ending 2: Escape – You leave the facility behind, knowing the danger remains.");
                        System.exit(0);
                    } else {
                        System.out.println("You need the keycard to escape.");
                    }
                } else if (currentLocation.equals("Central Core Room")) {
                    System.out.println("This is it. The end. What will you do?");

                    if (checkInventory(Main.player_id, "Special Item")) {
                        System.out.println("You have the Special Item. Do you want to use it to save the colony but sacrifice yourself? (yes/no)");
                        String choice = scanner.nextLine();
                        if (choice.equals("yes")) {
                            System.out.println("Ending 1: Sacrifice – You saved the colony, but at the cost of your own life.");
                            System.exit(0);
                        }
                    }

                    System.out.println("Do you wish to confront the source of all this? (yes/no)");
                    String choice = scanner.nextLine();
                    if (choice.equals("yes")) {
                        System.out.println("Ending 3: Confrontation – You faced the horror but did not survive.");
                        System.exit(0);
                    } else {
                        System.out.println("Ending 4: The Truth – You chose to live, but the knowledge haunts you.");
                        System.exit(0);
                    }
                }
            } else {
                System.out.println("You can't go that way.");
            }
        }
    }
}



