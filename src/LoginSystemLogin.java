import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LoginSystemLogin {
    static final String ACCOUNTS_FILE =
            "data/accounts.txt";

    static final Scanner scanner =
            new Scanner(System.in);

    static String loggedInUsername = "";

    public static boolean showLogin() {
        while (true) {
            Main.clearScreen();

            System.out.println("==============================");
            System.out.println("          QUITIQUIZ");
            System.out.println("        ACCOUNT MENU");
            System.out.println("==============================");
            System.out.println("[1] Log in");
            System.out.println("[2] Create an account");
            System.out.println("[3] Exit");
            System.out.print("Choose an option (1-3): ");

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

                    System.out.println(
                            "THANK YOU FOR USING QUITIQUIZ"
                    );

                    return false;

                default:
                    System.out.println(
                            "Invalid choice. Please enter 1, 2, or 3."
                    );

                    Main.waitForEnter(scanner);
            }
        }
    }

    static boolean login() {
        Main.clearScreen();

        System.out.println("========== LOG IN ==========");
        System.out.println("Enter 0 to return.");
        System.out.print("ID number (2026-xxxxxxx): ");

        String id = scanner.nextLine().trim();

        if (id.equals("0")) {
            return false;
        }

        if (!validID(id)) {
            System.out.println(
                    "Invalid ID format. Use 2026-xxxxxxx."
            );

            Main.waitForEnter(scanner);
            return false;
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        String[] account = findAccount(id);

        if (account == null
                || !account[2].equals(password)) {

            System.out.println(
                    "Incorrect ID number or password."
            );

            Main.waitForEnter(scanner);
            return false;
        }

        loggedInUsername = account[1];

        Main.clearScreen();

        System.out.println("LOGIN SUCCESSFUL");
        System.out.println(
                "Welcome, " + loggedInUsername + "!"
        );
        System.out.println("ID: " + id);

        Main.waitForEnter(scanner);

        return true;
    }

    static void signUp() {
        Main.clearScreen();

        System.out.println(
                "===== CREATE AN ACCOUNT ====="
        );
        System.out.println("Enter 0 to return.");
        System.out.print("ID number (2026-xxxxxxx): ");

        String id = scanner.nextLine().trim();

        if (id.equals("0")) {
            return;
        }

        if (!validID(id)) {
            System.out.println(
                    "Invalid ID format. Use 2026-xxxxxxx."
            );

            Main.waitForEnter(scanner);
            return;
        }

        if (findAccount(id) != null) {
            System.out.println(
                    "That ID number is already registered."
            );

            Main.waitForEnter(scanner);
            return;
        }

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println(
                    "All fields are required."
            );

            Main.waitForEnter(scanner);
            return;
        }

        if (username.contains("|")
                || password.contains("|")) {

            System.out.println(
                    "The | character is not allowed."
            );

            Main.waitForEnter(scanner);
            return;
        }

        try {
            File file = new File(ACCOUNTS_FILE);
            File folder = file.getParentFile();

            if (folder != null && !folder.exists()) {
                folder.mkdir();
            }

            FileWriter writer =
                    new FileWriter(file, true);

            writer.write(
                    id + "|"
                            + username + "|"
                            + password + "\n"
            );

            writer.close();

            System.out.println("ACCOUNT CREATED");
            System.out.println(
                    "Username: " + username
            );
            System.out.println("ID: " + id);

        } catch (IOException e) {
            System.out.println(
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
            Scanner fileScanner =
                    new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line =
                        fileScanner.nextLine();

                String[] account =
                        line.split("\\|");

                if (account.length == 3
                        && account[0].equals(id)) {

                    fileScanner.close();
                    return account;
                }
            }

            fileScanner.close();

        } catch (IOException e) {
            System.out.println(
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