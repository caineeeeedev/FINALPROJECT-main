import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LoginSystemLogin {

    static String loggedInUsername = "";
    static String loggedInID = "";

    static final String ACCOUNTS_FILE = "data/accounts.txt";

    static Scanner scanner = new Scanner(System.in);


    // Finds an account line in the file by ID.
    // Returns {id, username, password} or null if not found.
    static String[] findAccount(String id) {

        File file = new File(ACCOUNTS_FILE);

        if (!file.exists()) {
            return null;
        }

        try (BufferedReader reader =
                new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|");

                if (parts.length == 3 && parts[0].equals(id)) {
                    return parts;
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error reading accounts: " + e.getMessage()
            );
        }

        return null;
    }


    public static boolean showLogin() {

        while (true) {

            System.out.println();
            System.out.println("=========================================");
            System.out.println("             QUITIQUIZ LOGIN");
            System.out.println("=========================================");

            System.out.println("[1] LOG IN");
            System.out.println("[2] SIGN UP");
            System.out.println("[3] EXIT");

            System.out.println("=========================================");

            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                // LOG IN
                case "1" -> {
                    if (login()) {
                        return true;
                    }
                }

                // SIGN UP
                case "2" -> signUp();

                // EXIT
                case "3" -> {
                    System.out.println();
                    System.out.println("Thank you for using QuitiQUIZ!");
                    return false;
                }

                // INVALID OPTION
                default -> {
                    System.out.println();
                    System.out.println("Invalid option!");
                    System.out.println("Please choose 1, 2, or 3.");
                }
            }
        }
    }


    static boolean login() {

        System.out.println();
        System.out.println("------------- LOG IN -------------");

        System.out.println("ENTER 0 TO RETURN TO LOGIN CHOICES");
        System.out.print("ID Number (2026-xxxxxxx): ");

        String id = scanner.nextLine().trim();

        // RETURN TO LOGIN CHOICES
        if (id.equals("0")) {

            System.out.println();
            System.out.println("Returning to Login Choices...");

            return false;
        }

        if (!id.matches("2026-\\d{7}")) {

            System.out.println();
            System.out.println("Invalid ID Number!");
            System.out.println("Use this format: 2026-xxxxxxx");
            System.out.println("Example: 2026-1234567");

            return false;
        }

        System.out.print("Password: ");

        String password = scanner.nextLine();

        String[] account = findAccount(id);

        if (account != null && account[2].equals(password)) {

            loggedInUsername = account[1];
            loggedInID = id;

            System.out.println();
            System.out.println("=========================================");
            System.out.println("           LOGIN SUCCESSFUL");
            System.out.println("=========================================");
            System.out.println("Welcome, " + loggedInUsername);
            System.out.println("ID Number: " + loggedInID);
            System.out.println("=========================================");

            return true;

        } else {

            System.out.println();
            System.out.println("Invalid ID Number or Password!");

            return false;
        }
    }


    private static void signUp() {

        System.out.println();
        System.out.println("------------- SIGN UP -------------");

        System.out.println("ENTER 0 TO RETURN TO LOGIN CHOICES");
        System.out.print("ID Number (2026-xxxxxxx): ");

        String id = scanner.nextLine().trim();

        // RETURN TO LOGIN CHOICES
        if (id.equals("0")) {

            System.out.println();
            System.out.println("Returning to Login Choices...");

            return;
        }

        if (!id.matches("2026-\\d{7}")) {

            System.out.println();
            System.out.println("Invalid ID Number!");
            System.out.println("ID must follow this format:");
            System.out.println("2026-xxxxxxx");
            System.out.println("Example: 2026-1234567");

            return;
        }

        if (findAccount(id) != null) {

            System.out.println();
            System.out.println("ID Number already registered!");

            return;
        }

        System.out.print("Username: ");

        String username = scanner.nextLine().trim();

        System.out.print("Password: ");

        String password = scanner.nextLine();

        if (username.isEmpty() || password.isEmpty()) {

            System.out.println();
            System.out.println("Please fill in all fields!");

            return;
        }

        // Usernames/passwords can't contain "|" since it's the separator
        if (username.contains("|") || password.contains("|")) {

            System.out.println();
            System.out.println("Username and password cannot contain '|'.");

            return;
        }

        try {

            File file = new File(ACCOUNTS_FILE);

            // Create data folder
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            try (FileWriter writer = new FileWriter(file, true)) {

                writer.write(id + "|" + username + "|" + password + "\n");
            }

            System.out.println();
            System.out.println("=========================================");
            System.out.println("     ACCOUNT CREATED SUCCESSFULLY");
            System.out.println("=========================================");
            System.out.println("Username: " + username);
            System.out.println("ID Number: " + id);
            System.out.println("Your account has been saved.");
            System.out.println("=========================================");

        } catch (IOException e) {

            System.out.println();
            System.out.println("Could not save the account!");
            System.out.println("Error: " + e.getMessage());
        }
    }
}