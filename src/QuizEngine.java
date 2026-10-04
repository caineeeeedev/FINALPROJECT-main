import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class QuizEngine {
    //variables
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

        int number = 1;

        for (Question q : questions) {

            System.out.println("\nQuestion " + number + " of " + questions.size());
            System.out.println(q.questionText);
            System.out.println("   A) " + q.options[0]);
            System.out.println("   B) " + q.options[1]);
            System.out.println("   C) " + q.options[2]);
            System.out.println("   D) " + q.options[3]);

            char answer = getValidatedAnswer();

            if (q.isCorrect(answer)) {
                System.out.println("Correct!");
                score++;
            } else {
                System.out.println("Incorrect. The correct answer was " + q.correctAnswer + ".");
            }

            number++;
        }

        return score;
    }

    private char getValidatedAnswer() {
        while (true) {
            System.out.print("Your answer (A/B/C/D): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("A")) {
                return 'A';
            }
            if (input.equalsIgnoreCase("B")) {
                return 'B';
            }
            if (input.equalsIgnoreCase("C")) {
                return 'C';
            }
            if (input.equalsIgnoreCase("D")) {
                return 'D';
            }

            System.out.println("Invalid answer. Please enter A, B, C, or D.");
        }
    }
}