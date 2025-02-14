import java.util.*;
import java.sql.*;



class StartOfStory {
    public static int item_flash = 0;
    public static void startOfStory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Mission Log: Solo Assignment – Underwater Facility\n" +
                "\n" +
                "The briefing was clear: this was to be a solo mission.\n" +
                "\n" +
                "There was no room for a team this time, no backup, and no one to rely on. The cramped conditions of the submersible make it a one-person job—just you, the deep, and the facility beneath the waves.\n" +
                "\n" +
                "You were told little about the specifics of the mission. A sense of isolation weighs heavily on you as you prepare for what lies ahead. As the countdown to departure ticks down, you can’t help but wonder if this secrecy is simply part of the plan or if there’s something more ominous at play.\n" +
                "\n" +
                "The underwater facility—what’s left of it—remains a mystery. The depth alone would make it nearly impossible for most to access. But you? You were chosen for this mission. It’s your skills they trust. Yet, there’s an unsettling feeling in the back of your mind. You’ll be alone in the depths—out of contact, out of reach, cut off from the world above.\n" +
                "\n" +
                "In the final moments before you descend, the hatch closes with a heavy, final thud. There’s no turning back now. Your journey into the abyss is about to begin.");

        while (true) {
            System.out.println("-----------------------------------------------------------------------");

            System.out.println("You enter the air-lock. The power is out for the building.\n" +
                    "Would you like to enter through the hatch(enter) or search the airlock for any loot(search)?");
            String airlock = scanner.nextLine();

            if (airlock.equals("enter")) {
                System.out.println("You squeeze through the entrance. The room is empty, and visibility is poor.");
                System.out.println("------------------------------------------");

                while (true) {


                    System.out.println("Would you like to search the room(search) or enter a different room?");
                    String room = scanner.nextLine();

                    if (room.equals("search")) {
                        System.out.println("You search the front desk in the middle of the room and find a flashlight.");

                        try {
                            Connection connection = DriverManager.getConnection("jdbc:postgresql:project_postgresql", "jasonwoodruff", "8608");
                            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM inv WHERE name = 'Flashlight'");
                            ResultSet rs = stmt.executeQuery();

                            while (rs.next()) {
                                item_flash = rs.getInt("item_id");
                            }

                            // Update player inventory
                            PreparedStatement statement = connection.prepareStatement("UPDATE player SET inv = array_append(inv, ?) WHERE id = ?");

                            statement.setInt(1, item_flash);  // Adding flashlight to the inventory
                            statement.setInt(2, Main.player_id);
                            statement.executeUpdate();

                            System.out.println("Flashlight added to your inventory.");
                        } catch (SQLException ex) {
                            System.out.println("Error accessing the database: " + ex.getMessage());
                            // Consider allowing the game to continue even if there is an error
                        }
                    } else if (room.equals("enter")) {
                        System.out.println("Which room would you like to enter\n" + "(1).Captains quarters\n" + "(2).Security room" + "(3).The Stairs leading down");
                        String quarter = scanner.nextLine();

                        if (quarter.equals("1")) {
                            System.out.println("You enter the Captains quarter and see that the room is completely destroyed like someone was fighting over something.");
                            System.out.println("-------------------------\n" + "Would you like to search(search) or leave the room?(exit).");
                            if (scanner.nextLine().equals("exit")) {
                                break;
                            } else if (scanner.nextLine().equals("search")) {

                                System.out.println("You find traces of blood from the fight after looking around you also find a blue key-card that is labeled Security room");
                                try {
                                    int blue_key = 0;
                                    Connection connection = DriverManager.getConnection("jdbc:postgresql:project_postgresql", "jasonwoodruff", "8608");
                                    PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM inv WHERE name = 'blue key-card'");
                                    ResultSet rs2 = stmt2.executeQuery();
                                    while (rs2.next()) {
                                        blue_key = rs2.getInt("item_id");
                                    }


                                    PreparedStatement statement = connection.prepareStatement("UPDATE player SET inv = array_append(inv, ?) WHERE player_id = ?");
                                    statement.setInt(1, blue_key);
                                    statement.setInt(2, Main.player_id);
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                    System.out.println("Error accessing the database: " + ex.getMessage());
                                }

                            }
                        } else if (quarter.equals("2")) {
                            try {
                                Connection connection = DriverManager.getConnection("jdbc:postgrtesql:project_posgresql", "jasonwoodruff", "8608");
                                PreparedStatement statement = connection.prepareStatement("SELECT * FROM player WHERE id = ?");
                                statement.setInt(1, Main.player_id);
                                statement.executeQuery();
                                while (statement.execute()) {

                                }
                            }catch(SQLException ex){
                                ex.printStackTrace();
                                System.exit(5);
                            }



                        } else if (quarter.equals("3")) {
                            System.out.println("You head down stairs");
                            second_floor.second_floor2();
                            break;

                        }

                    }
                }
            } else if (airlock.equals("search")) {

                System.out.println("You find nothing but a broken keylock with a empty room, and visibility is poor while the airlock is cracked open to the other room.");
            } else {

                System.out.println("Not an option");
            }
        }
    }
}
