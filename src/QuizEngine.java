import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class QuizEngine {

    ArrayList<Question> questions;
    int score;
    Scanner scanner;

    public QuizEngine(
            ArrayList<Question> list,
            Scanner sc
    ) {
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
                    "QUESTION "
                            + (i + 1)
                            + " OF "
                            + questions.size()
            );
            Main.printCentered("Score: " + score);
            Main.printBottom();
            Main.printText("");
            Main.printText(question.questionText);
            Main.printText("");
            Main.printText("A. " + question.options[0]);
            Main.printText("B. " + question.options[1]);
            Main.printText("C. " + question.options[2]);
            Main.printText("D. " + question.options[3]);
            Main.printText("");

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
                Main.printText("");
                Main.printPrompt(
                        "Press ENTER for the next question..."
                );
                scanner.nextLine();
            }
        }

        return score;
    }

    private char getValidatedAnswer() {
        while (true) {
            Main.printPrompt("Your answer (A/B/C/D): ");

            String input =
                    scanner.nextLine().trim().toUpperCase();

            if (input.matches("[ABCD]")) {
                return input.charAt(0);
            }

            Main.printText(
                    "Please enter only A, B, C, or D."
            );
        }
    }
}