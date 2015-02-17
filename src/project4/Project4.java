/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    4 (Rock Paper Scissors)
 *
 *  The user will choose either rock paper scissors. The program will then
 *  randomly select one, and will output the result of the competition.
 *****************************************************************************/

package project4;

import java.util.Random;
import javax.swing.JOptionPane;

public class Project4 {
   public static void main(String[] args) {
      RockPaperScissorsGame game = new RockPaperScissorsGame();
      game.initUserChoice();
      game.displayWinner();
   }
}
class RockPaperScissorsGame {
   /*                           Class Constants                              */
   public static final int ROCK     = 1;
   public static final int PAPER    = 2;
   public static final int SCISSORS = 3;
   
   public static final int TIE      = 0;
   public static final int USER_WIN = 1;
   public static final int COMP_WIN = 2;
   
   private static String[] options = new String[]{"Rock", "Paper", "Scissors"};
   
   /*                          Member Variables                              */
   private int userChoice;
   private int compChoice;
   
   /*                            Constructors                                */
   public RockPaperScissorsGame() {
      initCompChoice();
   }
   
   /*                           Public Methods                               */
   
   /**
    * Initialize the user's choice via a dialog box.
    */
   public void initUserChoice() {
      userChoice = JOptionPane.showOptionDialog(null, "Choose your weapon",
            "Choose Your Weapon", JOptionPane.OK_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, options, this) + 1;
   }
   
   /**
    * This method will display the winner of the match. To get the winner
    * without displaying the results, use <code>findWinner</code>.
    * It will also return the result as an integer. Valid results are:
    * TIE, USER_WIN, and COMP_WIN
    * 
    * @return The integer result of the match.
    */
   public void displayWinner() {
      
   }
   
   /**
    * This method will return the winner of the match. If you would like to
    * also display this information to the user, convenience method 
    * <code>displayWinner</code> has also been provided. Valid results are:
    * TIE, USER_WIN, and COMP_WIN
    * 
    * @return The integer result of the match.
    */
   public void findWinner() {
      
   }
   
   /*                          Private Methods                               */
   // Use a random number to get a computer choice. Return this value.
   private void initCompChoice() {
      Random rand = new Random();
      rand.setSeed(System.currentTimeMillis());
      compChoice = rand.nextInt(3) + 1;
   }
}
