/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    Test 1 (Stock market application)
 *  File:       StockList.java (data type to manage recent searches)
 *
 *  The StockList class will extend the LinkedList datatype and will allow
 *  me to use it similar to a stack. It will also manage it's size,
 *  deleting nodes that are no longer relevant.
 *****************************************************************************/
package test1.model.util;

public class StockList extends java.util.LinkedList {
   /** The number of nodes I believe are relevant. */
   private final int numberToKeep = 10;
   
   /**
    * Overriding the default push Method. This method will make sure the list
    * is not longer than the specified length and drop any excess nodes.
    * 
    * @param o object to be pushed
    */
   @Override
   public void push(Object o) {
      super.push(o);
      while (size() > numberToKeep) {
         removeLast();
      }
   }
}
