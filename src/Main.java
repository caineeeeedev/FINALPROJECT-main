// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
   static final String QUESTIONS_FILE = "data/questions.txt";
   static final String RESULTS_FILE = "data/results.txt";

   public Main() {
   }

   public static void main(String[] var0) {
      if (LoginSystemLogin.showLogin()) {
         Scanner var1 = new Scanner(System.in);
         QuizFileHandler var2 = new QuizFileHandler("data/questions.txt", "data/results.txt");
         Logo.print();
         System.out.print("                                                        Type \"ENTER\" to Enter (\"QUIT\" to quitQUIT): ");
         String var3 = var1.nextLine();
         if (var3.equals("ENTER")) {
            System.out.println();
            System.out.println("                                                        =================================================");
            System.out.println("                                                                WELCOME TO THE QuitiQUIZ PROGRAM");
            System.out.println("                                                        =================================================");
            boolean var5 = true;

            while(var5) {
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
               switch (var1.nextLine()) {
                  case "1":
                     addNewQuestion(var2, var1);
                     System.out.println();
                  case "2":
                     System.out.println();
                     System.out.println();
                     String var8 = LoginSystemLogin.loggedInUsername;

                     ArrayList var9;
                     try {
                        var9 = var2.loadQuestions();
                     } catch (FileNotFoundException var14) {
                        System.out.println("Error: Could not find the questions file at 'data/questions.txt'.");
                        System.out.println("Please make sure the file exists and try again.");
                        var1.close();
                        return;
                     } catch (IOException var15) {
                        System.out.println("Error: Something went wrong while reading the questions file.");
                        System.out.println("Details: " + var15.getMessage());
                        var1.close();
                        return;
                     }

                     if (var9.isEmpty()) {
                        System.out.println("No questions were found in the file. Exiting.");
                        var1.close();
                        return;
                     }

                     QuizEngine var10 = new QuizEngine(var9, var1);
                     int var11 = var10.run();
                     System.out.println("\n=========================================");
                     System.out.println(var8 + ", you scored " + var11 + " out of " + var9.size() + "!");
                     System.out.println("=========================================");

                     try {
                        var2.saveResult(var8, var11, var9.size());
                        System.out.println("Your result has been saved to data/results.txt");
                     } catch (IOException var13) {
                        System.out.println("Warning: Could not save your result. Details: " + var13.getMessage());
                     }
                     break;
                  case "3":
                     System.out.println("THANKYOU FOR USING QuitiQUIZ");
                     var5 = false;
                     break;
                  default:
                     System.out.println("Invalid choice. Please enter 1, 2, or 3.\n");
               }
            }
         } else if (var3.equals("QUIT") && var3.equals("QUIT")) {
            System.out.println("Thank you for using the QuitiQUIZ Program");
            return;
         }

      }
   }

   private static void addNewQuestion(QuizFileHandler var0, Scanner var1) {
      System.out.println("\n--- Add a New Question ---");
      String var2 = readNonEmptyLine(var1, "Enter a new question: ");
      String var3 = readNonEmptyLine(var1, "Enter option A: ");
      String var4 = readNonEmptyLine(var1, "Enter option B: ");
      String var5 = readNonEmptyLine(var1, "Enter option C: ");
      String var6 = readNonEmptyLine(var1, "Enter option D: ");

      char var7;
      while(true) {
         System.out.print("What is the correct answer? (A/B/C/D): ");
         String var8 = var1.nextLine().trim();

         try {
            if (var8.isEmpty()) {
               throw new InvalidAnswerException("You didn't enter anything.");
            }

            char var9 = Character.toUpperCase(var8.charAt(0));
            if (var9 != 'A' && var9 != 'B' && var9 != 'C' && var9 != 'D') {
               throw new InvalidAnswerException("'" + var8 + "' is not a valid option.");
            }

            var7 = var9;
            break;
         } catch (InvalidAnswerException var12) {
            System.out.println("Invalid answer: " + var12.getMessage() + " Please enter A, B, C, or D.");
         }
      }

      String[] var13 = new String[]{var3, var4, var5, var6};
      Question var14 = new Question(var2, var13, var7);

      try {
         var0.addQuestion(var14);
         System.out.println("Your question was added successfully!");
      } catch (IOException var11) {
         System.out.println("Error: Could not save your question. Details: " + var11.getMessage());
      }

   }

   private static String readNonEmptyLine(Scanner var0, String var1) {
      while(true) {
         System.out.print(var1);
         String var2 = var0.nextLine().trim();
         if (var2.isEmpty()) {
            System.out.println("This can't be empty. Please try again.");
         } else {
            if (!var2.contains("|")) {
               return var2;
            }

            System.out.println("Please don't use the '|' character. Please try again.");
         }
      }
   }
}
