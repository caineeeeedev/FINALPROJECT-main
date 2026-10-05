import java.util.ArrayList;
import java.util.Scanner;

public class QuizEngine {
    ArrayList<Question> questions;
    Scanner scanner;
    int score;

    public QuizEngine(ArrayList<Question> questions, Scanner scanner) {
        this.questions = questions;
        this.scanner = scanner;
        score = 0;
    }

    public int run() {
        shuffleQuestions();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            Main.clearScreen();
            Main.printHeader("QUESTION " + (i + 1) + " OF " + questions.size(), "CURRENT SCORE: " + score);
            question.display();
            char answer = getAnswer();

            if (question.isCorrect(answer)) {
                score++;
                Main.printNotice("CORRECT!");
            } else {
                Main.printNotice("INCORRECT! Correct answer: " + question.correctAnswer);
            }

            if (i < questions.size() - 1) {
                System.out.print("\n  Press ENTER for the next question...");
                scanner.nextLine();
            }
        }
        return score;
    }

    void shuffleQuestions() {
        for (int i = questions.size() - 1; i > 0; i--) {
            int randomIndex = (int) (Math.random() * (i + 1));
            Question temporary = questions.get(i);
            questions.set(i, questions.get(randomIndex));
            questions.set(randomIndex, temporary);
        }
    }

    char getAnswer() {
        while (true) {
            System.out.print("\n  Your answer (A/B/C/D): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.length() == 1) {
                char answer = input.charAt(0);
                if (answer >= 'A' && answer <= 'D') return answer;
            }
            Main.printNotice("Please enter only A, B, C, or D.");
        }
    }
}
