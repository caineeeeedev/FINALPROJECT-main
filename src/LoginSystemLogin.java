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

            System.out.println(
                    Main.margin()
                            + "+------------------------------------------------------+"
            );

            System.out.println(
                    Main.margin()
                            + "|                      QUITIQUIZ                       |"
            );

            System.out.println(
                    Main.margin()
                            + "|                    ACCOUNT MENU                      |"
            );

            System.out.println(
                    Main.margin()
                            + "+------------------------------------------------------+"
            );

            System.out.println();
            System.out.println(
                    Main.margin() + "       [1]  Log in"
            );
            System.out.println(
                    Main.margin() + "       [2]  Create an account"
            );
            System.out.println(
                    Main.margin() + "       [3]  Exit"
            );
            System.out.println();

            System.out.println(
                    Main.margin()
                            + "+------------------------------------------------------+"
            );

            System.out.print(
                    Main.margin()
                            + "  Choose an option (1-3): "
            );

            String choice =
                    scanner.nextLine().trim();

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
                            Main.margin()
                                    + "+------------------------------------------------------+"
                    );

                    System.out.println(
                            Main.margin()
                                    + "|                      QUITIQUIZ                       |"
                    );

                    System.out.println(
                            Main.margin()
                                    + "|                       GOODBYE!                       |"
                    );

                    System.out.println(
                            Main.margin()
                                    + "+------------------------------------------------------+"
                    );

                    System.out.println();

                    System.out.println(
                            Main.margin()
                                    + "  Thank you for using QuitiQuiz."
                    );

                    System.out.println();

                    System.out.println(
                            Main.margin()
                                    + "+------------------------------------------------------+"
                    );

                    return false;

                default:
                    System.out.println();

                    System.out.println(
                            Main.margin()
                                    + "  >> Invalid choice. Please enter 1, 2, or 3."
                    );

                    System.out.print(
                            "\n"
                                    + Main.margin()
                                    + "  Press ENTER to continue..."
                    );

                    scanner.nextLine();
            }
        }
    }

    static boolean login() {
        Main.clearScreen();

        System.out.println(
                Main.margin()
                        + "+------------------------------------------------------+"
        );

        System.out.println(
                Main.margin()
                        + "|                      QUITIQUIZ                       |"
        );

        System.out.println(
                Main.margin()
                        + "|                        LOG IN                        |"
        );

        System.out.println(
                Main.margin()
                        + "+------------------------------------------------------+"
        );

        System.out.println();

        System.out.println(
                Main.margin()
                        + "  Enter 0 to return."
        );

        System.out.print(
                Main.margin()
                        + "  ID number (2026-xxxxxxx): "
        );

        String id =
                scanner.nextLine().trim();

        if (id.equals("0")) {
            return false;
        }

        if (!validID(id)) {
            System.out.println();

            System.out.println(
                    Main.margin()
                            + "  >> Invalid ID format. Use 2026-xxxxxxx."
            );

            System.out.print(
                    "\n"
                            + Main.margin()
                            + "  Press ENTER to continue..."
            );

            scanner.nextLine();
            return false;
        }

        System.out.print(
                Main.margin()
                        + "  Password: "
        );

        String password =
                scanner.nextLine();

        String[] account =
                findAccount(id);

        if (account == null
                || !account[2].equals(password)) {

            System.out.println();

            System.out.println(
                    Main.margin()
                            + "  >> Incorrect ID number or password."
            );

            System.out.print(
                    "\n"
                            + Main.margin()
                            + "  Press ENTER to continue..."
            );

            scanner.nextLine();
            return false;
        }

        loggedInUsername =
                account[1];

        Main.clearScreen();

        System.out.println(
                Main.margin()
                        + "+------------------------------------------------------+"
        );

        System.out.println(
                Main.margin()
                        + "|                  LOGIN SUCCESSFUL                    |"
        );

        System.out.println(
                Main.margin()
                        + "|                       WELCOME                        |"
        );

        System.out.println(
                Main.margin()
                        + "+------------------------------------------------------+"
        );

        System.out.println();

        System.out.println(
                Main.margin()
                        + "  Username : "
                        + loggedInUsername
        );

        System.out.println(
                Main.margin()
                        + "  ID       : "
                        + id
        );

        System.out.println();

        System.out.println(
                Main.margin()
                        + "+------------------------------------------------------+"
        );

        System.out.print(
                "\n"
                        + Main.margin()
                        + "  Press ENTER to continue..."
        );

        scanner.nextLine();

        return true;
    }

    static void signUp() {
        Main.clearScreen();

        System.out.println(
                Main.margin()
                        + "+------------------------------------------------------+"
        );

        System.out.println(
                Main.margin()
                        + "|                      QUITIQUIZ                       |"
        );

        System.out.println(
                Main.margin()
                        + "|                 CREATE AN ACCOUNT                    |"
        );

        System.out.println(
                Main.margin()
                        + "+------------------------------------------------------+"
        );

        System.out.println();

        System.out.println(
                Main.margin()
                        + "  Enter 0 to return."
        );

        System.out.print(
                Main.margin()
                        + "  ID number (2026-xxxxxxx): "
        );

        String id =
                scanner.nextLine().trim();

        if (id.equals("0")) {
            return;
        }

        if (!validID(id)) {
            System.out.println();

            System.out.println(
                    Main.margin()
                            + "  >> Invalid ID format. Use 2026-xxxxxxx."
            );

            System.out.print(
                    "\n"
                            + Main.margin()
                            + "  Press ENTER to continue..."
            );

            scanner.nextLine();
            return;
        }

        if (findAccount(id) != null) {
            System.out.println();

            System.out.println(
                    Main.margin()
                            + "  >> That ID number is already registered."
            );

            System.out.print(
                    "\n"
                            + Main.margin()
                            + "  Press ENTER to continue..."
            );

            scanner.nextLine();
            return;
        }

        System.out.print(
                Main.margin()
                        + "  Username: "
        );

        String username =
                scanner.nextLine().trim();

        System.out.print(
                Main.margin()
                        + "  Password: "
        );

        String password =
                scanner.nextLine();

        if (username.isEmpty()
                || password.isEmpty()) {

            System.out.println();

            System.out.println(
                    Main.margin()
                            + "  >> All fields are required."
            );

            System.out.print(
                    "\n"
                            + Main.margin()
                            + "  Press ENTER to continue..."
            );

            scanner.nextLine();
            return;
        }

        if (username.contains("|")
                || password.contains("|")) {

            System.out.println();

            System.out.println(
                    Main.margin()
                            + "  >> The | character is not allowed."
            );

            System.out.print(
                    "\n"
                            + Main.margin()
                            + "  Press ENTER to continue..."
            );

            scanner.nextLine();
            return;
        }

        try {
            File file =
                    new File(ACCOUNTS_FILE);

            File folder =
                    file.getParentFile();

            if (folder != null
                    && !folder.exists()) {

                folder.mkdir();
            }

            FileWriter writer =
                    new FileWriter(
                            file,
                            true
                    );

            writer.write(
                    id
                            + "|"
                            + username
                            + "|"
                            + password
                            + "\n"
            );

            writer.close();

            Main.clearScreen();

            System.out.println(
                    Main.margin()
                            + "+------------------------------------------------------+"
            );

            System.out.println(
                    Main.margin()
                            + "|                   ACCOUNT CREATED                    |"
            );

            System.out.println(
                    Main.margin()
                            + "|                       SUCCESS                        |"
            );

            System.out.println(
                    Main.margin()
                            + "+------------------------------------------------------+"
            );

            System.out.println();

            System.out.println(
                    Main.margin()
                            + "  Username : "
                            + username
            );

            System.out.println(
                    Main.margin()
                            + "  ID       : "
                            + id
            );

            System.out.println();

            System.out.println(
                    Main.margin()
                            + "+------------------------------------------------------+"
            );

        } catch (IOException e) {
            System.out.println();

            System.out.println(
                    Main.margin()
                            + "  >> The account could not be saved."
            );
        }

        System.out.print(
                "\n"
                        + Main.margin()
                        + "  Press ENTER to continue..."
        );

        scanner.nextLine();
    }

    static String[] findAccount(String id) {
        File file =
                new File(ACCOUNTS_FILE);

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
            System.out.println();

            System.out.println(
                    Main.margin()
                            + "  >> Could not read the accounts file."
            );
        }

        return null;
    }

    static boolean validID(String id) {
        if (id.length() != 12) {
            return false;
        }

        if (!id.substring(0, 5)
                .equals("2026-")) {

            return false;
        }

        for (int i = 5;
             i < id.length();
             i++) {

            if (!Character.isDigit(
                    id.charAt(i))) {

                return false;
            }
        }

        return true;
    }
}