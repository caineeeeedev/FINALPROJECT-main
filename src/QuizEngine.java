import java.util.ArrayList;
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
        shuffleQuestions();

        for (int i = 0;
             i < questions.size();
             i++) {

            Question question =
                    questions.get(i);

            Main.clearScreen();

            System.out.println(
                    Main.margin()
                            + "+------------------------------------------------------+"
            );

            String questionNumber =
                    "QUESTION "
                            + (i + 1)
                            + " OF "
                            + questions.size();

            int questionSpaces =
                    (54 - questionNumber.length()) / 2;

            System.out.print(
                    Main.margin() + "|"
            );

            for (int space = 0;
                 space < questionSpaces;
                 space++) {

                System.out.print(" ");
            }

            System.out.print(questionNumber);

            int remainingQuestionSpaces =
                    54
                            - questionSpaces
                            - questionNumber.length();

            for (int space = 0;
                 space < remainingQuestionSpaces;
                 space++) {

                System.out.print(" ");
            }

            System.out.println("|");

            String scoreText =
                    "CURRENT SCORE: " + score;

            int scoreSpaces =
                    (54 - scoreText.length()) / 2;

            System.out.print(
                    Main.margin() + "|"
            );

            for (int space = 0;
                 space < scoreSpaces;
                 space++) {

                System.out.print(" ");
            }

            System.out.print(scoreText);

            int remainingScoreSpaces =
                    54
                            - scoreSpaces
                            - scoreText.length();

            for (int space = 0;
                 space < remainingScoreSpaces;
                 space++) {

                System.out.print(" ");
            }

            System.out.println("|");

            System.out.println(
                    Main.margin()
                            + "+------------------------------------------------------+"
            );

            System.out.println();

            System.out.println(
                    Main.margin()
                            + "  "
                            + question.questionText
            );

            System.out.println();

            System.out.println(
                    Main.margin()
                            + "       [A] "
                            + question.options[0]
            );

            System.out.println(
                    Main.margin()
                            + "       [B] "
                            + question.options[1]
            );

            System.out.println(
                    Main.margin()
                            + "       [C] "
                            + question.options[2]
            );

            System.out.println(
                    Main.margin()
                            + "       [D] "
                            + question.options[3]
            );

            char answer = getAnswer();

            if (question.isCorrect(answer)) {
                score++;

                System.out.println();

                System.out.println(
                        Main.margin()
                                + "  >> CORRECT!"
                );

            } else {
                System.out.println();

                System.out.println(
                        Main.margin()
                                + "  >> INCORRECT! Correct answer: "
                                + question.correctAnswer
                );
            }

            if (i < questions.size() - 1) {
                System.out.print(
                        "\n"
                                + Main.margin()
                                + "  Press ENTER for the next question..."
                );

                scanner.nextLine();
            }
        }

        return score;
    }

    void shuffleQuestions() {
        for (int i = questions.size() - 1;
             i > 0;
             i--) {

            int randomIndex =
                    (int) (
                            Math.random()
                                    * (i + 1)
                    );

            Question temporary =
                    questions.get(i);

            questions.set(
                    i,
                    questions.get(randomIndex)
            );

            questions.set(
                    randomIndex,
                    temporary
            );
        }
    }

    char getAnswer() {
        while (true) {
            System.out.print(
                    "\n"
                            + Main.margin()
                            + "  Your answer (A/B/C/D): "
            );

            String input =
                    scanner.nextLine()
                            .trim()
                            .toUpperCase();

            if (input.length() == 1) {
                char answer =
                        input.charAt(0);

                if (answer >= 'A'
                        && answer <= 'D') {

                    return answer;
                }
            }

            System.out.println();

            System.out.println(
                    Main.margin()
                            + "  >> Please enter only A, B, C, or D."
            );
        }
    }
}