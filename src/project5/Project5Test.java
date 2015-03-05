/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    5 (Car Class)
 *  File:       Project5Test.java
 *
 *  This class will 
 *****************************************************************************/

package project5;

import javafx.stage.Stage;

/**
 *
 * @author ksc1077
 */
public class Project5Test extends Project5 {
   static {
      boolean assertionsEnabled = false;
      assert assertionsEnabled = true;
      if (!assertionsEnabled) {
         throw new RuntimeException(
            "This is a test class. Assertions must be enabled.");
      }
   }
   @Override
   public void start(Stage primaryStage) {
      this.primaryStage = primaryStage;
      this.primaryStage.setTitle("Cars Test");
      this.primaryStage.setHeight(90);
      this.primaryStage.setWidth(160);
      
      makeCar();
      initRootLayout();
      primaryStage.show();
      testGUIAccelerate();
      testGUIBrake();
      System.out.println(CarTest.getFailedAssertions() 
         + " failed GUI assertions");
   }
   
   private void testGUIAccelerate() {
      getCar().speed = 0;
      try {
         pushAccelerate();
      } catch (UnsupportedOperationException e) {}
      CarTest.fail(getCar().getSpeed() == Car.SPEED_QUANTIZATION,
         "Accelerating is not supported. "
         + "You should probably support that.");
      getCar().speed = Car.SPEED_OF_LIGHT - Car.SPEED_QUANTIZATION;
      try {
         pushAccelerate();
         CarTest.condescendingFail("Looks Like Einstein was wrong!!");
      } catch (UnsupportedOperationException e) {}
      CarTest.fail(getCar().getSpeed() == 
         Car.SPEED_OF_LIGHT - Car.SPEED_QUANTIZATION,
         "Looks like your code does weird stuff as it approaches c."
       + "Sounds like it could be a relatively bad error.");
   }
   
   private void testGUIBrake() {
      
      getCar().speed = Car.SPEED_QUANTIZATION;
      pushBrake();
      CarTest.fail(getCar().getSpeed() == 0,
         "Your brakes are broken. Good luck.");
      pushBrake();
      CarTest.condescendingFail(getCar().getSpeed() == 0, 
         "Awesome! You have magical brakes!");
   }
   
   public static void main(String[] args) {
      CarTest test = new CarTest();
      System.out.println(test.runAllTests() + " assertions failed in Car.");
      launch(args);
   }
}
class CarTest extends Car {
   private static int failedAssertions;
   public int runAllTests() {
      failedAssertions = 0;
      accelerate();
      brake();
      return failedAssertions;
   }
   /*                               Tests                                    */
   @Override
   public int accelerate() {
      speed = 0;
      try {
         super.accelerate();
      } catch (UnsupportedOperationException e) {}
         fail(speed == SPEED_QUANTIZATION, "Why is accelerating not supported?"
            + "\nYou should probably support that.");
      speed = SPEED_OF_LIGHT - SPEED_QUANTIZATION;
      try {
         super.accelerate();
         condescendingFail("Looks like Einstein was wrong!!");
      } catch(UnsupportedOperationException e) {}
      fail(speed == SPEED_OF_LIGHT - SPEED_QUANTIZATION,
         "Looks like your code does weird stuff as it approaches c."
       + "Sounds like it could be a relatively bad error.");
      return getSpeed();
   }
   @Override
   public int brake() {
      speed = SPEED_QUANTIZATION;
      super.brake();
      fail(getSpeed() == 0, "Your brakes are broken. Good luck.");
      super.brake();
      condescendingFail(getSpeed() == 0, "Awesome! You have magical brakes!");
      return getSpeed();
   }
   
   public static int getFailedAssertions() {return failedAssertions;}
   
   // Custom fail methods. 
   // Used to add humor and keep track of failed assertions.
   public static void condescendingFail(String message) {
      condescendingFail(false, message);
   }
   public static void condescendingFail(boolean passed, String message) {
      fail(passed, message + "\n...or there's just a problem with your code."
         + "\nBetter check it.");
   }
   public static void fail(String message) {
      fail(false, message);
   }
   public static void fail(boolean passed, String message) {
      if (!passed) {
         failedAssertions += 1;
      }
      assert passed : message;
   }
}
