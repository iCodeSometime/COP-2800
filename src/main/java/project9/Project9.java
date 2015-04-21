/*****************************************************************************
 *
 *  Instructor: Mr. Rich Cacase
 *  Class:      COP 2800 Java
 *  Author:     Kenneth Cochran
 *  Program:    9 (String Utility Class)
 *
 *  This class provides different utility methods for working with strings.
 *  The user will be able to input a string, and the program will
 *  find either the consonants, or vowels, or both, based on the user's choice.
 *****************************************************************************/

package project9;

import javax.swing.JOptionPane;

public class Project9 {
    public static void main(String[] args) {
        UtilString string =
                new UtilString(UtilString.getInput("Input a string"));
        boolean stop = false;
        while (!stop) {
            String choice = UtilString.getInput(
                "a. Count the number of vowels in the string\n"
              + "b. Count the number of consonants in the string\n"
              + "c. Count both the vowels and the consonants in the string\n"
              + "d. Enter another string\n"
              + "e. Exit the program");
            switch (choice.charAt(0)) {
                case 'a':
                    string.displayVowels();
                    break;
                case 'b':
                    string.displayConsonants();
                    break;
                case 'c':
                    string.displayAll();
                    break;
                case 'd':
                    string.newString();
                    break;
                case 'e':
                    stop = true;
                default:
                    JOptionPane.showMessageDialog(null,
                            "\"" + choice + "\" is not a valid option.");
            }
        }
    }
}

class UtilString {
    private String string;
    
    /**
     * This is a wrapper for {@link javax.swing.JOptionPane}
     * It prevents me from having to check for null values every time.
     * @param message 
     */
    public static String getInput(String message) {
        String temp = JOptionPane.showInputDialog(message);
        if (temp == null) {
            System.exit(0);
        }
        return temp;
    }
    /**
     * Gets a string from the user and constructs a new instance using that
     * If you would like to provide your own string, 
     * use {@link #UtilString(java.lang.String)}
     */
    public UtilString() {
        newString();
    }
    /**
     * Constructs a new instance using a given string
     * If you would like a convenience method to request a string from the user,
     * use {@link #UtilString()}
     * 
     * @param string the string to use for {@link string}
     */
    public UtilString(String string) {
        newString(string);
    }
    /**
     * Finds and displays the number of vowels in {@link #string}
     * If you do not wish to display this value, use {@link #countVowels()}
     * 
     * @return the number of vowels
     */
    public int displayVowels() {
        int count = countVowels();
        JOptionPane.showMessageDialog(null, 
                "\"" + string + "\" has " + count + " vowels");
        return count;
    }
    /**
     * Finds and displays the number of consonants in {@link #string}
     * If you do not wish to display this value, use {@link #countConsonants()}
     * 
     * @return the number of consonants
     */
    public int displayConsonants() {
        int count = countConsonants();
        JOptionPane.showMessageDialog(null, 
                "\"" + string + "\" has " + count + " consonants");
        return count;
    }
     /**
     * Finds and displays the number of characters in {@link #string}
     * If you do not wish to display this value, use {@link #countAll()}
     * 
     * @return the number of characters
     */
    public int displayAll() {
        int count = countAll();
        JOptionPane.showMessageDialog(null, 
                "\"" + string + "\" has " + count + " consonants and vowels");
        return count;
    }
    /**
     * Finds the number of vowels in {@link #string}
     * If you would like a convenience method to display this information,
     * use {@link #displayVowels()}
     * 
     * @return the number of vowels
     */
    public int countVowels() {
        int accum = 0;
        for (int i = 0; i < string.length(); i++) {
            char temp = string.charAt(i);
            switch (temp) {
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u':
                    accum++;
            }
        }
        return accum;
    }
    /**
     * Finds the number of consonants in {@link #string}
     * If you would like a convenience method to display this information, 
     * use {@link #displayConsonants()}
     * 
     * @return the number of consonants
     */
    public int countConsonants() {
        int accum = 0;
        for (int i = 0; i < string.length(); i++) {
            char temp = string.charAt(i);
            switch (temp) {
                case 'b':
                case 'c':
                case 'd':
                case 'f':
                case 'g':
                case 'h':
                case 'j':
                case 'k':
                case 'l':
                case 'm':
                case 'n':
                case 'p':
                case 'q':
                case 'r':
                case 's':
                case 't':
                case 'v':
                case 'w':
                case 'x':
                case 'y':
                case 'z':
                    accum++;
            }
        }
        return accum;
    }
    /**
     * Finds the total number of characters in {@link #string}
     * If you would like a convenience method to display this information,
     * use {@link #displayAll()}
     * 
     * @return the number of total characters
     */
    public int countAll() {
        return countVowels() + countConsonants();
    }
    /**
     * Gets a new String from the user, and assigns it to {@link #string}
     * If you would like to provide your own string,
     * use {@link #newString(java.lang.String)}
     */
    public void newString() {
        newString(getInput("Input a string"));
    }
    /**
     * If you would like a convenience method to get this string from the user,
     * use {@link #newString()}
     * 
     * @param string the new string to be used as {@link #string}
     */
    public void newString(String string) {
        this.string = string;
    }
}