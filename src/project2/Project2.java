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
      user.printDetails();
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
      height = getValue("height");
      weight = getValue("weight");
   }
   // Print the person's height and weight
   public void printDetails() {
      JOptionPane.showMessageDialog(null, "The height is " + height +
              "\nThe weight is " + weight);
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
         JOptionPane.showMessageDialog(null, 
            "There has been an error in function 'getValue()'");
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
}