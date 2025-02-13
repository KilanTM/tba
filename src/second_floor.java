import java.util.*;
import java.sql.*;

public class second_floor {
    private static final String START_LOCATION = "Floor 2 Lobby";
    private static final Map<String, String> locations = new HashMap<>();
    private static final Map<String, Set<String>> transitions = new HashMap<>();

    static {
        locations.put("Floor 2 Lobby","The main room for gatherings. The door to the next floor is locked.");
        locations.put("Hydrothermal Research Lab","A run-down lab that tested the vents.");
        locations.put("Deep Vault","One of the three vaults in this facility. You're not supposed to be in here.");
        locations.put("Storage Depot","This place used to be filled with useful gadgets. Now you'll have to be lucky to find something functional.");
        locations.put("Command Center","Where everything(?) was controlled. This room is strangely intact.");
        locations.put("Containment Chamber","This room held the worst of the worst experiments. It reeks of blood and decay.");
        locations.put("Pressure Stablization Unit","A factory that checked structure integrity. One wrong step would kill you.");
        locations.put("Hydrophonic Garden","This place used to be so beautiful. Now it's reduced to rubble.");
        locations.put("Reactor Chamber","Maintains energy consumption. The air is a death sentence.");
        locations.put("Security Operations","Used to protect the entire building. Now it's useless.");

        transitions.put("Floor 2 Lobby", Set.of("Command Center", "Hydrothermal Research Lab", "Pressure Stablization Unit", "Storage Depot"));
        transitions.put("Command Center", Set.of("Security Operations", "Floor 2 Lobby"));
        transitions.put("Security Operations", Set.of("Command Center", "Deep Vault"));
        transitions.put("Deep Vault", Set.of("Security Operations"));
        transitions.put("Hydrothermal Research Lab", Set.of("Hydrophonic Garden", "Containment Chamber", "Floor 2 Lobby"));
        transitions.put("Hydrophonic Garden", Set.of("Hydrothermal Research Lab", "Containment Chamber"));
        transitions.put("Containment Chamber", Set.of("Hydrothermal Research Lab"));
    }


    public static void second_floor2() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the second floor, Diver!");
        System.out.println("Honestly, I'm surprised you made it this far. Most people di-I mean...quit before this.");
        System.out.println("Now, everything will get harder from here on out. I hope you don't back out. This mission is important not just for the colony, but the entire world.");
        System.out.println("Are you up for the task, Diver?");
        String choice = scanner.nextLine();

        if (choice.equals("no")) {
            System.out.println("Sigh, it was bound to end like this...");
            System.out.println("It was fun navigating you. I'm sad it had to end like this.");
            System.out.println("I hope you enjoy your last breath. You should've known you weren't going home.");
            System.exit(0);
        } else if (choice.equals("yes")) {
            System.out.println("Perfect! The colony is blessed to have such a selfless person.");
        } else {
            System.out.println("Something went wrong...I can't hear you.");
        }
    }

    public static void changeLocation(String[] args) {

    }

}
