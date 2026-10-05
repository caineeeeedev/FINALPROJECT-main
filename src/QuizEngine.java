import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class QuizEngine {

    ArrayList<Question> questions;
    int score;
    Scanner scanner;

    public QuizEngine(ArrayList<Question> list, Scanner sc) {
        questions = list;
        scanner = sc;
        score = 0;
    }

    public int run() {
        Collections.shuffle(questions);

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            Main.clearScreen();
            Main.printTop();
            Main.printCentered(
                    "QUESTION " + (i + 1) + " OF " + questions.size()
            );
            Main.printCentered("Score: " + score);
            Main.printBottom();

            System.out.println();
            System.out.println(question.questionText);
            System.out.println();
            System.out.println("  A. " + question.options[0]);
            System.out.println("  B. " + question.options[1]);
            System.out.println("  C. " + question.options[2]);
            System.out.println("  D. " + question.options[3]);
            System.out.println();

            char answer = getValidatedAnswer();

            if (question.isCorrect(answer)) {
                score++;
                Main.showMessage("CORRECT!");
            } else {
                Main.showMessage(
                        "INCORRECT! Correct answer: "
                                + question.correctAnswer
                );
            }

            if (i < questions.size() - 1) {
                System.out.print("\nPress ENTER for the next question...");
                scanner.nextLine();
            }
        }

        return score;
    }

    private char getValidatedAnswer() {
        while (true) {
            System.out.print("Your answer (A/B/C/D): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.matches("[ABCD]")) {
                return input.charAt(0);
            }

            System.out.println("Please enter only A, B, C, or D.");
        }
    }
}