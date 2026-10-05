import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static final String QUESTIONS_FILE = "data/questions.txt";
    static final String RESULTS_FILE = "data/results.txt";

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

            System.out.println("==============================");
            System.out.println("          QUITIQUIZ");
            System.out.println("          MAIN MENU");
            System.out.println("==============================");
            System.out.println("[1] Add a new question");
            System.out.println("[2] Take the quiz");
            System.out.println("[3] Exit");
            System.out.print("Choose an option (1-3): ");

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
                    System.out.println("THANK YOU FOR USING QUITIQUIZ");
                    running = false;
                    break;

                default:
                    System.out.println(
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
            System.out.println(
                    "Could not read the questions file."
            );
            return;
        }

        if (questions.isEmpty()) {
            System.out.println(
                    "There are currently no questions."
            );
            return;
        }

        QuizEngine quiz = new QuizEngine(questions, scanner);
        int score = quiz.run();
        String username = LoginSystemLogin.loggedInUsername;

        clearScreen();

        System.out.println("==============================");
        System.out.println("        QUIZ COMPLETE");
        System.out.println("==============================");
        System.out.println(
                username + ", your final score is "
                        + score + " out of " + questions.size()
        );

        try {
            fileHandler.saveResult(
                    username,
                    score,
                    questions.size()
            );

            System.out.println(
                    "Your result was saved successfully."
            );
        } catch (IOException e) {
            System.out.println(
                    "Your result could not be saved."
            );
        }
    }

    static void addNewQuestion(
            QuizFileHandler fileHandler,
            Scanner scanner
    ) {
        System.out.println("==============================");
        System.out.println("      ADD A NEW QUESTION");
        System.out.println("==============================");

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
                    "Correct answer (A/B/C/D): "
            );

            String input =
                    scanner.nextLine().trim().toUpperCase();

            if (input.length() == 1
                    && input.charAt(0) >= 'A'
                    && input.charAt(0) <= 'D') {

                correctAnswer = input.charAt(0);
                break;
            }

            System.out.println(
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

            System.out.println(
                    "Question added successfully!"
            );
        } catch (IOException e) {
            System.out.println(
                    "The question could not be saved."
            );
        }
    }

    static String readText(
            Scanner scanner,
            String prompt
    ) {
        while (true) {
            System.out.print(prompt);

            String input =
                    scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println(
                        "This field cannot be empty."
                );
            } else if (input.contains("|")) {
                System.out.println(
                        "The | character is not allowed."
                );
            } else {
                return input;
            }
        }
    }

    static void waitForEnter(Scanner scanner) {
        System.out.print(
                "\nPress ENTER to continue..."
        );

        scanner.nextLine();
    }

    static void pause(Scanner scanner) {
        System.out.print(
                "\nPress ENTER to return to the menu..."
        );

        scanner.nextLine();
    }

    static void clearScreen() {
        for (int i = 0; i < 30; i++) {
            System.out.println();
        }
    }
}