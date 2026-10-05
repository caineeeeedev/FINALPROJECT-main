import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class QuizEngine {

    ArrayList<Question> questions;
    Scanner scanner;
    int score;

    public QuizEngine(
            ArrayList<Question> questions,
            Scanner scanner
    ) {
        this.questions = questions;
        this.scanner = scanner;
        score = 0;
    }

    public int run() {
        Collections.shuffle(questions);

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            Main.clearScreen();
            Main.printBorder();
            Main.printCentered(
                    "QUESTION "
                            + (i + 1)
                            + " OF "
                            + questions.size()
            );
            Main.printCentered("Score: " + score);
            Main.printBorder();
            Main.printText("");
            Main.printText(question.questionText);
            Main.printText("");

            for (int option = 0; option < 4; option++) {
                char letter = (char) ('A' + option);

                Main.printText(
                        letter
                                + ". "
                                + question.options[option]
                );
            }

            Main.printText("");

            char answer = getAnswer();

            if (question.isCorrect(answer)) {
                score++;
                Main.printMessage("CORRECT!");
            } else {
                Main.printMessage(
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

    char getAnswer() {
        while (true) {
            Main.printPrompt("Your answer (A/B/C/D): ");

            String input =
                    scanner.nextLine().trim().toUpperCase();

            if (input.length() == 1) {
                char answer = input.charAt(0);

                if (answer >= 'A' && answer <= 'D') {
                    return answer;
                }
            }

            Main.printText(
                    "Please enter only A, B, C, or D."
            );
        }
    }
}