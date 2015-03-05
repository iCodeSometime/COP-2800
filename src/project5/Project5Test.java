/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    5 (Car Class)
 *  File:       Project5Test.java
 *
 *  This class contains all unit tests for project 5. I manually checked the
 *  user interface to ensure the buttons functioned properly, but did not test
 *  it extensively, as the event handlers are merely wrappers for the 
 *  functions I am testing here.
 *****************************************************************************/

package project5;

/**
 *
 * @author ksc1077
 */
public class Project5Test {
   static {
      boolean assertionsEnabled = false;
      assert assertionsEnabled = true;
      if (!assertionsEnabled) {
         throw new RuntimeException(
            "This is a test class. Assertions must be enabled.");
      }
   }
   public static void main(String[] args) {
      CarTest test = new CarTest();
      System.out.println(test.runAllTests() + " assertions were failed.");
   }
}
class CarTest extends Car {
   private int failedAssertions;
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
      } catch (UnsupportedOperationException e) {
         fail("Why is accelerating not supported?"
            + "\nYou should probably support that.");
      }
      speed = SPEED_OF_LIGHT - SPEED_QUANTIZATION;
      try {
         super.accelerate();
         condescendingFail("Looks like Einstein was wrong!!");
      } catch(UnsupportedOperationException e){}
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
   
   /*          My own snarky custom fail method. Just for fun.               */
   public void condescendingFail(String message) {
      condescendingFail(false, message);
   }
   public void condescendingFail(boolean passed, String message) {
      fail(passed, message + "\n...or there's just a problem with your code."
         + "\nBetter check it.");
   }
   public void fail(String message) {
      fail(false, message);
   }
   public void fail(boolean passed, String message) {
      if (!passed) {
         failedAssertions += 1;
      }
      assert passed : message;
   }
}
