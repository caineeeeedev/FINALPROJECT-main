import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static final String QUESTIONS_FILE =
            "data/questions.txt";

    static final String RESULTS_FILE =
            "data/results.txt";

    static final int UI_WIDTH = 54;
    static final int CONSOLE_WIDTH = 100;

    public static void main(String[] args) {
        if (!LoginSystemLogin.showLogin()) {
            return;
        }

        Scanner scanner =
                LoginSystemLogin.scanner;

        QuizFileHandler fileHandler =
                new QuizFileHandler(
                        QUESTIONS_FILE,
                        RESULTS_FILE
                );

        clearScreen();
        Logo.print();
        waitForEnter(scanner);

        boolean running = true;

        while (running) {
            clearScreen();

            System.out.println(
                    margin()
                            + "+------------------------------------------------------+"
            );

            System.out.println(
                    margin()
                            + "|                      QUITIQUIZ                       |"
            );

            System.out.println(
                    margin()
                            + "|                      MAIN MENU                       |"
            );

            System.out.println(
                    margin()
                            + "+------------------------------------------------------+"
            );

            System.out.println();

            System.out.println(
                    margin()
                            + "       [1]  Add a new question"
            );

            System.out.println(
                    margin()
                            + "       [2]  Take the quiz"
            );

            System.out.println(
                    margin()
                            + "       [3]  View rankings"
            );

            System.out.println(
                    margin()
                            + "       [4]  Exit"
            );

            System.out.println();

            System.out.println(
                    margin()
                            + "+------------------------------------------------------+"
            );

            System.out.print(
                    margin()
                            + "  Choose an option (1-4): "
            );

            String choice =
                    scanner.nextLine();

            switch (choice) {
                case "1":
                    clearScreen();

                    addNewQuestion(
                            fileHandler,
                            scanner
                    );

                    pause(scanner);
                    break;

                case "2":
                    clearScreen();

                    takeQuiz(
                            fileHandler,
                            scanner
                    );

                    pause(scanner);
                    break;

                case "3":
                    clearScreen();
                    showRankings();
                    pause(scanner);
                    break;

                case "4":
                    clearScreen();

                    System.out.println(
                            margin()
                                    + "+------------------------------------------------------+"
                    );

                    System.out.println(
                            margin()
                                    + "|                      QUITIQUIZ                       |"
                    );

                    System.out.println(
                            margin()
                                    + "|                       GOODBYE!                       |"
                    );

                    System.out.println(
                            margin()
                                    + "+------------------------------------------------------+"
                    );

                    System.out.println();

                    System.out.println(
                            margin()
                                    + "  Thank you for using QuitiQuiz."
                    );

                    System.out.println();

                    System.out.println(
                            margin()
                                    + "+------------------------------------------------------+"
                    );

                    running = false;
                    break;

                default:
                    System.out.println();

                    System.out.println(
                            margin()
                                    + "  >> Invalid choice. Please enter 1, 2, 3, or 4."
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
            questions =
                    fileHandler.loadQuestions();

        } catch (IOException e) {
            System.out.println();

            System.out.println(
                    margin()
                            + "  >> Could not read the questions file."
            );

            return;
        }

        if (questions.isEmpty()) {
            System.out.println();

            System.out.println(
                    margin()
                            + "  >> There are currently no questions."
            );

            return;
        }

        QuizEngine quiz =
                new QuizEngine(
                        questions,
                        scanner
                );

        int score =
                quiz.run();

        String username =
                LoginSystemLogin.loggedInUsername;

        clearScreen();

        System.out.println(
                margin()
                        + "+------------------------------------------------------+"
        );

        System.out.println(
                margin()
                        + "|                    QUIZ COMPLETE                     |"
        );

        System.out.println(
                margin()
                        + "|                     FINAL RESULT                     |"
        );

        System.out.println(
                margin()
                        + "+------------------------------------------------------+"
        );

        System.out.println();

        System.out.println(
                margin()
                        + "  Player : "
                        + username
        );

        System.out.println(
                margin()
                        + "  Score  : "
                        + score
                        + "/"
                        + questions.size()
        );

        System.out.println();

        System.out.println(
                margin()
                        + "+------------------------------------------------------+"
        );

        try {
            fileHandler.saveResult(
                    username,
                    score,
                    questions.size()
            );

            System.out.println();

            System.out.println(
                    margin()
                            + "  >> Your result was saved successfully."
            );

        } catch (IOException e) {
            System.out.println();

            System.out.println(
                    margin()
                            + "  >> Your result could not be saved."
            );
        }
    }

    static void addNewQuestion(
            QuizFileHandler fileHandler,
            Scanner scanner
    ) {
        System.out.println(
                margin()
                        + "+------------------------------------------------------+"
        );

        System.out.println(
                margin()
                        + "|                      QUITIQUIZ                       |"
        );

        System.out.println(
                margin()
                        + "|                 ADD A NEW QUESTION                   |"
        );

        System.out.println(
                margin()
                        + "+------------------------------------------------------+"
        );

        System.out.println();

        String question =
                readText(
                        scanner,
                        "Question: "
                );

        String optionA =
                readText(
                        scanner,
                        "Option A: "
                );

        String optionB =
                readText(
                        scanner,
                        "Option B: "
                );

        String optionC =
                readText(
                        scanner,
                        "Option C: "
                );

        String optionD =
                readText(
                        scanner,
                        "Option D: "
                );

        char correctAnswer;

        while (true) {
            System.out.print(
                    margin()
                            + "  Correct answer (A/B/C/D): "
            );

            String input =
                    scanner.nextLine()
                            .trim()
                            .toUpperCase();

            if (input.length() == 1
                    && input.charAt(0) >= 'A'
                    && input.charAt(0) <= 'D') {

                correctAnswer =
                        input.charAt(0);

                break;
            }

            System.out.println();

            System.out.println(
                    margin()
                            + "  >> Please enter only A, B, C, or D."
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
            fileHandler.addQuestion(
                    newQuestion
            );

            System.out.println();

            System.out.println(
                    margin()
                            + "  >> Question added successfully!"
            );

        } catch (IOException e) {
            System.out.println();

            System.out.println(
                    margin()
                            + "  >> The question could not be saved."
            );
        }
    }

    static void showRankings() {
        ArrayList<String> names =
                new ArrayList<String>();

        ArrayList<Integer> scores =
                new ArrayList<Integer>();

        ArrayList<Integer> totals =
                new ArrayList<Integer>();

        File file =
                new File(RESULTS_FILE);

        System.out.println(
                margin()
                        + "+------------------------------------------------------+"
        );

        System.out.println(
                margin()
                        + "|                      QUITIQUIZ                       |"
        );

        System.out.println(
                margin()
                        + "|                       RANKINGS                       |"
        );

        System.out.println(
                margin()
                        + "+------------------------------------------------------+"
        );

        if (!file.exists()) {
            System.out.println();

            System.out.println(
                    margin()
                            + "  >> There are no saved results yet."
            );

            return;
        }

        try {
            Scanner fileScanner =
                    new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line =
                        fileScanner.nextLine().trim();

                if (!line.isEmpty()) {
                    String[] data =
                            line.split("\\|");

                    if (data.length >= 2) {
                        String username =
                                data[data.length - 2].trim();

                        String scoreText =
                                data[data.length - 1].trim();

                        if (scoreText.startsWith("Score:")) {
                            scoreText =
                                    scoreText.substring(6).trim();

                            String[] scoreParts =
                                    scoreText.split("/");

                            if (scoreParts.length == 2) {
                                try {
                                    int score =
                                            Integer.parseInt(
                                                    scoreParts[0].trim()
                                            );

                                    int total =
                                            Integer.parseInt(
                                                    scoreParts[1].trim()
                                            );

                                    names.add(username);
                                    scores.add(score);
                                    totals.add(total);

                                } catch (NumberFormatException e) {
                                    System.out.println(
                                            margin()
                                                    + "  >> One invalid result was skipped."
                                    );
                                }
                            }
                        }
                    }
                }
            }

            fileScanner.close();

        } catch (IOException e) {
            System.out.println();

            System.out.println(
                    margin()
                            + "  >> Error reading the results file."
            );

            return;
        }

        if (names.isEmpty()) {
            System.out.println();

            System.out.println(
                    margin()
                            + "  >> There are no valid rankings yet."
            );

            return;
        }

        for (int i = 0;
             i < scores.size() - 1;
             i++) {

            for (int j = i + 1;
                 j < scores.size();
                 j++) {

                if (scores.get(j) > scores.get(i)) {
                    int temporaryScore =
                            scores.get(i);

                    scores.set(
                            i,
                            scores.get(j)
                    );

                    scores.set(
                            j,
                            temporaryScore
                    );

                    int temporaryTotal =
                            totals.get(i);

                    totals.set(
                            i,
                            totals.get(j)
                    );

                    totals.set(
                            j,
                            temporaryTotal
                    );

                    String temporaryName =
                            names.get(i);

                    names.set(
                            i,
                            names.get(j)
                    );

                    names.set(
                            j,
                            temporaryName
                    );
                }
            }
        }

        System.out.println();

        System.out.println(
                margin()
                        + "       Rank     Username                 Score"
        );

        System.out.println(
                margin()
                        + "       ----------------------------------------"
        );

        for (int i = 0;
             i < names.size();
             i++) {

            String rank =
                    String.valueOf(i + 1);

            String username =
                    names.get(i);

            String score =
                    scores.get(i)
                            + "/"
                            + totals.get(i);

            while (rank.length() < 9) {
                rank += " ";
            }

            if (username.length() > 22) {
                username =
                        username.substring(0, 22);
            }

            while (username.length() < 25) {
                username += " ";
            }

            System.out.println(
                    margin()
                            + "       "
                            + rank
                            + username
                            + score
            );
        }

        System.out.println();

        System.out.println(
                margin()
                        + "+------------------------------------------------------+"
        );
    }

    static String readText(
            Scanner scanner,
            String prompt
    ) {
        while (true) {
            System.out.print(
                    margin()
                            + "  "
                            + prompt
            );

            String input =
                    scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println();

                System.out.println(
                        margin()
                                + "  >> This field cannot be empty."
                );

            } else if (input.contains("|")) {
                System.out.println();

                System.out.println(
                        margin()
                                + "  >> The | character is not allowed."
                );

            } else {
                return input;
            }
        }
    }

    static void waitForEnter(
            Scanner scanner
    ) {
        System.out.print(
                "\n"
                        + margin()
                        + "  Press ENTER to continue..."
        );

        scanner.nextLine();
    }

    static void pause(
            Scanner scanner
    ) {
        System.out.print(
                "\n"
                        + margin()
                        + "  Press ENTER to return to the menu..."
        );

        scanner.nextLine();
    }

    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int i = 0; i < 4; i++) {
            System.out.println();
        }
    }

    static String margin() {
        String spaces = "";

        int amount =
                (CONSOLE_WIDTH - UI_WIDTH - 2) / 2;

        for (int i = 0;
             i < amount;
             i++) {

            spaces += " ";
        }

        return spaces;
    }
}