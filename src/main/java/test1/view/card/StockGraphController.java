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

import javax.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.time.LocalDate;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javax.net.ssl.HttpsURLConnection;
import test1.*;

/**
 * FXML Controller class
 */
public class StockGraphController {
   // Reference to the main application
   private MainApp mainApp;
   private String  stockName;
   
   @FXML
   private Label stockLabel;
   @FXML
   private CategoryAxis xAxis;
   @FXML
   private NumberAxis yAxis;
   @FXML
   private LineChart<String, Number> stockChart;
   
   private static ObservableList<String> dateList;
   private LocalDate date = LocalDate.now();
   
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
      xAxis = new CategoryAxis(dateList);
      yAxis = new NumberAxis();
      // I don't have time to perfect the display of the axes, so I have
      // set them to be invisible in the fxml. 
      xAxis.setCategories(dateList);
      
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
      Task<String> task = new GetStockHistory();
      Thread th = new Thread(task);
      th.setDaemon(true);
      task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
         @Override
         public void handle(WorkerStateEvent event) {
            fillGraph(task.getValue());
         }
      });
      th.start();
      
   }
   
   /**
    * Plots the points on the graph. As this function will take some time to
    * parse out the JSON values, it should NOT be called from the main thread.
    * 
    * @param json JSON representation of the stock history. Should be in the same format as is used by the yahoo finance api.
    */
   private void fillGraph(String json) {
      JsonObject results;
      XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
      XYChart.Series<String, Number> reversedSeries = new XYChart.Series<String, Number>();
      JsonReader jReader = Json.createReader(new StringReader(json));
      JsonObject ob = jReader.readObject();
      jReader.close();
      ob = ob.getJsonObject("query");
      try {
         results = ob.getJsonObject("results");
      } catch (ClassCastException e) {
         System.err.println("The results were null for stock " + stockName);
         return;
      }
      JsonArray quotes = results.getJsonArray("quote");
      quotes.forEach(quote -> {
         JsonObject quoteObject = (JsonObject)quote;
         String dateString = quoteObject.getString("Date");
         if (dateString.substring(8).equals("01")) {
            dateString = getMonth(dateString.substring(5, 7)) + " " + dateString.substring(0, 4);
         }
         series.getData().add(new XYChart.Data(dateString,
            Double.parseDouble(quoteObject.getString("Close"))));
      });
      // For some reason I get errors when using FXCollections.reverse()
      // This should just reverse the list.
      series.getData().sort((Object value1, Object value2) -> 1);
      Platform.runLater(new Task() {
         @Override
         protected Boolean call() {
            if (Platform.isFxApplicationThread()) {
               return stockChart.getData().addAll(series);
            } else return false;
         }
      });
   }
   
   /**
    * Will get the daily stock history for the past year.
    * If it encounters a 504 error it will recursively call itself
    * up to five times. If it is still unable to make a connection, it will
    * throw the error to the calling function.
    */ 
   private class GetStockHistory extends Task<String> {
      int iteration = 0;
      /**
       * Override the abstract call method from Task
       * @return the JSON String received from Yahoo Finance
       * @throws IOException Tries to handle it, if it can't it throws it.
       */
      @Override
      protected String call() throws IOException {
         URL url;
         String json = "";
         String line;
         try {
            url = new URL(requestBuilder());
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            StringBuilder builder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
               new InputStreamReader(con.getInputStream()))) {
               while((line = reader.readLine()) != null) {
                  builder.append(line);
               }
               json = builder.toString();
            }
         } catch (IOException e) {
            if (e.toString().contains("504")) {
               // If it failed due to a 504 error, let's try again.
               // but only up to five times.
               if (iteration < 5) {
                  iteration++;
                  json = call();
               } else throw e;
            }
         }
         return json;
      }
   };
   
   /**
    * Builds the URL to query the Yahoo finance API for the appropriate stock
    * and date range.
    * 
    * @return A query for the Yahoo finance API
    */
   private String requestBuilder() {
      StringBuilder request = new StringBuilder();
      
      request.append("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%3D%22");
      request.append(stockName);
      request.append("%22%20and%20startDate%3D%22");
      request.append(date.minusYears(1).toString());
      request.append("%22%20and%20endDate%3D%22");
      request.append(date.toString());
      request.append("%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");
      
      return request.toString();
   }
   
   /**
    * A list containing the month, and year for every month in the past year.
    */
   private void getDateList() {
      ObservableList<String> localDateList = FXCollections.observableArrayList();
      for (int i = 12; i >= 0; i--) {
         LocalDate temp = date.minusMonths(i);
         localDateList.add(temp.getMonth().toString().substring(0, 3) + " " +
            temp.getYear());
      }
      dateList = localDateList;
   }
   /**
    * Takes in a two number representation of a month, and returns
    * the string value of the month. For example, for input 01, it would 
    * return JAN. For 02, FEB, etc.
    * 
    * @param month A two digit string representing a month
    * @return A month's three digit abbreviation.
    */
   private String getMonth(String month) {
      String monthName;
      switch (month) {
         case "01":
            monthName = "JAN";
            break;
         case "02":
            monthName = "FEB";
            break;
         case "03":
            monthName = "MAR";
            break;
         case "04":
            monthName = "APR";
            break;
         case "05":
            monthName = "MAY";
            break;
         case "06":
            monthName = "JUN";
            break;
         case "07":
            monthName = "JUL";
            break;
         case "08":
            monthName = "AUG";
            break;
         case "09":
            monthName = "SEP";
            break;
         case "10":
            monthName = "OCT";
            break;
         case "11":
            monthName = "NOV";
            break;
         case "12":
            monthName = "DEC";
            break;
         default:
            throw new IllegalArgumentException(month + " is not a valid month");
      }
      return monthName;
   }
   /**
    * Get the name of the stock listed on this info card
    * @return The name of the stock.
    */
   public String getStockName() {
      return stockName;
   }
}
