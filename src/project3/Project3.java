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

package project3;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Project3 {
   public static void main(String[] args) {
      TemperatureChart chart = new TemperatureChart();
      chart.addTemperatures();
   }
}

class TemperatureChart {
   /*                           Class Constants                              */
   private final int START_TEMP = 0;
   private final int END_TEMP   = 20;
   /*                          Member Variables                              */
   JTextArea   display = new JTextArea(20, 20);
   JScrollPane scroll  = new JScrollPane(display);
   /*                            Constructors                                */
   public void TemperatureChart() {
      // Instantiate JTextArea and JScrollPane objects, and set defaults
      //display = new JTextArea(20, 20);
      //scroll  = new JScrollPane(display);
      display.setEditable(false);
   }
   /*                           Public Methods                               */
   // Adds all the temperatures we are finding to the text area
   public void addTemperatures() {
      for (int temp = START_TEMP; temp <= END_TEMP; temp++) {
//         display.append(temp + " degrees Celcius is equal to + " + 
//            getTemp(temp) + " degrees Farenheit\n");
         display.append("hi");
      }
   }
   // Returns the conversion for a single temperature
   private int getTemp(int celsiusTemp) {
      return celsiusTemp * 9 / 5 + 32;
   }
}