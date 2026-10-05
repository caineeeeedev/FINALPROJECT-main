public class Question {
    String questionText;
    String options[];
    char correctAnswer;

    public Question(
            String questionText,
            String[] options,
            char correctAnswer
    ) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isCorrect(char givenAnswer) {
        return Character.toUpperCase(givenAnswer)
                == correctAnswer;
    }

    public void display() {
        System.out.println(
                Main.margin()
                        + "  "
                        + questionText
        );

        System.out.println();

        System.out.println(
                Main.margin()
                        + "       [A] "
                        + options[0]
        );

        System.out.println(
                Main.margin()
                        + "       [B] "
                        + options[1]
        );

        System.out.println(
                Main.margin()
                        + "       [C] "
                        + options[2]
        );

        System.out.println(
                Main.margin()
                        + "       [D] "
                        + options[3]
        );
    }
}