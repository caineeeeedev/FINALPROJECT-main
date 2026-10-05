import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class QuizFileHandler {
    String questionsFile;
    String resultsFile;

    public QuizFileHandler(
            String questionsFile,
            String resultsFile
    ) {
        this.questionsFile = questionsFile;
        this.resultsFile = resultsFile;
    }

    public ArrayList<Question> loadQuestions()
            throws IOException {

        ArrayList<Question> questions =
                new ArrayList<Question>();

        Scanner fileScanner =
                new Scanner(
                        new File(questionsFile)
                );

        while (fileScanner.hasNextLine()) {
            String line =
                    fileScanner.nextLine();

            String[] parts =
                    line.split("\\|");

            if (parts.length == 6) {
                String[] options = {
                        parts[1],
                        parts[2],
                        parts[3],
                        parts[4]
                };

                char answer =
                        Character.toUpperCase(
                                parts[5].charAt(0)
                        );

                Question question =
                        new Question(
                                parts[0],
                                options,
                                answer
                        );

                questions.add(question);
            }
        }

        fileScanner.close();

        return questions;
    }

    public void addQuestion(Question question)
            throws IOException {

        FileWriter writer =
                new FileWriter(
                        questionsFile,
                        true
                );

        writer.write(
                question.questionText
                        + "|"
                        + question.options[0]
                        + "|"
                        + question.options[1]
                        + "|"
                        + question.options[2]
                        + "|"
                        + question.options[3]
                        + "|"
                        + question.correctAnswer
                        + "\n"
        );

        writer.close();
    }

    public void saveResult(
            String username,
            int score,
            int total
    ) throws IOException {

        FileWriter writer =
                new FileWriter(
                        resultsFile,
                        true
                );

        writer.write(
                username
                        + " | Score: "
                        + score
                        + "/"
                        + total
                        + "\n"
        );

        writer.close();
    }
}