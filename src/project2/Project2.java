/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    2 (Calculate BMI)
 *
 *  The user will input a height and a weight. The program will then 
 *  calculate the BMI and let the user know if they are underweight, 
 *  overweight, etc.
 *****************************************************************************/

package project2;

import javax.swing.JOptionPane;

public class Project2 {
   public static void main(String[] args) {
      // Instantiate a Person object and get the height and weight
      Person user = new Person();
      user.getDetails();
      
      // Display the BMI results
      user.displayBMI();
   }
}
class Person {
   /*                           Class Constants                             */
   // Beneath this BMI is underweight
   private static final double UNDER_WEIGHT   = 18.5;
   // Beneath this BMI is optimal weight
   private static final double OPTIMAL_WEIGHT = 25.0;
   // Beneath this BMI is overweight
   private static final double OVER_WEIGHT    = 30.0;
   // Base prompt to get the input from user
   private static final String INPUT_PROMPT   =
      "Please enter an integer ";
   
   /*                          Member Variables                              */
   private int height;
   private int weight;
    
   /*                           Public Methods                               */
   // Get the height and weight from the user
   public void getDetails() {
      height = getValue("height");
      weight = getValue("weight");
   }
   // Display the BMI and status
   public void displayBMI() {
      double BMI;       // The user's BMI
      String status; // The user's weight status
      BMI = calculateBMI(); 
      if (BMI < UNDER_WEIGHT) {
         status = "underweight";
      } else if (BMI < OPTIMAL_WEIGHT) {
         status = "at an optimal weight";
      } else if (BMI < OVER_WEIGHT) {
         status = "overweight";
      } else {
         status = "obese";
      }
      JOptionPane.showMessageDialog(null, "Your BMI is " + BMI +
         ". \nThis means that you are " + status);
   }
    
   /*                          Private Methods                               */
   private static int getValue(String item) {
      String  input;               // Used to store the input before processing
      String  units       = "";    // Units that go with the item we are finding
      boolean valid       = false; // Only true when a valid value is input
      int     returnValue = 0;     // Store the value to be returned
      
      // Find the units
      if (item.equalsIgnoreCase("height")) {
         units = "inches";
      } else if (item.equalsIgnoreCase("weight")) {
         units = "pounds";
      } else {
         JOptionPane.showMessageDialog(null, 
            "There has been an error. Exiting Program.");
         System.err.println("Error in function getValue(String item).");
         System.err.println("Function has received an invalid parameter.");
         System.err.println("Expected (height || weight) but received " + item);
         System.exit(1);
      }
      
      // Loop until we have a valid value
      while (!valid) {
         // Get a height from the user
         input = JOptionPane.showInputDialog(null, 
            INPUT_PROMPT + item + " in " + units);
         
         // Try to parse out the integer, or resolve the errors.
         try {
            returnValue = Integer.parseInt(input);
         } catch(NumberFormatException e) {
            if (input == null) {
                  System.exit(0);
            } else {
               JOptionPane.showMessageDialog(null, 
                  "The input was not an integer.");
               continue;
            }
         }
         
         // Make sure the integer is a valid height
         if (returnValue < 1) {
            JOptionPane.showMessageDialog(null, 
               "The input was too small.");
         } else {
            valid = true;
         }
      }
      return returnValue;
   }
   private double calculateBMI() {
      return weight * 703.0 / (height * height);
   }
}