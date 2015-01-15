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
   // Base prompt to get the input from user
   private static final String INPUT_PROMPT =
      "Please enter an integer ";
   /*                          Member Variables                              */
   private int height;
   private int weight;
    
   /*                           Public Methods                               */
   // Get the height and weight from the user
   public void getDetails() {
      try {
        height = getValue("height");
        weight = getValue("weight");
      } catch(IllegalArgumentException e) {
          JOptionPane.showMessageDialog(null,
             "There has been an error. Exiting.");
          System.err.println("getValue was passed an illegal argument.\n" + e);
          System.exit(1);
      }
   }
   // Display the BMI and status
   public void displayBMI() {
      double BMI;       // The user's BMI
      String status;    // The user's weight status
      String formatBMI; // Used to format the BMI
      BMI = calculateBMI();
      if (BMI < 18.5) {
         status = "underweight";
      } else if (BMI < 25.0) {
         status = "at an optimal weight";
      } else if (BMI < 30.0) {
         status = "overweight";
      } else {
         status = "obese";
      }
      formatBMI = new java.text.DecimalFormat("#.##").format(BMI);
      JOptionPane.showMessageDialog(null, "Your BMI is " + formatBMI +
         ". \nThis means that you are " + status);
   }
    
   /*                          Private Methods                               */
   private int getValue(String item) {
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
          throw new IllegalArgumentException(item + " is not a valid argument");
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