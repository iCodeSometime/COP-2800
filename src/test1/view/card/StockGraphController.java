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

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import test1.*;

public class StockGraphController {
   // Reference to the main application
   private MainApp mainApp;
   private String  stockName;
   
   @FXML
   private Label stockLabel;
   @FXML
   private CategoryAxis horizontalAxis;
   @FXML
   private NumberAxis verticalAxis;
   @FXML
   private LineChart<String, Number> stockChart;
   
   private static ObservableList<String> dateList;
   
   public StockGraphController() {
      if (dateList == null) {
         getDateList();
      }
   }
   
   /**
    * Initialize the controller class. This method is automatically
    * called after the fxml file has been loaded.
    */
   @FXML
   private void initialize() {
      horizontalAxis.setCategories(dateList);
      verticalAxis = new NumberAxis();
      stockChart = new LineChart<String, Number>(horizontalAxis, verticalAxis);
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
      createGraph();
   }
   /**
    * Creates the line graph in it's stock card.
    */
   private void createGraph() {
      Random rand = new Random();
      for (int i = 0; i < 5; i++) {
         if (stockChart != null) {
            //stockChart.getData().add(new XYChart.Data(i, rand.nextInt()));
         } else {
            System.err.println("stockChart is still null D:");
         }
      }
   }
   
   /**
    * 
    */
   private void getDateList() {
      ObservableList<String> localDateList = FXCollections.observableArrayList();
      LocalDate date;
      date = LocalDate.now();
      for (int i = 4; i >= 0; i--) {
         LocalDate temp = date.minusDays(i);
         localDateList.add(temp.getMonth().toString().substring(0, 3) +
            " " + temp.getDayOfMonth());
      }
      dateList = localDateList;
   }
}
