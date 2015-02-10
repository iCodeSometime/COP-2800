/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    Test 1 (Stock market application)
 *  File:       RootLayout.java (controller for the root layout)
 *
 *  The sole job of the root layout in this application is to house the
 *  menu bar. Thus, the sole purpose of the root layout controller is
 *  to provide event handlers for the menu bar.
 *****************************************************************************/
package test1.view;

import test1.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.json.*;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 */
public class RootLayoutController {
   private MainApp mainApp;
   File saveFile;
   @FXML
   private BorderPane rootLayout;

   /**
    * Is called by the main application to provide a reference back to itself
    * @param mainApp a reference to the main application
    */
   public void setMainApp(MainApp mainApp) {
      this.mainApp = mainApp;
   }
   
   /**
    * Event handler for the save button. Also responds to "Ctrl+S"
    * Will save key information about stocks to a file specified by the user.
    * If the user has previously specified a file, will not ask again.
    */
   @FXML
   private void handleSave() {
      // Get a file from the user.
      if (saveFile == null) {
         getFile();
      }
      // Since we will be appending to the file later, we need to make sure it
      // is empty now.
      saveFile.delete();
      StockOverviewController controller = mainApp.getStockOverviewLoader().getController();
      controller.getStockList().forEach(stockName -> {
      Task<String> task = new GetStockQuote(stockName);
      Thread th = new Thread(task);
      // Do not cause the exit button to terminate this thread. Save anyways.
      th.setDaemon(false);
      task.setOnSucceeded((WorkerStateEvent event) -> {
         appendToFile(task.getValue());
      });
      th.start();
      });
   }
   
   /**
    * Event handler for the Save As button. Also responds to "Ctrl+Shift+S"
    * Will save key information about stocks to a file specified by the user.
    * This function will ignore a previously specified file.
    */
   @FXML
   private void handleSaveAs() {
      getFile();
      if (saveFile != null) {
         handleSave();
      }
   }
   
   /**
    * Event handler for the about button. Also responds to "Ctrl+A"
    * Provides a short description of the program.
    */
   @FXML
   private void handleAbout() {
      Thread th = new Thread(new Task() {
         @Override
         protected Boolean call() {
            JOptionPane.showMessageDialog(null,
         "This is a stock market application, written entirely in javafx 8.\n"
       + "In order to fully meet the requirements set forth in the guidelines\n"
       + "in addition to what I have done extra, there is a test button.\n"
       + "(also accessible through Ctrl+T). In this test mode the application\n"
       + "will no longer query the yahoo finance api for information and will\n"
       + "instead ask the user to input data.");
            return true;
         }
      });
      th.setDaemon(true);
      th.start();
   }
   
   /**
    * Event handler for the Test button. Also responds to "Ctrl+T"
    * Asks the user for information about a stock, and outputs data
    * derived from that. Will NOT query Yahoo finance for information.
    */
   @FXML
   private void handleTest() {
      Thread th = new Thread (new Task<Boolean>() {
         String stockName;
         Double openPrice;
         Double closePrice;
         int    sharesOwned;
         String glMessage;
         
         @Override
         protected Boolean call() {
            DecimalFormat fmt = new DecimalFormat("$,###.00");
            try {
               stockName  = JOptionPane.showInputDialog("Please input a stock name.");
               if (stockName == null) return false;
               try {
                  openPrice  = Double.parseDouble(
                     JOptionPane.showInputDialog("Please input the opening price."));
                  closePrice = Double.parseDouble(
                     JOptionPane.showInputDialog("Please input the closing price."));
                  sharesOwned = Integer.parseInt(
                     JOptionPane.showInputDialog("Please input the number of shares."));
               } catch(NullPointerException e) {
                  return false;
               } catch (Exception e) {
                  JOptionPane.showMessageDialog(null, 
                     "Invalid entry. Returning to main Application.");
                  return false;
               }
            } catch (NullPointerException e) {
               return false;
            }
            if (closePrice > openPrice) {
               glMessage = "gained ";
            } else {
               glMessage = "lost ";
            }
            JOptionPane.showMessageDialog(null,
               "Stock: " + stockName +
               "\nOpening Price: "    + fmt.format(openPrice) +
               "\nClosing Price: "    + fmt.format(closePrice) +
               "\nNumber of Shares: " + sharesOwned +
               "\nOpening Value: "    + fmt.format(openPrice * sharesOwned) +
               "\nClosing Value: "    + fmt.format(closePrice * sharesOwned) +
               "\nGain/Loss: "        + "Your portfolio " + glMessage +
               fmt.format(Math.abs(openPrice - closePrice) * sharesOwned) +
               " in value."
            );
            return true;
         }
      });
      th.setDaemon(true);
      th.start();
   }
   
   /**
    * Uses a system file chooser to get a file.
    */
   private void getFile() {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Choose Save File");
      fileChooser.getExtensionFilters().add(
         new ExtensionFilter("Stock Market Files", "*.stock"));
      saveFile = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
   }
   
   /**
    * Appends basic information about a stock from it's JSON description.
    * JSON must be in the same format as that returned from 
    * yahoo.finance.historicaldata, with only one quote member.
    * 
    * @param json a JSON string formated like yahoo.finance.historicaldata
    */
   private void appendToFile(String json) {
      JsonReader jReader = Json.createReader(new StringReader(json));
      JsonObject ob = jReader.readObject();
      JsonObject quote = ob.getJsonObject("query")
                           .getJsonObject("results")
                           .getJsonObject("quote");
      try (PrintStream out = new PrintStream(
         new FileOutputStream(saveFile, true))) {
         DecimalFormat fmt = new DecimalFormat("$,###.00");
         out.println("Stock: " + quote.getString("Symbol"));
         out.println("Opening Price: " + fmt.format(Double.parseDouble(quote.getString("Open"))));
         out.println("Closing Price: " + fmt.format(Double.parseDouble(quote.getString("Close"))));
      } catch (FileNotFoundException e) {
         System.err.println("The file " + saveFile + " does not exist.");
      }
   }
   
   /**
    * A task used to get a stock quote from Yahoo Finance.
    */
   private class GetStockQuote extends Task<String> {
      int iteration = 0;
      String stockName;
      public GetStockQuote(String stockName) {
         this.stockName = stockName;
      }
      
      /**
       * Override the abstract call method in Task.
       * 
       * @return The JSON we receive
       * @throws IOException We attempt to resolve conflicts,
       *         but throw them if we can not
       */
      @Override
      protected String call() throws IOException {
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
                  json = call();
               } else throw e;
            }
         }
         return json;
      }
   };
   
   /**
    * Builds our Yahoo Finance request.
    * @param stockName The name of the stock we want information on
    * @return The URL for our request
    */
   private String requestBuilder(String stockName) {
      StringBuilder request = new StringBuilder();
      LocalDate date = LocalDate.now().minusDays(1);
         
      request.append("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.historicaldata%20where%20symbol%3D%22");
      request.append(stockName);
      request.append("%22%20and%20startDate%3D%22");
      request.append(date.toString());
      request.append("%22%20and%20endDate%3D%22");
      request.append(date.toString());
      request.append("%22&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");
         
      return request.toString();
   }
   
}
