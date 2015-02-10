/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    Test 1 (Stock market application)
 *  File:       SearchController.java (controller for the search bar)
 *
 *  The search bar will validate that the input value is a valid symbol, and
 *  then load a stockGraph card for that symbol.
 *****************************************************************************/
package test1.view.card;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import test1.view.*;

import javafx.scene.layout.FlowPane;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 */
public class SearchController {
   StockOverviewController parent;
   @FXML
   Button searchButton;
   @FXML
   TextField searchField;
   
   /**
    * @param parent reference to the controller of the parent container.
    */
   public void setParent(StockOverviewController parent) {
      this.parent = parent;
   }
   
   /**
    * Event handler for the search button. Can also be called by pushing enter
    * while the text field has focus. This method will check with Yahoo Finance
    * to make sure data for the stock exists. If it does, it will call 
    * loadStockCard in its parent controller.
    */
   @FXML
   private void handleSearchButton() {
      String stockName = searchField.getText().toUpperCase();
      searchField.setText("");
      Task<Boolean> task = new checkStockName(stockName);
      Thread th = new Thread(task);
      th.setDaemon(true);
      task.setOnSucceeded(event -> {
         if (task.getValue()) {
            parent.loadStockCard(stockName);
         }
      });
      th.start();
      
      //parent.loadStockCard(stockName);
   }
   
   /**
    * Task class used to check that a stock name is valid.
    */
   private class checkStockName extends Task<Boolean> {
      private String stockName;
      private int    iteration;
      
      public checkStockName(String stockName) {
         this.stockName = stockName;
      }
      @Override
      protected Boolean call() {
         String json = "";
         try {
            json = getData();
         } catch(IOException e) {
            
         }
         if (json.contains("CompanyName"))
            return true;
         else {
            JOptionPane.showMessageDialog(null,
               stockName + " is not a valid stock name.");
            return false;
         }
      }
      
      protected String getData() throws IOException {
         URL url;
         String json = "";
         String line;
         try {
            url = new URL(requestBuilder(stockName));
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
                  json = getData();
               } else throw e;
               
            }
         }
         return json;
      }
   }
   private String requestBuilder(String stockName) {
      StringBuilder request = new StringBuilder();
      
      request.append("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.stocks%20where%20symbol%3D%22");
      request.append(stockName);
      request.append("%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");
      
      return request.toString();
   }
}
 