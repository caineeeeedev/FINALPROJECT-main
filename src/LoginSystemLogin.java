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
    static final Scanner scanner = new Scanner(System.in);

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
            Main.showMessage("Could not read the accounts file.");
        }

        return null;
    }

    public static boolean showLogin() {
        while (true) {
            Main.clearScreen();
            Main.printTop();
            Main.printCentered("QUITIQUIZ");
            Main.printCentered("ACCOUNT MENU");
            Main.printMiddle();
            Main.printLine("");
            Main.printLine("  [1] Log in");
            Main.printLine("  [2] Create an account");
            Main.printLine("  [3] Exit");
            Main.printLine("");
            Main.printBottom();
            Main.printPrompt("Choose an option (1-3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    if (login()) {
                        return true;
                    }
                    break;

                case "2":
                    signUp();
                    break;

                case "3":
                    Main.clearScreen();
                    Main.printBox(
                            "THANK YOU FOR USING QUITIQUIZ"
                    );
                    return false;

                default:
                    Main.showMessage(
                            "Invalid choice. Please enter 1, 2, or 3."
                    );
                    Main.waitForEnter(scanner);
            }
        }
    }

    static boolean login() {
        Main.clearScreen();
        Main.printBox("LOG IN");
        Main.printText("");
        Main.printText(
                "Enter 0 to return to the account menu."
        );
        Main.printText("");
        Main.printPrompt("ID number (2026-xxxxxxx): ");

        String id = scanner.nextLine().trim();

        if (id.equals("0")) {
            return false;
        }

        if (!id.matches("2026-\\d{7}")) {
            Main.showMessage(
                    "Invalid ID format: 2026-xxxxxxx"
            );
            Main.waitForEnter(scanner);
            return false;
        }

        Main.printPrompt("Password: ");
        String password = scanner.nextLine();

        String[] account = findAccount(id);

        if (account != null && account[2].equals(password)) {
            loggedInUsername = account[1];
            loggedInID = account[0];

            Main.clearScreen();
            Main.printTop();
            Main.printCentered("LOGIN SUCCESSFUL");
            Main.printMiddle();
            Main.printCentered(
                    "Welcome, " + loggedInUsername + "!"
            );
            Main.printCentered("ID: " + loggedInID);
            Main.printBottom();
            Main.waitForEnter(scanner);
            return true;
        }

        Main.showMessage("Incorrect ID number or password.");
        Main.waitForEnter(scanner);
        return false;
    }

    static void signUp() {
        Main.clearScreen();
        Main.printBox("CREATE AN ACCOUNT");
        Main.printText("");
        Main.printText(
                "Enter 0 to return to the account menu."
        );
        Main.printText("");
        Main.printPrompt("ID number (2026-xxxxxxx): ");

        String id = scanner.nextLine().trim();

        if (id.equals("0")) {
            return;
        }

        if (!id.matches("2026-\\d{7}")) {
            Main.showMessage(
                    "Invalid ID format: 2026-xxxxxxx"
            );
            Main.waitForEnter(scanner);
            return;
        }

        if (findAccount(id) != null) {
            Main.showMessage(
                    "That ID number is already registered."
            );
            Main.waitForEnter(scanner);
            return;
        }

        Main.printPrompt("Username: ");
        String username = scanner.nextLine().trim();

        Main.printPrompt("Password: ");
        String password = scanner.nextLine();

        if (username.isEmpty() || password.isEmpty()) {
            Main.showMessage("All fields are required.");
            Main.waitForEnter(scanner);
            return;
        }

        if (username.contains("|") || password.contains("|")) {
            Main.showMessage("The | character is not allowed.");
            Main.waitForEnter(scanner);
            return;
        }

        try {
            File file = new File(ACCOUNTS_FILE);

            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            try (FileWriter writer =
                         new FileWriter(file, true)) {
                writer.write(
                        id
                                + "|"
                                + username
                                + "|"
                                + password
                                + "\n"
                );
            }

            Main.clearScreen();
            Main.printTop();
            Main.printCentered("ACCOUNT CREATED");
            Main.printMiddle();
            Main.printCentered("Username: " + username);
            Main.printCentered("ID: " + id);
            Main.printBottom();

        } catch (IOException e) {
            Main.showMessage(
                    "The account could not be saved."
            );
        }

        Main.waitForEnter(scanner);
    }
}