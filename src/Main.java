import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static final String QUESTIONS_FILE = "data/questions.txt";
    static final String RESULTS_FILE = "data/results.txt";
    static final int BOX_WIDTH = 62;
    static final int CONSOLE_WIDTH = 120;

    public static void main(String[] args) {
        if (!LoginSystemLogin.showLogin()) {
            return;
        }

        Scanner scanner = LoginSystemLogin.scanner;
        QuizFileHandler fileHandler =
                new QuizFileHandler(QUESTIONS_FILE, RESULTS_FILE);

        clearScreen();
        Logo.print();
        waitForEnter(scanner);

        boolean running = true;

        while (running) {
            clearScreen();
            showMainMenu();

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    clearScreen();
                    addNewQuestion(fileHandler, scanner);
                    pause(scanner);
                    break;

                case "2":
                    clearScreen();
                    takeQuiz(fileHandler, scanner);
                    pause(scanner);
                    break;

                case "3":
                    clearScreen();
                    printBox("THANK YOU FOR USING QUITIQUIZ");
                    running = false;
                    break;

                default:
                    printMessage(
                            "Invalid choice. Please enter 1, 2, or 3."
                    );
                    pause(scanner);
            }
        }
    }

    static void showMainMenu() {
        printBorder();
        printCentered("QUITIQUIZ");
        printCentered("MAIN MENU");
        printBorder();
        printLine("");
        printLine("  [1] Add a new question");
        printLine("  [2] Take the quiz");
        printLine("  [3] Exit");
        printLine("");
        printBorder();
        printPrompt("Choose an option (1-3): ");
    }

    static void takeQuiz(
            QuizFileHandler fileHandler,
            Scanner scanner
    ) {
        ArrayList<Question> questions;

        try {
            questions = fileHandler.loadQuestions();
        } catch (IOException e) {
            printMessage("Could not read the questions file.");
            return;
        }

        if (questions.isEmpty()) {
            printMessage("There are currently no questions.");
            return;
        }

        QuizEngine quiz = new QuizEngine(questions, scanner);
        int score = quiz.run();
        String username = LoginSystemLogin.loggedInUsername;

        clearScreen();
        printBorder();
        printCentered("QUIZ COMPLETE");
        printBorder();
        printCentered(username + ", your final score is");
        printCentered(score + " out of " + questions.size());
        printBorder();

        try {
            fileHandler.saveResult(
                    username,
                    score,
                    questions.size()
            );
            printText("");
            printText("Your result was saved successfully.");
        } catch (IOException e) {
            printText("");
            printText("Your result could not be saved.");
        }
    }

    static void addNewQuestion(
            QuizFileHandler fileHandler,
            Scanner scanner
    ) {
        printBox("ADD A NEW QUESTION");
        printText("");

        String question =
                readText(scanner, "Question: ");
        String optionA =
                readText(scanner, "Option A: ");
        String optionB =
                readText(scanner, "Option B: ");
        String optionC =
                readText(scanner, "Option C: ");
        String optionD =
                readText(scanner, "Option D: ");

        char correctAnswer;

        while (true) {
            printPrompt("Correct answer (A/B/C/D): ");
            String input =
                    scanner.nextLine().trim().toUpperCase();

            if (input.length() == 1 &&
                    input.charAt(0) >= 'A' &&
                    input.charAt(0) <= 'D') {

                correctAnswer = input.charAt(0);
                break;
            }

            printText("Please enter only A, B, C, or D.");
        }

        String[] options = {
                optionA,
                optionB,
                optionC,
                optionD
        };

        Question newQuestion =
                new Question(question, options, correctAnswer);

        try {
            fileHandler.addQuestion(newQuestion);
            printMessage("Question added successfully!");
        } catch (IOException e) {
            printMessage("The question could not be saved.");
        }
    }

    static String readText(
            Scanner scanner,
            String prompt
    ) {
        while (true) {
            printPrompt(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                printText("This field cannot be empty.");
            } else if (input.contains("|")) {
                printText("The | character is not allowed.");
            } else {
                return input;
            }
        }
    }

    static void waitForEnter(Scanner scanner) {
        printText("");
        printPrompt("Press ENTER to continue...");
        scanner.nextLine();
    }

    static void pause(Scanner scanner) {
        printText("");
        printPrompt("Press ENTER to return to the menu...");
        scanner.nextLine();
    }

    static void clearScreen() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    static void printMessage(String message) {
        printText("");
        printBox(message);
    }

    static void printBox(String text) {
        printBorder();
        printCentered(text);
        printBorder();
    }

    static void printBorder() {
        System.out.println(
                margin() + "+" + repeat("-", BOX_WIDTH) + "+"
        );
    }

    static void printLine(String text) {
        if (text.length() > BOX_WIDTH) {
            text = text.substring(0, BOX_WIDTH);
        }

        System.out.println(
                margin()
                        + "|"
                        + text
                        + repeat(" ", BOX_WIDTH - text.length())
                        + "|"
        );
    }

    static void printCentered(String text) {
        if (text.length() > BOX_WIDTH) {
            text = text.substring(0, BOX_WIDTH);
        }

        int left = (BOX_WIDTH - text.length()) / 2;
        int right = BOX_WIDTH - text.length() - left;

        System.out.println(
                margin()
                        + "|"
                        + repeat(" ", left)
                        + text
                        + repeat(" ", right)
                        + "|"
        );
    }

    static void printPrompt(String text) {
        System.out.print(margin() + text);
    }

    static void printText(String text) {
        System.out.println(margin() + text);
    }

    static String margin() {
        int size = (CONSOLE_WIDTH - BOX_WIDTH - 2) / 2;

        if (size < 0) {
            size = 0;
        }

        return repeat(" ", size);
    }

    static String repeat(String text, int amount) {
        String result = "";

        for (int i = 0; i < amount; i++) {
            result += text;
        }

        return result;
    }
}