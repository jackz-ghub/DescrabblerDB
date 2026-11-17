
package com.mycompany.mavenproject3;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;


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
        while (!"-".equals(main)) {
        System.out.println("Welome to Descrabbler!");
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
        
        validWords.sort((a, b) -> getValue(b) - getValue(a));
        
        for (String word : validWords) {
            System.out.println(word + " - " + getValue(word) + " points");
        }
        // end input
        }
        }
    }
    
    public class Options {
        
        private static boolean changeOrder() {
            //changes order of words presented between ascending and descending
            return false;
        }
        
        private static int changeValues() {
            //changes letter values
            return 1;
        }
        
        private static int maxLength() {
            //self explanatory
            return 100;
        }
        
        private static int minLength() {
            //ditto
            return 1;
        }
        
        private static char firstLetter() {
            //only words starting with this will show up
            return 'a';
        }
        
        private static char lastLetter() {
            //only words ending with this will show up
            return 'z';
        }
    }
    
    public class Login {
        
        private static boolean isAdmin(){
            return false;
        }
        
    }
    
    private static List<String> getWords(String wordsPath, 
    String enteredLetters) {
        ArrayList<String> result = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader
        (new FileReader(wordsPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                
                String possibleWord = line.toLowerCase();
                if (workableLetters(possibleWord, enteredLetters)) {
                    result.add(possibleWord);
                    
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
