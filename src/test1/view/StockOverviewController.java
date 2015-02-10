/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    Test 1 (Stock market application)
 *  File:       StockOverviewController.java (controller for stock overview)
 *
 *  The stock overview will act as a container that I will use to place my
 *  info cards. It's only element by default is a flowPane.
 *  The controllers main job will be to add the info cards as children
 *  of the flowPane, at which point they should be automatically organized.
 *****************************************************************************/
package test1.view;

import test1.view.card.*;

import java.io.IOException;
import java.util.LinkedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import test1.*;

/**
 * FXML Controller class
 */
public class StockOverviewController {
   private MainApp            mainApp;
   private LinkedList<String> stockList = new LinkedList<String>();
   
   @FXML
   private FlowPane rootFlowPane;
   
   
   /**
    * Initialize the controller class. This method is automatically
    * called after the fxml file has been loaded.
    */
   @FXML
   private void initialize() {
      loadSearchBar();
   }
   
   /**
    * Inserts the search bar into the top of the StockOverview page.
    */
   private void loadSearchBar() {
      try {
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainApp.class.getResource("view/card/Search.fxml"));
         AnchorPane searchCard = (AnchorPane)loader.load();
         SearchController controller = loader.getController();
         controller.setParent(this);
         
         rootFlowPane.getChildren().add(searchCard);
         
      } catch (IOException e) {
         System.err.println("There was an error in loading fxml for the search card.\n");
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   /**
    * Inserts a stock graph into the StockOverview page
    * 
    * @param stock The name of a stock symbol.
    */
  public void loadStockCard(String stock) {
      stockList.addFirst(stock);
      try {
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(MainApp.class.getResource("view/card/StockGraph.fxml"));
         AnchorPane stockGraph = (AnchorPane)loader.load();
         
         rootFlowPane.getChildren().add(stockGraph);
         StockGraphController controller = loader.getController();
         controller.setNameAndMainApp(mainApp, stock);
      } catch (IOException e) {
         System.err.println("There was an error in loading fxml for the stock card.\n");
         e.printStackTrace();
      }
   }
   
   /**
    * Is called by the main application to give a reference back to itself
    * 
    * @param mainApp a reference to the main application class
    */
   public void setMainApp(MainApp mainApp) {
      this.mainApp = mainApp;
   }
   
   /**
    * @return a reference to the flowPane (The main element in this file)
    */
   public FlowPane getRootFlowPane() {
      return rootFlowPane;
   }
   
   /**
    * @return a list of all Stocks currently displayed in our application
    */
   public LinkedList<String> getStockList() {      
      return stockList;
   }
}
