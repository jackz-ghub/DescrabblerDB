
package com.mycompany.mavenproject3;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.nio.file.StandardOpenOption;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DescrabblerDB {
    
    private static int[] values = {
        1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10,
        1, 1, 1, 1, 4, 4, 8, 4, 10
    };
  
    public static void main(String[] args) {
    String wordsPath = 
            "src/main/java/com/mycompany/mavenproject3/descrabblerWordList.txt";

        Scanner input = new Scanner(System.in);
        //input page
        String main = "";
        System.out.println("Welome to Descrabbler!");
        while (!"-".equals(main)) {

        System.out.println("! for input");
        System.out.println("@ to login");
        System.out.println("* for options");
        System.out.println("- to exit");
        
        main = input.next();
        if ("!".equals(main)) {
        
        System.out.println("Enter letters:");
        
        input.nextLine();
        String enteredLetters = input.nextLine().toLowerCase();
        
        List<String> validWords = getWords(wordsPath, enteredLetters);

        if (validWords.isEmpty()) {
            System.out.println("N/A - 0 points");
        }
        
        if (changeOrder == false) {
            validWords.sort((a, b) -> getValue(b) - getValue(a)); 
        }
        else {
            validWords.sort((a, b) -> getValue(a) - getValue(b));
        }
        for (String word : validWords) {
            System.out.println(word + " - " + getValue(word) + " points");
        }
        // end input
        }
        if ("*".equals(main)) {
            if (changeOrder == false) {
                System.out.println("Words are being sorted in descending"
                        + " point value");
            }
            else {
                System.out.println("Words are being sorted in ascending"
                        + " point value");
            }
            System.out.println("Enter DESC for descending sort order or ASC"
                    + " for ascending sort order:");
            String sort = input.next();
            if ("DESC".equals(sort)) {
                changeOrder = false;
            }
            else if ("ASC".equals(sort)) {
                changeOrder = true;
            }
            else {
                System.out.println("Invalid, order unchanged");
            }
            
            System.out.println("Enter starting letter(s), or - for default:");
            firstLetterSelect = input.next();
            if ("-".equals(firstLetterSelect)) {
                firstLetterSelect = "";
            }
            
            System.out.println("Enter ending letter(s), or - for default:");
            lastLetterSelect = input.next();
            if ("-".equals(lastLetterSelect)) {
                lastLetterSelect = "";
            }
            
            System.out.println("Enter minimum word length:");
            minLength = input.nextInt();
            
            System.out.println("Enter maximum word length:");
            maxLength = input.nextInt();
            
            boolean valueChanger = true;
                while (valueChanger == true){
                System.out.println("Change letter values? (Y/N)");
                if ("N".equals(input.next())) {
                    valueChanger = false;
                }
                else {
                    System.out.println("Enter letter:");
                    char letter = input.next().charAt(0);
                    int number = letter - 'a';
                    System.out.println("Enter new value:");
                    values[number]=input.nextInt(); 
                }

                }
        }
        //end options
        }
    }
    
        //options
        public static boolean changeOrder = false; //changes asc/desc sort
        
        
        public static int maxLength = 100;
        
        public static int minLength = 1;
        
        public static String firstLetterSelect = ""; //first letter
        
        public static String lastLetterSelect = ""; //last letter
    
    
    public class Login {
        
        private static boolean isAdmin(){
            return false;
        }
    
        
    }
    
    String wordUpdate = "input placeholder \n";
    
    //word list update demo
    /* try {
    Files.writeString(wordsPath, wordUpdate, StandardOpenOption.APPEND);
            System.out.println(wordUpdate + " has been added to the list");
    }
    catch (IOException e) {
    System.err.println("Could not update list"); } */
    
    private static List<String> getWords(String wordsPath, 
    String enteredLetters) {
        ArrayList<String> result = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader
        (new FileReader(wordsPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                String possibleWord = line.toLowerCase();
                if (workableLetters(possibleWord, enteredLetters)) {
                    if (possibleWord.startsWith(firstLetterSelect) &&
                            possibleWord.endsWith(lastLetterSelect) &&
                            possibleWord.length() < maxLength &&
                            possibleWord.length() > minLength) {
                    result.add(possibleWord); }
                    
                }
            }
        }
        catch (IOException e) {
            System.err.println("Word list could not be accessed");
        }
        return result;
    }
    
    private static boolean workableLetters(String word, String enteredLetters) {
        int[] alphabet = new int[26];
        
        for (char x : enteredLetters.toCharArray()) {
            if (x >= 'a' && x <= 'z') alphabet [x - 'a']++;
        }
        for (char x : word.toCharArray()) {
            if (x >= 'a' && x <= 'z') {
                if (--alphabet[x-'a'] < 0)
                    return false;
            }   
        }
        return true;
    }
    
    private static int getValue(String word) {
        int letterValue = 0;
        
        for (char x: word.toCharArray()) {
            if (x >= 'a' && x <= 'z') {
                letterValue += values [x - 'a'];
            }
        }
        return letterValue;
    }
}
