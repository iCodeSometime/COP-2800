/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    5 (Car Class)
 *
 *  The user will provide a make and model for a car. They will then be
 *  presented with an interface to brake and accelerate. The car will increase
 *  or decrease it's speed by five MPH, while observing all relevant laws of
 *  physics.
 *****************************************************************************/

package project5;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class Project5 extends Application {
   private Stage primaryStage;
   private Car   car;
   @Override
   public void start(Stage primaryStage) {
      this.primaryStage = primaryStage;
      this.primaryStage.setTitle("Cars");
      this.primaryStage.setHeight(150);
      this.primaryStage.setWidth(150);
      
      getCar();
      
      initRootLayout();
   }
   
   private void getCar() {
      boolean valid = false;
      String make = JOptionPane.showInputDialog(null,
         "Please enter the make of the car.");
      if (make == null) {
         System.exit(0);
      }
      String model = JOptionPane.showInputDialog(null,
         "Please enter the model year of the car.");
      car = new Car(make, model);
   }
   
   private void initRootLayout() {
      BorderPane rootLayout = new BorderPane();
      FlowPane   buttonPane = new FlowPane();
      FlowPane   labelPane  = new FlowPane();
      Button     accelerate = new Button();
      Button     brake      = new Button();
      Label      speed      = new Label();
      
      buttonPane.setHgap(10);
      labelPane.setMinHeight(15);
      
      speed.textProperty().set(Integer.toString(car.getSpeed()));
      accelerate.setText("accelerate");
      brake.setText("brake");
      accelerate.setOnAction((event) ->
            speed.textProperty().set(Integer.toString(car.accelerate())));
      brake.setOnAction((event) -> 
            speed.textProperty().set(Integer.toString(car.brake())));
      
      buttonPane.getChildren().add(brake);
      buttonPane.getChildren().add(accelerate);
      labelPane.getChildren().add(speed);
      rootLayout.setBottom(buttonPane);
      rootLayout.setTop(labelPane);
      primaryStage.setScene(new Scene(rootLayout));
      primaryStage.show();
   }
           
   public static void main(String[] args) {
      launch(args);
   }
}

class Car {
   /*                           Member Variables                            */
   // The initial speed of the car
   protected static final int DEFAULT_SPEED      = 0;
   // Any change in speed will be in increments of this number
   protected static final int SPEED_QUANTIZATION = 5;
   // Speed of light in mph: 670,616,629
   public static final int SPEED_OF_LIGHT        = 670616629;
   
   private final String modelYear;
   private final String make;
   // Protected instead of private to make testing easier
   protected int speed; 
   /*                             Constructors                              */
   /**
    * Constructs a car from the make and model year
    * 
    * @param make      non null String representing the car's make
    * @param modelYear non null String representing the car's model year
    */
   public Car(String make, String modelYear) {
       this.modelYear = modelYear;
       this.make      = make;
       speed          = DEFAULT_SPEED;
   }
   public Car(){
       modelYear = "";
       make      = "";
   }
   /*                               Accessors                               */
   public String getYearModel() {return modelYear;}
   public String getMake()      {return make;}
   public int    getSpeed()     {return speed;}
   
   /*                            Public Methods                             */
   /**
    * Increase the speed of the car.
    * The speed is increased by the quantization factor. The speed must remain
    * lower than the speed of light. If accelerating will increase the speed
    * beyond the speed of light, an UnsupportedOperationException 
    * will be thrown.
    * 
    * @return the new speed
    * @throws UnsupportedOperationException if surpassing the speed of light
    */
   public int accelerate() {
      if (speed + SPEED_QUANTIZATION >= SPEED_OF_LIGHT) {
         throw new UnsupportedOperationException(
            "Nice try, Einstein. Ever heard of relativity?");
      }
      speed += SPEED_QUANTIZATION;
      return speed;
   }
   /**
    * Decrease the speed of the car.
    * The speed is decreased by the quantization factor. The speed will not
    * drop below zero.
    * 
    * @return the new speed
    */
   public int brake() {
      if (speed != 0) {
         speed -= SPEED_QUANTIZATION;
      }
      return speed;
   }
}