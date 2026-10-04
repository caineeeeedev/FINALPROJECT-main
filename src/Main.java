import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class Main {

    static final String QUESTIONS_FILE = "data/questions.txt";
    static final String RESULTS_FILE = "data/results.txt";

    public static void main(String[] args) {

        if (!LoginSystemLogin.showLogin()) {
    return;
    }


        Scanner scanner = new Scanner(System.in);
        QuizFileHandler fileHandler = new QuizFileHandler(QUESTIONS_FILE, RESULTS_FILE);
  

    
        Logo.print();
        System.out.print("                                                        Type \"ENTER\" to Enter (\"QUIT\" to quitQUIT): ");
        String Enter = scanner.nextLine();


        if (Enter.equals("ENTER")){
        System.out.println();
        System.out.println("                                                        =================================================");
        System.out.println("                                                                WELCOME TO THE QuitiQUIZ PROGRAM");
        System.out.println("                                                        =================================================");

        String menuChoice;
        boolean running = true;

        while (running) {

            System.out.println("                                                            +---------------------------------------+");
            System.out.println("                                                            |               MAIN MENU               |");
            System.out.println("                                                            +---------------------------------------+");
            System.out.println("                                                            |                                       |");
            System.out.println("                                                            |   [1]  Add a new question             |");
            System.out.println("                                                            |   [2]  Take the quiz                  |");
            System.out.println("                                                            |   [3]  Exit                           |");
            System.out.println("                                                            |                                       |");
            System.out.println("                                                            +---------------------------------------+");
            System.out.print("                                                                     Choose an option (1 - 3): ");

            menuChoice = scanner.nextLine();

            switch(menuChoice) {

                case "1":
                    addNewQuestion(fileHandler, scanner);
                    System.out.println();
                    break;

                case "2": {
                    System.out.println();
                    System.out.println();
                    System.out.print("                                                            Enter your name: ");
                    String playerName = LoginSystemLogin.loggedInUsername;
                    

                    ArrayList<Question> questions;

                    try {
                        questions = fileHandler.loadQuestions();
                    } catch (FileNotFoundException e) {
                        System.out.println("Error: Could not find the questions file at '" + QUESTIONS_FILE + "'.");
                        System.out.println("Please make sure the file exists and try again.");
                        scanner.close();
                        return;
                    } catch (IOException e) {
                        System.out.println("Error: Something went wrong while reading the questions file.");
                        System.out.println("Details: " + e.getMessage());
                        scanner.close();
                        return;
                    }

                    if (questions.isEmpty()) {
                        System.out.println("No questions were found in the file. Exiting.");
                        scanner.close();
                        return;
                    }

                    QuizEngine engine = new QuizEngine(questions, scanner);
                    int finalScore = engine.run();

                    System.out.println("\n=========================================");
                    System.out.println(playerName + ", you scored " + finalScore + " out of " + questions.size() + "!");
                    System.out.println("=========================================");

                    // --- File handling + exception handling for saving results ---
                    try {
                        fileHandler.saveResult(playerName, finalScore, questions.size());
                        System.out.println("Your result has been saved to " + RESULTS_FILE);
                    } catch (IOException e) {
                        System.out.println("Warning: Could not save your result. Details: " + e.getMessage());
                    }//CATCH
                    break;
                }

                case "3":
                    System.out.println("THANKYOU FOR USING QuitiQUIZ");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.\n");
                    break;

        
            }//swich
        }//loop while
    }//if loop
    
        
    else if(Enter.equals("QUIT")){
        while(Enter.equals("QUIT")){
            System.out.println("Thank you for using the QuitiQUIZ Program");
            return;
        }//while
    }//elseIF
}// end of main


    private static void addNewQuestion(QuizFileHandler fileHandler, Scanner scanner) {
        System.out.println("\n--- Add a New Question ---");

        String questionText = readNonEmptyLine(scanner, "Enter a new question: ");
        String optionA = readNonEmptyLine(scanner, "Enter option A: ");
        String optionB = readNonEmptyLine(scanner, "Enter option B: ");
        String optionC = readNonEmptyLine(scanner, "Enter option C: ");
        String optionD = readNonEmptyLine(scanner, "Enter option D: ");

        char correctAnswer;
        while (true) {
            System.out.print("What is the correct answer? (A/B/C/D): ");
            String input = scanner.nextLine().trim();

            try {
                if (input.isEmpty()) {
                    throw new InvalidAnswerException("You didn't enter anything.");
                }

                char choice = Character.toUpperCase(input.charAt(0));

                if (choice != 'A' && choice != 'B' && choice != 'C' && choice != 'D') {
                    throw new InvalidAnswerException("'" + input + "' is not a valid option.");
                }

                correctAnswer = choice;
                break;

            } catch (InvalidAnswerException e) {
                System.out.println("Invalid answer: " + e.getMessage() + " Please enter A, B, C, or D.");
            }
        }

        String[] options = { optionA, optionB, optionC, optionD };
        Question newQuestion = new Question(questionText, options, correctAnswer);

        try {
            fileHandler.addQuestion(newQuestion);
            System.out.println("Your question was added successfully!");
        } catch (IOException e) {
            System.out.println("Error: Could not save your question. Details: " + e.getMessage());
        }
    }


    private static String readNonEmptyLine(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("This can't be empty. Please try again.");
            } else if (input.contains("|")) {
                System.out.println("Please don't use the '|' character. Please try again.");
            } else {
                return input;
            
            }
        }
    }
}

// Six Seven