import java.io.File;
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
            Main.printHeader("QUITIQUIZ", "ACCOUNT MENU");
            Main.printOption("1", "Log in");
            Main.printOption("2", "Create an account");
            Main.printOption("3", "Exit");
            Main.printFooter();
            System.out.print("  Choose an option (1-3): ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    if (login()) return true;
                    break;
                case "2":
                    signUp();
                    break;
                case "3":
                    Main.clearScreen();
                    Main.printHeader("QUITIQUIZ", "GOODBYE!");
                    System.out.println("  Thank you for using QuitiQuiz.");
                    Main.printFooter();
                    return false;
                default:
                    Main.printNotice("Invalid choice. Please enter 1, 2, or 3.");
                    Main.waitForEnter(scanner);
            }
        }
    }

    static boolean login() {
        Main.clearScreen();
        Main.printHeader("QUITIQUIZ", "LOG IN");
        System.out.println("  Enter 0 to return.");
        System.out.print("  ID number (2026-xxxxxxx): ");
        String id = scanner.nextLine().trim();

        if (id.equals("0")) return false;
        if (!validID(id)) {
            Main.printNotice("Invalid ID format. Use 2026-xxxxxxx.");
            Main.waitForEnter(scanner);
            return false;
        }

        System.out.print("  Password: ");
        String password = scanner.nextLine();
        String[] account = findAccount(id);
        if (account == null || !account[2].equals(password)) {
            Main.printNotice("Incorrect ID number or password.");
            Main.waitForEnter(scanner);
            return false;
        }

        loggedInUsername = account[1];
        Main.clearScreen();
        Main.printHeader("LOGIN SUCCESSFUL", "WELCOME");
        System.out.println("  Username : " + loggedInUsername);
        System.out.println("  ID       : " + id);
        Main.printFooter();
        Main.waitForEnter(scanner);
        return true;
    }

    static void signUp() {
        Main.clearScreen();
        Main.printHeader("QUITIQUIZ", "CREATE AN ACCOUNT");
        System.out.println("  Enter 0 to return.");
        System.out.print("  ID number (2026-xxxxxxx): ");
        String id = scanner.nextLine().trim();

        if (id.equals("0")) return;
        if (!validID(id)) {
            Main.printNotice("Invalid ID format. Use 2026-xxxxxxx.");
            Main.waitForEnter(scanner);
            return;
        }
        if (findAccount(id) != null) {
            Main.printNotice("That ID number is already registered.");
            Main.waitForEnter(scanner);
            return;
        }

        System.out.print("  Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("  Password: ");
        String password = scanner.nextLine();

        if (username.isEmpty() || password.isEmpty()) {
            Main.printNotice("All fields are required.");
            Main.waitForEnter(scanner);
            return;
        }
        if (username.contains("|") || password.contains("|")) {
            Main.printNotice("The | character is not allowed.");
            Main.waitForEnter(scanner);
            return;
        }

        try {
            File file = new File(ACCOUNTS_FILE);
            File folder = file.getParentFile();
            if (folder != null && !folder.exists()) folder.mkdir();

            FileWriter writer = new FileWriter(file, true);
            writer.write(id + "|" + username + "|" + password + "\n");
            writer.close();

            System.out.println();
            Main.printHeader("ACCOUNT CREATED", "SUCCESS");
            System.out.println("  Username : " + username);
            System.out.println("  ID       : " + id);
            Main.printFooter();
        } catch (IOException e) {
            Main.printNotice("The account could not be saved.");
        }
        Main.waitForEnter(scanner);
    }

    static String[] findAccount(String id) {
        File file = new File(ACCOUNTS_FILE);
        if (!file.exists()) return null;

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String[] account = fileScanner.nextLine().split("\\|");
                if (account.length == 3 && account[0].equals(id)) {
                    fileScanner.close();
                    return account;
                }
            }
            fileScanner.close();
        } catch (IOException e) {
            Main.printNotice("Could not read the accounts file.");
        }
        return null;
    }

    static boolean validID(String id) {
        if (id.length() != 12 || !id.substring(0, 5).equals("2026-")) return false;
        for (int i = 5; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) return false;
        }
        return true;
    }
}
