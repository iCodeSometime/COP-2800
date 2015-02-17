/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    3 (Convert Temperatures)
 *
 *  A text box will be displayed, containing the temperatures 0-20 degrees
 *  Celsius, along with it's corresponding temperature in Fahrenheit.
 *  The user will then input the name of a file to output the table to.
 *****************************************************************************/

package project3;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Project3 {
   public static void main(String[] args) {
      TemperatureChart chart = new TemperatureChart();
      chart.addTemperatures();
      chart.display();
      chart.getFile();
      chart.saveFile();
   }
}

class TemperatureChart {
   /*                           Class Constants                              */
   private static final int START_TEMP = 0;
   private static final int END_TEMP   = 20;
   private static final int DISPLAY_HEIGHT = 20;
   private static final int DISPLAY_WIDTH  = 30;
           
   /*                          Member Variables                              */
   JTextArea   display = new JTextArea(DISPLAY_HEIGHT, DISPLAY_WIDTH);
   JScrollPane scroll  = new JScrollPane(display);
   
   File file;
   
   /*                            Constructors                                */
   public void TemperatureChart() {
      // Instantiate JTextArea and JScrollPane objects, and set defaults
      display.setEditable(false);
   }
   /*                           Public Methods                               */
   // Adds all the temperatures we are finding to the text area
   public void addTemperatures() {
       display.setText(getOutputText());
   }
   
   // Displays the chart
   public void display() {
       JOptionPane.showMessageDialog(null, scroll);
   }
   
   // Gets a filename from the user
   public void getFile() {
       String fileName;
       fileName = JOptionPane.showInputDialog("Please input the name of a file");
       // If the user canceled
       if (fileName == null) {
           System.exit(0);
       } else {
        file = new File(fileName);
       }
   }
   
   // Saves the chart to the specified file.
   public void saveFile() {
       try(PrintStream out = new PrintStream(file)) {
           out.print(getOutputText());
       } catch(IOException e) {
           System.err.println("There has been an error");
       }
   }
   
   // Returns the conversion for a single temperature
   private float getTemp(float celsiusTemp) {
      return celsiusTemp * 9 / 5 + 32;
   }
   private String getOutputText() {
       String returnString = "";
      DecimalFormat df = new DecimalFormat("00.00");
      for (int temp = START_TEMP; temp <= END_TEMP; temp++) {
         returnString += df.format(temp) + " degrees Celcius is equal to " + 
            df.format(getTemp(temp)) + " degrees Farenheit\r\n";
      }
       return returnString;
   }
}