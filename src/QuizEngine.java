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

            System.out.println("+------------------------------------------------------+");
            System.out.println("QUESTION " + (i + 1) + " OF " + questions.size());
            System.out.println("CURRENT SCORE: " + score);
            System.out.println("+------------------------------------------------------+");

            System.out.println(question.questionText);
            System.out.println("[A] " + question.options[0]);
            System.out.println("[B] " + question.options[1]);
            System.out.println("[C] " + question.options[2]);
            System.out.println("[D] " + question.options[3]);

            char answer = getAnswer();

            if (question.isCorrect(answer)) {
                score++;
                System.out.println(">> CORRECT!");
            } else {
                System.out.println(">> INCORRECT! Correct answer: " + question.correctAnswer);
            }

            if (i < questions.size() - 1) {
                System.out.println("Press ENTER for the next question...");
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
            System.out.print("Your answer (A/B/C/D): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.length() == 1) {
                char answer = input.charAt(0);

                if (answer >= 'A' && answer <= 'D') {
                    return answer;
                }
            }

            System.out.println(">> Please enter only A, B, C, or D.");
        }
    }
}