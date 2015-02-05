/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    Test 1 (Stock market application)
 *  File:       User.java (data model for users)
 *
 *  The user data model will manage all user data. The main thing we will
 *  have to keep track of is the recent searches.
 *****************************************************************************/
package test1.model;

import java.util.LinkedList;
import java.util.function.Consumer;
import javafx.concurrent.Task;

public class User {
   /** Linked list containing all the recent stock searches. */
   private final LinkedList<String> recentStocks;
   /** Maximum number of stocks that are considered relevant. */
   private final int        maxStocks = 10;
   
   public User() {
      this(null);
   }
   public User(String name) {
      this.recentStocks = new LinkedList<String>();
      if (name != null) {
         
      }
   }
   /**
    * Will add a single stock to the recent stockList.
    * @param stock 
    */
   public void addStock(String stock) {
      recentStocks.push(stock);
      while (recentStocks.size() > maxStocks) {
         recentStocks.removeLast();
      }
   }
   
   /**
    * This function uses a callback to process each member of the list.
    * 
    * @param callback method to be called on each list member.
    */
   public void process(Consumer callback) {
      recentStocks.stream().forEach(callback);
   }
}
