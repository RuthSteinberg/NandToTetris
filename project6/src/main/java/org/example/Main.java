package org.example;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // נתיב הקובץ
        File file = new File("C:\\Users\\ruths\\Downloads\\add.asm");

        // יצירת אובייקט של Parser
        Parser parser = new Parser(file);

        try {
            // לולאת קריאה של כל ה-instructions
            while (parser.hasMoreLines()) {
                parser.advance();  // Move to the next instruction
                if (parser.instruction != null) {  // אם מצאנו instruction
                    System.out.println("Current instruction: " + parser.instruction);  // הדפסת ה-instruction הנוכחי
                }
            }

        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
