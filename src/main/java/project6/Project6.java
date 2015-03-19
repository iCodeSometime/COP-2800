/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    6 (Rainfall Class)
 *
 *  The user will input rainfall data for each month, and the program will do
 *  some calculations and display information based on the data entered.
 *****************************************************************************/

package project6;

import javafx.stage.*;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;

public class Project6 extends Application {

   Stage     primaryStage;
   SplitPane rootLayout;
   GridPane  left;
   GridPane  right;

   @Override
   public void start(Stage primaryStage) {
      this.primaryStage = primaryStage;
      this.primaryStage.setTitle("Rainfall Analysis");
      this.primaryStage.setWidth(450);

      initRootLayout();
      primaryStage.show();
   }

   private void initRootLayout() {
      rootLayout = new SplitPane();
      left = new GridPane();
      right = new GridPane();
      rootLayout.getItems().addAll(new AnchorPane(left), new AnchorPane(right));
      
      // Initialize each of the months in the left pane
      for (int i = 0; i < 12; i++) {
         left.add(new Label(getMonth(i) + ": "), 0, i);
         TextField tField = new TextField();
         tField.setId(getMonth(i) + "Input");
         tField.promptTextProperty().set("0");
         tField.onKeyTypedProperty().set(event -> monthEventHandler(event));
         tField.onKeyReleasedProperty().set(event -> monthEventHandler(event));

         left.add(tField, 1, i);
      }
      
      // Add the info labels to the right
      right.add(new Label("Total Rainfall: "), 0, 0);
      right.add(new Label("Average Rainfall: "), 0, 1);
      right.add(new Label("Highest Month: "), 0, 2);
      right.add(new Label("Lowest Month: "), 0, 3);
      
      // Add the data labels to the right
      Label label = new Label("0");
      label.setId("totalRainfall");
      right.add(label, 1, 0);
      label = new Label("0");
      label.setId("averageRainfall");
      right.add(label, 1, 1);
      label = new Label("0");
      label.setId("mostRainfall");
      right.add(label, 1, 2);
      label = new Label("0");
      label.setId("leastRainfall");
      right.add(label, 1, 3);
      
      // Set the scene
      primaryStage.setScene(new Scene(rootLayout));
   }

   private void monthEventHandler(KeyEvent event) {
      if (event.getCharacter().matches("\\d") ||
          event.getCode() == KeyCode.BACK_SPACE) {
         // Key Typed events call the handler before changing the data,
         // so we will do it ourself, and consume the event so it
         // doesn't get appended twice.
         if (event.getEventType() == KeyEvent.KEY_TYPED) {
            TextField field = (TextField)event.getSource();
            field.appendText(event.getCharacter());
            event.consume();
         }
         Label total   = (Label)right.lookup("#totalRainfall");
         Label average = (Label)right.lookup("#averageRainfall");
         Label least   = (Label)right.lookup("#leastRainfall");
         Label most    = (Label)right.lookup("#mostRainfall");
         
         int accum = 0;
         int max = 0;
         int min = 0;
         for (int i = 0; i < 12; i++) {
            TextField field = 
               (TextField)left.lookup("#" + getMonth(i) + "Input");
            try {
               int temp = Integer.parseInt(field.textProperty().get());
               accum += temp;
               if (i == 0) {
                  max = min = temp;
               } else if (temp > max) {
                  max = temp;
               } else if (temp < min) {
                  min = temp;
               }
            } catch(NumberFormatException e) {
               if ((field.textProperty().get().equals(""))) {
                  min = 0;
               } else {
                  throw new RuntimeException(
                     "There's something wrong with the character validation");
               }
            }
         }
         total.textProperty().set(Integer.toString(accum));
         average.textProperty().set(Integer.toString(accum / 12));
         most.textProperty().set(Integer.toString(max));
         least.textProperty().set(Integer.toString(min));
      } else if (event.getEventType() == KeyEvent.KEY_TYPED) {
         event.consume();
      }
   }

   private String getMonth(int num) {
      String returnString;
      switch (num) {
         case 0:
            returnString = "January";
            break;
         case 1:
            returnString = "February";
            break;
         case 2:
            returnString = "March";
            break;
         case 3:
            returnString = "April";
            break;
         case 4:
            returnString = "May";
            break;
         case 5:
            returnString = "June";
            break;
         case 6:
            returnString = "July";
            break;
         case 7:
            returnString = "August";
            break;
         case 8:
            returnString = "September";
            break;
         case 9:
            returnString = "October";
            break;
         case 10:
            returnString = "November";
            break;
         case 11:
            returnString = "December";
            break;
         default:
            returnString = "error";
      }
      return returnString;
   }
   
   public static void main(String[] args) {
      launch(args);
   }
}
