import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class QuizFileHandler {

    private String questionsFilePath;
    private String resultsFilePath;

    public QuizFileHandler(String questionsFilePath, String resultsFilePath) {
        this.questionsFilePath = questionsFilePath;
        this.resultsFilePath = resultsFilePath;
    }


    public ArrayList<Question> loadQuestions() throws IOException {
        ArrayList<Question> questions = new ArrayList<>();

        
        try (BufferedReader reader = new BufferedReader(new FileReader(questionsFilePath))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                if (parts.length != 6) {
                    System.out.println("Skipping malformed line " + lineNumber + ": " + line);
                    continue;
                }

                try {
                    String questionText = parts[0];
                    String[] options = { parts[1], parts[2], parts[3], parts[4] };
                    char correctAnswer = Character.toUpperCase(parts[5].charAt(0));

                    questions.add(new Question(questionText, options, correctAnswer));
                } catch (Exception parseError) {


                    System.out.println("Error parsing line " + lineNumber + ", skipping it.");
                }
            }
        }


        return questions;
    }


    public void addQuestion(Question question) throws IOException {

        try (FileWriter writer = new FileWriter(questionsFilePath, true)) {
            String[] options = question.getOptions();
            String line = String.format("%s|%s|%s|%s|%s|%s%n",
                    question.getQuestionText(),
                    options[0], options[1], options[2], options[3],
                    question.getCorrectAnswer());
            writer.write(line);
        }
    }


    public void saveResult(String playerName, int score, int totalQuestions) throws IOException {
        
        try (FileWriter writer = new FileWriter(resultsFilePath, true)) {
            String timestamp = java.time.LocalDateTime.now().toString();
            String line = String.format("%s | %s | Score: %d/%d%n",
                    timestamp, playerName, score, totalQuestions);
            writer.write(line);
        }
    }
}
