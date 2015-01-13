/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    1 (Average Three Integer Test Scores)
 *
 *  The user will input three integer test scores into a dialog box,
 *  which will then be averaged. The result will be displayed as
 *  a double precision decimal number
 *****************************************************************************/

package project1;

import javax.swing.JOptionPane;

public class Project1 {
   public static void main(String[] args) {
      // Instantiate the Grades class and get the scores
      Grades testGrades = new Grades();
      testGrades.getScores();
      
      // Average the scores and display the result
      testGrades.displayAverage();
   }
}

class Grades { // Class to store and process the test grades
       
   /*                          Global Constants                             */
   // Maximum allowed value
   private static final int    MAX_GRADE   = 100; 
   // Number used to record a skipped value
   private static final int    SKIP_NUM    = -1;
   // Minimum allowed value
   private static final int    MIN_GRADE   = 0;   
   // Number of scores accepted
   private static final int    SCORE_NUM   = 3;
   // Query presented to the user
   private static final String INPUT_QUERY = 
         "Please input a test score -- (Cancel to abort)";
   // Used to inform the user of an invalid entry.
   private static final String INPUT_ERROR = 
         "Please make sure the test score is an integer between " +
         MIN_GRADE + " and " + MAX_GRADE;
   
   /*                          Member Variables                             */
   private int[] score = new int[SCORE_NUM]; // All of the test scores
   
   /*                           Public Methods                              */
   // Gets all of the scores from the user
   public void getScores() { 
      for (int test = 0; test < SCORE_NUM; test++) {
         score[test] = getScore();
      }
   }
   // Averages the test scores and displays the result
   public void displayAverage() {
      JOptionPane.showMessageDialog(null, calcAverage()); 
   }
   
   /*                           Private Methods                              */
   // Gets and tests a single valid test score
   private static int getScore() {
      int    returnInt = 0;     // Integer to return to caller
      String input;             // Stores the input before processing
      boolean valid    = false; // Only true after a valid score is entered
      boolean skip     = false; // True if the test is being skipped 
      // Loop until we have a valid score
      while (!valid) {
         // Get a score from the user, and parse its value
         input = JOptionPane.showInputDialog(INPUT_QUERY);
         try {
            returnInt = Integer.parseInt(input);
         } catch (NumberFormatException e) {
            if (input == null) {
               System.exit(0);
            } else if (input.isEmpty()) {
               JOptionPane.showMessageDialog(null,
                  "The field was blank. Skipping...");
               returnInt = SKIP_NUM;
               skip = true;
            }
            else {
               JOptionPane.showMessageDialog(null, 
                  "The input was not an integer. " + INPUT_ERROR);
               continue;
            }
         }
         
         // Make sure the score is between the min and max grades
         if (returnInt >= MIN_GRADE && returnInt <= MAX_GRADE || skip ) {
            valid = true;
         } else {JOptionPane.showMessageDialog(null, 
              "The input was not in the correct range. " + INPUT_ERROR);
         }
      }
      return returnInt;
   }
   private double calcAverage() {
      int accum = 0;
      int skips = 0;
      for (int test = 0; test < SCORE_NUM; test++) {
         if (score[test] != SKIP_NUM) {
            accum += score[test];
         } else {
            skips += 1;
         }
      }
      return (double) accum / (SCORE_NUM - skips);
   }
}


