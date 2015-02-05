/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    Test 1 (Stock market application)
 *  File:       MainApp.java (Main application file)
 *
 *  This program will accept a stock symbol as well as shares owned and 
 *  an opening and closing price from the user. It will then compute the
 *  net gain or loss for the day. It will ask for a file from the user
 *  to save the information to.
 *****************************************************************************/
package test1;

import test1.view.*;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {
   private Stage primaryStage;
   private BorderPane rootLayout;
   
   /**
    * Override the virtual start method from the Application class
    * 
    * @param primaryStage Our reference to the application window.
    */
   @Override
   public void start(Stage primaryStage) {
      this.primaryStage = primaryStage;
      this.primaryStage.setTitle("Stock Market Application");
      
      initRootLayout();
      
      showStockOverview();
   }
   
   /**
    * Initialize the root layout (the 'stage')
    */
   private void initRootLayout() {
      // Try to load the layout from the fxml
      try {
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
         rootLayout = (BorderPane)loader.load();
         
         Scene scene = new Scene (rootLayout);
         primaryStage.setScene(scene);
         primaryStage.show();
      } catch (IOException e) {
         System.err.println("There was an error loading fxml from rootLayout");
         System.out.println("Here is the stack trace:\n");
         e.printStackTrace(System.err);
      } catch (Exception e) {
         System.out.println("There has been an error. Here is the stack trace:\n");
         e.printStackTrace(System.err);
      }
   }
   /**
    * Create our StockOverview container and 
    * place it in the center of the primaryStage
    */
   private void showStockOverview() {
      // Try to load the layout from fxml
      try {
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainApp.class.getResource("view/StockOverview.fxml"));
         AnchorPane stockOverview = (AnchorPane)loader.load();
         
         rootLayout.setCenter(stockOverview);
         
         // Give the controller a reference to the main app.
         StockOverviewController controller = loader.getController();
         controller.setMainApp(this);
      } catch (IOException e) {
         System.err.println("There was an error loading fxml from stockOverview");
         System.out.println("Here is the stack trace:\n");
         e.printStackTrace(System.err);
      } catch (Exception e) {
         System.out.println("There has been an error. Here is the stack trace:\n");
         e.printStackTrace(System.err);
      }
   }

   public static void main(String[] args) {
      launch(args);
   }
}
