/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    6 (Rainfall Class)
 *
 *  The user will input rainfall data for each month, and the program will
 *  do some calculations and display information based on the data entered.
 *****************************************************************************/

package project6;

import javafx.stage.*;
import javafx.application.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        setEventHandlers();
        primaryStage.show();
    }
    
    private void initRootLayout() {
        rootLayout = new SplitPane();
        rootLayout.getItems().addAll(new AnchorPane(left), new AnchorPane(right));
        left = new GridPane();
        for (int i = 0; i < 12; i++) {
            left.add(new Label(getMonth(i) + ": "), 0, i);
            left.add(new TextField(), 1, i);
        }
        right = new GridPane();
        right.add(new Label("Total Rainfall: "), 0, 0);
        right.add(new Label("Average Rainfall: "), 0, 1);
        right.add(new Label("Highest Month: "), 0, 2);
        right.add(new Label("Lowest Month: "), 0, 3);
        Label label = new Label();
        label.setId("totalRainfall");
        right.add(label, 1, 0);
        label = new Label();
        label.setId("averageRainfall");
        right.add(label, 1, 1);
        label = new Label();
        label.setId("hightestMonth");
        right.add(label, 1, 2);
        label = new Label();
        label.setId("lowestMonth");
        right.add(label, 1, 3);
        primaryStage.setScene(new Scene(rootLayout));
    }
    
    private void setEventHandlers() {
        
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
