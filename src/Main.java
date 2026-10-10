import java.io.File;
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
        QuizFileHandler fileHandler = new QuizFileHandler(QUESTIONS_FILE, RESULTS_FILE);

        Logo.print();

        boolean running = true;

        while (running) {
            System.out.println("+------------------------------------------------------+");
            System.out.println("|                      QUITIQUIZ                       |");
            System.out.println("|                      MAIN MENU                       |");
            System.out.println("+------------------------------------------------------+");
            System.out.println("[1] Add a new question");
            System.out.println("[2] Take the quiz");
            System.out.println("[3] View rankings");
            System.out.println("[4] Exit");
            System.out.print("Choose an option (1-4): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addNewQuestion(fileHandler, scanner);
                    break;
                case "2":
                    takeQuiz(fileHandler, scanner);
                    break;
                case "3":
                    showRankings();
                    break;
                case "4":
                    System.out.println("+------------------------------------------------------+");
                    System.out.println("|                      QUITIQUIZ                       |");
                    System.out.println("|                       GOODBYE!                       |");
                    System.out.println("+------------------------------------------------------+");
                    System.out.println("Thank you for using QuitiQuiz.");
                    running = false;
                    break;
                default:
                    System.out.println(">> Invalid choice. Please enter 1, 2, 3, or 4.");
            }
        }
    }

    static void takeQuiz(QuizFileHandler fileHandler, Scanner scanner) {
        ArrayList<Question> questions;

        try {
            questions = fileHandler.loadQuestions();
        } catch (IOException e) {
            System.out.println(">> Could not read the questions file.");
            return;
        }

        if (questions.isEmpty()) {
            System.out.println(">> There are currently no questions.");
            return;
        }

        QuizEngine quiz = new QuizEngine(questions, scanner);
        int score = quiz.run();
        String username = LoginSystemLogin.loggedInUsername;

        System.out.println("+------------------------------------------------------+");
        System.out.println("|                    QUIZ COMPLETE                     |");
        System.out.println("|                     FINAL RESULT                     |");
        System.out.println("+------------------------------------------------------+");
        System.out.println("Player : " + username);
        System.out.println("Score  : " + score + "/" + questions.size());
        System.out.println("+------------------------------------------------------+");

        try {
            fileHandler.saveResult(username, score, questions.size());
            System.out.println(">> Your result was saved successfully.");
        } catch (IOException e) {
            System.out.println(">> Your result could not be saved.");
        }
    }

    static void addNewQuestion(QuizFileHandler fileHandler, Scanner scanner) {
        System.out.println("+------------------------------------------------------+");
        System.out.println("|                      QUITIQUIZ                       |");
        System.out.println("|                 ADD A NEW QUESTION                   |");
        System.out.println("+------------------------------------------------------+");

        String question = readText(scanner, "Question: ");
        String optionA = readText(scanner, "Option A: ");
        String optionB = readText(scanner, "Option B: ");
        String optionC = readText(scanner, "Option C: ");
        String optionD = readText(scanner, "Option D: ");

        char correctAnswer;

        while (true) {
            System.out.print("Correct answer (A/B/C/D): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'D') {
                correctAnswer = input.charAt(0);
                break;
            }

            System.out.println(">> Please enter only A, B, C, or D.");
        }

        String[] options = {optionA, optionB, optionC, optionD};
        Question newQuestion = new Question(question, options, correctAnswer);

        try {
            fileHandler.addQuestion(newQuestion);
            System.out.println(">> Question added successfully!");
        } catch (IOException e) {
            System.out.println(">> The question could not be saved.");
        }
    }

    static void showRankings() {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> scores = new ArrayList<Integer>();
        ArrayList<Integer> totals = new ArrayList<Integer>();
        File file = new File(RESULTS_FILE);

        System.out.println("+------------------------------------------------------+");
        System.out.println("|                      QUITIQUIZ                       |");
        System.out.println("|                       RANKINGS                       |");
        System.out.println("+------------------------------------------------------+");

        if (!file.exists()) {
            System.out.println(">> There are no saved results yet.");
            return;
        }

        try {
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();

                if (!line.isEmpty()) {
                    String[] data = line.split("\\|");

                    if (data.length >= 2) {
                        String username = data[data.length - 2].trim();
                        String scoreText = data[data.length - 1].trim();

                        if (scoreText.startsWith("Score:")) {
                            scoreText = scoreText.substring(6).trim();
                            String[] scoreParts = scoreText.split("/");

                            if (scoreParts.length == 2) {
                                try {
                                    int score = Integer.parseInt(scoreParts[0].trim());
                                    int total = Integer.parseInt(scoreParts[1].trim());

                                    names.add(username);
                                    scores.add(score);
                                    totals.add(total);
                                } catch (NumberFormatException e) {
                                    System.out.println(">> One invalid result was skipped.");
                                }
                            }
                        }
                    }
                }
            }

            fileScanner.close();

        } catch (IOException e) {
            System.out.println(">> Error reading the results file.");
            return;
        }

        if (names.isEmpty()) {
            System.out.println(">> There are no valid rankings yet.");
            return;
        }

        for (int i = 0; i < scores.size() - 1; i++) {
            for (int j = i + 1; j < scores.size(); j++) {
                if (scores.get(j) > scores.get(i)) {
                    int temporaryScore = scores.get(i);
                    scores.set(i, scores.get(j));
                    scores.set(j, temporaryScore);

                    int temporaryTotal = totals.get(i);
                    totals.set(i, totals.get(j));
                    totals.set(j, temporaryTotal);

                    String temporaryName = names.get(i);
                    names.set(i, names.get(j));
                    names.set(j, temporaryName);
                }
            }
        }

        System.out.println("Rank     Username                 Score");
        System.out.println("----------------------------------------");

        for (int i = 0; i < names.size(); i++) {
            String rank = String.valueOf(i + 1);
            String username = names.get(i);
            String score = scores.get(i) + "/" + totals.get(i);

            while (rank.length() < 9) {
                rank += " ";
            }

            if (username.length() > 22) {
                username = username.substring(0, 22);
            }

            while (username.length() < 25) {
                username += " ";
            }

            System.out.println(rank + username + score);
        }

        System.out.println("+------------------------------------------------------+");
    }

    static String readText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println(">> This field cannot be empty.");
            } else if (input.contains("|")) {
                System.out.println(">> The | character is not allowed.");
            } else {
                return input;
            }
        }
    }
}