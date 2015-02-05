/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    Test 1 (Stock market application)
 *  File:       StockGraphController.java (controller for a single stock card)
 *
 *  The StockGraph info card is a basic info card containing only
 *  the name of a single stock and it's associated graph.
 *****************************************************************************/
package test1.view.card;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import test1.*;

public class StockGraphController {
   // Reference to the main application
   private MainApp mainApp;
   private String  stockName;
   
   @FXML
   private Label stockLabel;
   
   /**
    * Initialize the controller class. This method is automatically
    * called after the fxml file has been loaded.
    */
   @FXML
   private void initialize() {
   }
   
   /**
    * This will be called by initializing class to pass a reference to the
    * main application as well as the name of the stock to be displayed.
    * 
    * @param mainApp   Reference to the main application
    * @param stockName The name of a single stock.
    */
   public void setNameAndMainApp(MainApp mainApp, String stockName) {
      this.mainApp   = mainApp;
      this.stockName = stockName;
      try {
         stockLabel.setText(stockName);
      } catch (Exception e) {
         System.err.println("there was an issue setting the stockLabel text.\n");
         e.printStackTrace();
      }
   }
}
