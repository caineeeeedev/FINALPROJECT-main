import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LoginSystemLogin {

    static final String ACCOUNTS_FILE = "data/accounts.txt";
    static final Scanner scanner = new Scanner(System.in);
    static String loggedInUsername = "";

    public static boolean showLogin() {
        while (true) {
            Main.clearScreen();
            Main.printBorder();
            Main.printCentered("QUITIQUIZ");
            Main.printCentered("ACCOUNT MENU");
            Main.printBorder();
            Main.printLine("");
            Main.printLine("  [1] Log in");
            Main.printLine("  [2] Create an account");
            Main.printLine("  [3] Exit");
            Main.printLine("");
            Main.printBorder();
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
                    Main.printMessage(
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
        Main.printText("Enter 0 to return.");
        Main.printText("");
        Main.printPrompt("ID number (2026-xxxxxxx): ");

        String id = scanner.nextLine().trim();

        if (id.equals("0")) {
            return false;
        }

        if (!validID(id)) {
            Main.printMessage(
                    "Invalid ID format. Use 2026-xxxxxxx."
            );
            Main.waitForEnter(scanner);
            return false;
        }

        Main.printPrompt("Password: ");
        String password = scanner.nextLine();

        String[] account = findAccount(id);

        if (account == null || !account[2].equals(password)) {
            Main.printMessage(
                    "Incorrect ID number or password."
            );
            Main.waitForEnter(scanner);
            return false;
        }

        loggedInUsername = account[1];

        Main.clearScreen();
        Main.printBorder();
        Main.printCentered("LOGIN SUCCESSFUL");
        Main.printBorder();
        Main.printCentered(
                "Welcome, " + loggedInUsername + "!"
        );
        Main.printCentered("ID: " + id);
        Main.printBorder();
        Main.waitForEnter(scanner);

        return true;
    }

    static void signUp() {
        Main.clearScreen();
        Main.printBox("CREATE AN ACCOUNT");
        Main.printText("");
        Main.printText("Enter 0 to return.");
        Main.printText("");
        Main.printPrompt("ID number (2026-xxxxxxx): ");

        String id = scanner.nextLine().trim();

        if (id.equals("0")) {
            return;
        }

        if (!validID(id)) {
            Main.printMessage(
                    "Invalid ID format. Use 2026-xxxxxxx."
            );
            Main.waitForEnter(scanner);
            return;
        }

        if (findAccount(id) != null) {
            Main.printMessage(
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
            Main.printMessage("All fields are required.");
            Main.waitForEnter(scanner);
            return;
        }

        if (username.contains("|") || password.contains("|")) {
            Main.printMessage(
                    "The | character is not allowed."
            );
            Main.waitForEnter(scanner);
            return;
        }

        try {
            File file = new File(ACCOUNTS_FILE);
            File folder = file.getParentFile();

            if (folder != null) {
                folder.mkdirs();
            }

            FileWriter writer = new FileWriter(file, true);
            writer.write(
                    id + "|" + username + "|" + password + "\n"
            );
            writer.close();

            Main.clearScreen();
            Main.printBorder();
            Main.printCentered("ACCOUNT CREATED");
            Main.printBorder();
            Main.printCentered("Username: " + username);
            Main.printCentered("ID: " + id);
            Main.printBorder();

        } catch (IOException e) {
            Main.printMessage(
                    "The account could not be saved."
            );
        }

        Main.waitForEnter(scanner);
    }

    static String[] findAccount(String id) {
        File file = new File(ACCOUNTS_FILE);

        if (!file.exists()) {
            return null;
        }

        try {
            BufferedReader reader =
                    new BufferedReader(new FileReader(file));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] account = line.split("\\|");

                if (account.length == 3 &&
                        account[0].equals(id)) {

                    reader.close();
                    return account;
                }
            }

            reader.close();
        } catch (IOException e) {
            Main.printMessage(
                    "Could not read the accounts file."
            );
        }

        return null;
    }

    static boolean validID(String id) {
        if (id.length() != 12) {
            return false;
        }

        if (!id.substring(0, 5).equals("2026-")) {
            return false;
        }

        for (int i = 5; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}