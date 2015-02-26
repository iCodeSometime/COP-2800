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

package project5;

import javafx.application.Application;
import javafx.stage.Stage;

public class Project5 extends Application {
   @Override
   public void start(Stage primaryStage) {
       primaryStage.setTitle("Cars");
   }
           
   public static void main(String[] args) {
       launch(args);
   }
}

class Car {
    /*                           Member Variables                            */
    // The initial speed of the car
    private static final int DEFAULT_SPEED      = 0;
    // Any change in speed will be in increments of this number
    private static final int SPEED_QUANTIZATION = 5;
    
    private final int    yearModel; // The model year of the car
    private final String make;      // The make of the car
    
    private int speed; // The car's current speed
    /*                             Constructors                              */
    public Car(int _yearModel, String _make) {
        yearModel = _yearModel;
        make      = _make;
        speed     = DEFAULT_SPEED;
    }
    /*                               Accessors                               */
    public int    getYearModel() {return yearModel;}
    public String getMake()      {return make;}
    public int    getSpeed()     {return speed;}
    /*                            Public Methods                             */
    // Increase the speed of the car and return the new speed
    public int accelerate() {
        speed += SPEED_QUANTIZATION;
        return speed;
    }
    // Decrease the speed of the car and return the new speed, or 
    public int brake() throws UnsupportedOperationException {
        if (speed == 0) {
            throw new UnsupportedOperationException(
                    "Maybe you don't understand how a brake works...");
        } else {
            speed -= SPEED_QUANTIZATION;
        }
        return speed;
    }
}