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

    for (int i = 0; i < questions.size(); i++) {
        Question q = questions.get(i);

        System.out.println("\nQuestion " + (i + 1) + " of " + questions.size());
        q.display();

        char answer = getValidatedAnswer();

        if (q.isCorrect(answer)) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect. The correct answer was " + q.getCorrectAnswer() + ".");
        }
    }

    return score;
}

   private char getValidatedAnswer() {
    while (true) {
        System.out.print("Your answer (A/B/C/D): ");
        String input = scanner.nextLine().trim().toUpperCase();

        try {
            if (!input.equals("A") && !input.equals("B") && !input.equals("C") && !input.equals("D")) {
                throw new InvalidAnswerException("Invalid answer.");
            }
            return input.charAt(0);

        } 
        catch (InvalidAnswerException e) {
            System.out.println(e.getMessage() + " Please enter A, B, C, or D.");
        }
     }
    }
}