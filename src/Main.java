import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static final String QUESTIONS_FILE = "data/questions.txt";
    static final String RESULTS_FILE = "data/results.txt";

    static final int UI_WIDTH = 54;
    static final int CONSOLE_WIDTH = 100;

    public static void main(String[] args) {
        if (!LoginSystemLogin.showLogin()) {
            return;
        }

        Scanner scanner = LoginSystemLogin.scanner;

        QuizFileHandler fileHandler =
                new QuizFileHandler(
                        QUESTIONS_FILE,
                        RESULTS_FILE
                );

        clearScreen();
        Logo.print();
        waitForEnter(scanner);

        boolean running = true;

        while (running) {
            clearScreen();

            printHeader("QUITIQUIZ", "MAIN MENU");
            printOption("1", "Add a new question");
            printOption("2", "Take the quiz");
            printOption("3", "Exit");
            printFooter();

            System.out.print(
                    margin() + "  Choose an option (1-3): "
            );

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

                    printHeader("QUITIQUIZ", "GOODBYE!");

                    System.out.println(
                            margin()
                                    + "  Thank you for using QuitiQuiz."
                    );

                    printFooter();
                    running = false;
                    break;

                default:
                    printNotice(
                            "Invalid choice. Please enter 1, 2, or 3."
                    );

                    pause(scanner);
            }
        }
    }

    static void takeQuiz(
            QuizFileHandler fileHandler,
            Scanner scanner
    ) {
        ArrayList<Question> questions;

        try {
            questions = fileHandler.loadQuestions();
        } catch (IOException e) {
            printNotice(
                    "Could not read the questions file."
            );

            return;
        }

        if (questions.isEmpty()) {
            printNotice(
                    "There are currently no questions."
            );

            return;
        }

        QuizEngine quiz =
                new QuizEngine(questions, scanner);

        int score = quiz.run();

        String username =
                LoginSystemLogin.loggedInUsername;

        clearScreen();

        printHeader(
                "QUIZ COMPLETE",
                "FINAL RESULT"
        );

        System.out.println(
                margin() + "  Player : " + username
        );

        System.out.println(
                margin()
                        + "  Score  : "
                        + score
                        + " / "
                        + questions.size()
        );

        printFooter();

        try {
            fileHandler.saveResult(
                    username,
                    score,
                    questions.size()
            );

            printNotice(
                    "Your result was saved successfully."
            );
        } catch (IOException e) {
            printNotice(
                    "Your result could not be saved."
            );
        }
    }

    static void addNewQuestion(
            QuizFileHandler fileHandler,
            Scanner scanner
    ) {
        printHeader(
                "QUITIQUIZ",
                "ADD A NEW QUESTION"
        );

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
            System.out.print(
                    margin()
                            + "  Correct answer (A/B/C/D): "
            );

            String input =
                    scanner.nextLine().trim().toUpperCase();

            if (input.length() == 1
                    && input.charAt(0) >= 'A'
                    && input.charAt(0) <= 'D') {

                correctAnswer = input.charAt(0);
                break;
            }

            printNotice(
                    "Please enter only A, B, C, or D."
            );
        }

        String[] options = {
                optionA,
                optionB,
                optionC,
                optionD
        };

        Question newQuestion =
                new Question(
                        question,
                        options,
                        correctAnswer
                );

        try {
            fileHandler.addQuestion(newQuestion);

            printNotice(
                    "Question added successfully!"
            );
        } catch (IOException e) {
            printNotice(
                    "The question could not be saved."
            );
        }
    }

    static String readText(
            Scanner scanner,
            String prompt
    ) {
        while (true) {
            System.out.print(
                    margin() + "  " + prompt
            );

            String input =
                    scanner.nextLine().trim();

            if (input.isEmpty()) {
                printNotice(
                        "This field cannot be empty."
                );
            } else if (input.contains("|")) {
                printNotice(
                        "The | character is not allowed."
                );
            } else {
                return input;
            }
        }
    }

    static void waitForEnter(Scanner scanner) {
        System.out.print(
                "\n"
                        + margin()
                        + "  Press ENTER to continue..."
        );

        scanner.nextLine();
    }

    static void pause(Scanner scanner) {
        System.out.print(
                "\n"
                        + margin()
                        + "  Press ENTER to return to the menu..."
        );

        scanner.nextLine();
    }

    static void clearScreen() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }

    static void printHeader(
            String title,
            String subtitle
    ) {
        printBorder();
        printCentered(title);
        printCentered(subtitle);
        printBorder();

        System.out.println();
    }

    static void printOption(
            String number,
            String text
    ) {
        System.out.println(
                margin()
                        + "       ["
                        + number
                        + "]  "
                        + text
        );
    }

    static void printNotice(String text) {
        System.out.println();

        System.out.println(
                margin() + "  >> " + text
        );
    }

    static void printFooter() {
        System.out.println();
        printBorder();
    }

    static void printBorder() {
        System.out.print(margin() + "+");

        for (int i = 0; i < UI_WIDTH; i++) {
            System.out.print("-");
        }

        System.out.println("+");
    }

    static void printCentered(String text) {
        int left =
                (UI_WIDTH - text.length()) / 2;

        int right =
                UI_WIDTH - text.length() - left;

        System.out.print(margin() + "|");

        for (int i = 0; i < left; i++) {
            System.out.print(" ");
        }

        System.out.print(text);

        for (int i = 0; i < right; i++) {
            System.out.print(" ");
        }

        System.out.println("|");
    }

    static String margin() {
        String spaces = "";

        int amount =
                (CONSOLE_WIDTH - UI_WIDTH - 2) / 2;

        for (int i = 0; i < amount; i++) {
            spaces += " ";
        }

        return spaces;
    }
}