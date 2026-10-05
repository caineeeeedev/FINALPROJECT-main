import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static final String QUESTIONS_FILE = "data/questions.txt";
    static final String RESULTS_FILE = "data/results.txt";
    static final int WIDTH = 62;

    public static void main(String[] args) {
        if (!LoginSystemLogin.showLogin()) {
            return;
        }

        Scanner scanner = LoginSystemLogin.scanner;
        QuizFileHandler fileHandler = new QuizFileHandler(QUESTIONS_FILE, RESULTS_FILE);

        clearScreen();
        Logo.print();
        waitForEnter(scanner);

        boolean running = true;

        while (running) {
            clearScreen();
            showMainMenu();

            String menuChoice = scanner.nextLine().trim();

            switch (menuChoice) {
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
                    System.out.println();
                    running = false;
                    break;

                default:
                    showMessage("Invalid choice. Please enter 1, 2, or 3.");
                    pause(scanner);
            }
        }
    }

    static void showMainMenu() {
        printTop();
        printCentered("QUITIQUIZ");
        printCentered("MAIN MENU");
        printMiddle();
        printLine("");
        printLine("  [1] Add a new question");
        printLine("  [2] Take the quiz");
        printLine("  [3] Exit");
        printLine("");
        printBottom();
        System.out.print("Choose an option (1-3): ");
    }

    static void takeQuiz(QuizFileHandler fileHandler, Scanner scanner) {
        ArrayList<Question> questions;

        try {
            questions = fileHandler.loadQuestions();
        } catch (FileNotFoundException e) {
            showMessage("Questions file could not be found.");
            return;
        } catch (IOException e) {
            showMessage("Could not read the questions file.");
            return;
        }

        if (questions.isEmpty()) {
            showMessage("There are currently no quiz questions.");
            return;
        }

        QuizEngine engine = new QuizEngine(questions, scanner);
        int finalScore = engine.run();
        String playerName = LoginSystemLogin.loggedInUsername;

        clearScreen();
        printTop();
        printCentered("QUIZ COMPLETE");
        printMiddle();
        printCentered(playerName + ", your final score is");
        printCentered(finalScore + " out of " + questions.size());
        printBottom();

        try {
            fileHandler.saveResult(playerName, finalScore, questions.size());
            System.out.println("\nYour result was saved successfully.");
        } catch (IOException e) {
            System.out.println("\nWarning: Your result could not be saved.");
        }
    }

    static void addNewQuestion(QuizFileHandler fileHandler, Scanner scanner) {
        printBox("ADD A NEW QUESTION");

        String questionText = readNonEmptyLine(scanner, "Question: ");
        String optionA = readNonEmptyLine(scanner, "Option A: ");
        String optionB = readNonEmptyLine(scanner, "Option B: ");
        String optionC = readNonEmptyLine(scanner, "Option C: ");
        String optionD = readNonEmptyLine(scanner, "Option D: ");

        char correctAnswer;

        while (true) {
            System.out.print("Correct answer (A/B/C/D): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.matches("[ABCD]")) {
                correctAnswer = input.charAt(0);
                break;
            }

            System.out.println("Please enter only A, B, C, or D.");
        }

        String[] options = {optionA, optionB, optionC, optionD};
        Question newQuestion =
                new Question(questionText, options, correctAnswer);

        try {
            fileHandler.addQuestion(newQuestion);
            showMessage("Question added successfully!");
        } catch (IOException e) {
            showMessage("The question could not be saved.");
        }
    }

    static String readNonEmptyLine(
            Scanner scanner,
            String prompt
    ) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("This field cannot be empty.");
            } else if (input.contains("|")) {
                System.out.println("The | character is not allowed.");
            } else {
                return input;
            }
        }
    }

    static void waitForEnter(Scanner scanner) {
        System.out.print("\nPress ENTER to continue...");
        scanner.nextLine();
    }

    static void pause(Scanner scanner) {
        System.out.print("\nPress ENTER to return to the menu...");
        scanner.nextLine();
    }

    static void clearScreen() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }

    static void showMessage(String message) {
        System.out.println();
        printBox(message);
    }

    static void printBox(String text) {
        printTop();
        printCentered(text);
        printBottom();
    }

    static void printTop() {
        System.out.println("+" + "-".repeat(WIDTH) + "+");
    }

    static void printMiddle() {
        System.out.println("+" + "-".repeat(WIDTH) + "+");
    }

    static void printBottom() {
        System.out.println("+" + "-".repeat(WIDTH) + "+");
    }

    static void printLine(String text) {
        String shortened = text;

        if (shortened.length() > WIDTH) {
            shortened = shortened.substring(0, WIDTH);
        }

        System.out.printf("|%-" + WIDTH + "s|%n", shortened);
    }

    static void printCentered(String text) {
        String shortened = text;

        if (shortened.length() > WIDTH) {
            shortened = shortened.substring(0, WIDTH);
        }

        int leftPadding = (WIDTH - shortened.length()) / 2;
        int rightPadding =
                WIDTH - shortened.length() - leftPadding;

        System.out.println(
                "|" +
                " ".repeat(leftPadding) +
                shortened +
                " ".repeat(rightPadding) +
                "|"
        );
    }
}