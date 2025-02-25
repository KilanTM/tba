import java.util.*;
import java.sql.*;


public class Main {
   public static int player_id = 0;
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);

      System.out.println("Hello Adventurer would you like to login or sign up?");
      String login = input.nextLine();
      while (true) {
         if (login.equals("login")) {
            System.out.println("Username:");
            String username = input.nextLine();

            System.out.println("Password:");
            String password = input.nextLine();

            try {
               Connection connection = DriverManager.getConnection("jdbc:postgresql:project_postgresql", "jasonwoodruff", "8608");
               PreparedStatement statement = connection.prepareStatement("select * from player where username=? and password=?");

               statement.setString(1, username);
               statement.setString(2, password);

               ResultSet resultSet = statement.executeQuery();



               if (resultSet.next()) {
                  System.out.println("Welcome back, " + username + "! You are logged in.");
                  player_id = resultSet.getInt("id");
                  StartOfStory.startOfStory();
                  break;
               } else {
                  System.out.println("User does not exist.");
               }
               connection.close();
               statement.close();


            } catch (SQLException e) {
               e.printStackTrace();

               System.exit(1);
            }


         } else if (login.equals("signup")) {
            System.out.println("Username:");
            String username = input.nextLine();

            System.out.println("Password:");
            String password = input.nextLine();

            try {
               Connection connection = DriverManager.getConnection("jdbc:postgresql:project_postgresql", "jasonwoodruff", "8608");
               PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM player WHERE username=?");

               stmt.setString(1, username);

               ResultSet resultSet = stmt.executeQuery();

               if (resultSet.next()) {
                  if (resultSet.getInt(1) > 0) {

                     System.out.println("User already exists.");
                  } else {

                     PreparedStatement statement = connection.prepareStatement("INSERT INTO player(username, password ) VALUES (?,?)");

                     statement.setString(1, username);
                     statement.setString(2, password);
                     PreparedStatement stmt2 = connection.prepareStatement("SELECT * FROM player WHERE username=?");

                     stmt2.setString(1, username);

                     ResultSet resultSet2 = stmt2.executeQuery();
                     if (resultSet2.next()) {
                        player_id = resultSet2.getInt("player_id");

                     }

                     int es = statement.executeUpdate();

                     if (es == 0) {
                        System.out.println("Username already exists.");

                     } else {

                        System.out.println("Successfully logged in.");
                        StartOfStory.startOfStory();
                        break;
                     }

                     connection.close();
                     statement.close();

                  }
               }

            } catch (SQLException e) {

               e.printStackTrace();
               System.exit(2);
            }
         }

      }
   }
}